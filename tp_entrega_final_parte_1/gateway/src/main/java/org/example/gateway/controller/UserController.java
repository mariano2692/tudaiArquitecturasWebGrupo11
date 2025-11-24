package org.example.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.gateway.service.UserService;
import org.example.gateway.service.dto.user.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Registrar un User",
            description = "Este endpoint permite crear un nuevo usuario en el sistema, especificando un rol, para luego poder autenticarse y operar la aplicación.",
            operationId = "saveUser",
            tags = {"User", "Auth"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Usuario creado con éxito"),
                    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta")
            }
    )
    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO userDTO) {
        final var id = userService.saveUser( userDTO );
        return new ResponseEntity<>( id, HttpStatus.CREATED );
    }
}
