package org.example.microUsuarios;

import com.usuarios.dto.UsuarioSimpleDTO;
import com.usuarios.repository.UsuarioRepository;
import com.usuarios.service.UsuarioService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class microServicioUsuariosTest {
    @Mock
    private UsuarioRepository usuarioRepositorio;

    @InjectMocks
    private UsuarioService usuarioServicio;

    @Test
    void testGetUsuarioByIdNotFound(){

        Long id = 1000L;
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.empty());

        ResponseEntity<UsuarioSimpleDTO> response = usuarioServicio.getById(id);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(usuarioRepositorio, times(1)).findById(id);
    }
}
