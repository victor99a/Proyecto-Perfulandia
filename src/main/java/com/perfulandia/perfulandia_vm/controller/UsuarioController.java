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
    public ResponseEntity<UsuarioResponseDTO> registrarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) {
        // Validación de existencia del usuario, etc.
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setContrasena(usuarioDTO.getContrasena());

        usuarioService.guardarUsuario(usuario);

        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        usuarioResponseDTO.setId(usuario.getId());
        usuarioResponseDTO.setNombre(usuario.getNombre());
        usuarioResponseDTO.setApellido(usuario.getApellido());
        usuarioResponseDTO.setCorreo(usuario.getCorreo());
        usuarioResponseDTO.setTelefono(usuario.getTelefono());

        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioResponseDTO);
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
    public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario != null) {
            UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
            usuarioResponseDTO.setId(usuario.getId());
            usuarioResponseDTO.setNombre(usuario.getNombre());
            usuarioResponseDTO.setApellido(usuario.getApellido());
            usuarioResponseDTO.setCorreo(usuario.getCorreo());
            usuarioResponseDTO.setTelefono(usuario.getTelefono());
            return ResponseEntity.ok(usuarioResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 4) Actualizar usuario (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable Integer id, @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id);
        if (usuarioExistente != null) {
            usuarioExistente.setNombre(usuarioDTO.getNombre());
            usuarioExistente.setApellido(usuarioDTO.getApellido());
            usuarioExistente.setCorreo(usuarioDTO.getCorreo());
            usuarioExistente.setTelefono(usuarioDTO.getTelefono());
            usuarioExistente.setContrasena(usuarioDTO.getContrasena());
            usuarioService.guardarUsuario(usuarioExistente);

            UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
            usuarioResponseDTO.setId(usuarioExistente.getId());
            usuarioResponseDTO.setNombre(usuarioExistente.getNombre());
            usuarioResponseDTO.setApellido(usuarioExistente.getApellido());
            usuarioResponseDTO.setCorreo(usuarioExistente.getCorreo());
            usuarioResponseDTO.setTelefono(usuarioExistente.getTelefono());
            return ResponseEntity.ok(usuarioResponseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // 5) Eliminar usuario (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);
        if (usuario != null) {
            usuarioService.eliminarUsuario(usuario);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

