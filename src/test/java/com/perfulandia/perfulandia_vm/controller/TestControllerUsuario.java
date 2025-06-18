package com.perfulandia.perfulandia_vm.controller;

import com.perfulandia.perfulandia_vm.ProyectoFullstackApplication;
import com.perfulandia.perfulandia_vm.dto.UsuarioRequestDTO;
import com.perfulandia.perfulandia_vm.dto.UsuarioResponseDTO;
import com.perfulandia.perfulandia_vm.model.Usuario;
import com.perfulandia.perfulandia_vm.service.IUsuarioService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ProyectoFullstackApplication.class)
@ActiveProfiles("test")
public class TestControllerUsuario {

    @Mock
    private IUsuarioService usuarioService;

    @InjectMocks
    private UsuarioController usuarioController;

    private UsuarioRequestDTO usuarioRequestDTO;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);

        // Datos de prueba para el DTO y el modelo de Usuario
        usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juan");
        usuarioRequestDTO.setApellido("Perez");
        usuarioRequestDTO.setCorreo("juan.perez@example.com");
        usuarioRequestDTO.setTelefono(123456789);
        usuarioRequestDTO.setContrasena("password");

        usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        usuario.setApellido("Perez");
        usuario.setCorreo("juan.perez@example.com");
        usuario.setTelefono(123456789);
        usuario.setContrasena("password");
    }

    @Test
    void testRegistrarUsuario() {
        // Simula el comportamiento del servicio para métodos void
        doNothing().when(usuarioService).guardarUsuario(any(Usuario.class));

        // Llamar al método del controlador
        ResponseEntity<String> response = usuarioController.registrarUsuario(usuarioRequestDTO);

        // Verificar el comportamiento esperado
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario creado con éxito"));

        // Verificar que el método guardarUsuario del servicio fue llamado una vez
        verify(usuarioService, times(1)).guardarUsuario(any(Usuario.class));
    }

    @Test
    void testRegistrarUsuario_Excepcion() {
        // Simulamos que el servicio lanza una excepción (por ejemplo, ya existe el usuario)
        doThrow(new RuntimeException("El correo ya está registrado")).when(usuarioService).guardarUsuario(any(Usuario.class));

        // Llamar al método del controlador y capturar la excepción
        Exception exception = assertThrows(RuntimeException.class, () -> {
            usuarioController.registrarUsuario(usuarioRequestDTO);
        });

        // Verificar el mensaje de la excepción
        assertEquals("El correo ya está registrado", exception.getMessage());
    }

    @Test
    void testListarUsuarios() {
        // Simulamos que el servicio devuelve una lista de usuarios
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario);
        when(usuarioService.listarUsuarios()).thenReturn(usuarios);

        // Llamar al método del controlador
        ResponseEntity<List<UsuarioResponseDTO>> response = usuarioController.listarUsuarios();

        // Verificar que el código de estado sea 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Juan", response.getBody().get(0).getNombre());
    }

    @Test
    void testObtenerUsuarioPorId() {
        // Simulamos que el servicio devuelve un usuario con un ID válido
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        // Llamar al método del controlador
        ResponseEntity<String> response = usuarioController.obtenerUsuarioPorId(1);

        // Verificar que el código de estado sea 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario con ID 1 encontrado"));

        // Simulamos que el servicio no devuelve un usuario con un ID inválido
        when(usuarioService.buscarUsuarioPorId(99)).thenReturn(null);

        // Llamar al método del controlador con un ID no existente
        response = usuarioController.obtenerUsuarioPorId(99);

        // Verificar que el código de estado sea 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario con ID 99 no encontrado"));
    }
    @Test
    void testActualizarUsuario() {
        // Datos de prueba para el DTO de actualización
        UsuarioRequestDTO usuarioRequestDTO = new UsuarioRequestDTO();
        usuarioRequestDTO.setNombre("Juanito");
        usuarioRequestDTO.setApellido("Pérez");
        usuarioRequestDTO.setCorreo("juanito.perez@example.com");
        usuarioRequestDTO.setTelefono(987654321);
        usuarioRequestDTO.setContrasena("newpassword");

        // Simulamos que el servicio busca un usuario existente
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        // Llamar al método del controlador
        ResponseEntity<String> response = usuarioController.actualizarUsuario(1, usuarioRequestDTO);

        // Verificar que el código de estado sea 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario con ID 1 actualizado con éxito"));

        // Verificar que el método guardarUsuario sea llamado
        verify(usuarioService, times(1)).guardarUsuario(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario() {
        // Simulamos que el servicio devuelve un usuario existente
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        // Llamar al método del controlador
        ResponseEntity<String> response = usuarioController.eliminarUsuario(1);

        // Verificar que el código de estado sea 200 OK
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario con ID 1 fue eliminado exitosamente"));

        // Verificar que el método eliminarUsuario sea llamado
        verify(usuarioService, times(1)).eliminarUsuario(any(Usuario.class));

        // Simulamos que el servicio no encuentra un usuario con el ID
        when(usuarioService.buscarUsuarioPorId(99)).thenReturn(null);

        // Llamar al método del controlador con un ID no existente
        response = usuarioController.eliminarUsuario(99);

        // Verificar que el código de estado sea 404 Not Found
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario con ID 99 no encontrado"));
    }


}
