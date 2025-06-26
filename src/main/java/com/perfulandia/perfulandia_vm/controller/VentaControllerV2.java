package com.perfulandia.perfulandia_vm.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import java.util.List;
import java.util.stream.Collectors;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import com.perfulandia.perfulandia_vm.assemblers.VentaModelAssembler;
import com.perfulandia.perfulandia_vm.model.Venta;
import com.perfulandia.perfulandia_vm.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2/ventas")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaModelAssembler assembler;

    @PostMapping
    public ResponseEntity<EntityModel<Venta>> crearVenta(@RequestBody Venta venta) {
        Venta creada = ventaService.crearVentaEntidad(venta);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(assembler.toModel(creada));
    }

    @GetMapping
    public CollectionModel<EntityModel<Venta>> obtenerTodas() {
        List<EntityModel<Venta>> ventas = ventaService.obtenerVentas()
                .stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(ventas,
                linkTo(methodOn(VentaControllerV2.class).obtenerTodas()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<Venta> obtenerVentaPorId(@PathVariable("id") Long id) {
        Venta venta = ventaService.obtenerVentaPorId(id);
        return assembler.toModel(venta);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarVenta(@PathVariable("id") Long id) {
        ventaService.eliminarVenta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Venta>> actualizarEstado(@PathVariable("id") Long id, @RequestBody String nuevoEstado) {
        ventaService.actualizarEstado(id, nuevoEstado);
        Venta actualizada = ventaService.obtenerVentaPorId(id);
        return ResponseEntity.ok(assembler.toModel(actualizada));
    }

}



