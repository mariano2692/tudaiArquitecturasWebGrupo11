package org.example.viaje;


import com.viajes.entity.Viaje;
import com.viajes.repository.ViajeRepository;
import com.viajes.service.ViajeService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class microServicioViajeTest {
    @Mock
    private ViajeRepository viajeRepositorio;

    @InjectMocks
    private ViajeService viajeServicio;

    @Test
    void testGetAllViajes() {
        // Datos de prueba
        Viaje viaje1 = new Viaje();
        viaje1.setIdCuenta(2L);
        viaje1.setIdMonopatin(3L);
        viaje1.setValorTotal(100.0);
        viaje1.setFechaHoraInicio(LocalDateTime.of(2025, 10, 3, 10, 15, 30));
        viaje1.setFechaHoraFin(LocalDateTime.of(2025, 10, 3, 10, 20, 45));
        viaje1.setKmRecorridos(10L);

        Viaje viaje2 = new Viaje();
        viaje1.setIdCuenta(5L);
        viaje1.setIdMonopatin(1L);
        viaje1.setValorTotal(80.0);
        viaje1.setFechaHoraInicio(LocalDateTime.of(2025, 11, 6, 15, 1, 6));
        viaje1.setFechaHoraFin(LocalDateTime.of(2025, 11, 6, 15, 8, 45));
        viaje1.setKmRecorridos(3L);

        List<Viaje> mockViajes = List.of(viaje1, viaje2);

        // Configurar el mock
        when(viajeRepositorio.findAll()).thenReturn(mockViajes);

        // Llamar al método
        List<Viaje> result = viajeServicio.getAll();

        // Verificaciones
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(mockViajes, result);
        verify(viajeRepositorio, times(1)).findAll();
    }
}