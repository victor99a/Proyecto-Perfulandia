package com.perfulandia.perfulandia_vm.Service;

import com.perfulandia.perfulandia_vm.ProyectoFullstackApplication;
import com.perfulandia.perfulandia_vm.controller.UsuarioController;
import com.perfulandia.perfulandia_vm.dto.UsuarioRequestDTO;
import com.perfulandia.perfulandia_vm.dto.UsuarioResponseDTO;
import com.perfulandia.perfulandia_vm.model.Usuario;
import com.perfulandia.perfulandia_vm.repository.UsuarioRepository;
import com.perfulandia.perfulandia_vm.service.IUsuarioService;
import com.perfulandia.perfulandia_vm.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = ProyectoFullstackApplication.class)
public class TestServiceUsuario {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UsuarioService usuarioService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Crear un objeto de Usuario para usar en las pruebas
        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan Pérez");
        usuario.setCorreo("juanperez@example.com");
    }

    @Test
    void testListarUsuarios() {
        // Simular que el repositorio retorna una lista de usuarios
        when(usuarioRepository.findAll()).thenReturn(List.of(usuario));

        // Llamar al método del servicio
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        // Verificar que el repositorio fue llamado y el resultado es el esperado
        verify(usuarioRepository, times(1)).findAll();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
        assertEquals("Juan Pérez", usuarios.get(0).getNombre());
    }

    @Test
    void testBuscarUsuarioPorId() {
        // Simular que el repositorio retorna un usuario al buscar por ID
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuario));

        // Llamar al método del servicio
        Usuario foundUsuario = usuarioService.buscarUsuarioPorId(1);

        // Verificar que el repositorio fue llamado y el resultado es el esperado
        verify(usuarioRepository, times(1)).findById(1);
        assertNotNull(foundUsuario);
        assertEquals("Juan Pérez", foundUsuario.getNombre());
    }

    @Test
    void testBuscarUsuarioPorId_NotFound() {
        // Simular que el repositorio no encuentra el usuario con el ID dado
        when(usuarioRepository.findById(1)).thenReturn(Optional.empty());

        // Llamar al método del servicio
        Usuario foundUsuario = usuarioService.buscarUsuarioPorId(1);

        // Verificar que el repositorio fue llamado y el resultado es null
        verify(usuarioRepository, times(1)).findById(1);
        assertNull(foundUsuario);
    }

    @Test
    void testGuardarUsuario() {
        // Simular que el repositorio guarda un usuario
        when(usuarioRepository.save(usuario)).thenReturn(usuario);

        // Llamar al método del servicio
        usuarioService.guardarUsuario(usuario);

        // Verificar que el repositorio fue llamado para guardar el usuario
        verify(usuarioRepository, times(1)).save(usuario);
    }

    @Test
    void testEliminarUsuario() {
        // Simular que el repositorio elimina un usuario
        doNothing().when(usuarioRepository).delete(usuario);

        // Llamar al método del servicio
        usuarioService.eliminarUsuario(usuario);

        // Verificar que el repositorio fue llamado para eliminar el usuario
        verify(usuarioRepository, times(1)).delete(usuario);
    }

}
