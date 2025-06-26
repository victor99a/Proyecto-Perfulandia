package com.perfulandia.perfulandia_vm.controller;

import com.perfulandia.perfulandia_vm.assemblers.InventarioModelAssembler;
import com.perfulandia.perfulandia_vm.model.Inventario;
import com.perfulandia.perfulandia_vm.service.InventarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/inventario-v2")
public class InventarioControllerV2 {

    @Autowired
    private InventarioService inventarioService;

    @Autowired
    private InventarioModelAssembler assembler;

    // GET: obtener uno
    @GetMapping("/{id}")
    public ResponseEntity<?> uno(@PathVariable("id") Long id) {
        Inventario inventario = inventarioService.buscarPorId(id);
        return ResponseEntity.ok(assembler.toModel(inventario));
    }

    // GET: obtener todos
    @GetMapping
    public CollectionModel<EntityModel<Inventario>> todos() {
        List<EntityModel<Inventario>> lista = inventarioService
                .obtenerTodo()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(lista,
                linkTo(methodOn(InventarioControllerV2.class).todos()).withSelfRel());
    }

    // POST: crear nuevo
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Inventario nuevo) {
        Inventario creado = inventarioService.guardar(nuevo);
        EntityModel<Inventario> recurso = assembler.toModel(creado);
        return ResponseEntity
                .created(linkTo(methodOn(InventarioControllerV2.class).uno(creado.getId())).toUri())
                .body(recurso);
    }

    // PUT: actualizar existente
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable("id") Long id, @RequestBody Inventario inventario) {
        Inventario actualizado = inventarioService.actualizar(id, inventario);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    // DELETE: eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable ("id") Long id) {
        String mensaje = inventarioService.eliminar(id);
        return ResponseEntity.ok(mensaje);
    }
}
