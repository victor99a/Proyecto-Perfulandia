package com.perfulandia.perfulandia_vm.controller;

import com.perfulandia.perfulandia_vm.model.Inventario;
import com.perfulandia.perfulandia_vm.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

/**
 * @Autor Consuelo
 */

@RestController
@RequestMapping("/api/inventario")
@Tag(name = "Inventario", description = "Operaciones Relaciondas con productos del inventario")
public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    @Operation(summary = "Obtener todos los productos", description = "Devuelve una lista de todos los productos del inventario")
    public List<Inventario> listarInventario(){
        return inventarioService.obtenerTodo();
    }

    @PostMapping
    @Operation(summary = "Crear producto", description = "Crea un nuevo producto en el inventario")
    public Inventario crearInventario(@RequestBody Inventario inventario){
        return inventarioService.guardar(inventario);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar producto", description = "Elimina un producto por ID del inventario")
    public ResponseEntity<String> eliminarProducto(@PathVariable Long id) {
        String mensaje = inventarioService.eliminar(id);

        if (mensaje.contains("no encontrado")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(mensaje);
        }
        return ResponseEntity.ok(mensaje);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente en el inventario")
    public ResponseEntity<?> actualizarProducto(@PathVariable Long id, @RequestBody Inventario inventario) {
        try {
            Inventario actualizado = inventarioService.actualizar(id, inventario);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}