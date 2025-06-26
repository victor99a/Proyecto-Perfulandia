package com.perfulandia.perfulandia_vm.controller;


import com.perfulandia.perfulandia_vm.dto.UsuarioRequestDTO;
import com.perfulandia.perfulandia_vm.dto.UsuarioResponseDTO;
import com.perfulandia.perfulandia_vm.model.Usuario;
import com.perfulandia.perfulandia_vm.service.IUsuarioService;
import io.swagger.v3.oas.annotations.Parameter;
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
    public ResponseEntity<?> obtenerUsuarioPorId(
            @Parameter(description = "ID del usuario a buscar", example = "8", required = true)
            @PathVariable("id") Integer id) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            UsuarioResponseDTO dto = new UsuarioResponseDTO();
            dto.setId(usuario.getId());
            dto.setNombre(usuario.getNombre());
            dto.setApellido(usuario.getApellido());
            dto.setCorreo(usuario.getCorreo());
            dto.setTelefono(usuario.getTelefono());

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno: " + e.getMessage());
        }
    }





    // 4) Actualizar usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(
            @Parameter(description = "ID del usuario a actualizar", example = "8", required = true)
            @PathVariable("id") Integer id,
            @RequestBody UsuarioRequestDTO usuarioDTO) {
        try {
            Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id);

            if (usuarioExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
            }

            usuarioExistente.setNombre(usuarioDTO.getNombre());
            usuarioExistente.setApellido(usuarioDTO.getApellido());
            usuarioExistente.setCorreo(usuarioDTO.getCorreo());
            usuarioExistente.setTelefono(usuarioDTO.getTelefono());
            usuarioExistente.setContrasena(usuarioDTO.getContrasena());

            usuarioService.guardarUsuario(usuarioExistente);

            UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
            responseDTO.setId(usuarioExistente.getId());
            responseDTO.setNombre(usuarioExistente.getNombre());
            responseDTO.setApellido(usuarioExistente.getApellido());
            responseDTO.setCorreo(usuarioExistente.getCorreo());
            responseDTO.setTelefono(usuarioExistente.getTelefono());

            return ResponseEntity.ok(responseDTO);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno al actualizar el usuario: " + e.getMessage());
        }
    }






    //eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(
            @Parameter(description = "ID del usuario a eliminar", example = "8", required = true)
            @PathVariable("id") Integer id) {
        try {
            Usuario usuario = usuarioService.buscarUsuarioPorId(id);

            if (usuario == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario con ID " + id + " no encontrado.");
            }

            usuarioService.eliminarUsuario(usuario);

            return ResponseEntity.ok("Usuario con ID " + id + " eliminado exitosamente.");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error interno al eliminar el usuario: " + e.getMessage());
        }
    }



}

