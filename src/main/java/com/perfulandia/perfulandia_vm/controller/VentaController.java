/**
 * @Autor Valentina Gomez
 */
package com.perfulandia.perfulandia_vm.controller;


import com.perfulandia.perfulandia_vm.dto.DetalleVentaDTO;
import com.perfulandia.perfulandia_vm.model.Venta;
import com.perfulandia.perfulandia_vm.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping
    public ResponseEntity<String> crearVenta(@RequestBody Venta venta) {
        try {
            String mensaje = ventaService.crearVenta(venta);
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la venta: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerVentas() {
        List<Venta> ventas = ventaService.obtenerVentas();
        if (ventas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ventas);
        }
        return ResponseEntity.ok(ventas);
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<DetalleVentaDTO>> obtenerDetallesDeVenta(@PathVariable Long id) {
        try {

            List<DetalleVentaDTO> detallesDTO = ventaService.obtenerDetallesDeVentaID(id);
            if (detallesDTO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(detallesDTO);
            }
            return ResponseEntity.ok(detallesDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long id) {
        try {
            String mensaje = ventaService.eliminarVenta(id);
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada: " + e.getMessage());
        }
    }
}




