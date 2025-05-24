/**
 * @Autor Valentina Gomez
 */

package com.ymy.cl.perfulandiafs.ventas.controller;

import com.ymy.cl.perfulandiafs.ventas.dto.DetalleVentaDTO;
import com.ymy.cl.perfulandiafs.ventas.model.Venta;
import com.ymy.cl.perfulandiafs.ventas.service.VentaService;
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
            return ResponseEntity.status(HttpStatus.CREATED).body(mensaje);  // 201 Created
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear la venta: " + e.getMessage());  // 500 Internal Server Error
        }
    }

    @GetMapping
    public ResponseEntity<List<Venta>> obtenerVentas() {
        List<Venta> ventas = ventaService.obtenerVentas();
        if (ventas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(ventas);  // 204 No Content
        }
        return ResponseEntity.ok(ventas);  // 200 OK
    }

    @GetMapping("/{id}/detalles")
    public ResponseEntity<List<DetalleVentaDTO>> obtenerDetallesDeVenta(@PathVariable Long id) {
        try {
            // Llamamos al servicio para obtener los detalles de la venta como DTOs
            List<DetalleVentaDTO> detallesDTO = ventaService.obtenerDetallesDeVentaID(id);
            if (detallesDTO.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(detallesDTO);  // 204 No Content si no hay detalles
            }
            return ResponseEntity.ok(detallesDTO);  // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // 404 Not Found
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarVenta(@PathVariable Long id) {
        try {
            String mensaje = ventaService.eliminarVenta(id);
            return ResponseEntity.status(HttpStatus.OK).body(mensaje);  // 200 OK
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Venta no encontrada: " + e.getMessage());  // 404 Not Found
        }
    }
}




