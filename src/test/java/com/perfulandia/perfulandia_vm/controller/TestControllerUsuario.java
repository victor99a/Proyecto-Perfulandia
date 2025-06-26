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
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.obtenerUsuarioPorId(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof UsuarioResponseDTO);

        UsuarioResponseDTO dto = (UsuarioResponseDTO) response.getBody();
        assertEquals("Juan", dto.getNombre());

        // Simulamos usuario no existente
        when(usuarioService.buscarUsuarioPorId(99)).thenReturn(null);

        response = usuarioController.obtenerUsuarioPorId(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario no encontrado.", response.getBody());
    }

    @Test
    void testActualizarUsuario() {
        UsuarioRequestDTO updateDTO = new UsuarioRequestDTO();
        updateDTO.setNombre("Juanito");
        updateDTO.setApellido("Pérez");
        updateDTO.setCorreo("juanito.perez@example.com");
        updateDTO.setTelefono(987654321);
        updateDTO.setContrasena("newpassword");

        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.actualizarUsuario(1, updateDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof UsuarioResponseDTO);

        UsuarioResponseDTO dto = (UsuarioResponseDTO) response.getBody();
        assertEquals("Juanito", dto.getNombre());

        verify(usuarioService, times(1)).guardarUsuario(any(Usuario.class));
    }

    @Test
    void testEliminarUsuario() {
        when(usuarioService.buscarUsuarioPorId(1)).thenReturn(usuario);

        ResponseEntity<?> response = usuarioController.eliminarUsuario(1);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Usuario con ID 1 eliminado exitosamente.", response.getBody());

        verify(usuarioService, times(1)).eliminarUsuario(any(Usuario.class));

        when(usuarioService.buscarUsuarioPorId(99)).thenReturn(null);

        response = usuarioController.eliminarUsuario(99);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Usuario con ID 99 no encontrado.", response.getBody());
    }



}
