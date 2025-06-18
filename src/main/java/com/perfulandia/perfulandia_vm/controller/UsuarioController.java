package com.perfulandia.perfulandia_vm.controller;


import com.perfulandia.perfulandia_vm.dto.UsuarioRequestDTO;
import com.perfulandia.perfulandia_vm.dto.UsuarioResponseDTO;
import com.perfulandia.perfulandia_vm.model.Usuario;
import com.perfulandia.perfulandia_vm.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    // 1) Registrar nuevo usuario (POST)
    @PostMapping
    public ResponseEntity<String> registrarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) {
        // Crear un nuevo usuario
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setContrasena(usuarioDTO.getContrasena());

        // Guardar el nuevo usuario
        usuarioService.guardarUsuario(usuario);

        // Crear el DTO para la respuesta
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNombre(usuario.getNombre());
        usuarioResponseDTO.setApellido(usuario.getApellido());
        usuarioResponseDTO.setCorreo(usuario.getCorreo());
        usuarioResponseDTO.setTelefono(usuario.getTelefono());

        // Devolver respuesta con el mensaje "Usuario creado con éxito" y el usuario creado
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario creado con éxito: " + usuarioResponseDTO);
    }


    // 2) Listar usuarios (GET)
    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();
        List<UsuarioResponseDTO> usuariosResponseDTO = usuarios.stream()
                .map(usuario -> {
                    UsuarioResponseDTO dto = new UsuarioResponseDTO();
                    dto.setId(usuario.getId());
                    dto.setNombre(usuario.getNombre());
                    dto.setApellido(usuario.getApellido());
                    dto.setCorreo(usuario.getCorreo());
                    dto.setTelefono(usuario.getTelefono());
                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(usuariosResponseDTO);
    }

    // 3) Obtener un usuario por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<String> obtenerUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario != null) {
            // Crear el DTO para la respuesta
            UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
            usuarioResponseDTO.setId(usuario.getId());
            usuarioResponseDTO.setNombre(usuario.getNombre());
            usuarioResponseDTO.setApellido(usuario.getApellido());
            usuarioResponseDTO.setCorreo(usuario.getCorreo());
            usuarioResponseDTO.setTelefono(usuario.getTelefono());

            // Retornar usuario encontrado con la respuesta del DTO
            return ResponseEntity.ok("Usuario con ID " + id + " encontrado: " + usuarioResponseDTO);
        } else {
            // Si el usuario no es encontrado, devolver un mensaje personalizado
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + " no encontrado.");
        }
    }


    // 4) Actualizar usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id);

        if (usuarioExistente == null) {
            // Si no se encuentra el usuario, se retorna un 404 con un mensaje
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + " no encontrado.");
        }

        // Actualización de los datos
        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setCorreo(usuarioDTO.getCorreo());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setContrasena(usuarioDTO.getContrasena());

        // Guardar el usuario actualizado
        usuarioService.guardarUsuario(usuarioExistente);

        // Mensaje de éxito con el usuario actualizado
        return ResponseEntity.ok("Usuario con ID " + id + " actualizado con éxito.");
    }




    //eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario != null) {
            // Eliminar el usuario
            usuarioService.eliminarUsuario(usuario);

            // Mensaje de éxito
            return ResponseEntity.ok("Usuario con ID " + id + " fue eliminado exitosamente.");
        } else {
            // Si no se encuentra el usuario
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario con ID " + id + " no encontrado.");
        }
    }
}

