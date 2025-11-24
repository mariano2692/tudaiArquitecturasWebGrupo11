package org.example.microMonopatin;


import com.monopatines.DTO.MonopatinDTO;
import com.monopatines.Repository.MonopatinRepository;
import com.monopatines.Service.MonopatinService;
import com.monopatines.entities.Monopatin;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class microServicioMonopatinTest {
    @Mock
    private MonopatinRepository monopatinRepositorio;

    @InjectMocks
    private MonopatinService monopatinServicio;

    @Test
    void testGetMonopatinByIdNotFound(){
        int id = 2;

        when(monopatinRepositorio.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<MonopatinDTO> response = monopatinServicio.getById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(monopatinRepositorio, times(1)).findById(id);
    }

    @Test
    void testGetAllMonopatines() {
        // Arrange
        Monopatin monopatin1 = new Monopatin();
        monopatin1.setId(1);
        monopatin1.setEstado("disponible");

        Monopatin monopatin2 = new Monopatin();
        monopatin2.setId(2);
        monopatin2.setEstado("en uso");

        Mockito.when(monopatinRepositorio.findAll()).thenReturn(Arrays.asList(monopatin1, monopatin2));

        // Act
        List<MonopatinDTO> result = monopatinServicio.getAllMonopatines();

        // Assert
        assertEquals(2, result.size());
        assertEquals("disponible", result.get(0).getEstado());
        Mockito.verify(monopatinRepositorio, times(1)).findAll();
    }
}
