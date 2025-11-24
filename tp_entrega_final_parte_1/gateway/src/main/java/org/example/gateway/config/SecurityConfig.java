package org.example.gateway.config;


import org.example.gateway.security.AuthorityConstant;
import org.example.gateway.security.jwt.JwtFilter;
import org.example.gateway.security.jwt.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private final TokenProvider tokenProvider;

    public SecurityConfig(TokenProvider tokenProvider){
        this.tokenProvider = tokenProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable);
        http
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http
                //.securityMatcher("/api/**")
                .authorizeHttpRequests(authz -> authz
                        // === AUTENTICACIÓN (públicos) ===
                        .requestMatchers(HttpMethod.POST, "/api/authenticate").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/users").permitAll()

                        // === SWAGGER (públicos) ===
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .requestMatchers("/swagger-ui.html").permitAll()
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/webjars/**").permitAll()

                        // === MONOPATINES ===
                        // g. Monopatines cercanos (USUARIO)
                        .requestMatchers(HttpMethod.GET, "/api/monopatines/cercanos").hasAuthority(AuthorityConstant._USUARIO)
                        // Poner/quitar mantenimiento (MANTENIMIENTO)
                        .requestMatchers(HttpMethod.PUT, "/api/monopatines/ponerEnMantenimiento/**").hasAuthority(AuthorityConstant._MANTENIMIENTO)
                        .requestMatchers(HttpMethod.PUT, "/api/monopatines/quitarDeMantenimiento/**").hasAuthority(AuthorityConstant._MANTENIMIENTO)
                        // a. Reporte uso por km (ADMIN)
                        .requestMatchers(HttpMethod.GET, "/api/monopatines/reporte-uso").hasAuthority(AuthorityConstant._ADMIN)
                        // Resto de monopatines (ADMIN)
                        .requestMatchers("/api/monopatines/**").hasAuthority(AuthorityConstant._ADMIN)

                        // === CUENTAS ===
                        // b. Anular/activar cuentas (ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/cuentas/*/anular").hasAuthority(AuthorityConstant._ADMIN)
                        .requestMatchers(HttpMethod.PUT, "/api/cuentas/*/activar").hasAuthority(AuthorityConstant._ADMIN)
                        // Cargar saldo (USUARIO)
                        .requestMatchers(HttpMethod.PUT, "/api/cuentas/*/cargar-saldo").hasAuthority(AuthorityConstant._USUARIO)
                        // Resto de cuentas (ADMIN)
                        .requestMatchers("/api/cuentas/**").hasAuthority(AuthorityConstant._ADMIN)

                        // === USUARIOS ===
                        .requestMatchers("/api/usuarios/**").hasAuthority(AuthorityConstant._ADMIN)

                        // === VIAJES ===
                        // Iniciar viaje, registrar pausa, finalizar (USUARIO)
                        .requestMatchers(HttpMethod.POST, "/api/viajes").hasAuthority(AuthorityConstant._USUARIO)
                        .requestMatchers(HttpMethod.POST, "/api/viajes/iniciar").hasAuthority(AuthorityConstant._USUARIO)
                        .requestMatchers(HttpMethod.POST, "/api/viajes/registrarPausa").hasAuthority(AuthorityConstant._USUARIO)
                        .requestMatchers(HttpMethod.PUT, "/api/viajes/finalizar").hasAuthority(AuthorityConstant._USUARIO)
                        .requestMatchers(HttpMethod.PUT, "/api/viajes/asociarCuenta").hasAuthority(AuthorityConstant._USUARIO)
                        // h. Uso de monopatines por cuenta (USUARIO)
                        .requestMatchers(HttpMethod.GET, "/api/viajes/uso").hasAuthority(AuthorityConstant._USUARIO)
                        // c. Monopatines con X viajes en año (ADMIN)
                        // d. Total facturado (ADMIN)
                        // e. Usuarios más activos (ADMIN)
                        .requestMatchers("/api/viajes/**").hasAuthority(AuthorityConstant._ADMIN)

                        // === TARIFAS ===
                        // f. Ajuste de precios (ADMIN)
                        .requestMatchers("/api/tarifas/**").hasAuthority(AuthorityConstant._ADMIN)

                        // === CHAT (solo PREMIUM) ===
                        .requestMatchers(HttpMethod.GET, "/api/chat/health").permitAll()
                        .requestMatchers("/api/chat/**").hasAuthority(AuthorityConstant._PREMIUM)

                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .addFilterBefore(new JwtFilter(this.tokenProvider), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
