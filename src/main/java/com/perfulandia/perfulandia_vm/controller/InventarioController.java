package com.perfulandia.perfulandia_vm.controller;

import com.perfulandia.perfulandia_vm.model.Inventario;
import com.perfulandia.perfulandia_vm.service.InventarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Autor Consuelo
 */

@RestController
@RequestMapping("/api/inventario")

public class InventarioController {
    @Autowired
    private InventarioService inventarioService;

    @GetMapping
    public List<Inventario> listarInventario(){
        return inventarioService.obtenerTodo();
    }

    @PostMapping
    public Inventario crearInventario(@RequestBody Inventario inventario){
        return inventarioService.guardar(inventario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
    eliminar(@PathVariable("id")Long id){
        try{
            inventarioService.eliminar(id);
        return
         ResponseEntity.status(HttpStatus.OK).body("Producto eliminado con exito");
        } catch (RuntimeException e){
            return
          ResponseEntity.status(HttpStatus.NOT_FOUND).body("Producto no encontrado:"+ e.getMessage());
        }
    }

}