package com.perfulandia.perfulandia_vm.controller;

import com.perfulandia.perfulandia_vm.assemblers.UsuarioModelAssembler;
import com.perfulandia.perfulandia_vm.dto.UsuarioRequestDTO;
import com.perfulandia.perfulandia_vm.model.Usuario;
import com.perfulandia.perfulandia_vm.service.IUsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/usuarios")
public class UsuarioControllerV2 {

    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private UsuarioModelAssembler assembler;

    // POST: Crear nuevo usuario
    @PostMapping
    public ResponseEntity<EntityModel<?>> registrarUsuario(@RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuario = new Usuario();
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setCorreo(usuarioDTO.getCorreo());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setContrasena(usuarioDTO.getContrasena());

        usuarioService.guardarUsuario(usuario);

        return ResponseEntity.status(HttpStatus.CREATED).body(assembler.toModel(usuario));
    }

    // GET: Listar todos los usuarios
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<?>>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.listarUsuarios();

        List<EntityModel<?>> usuariosModel = usuarios.stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(usuariosModel,
                        linkTo(methodOn(UsuarioControllerV2.class).listarUsuarios()).withSelfRel())
        );
    }

    // GET: Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<?>> obtenerUsuarioPorId(@PathVariable("id") Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(assembler.toModel(usuario));
    }

    // PUT: Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarUsuario(@PathVariable("id") Integer id, @RequestBody UsuarioRequestDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioService.buscarUsuarioPorId(id);

        if (usuarioExistente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        usuarioExistente.setNombre(usuarioDTO.getNombre());
        usuarioExistente.setApellido(usuarioDTO.getApellido());
        usuarioExistente.setCorreo(usuarioDTO.getCorreo());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setContrasena(usuarioDTO.getContrasena());

        usuarioService.guardarUsuario(usuarioExistente);

        return ResponseEntity.ok(assembler.toModel(usuarioExistente));
    }

    // DELETE: Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable("id") Integer id) {
        Usuario usuario = usuarioService.buscarUsuarioPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        usuarioService.eliminarUsuario(usuario);

        return ResponseEntity.noContent().build();
    }
}
