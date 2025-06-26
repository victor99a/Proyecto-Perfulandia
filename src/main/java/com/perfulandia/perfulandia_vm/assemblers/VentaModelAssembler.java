package com.perfulandia.perfulandia_vm.assemblers;

import com.perfulandia.perfulandia_vm.controller.VentaControllerV2;
import com.perfulandia.perfulandia_vm.model.Venta;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {

    @Override
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta,
                linkTo(methodOn(VentaControllerV2.class).obtenerVentaPorId(venta.getId())).withSelfRel(),
                linkTo(methodOn(VentaControllerV2.class).obtenerTodas()).withRel("ventas"),
                linkTo(methodOn(VentaControllerV2.class).eliminarVenta(venta.getId())).withRel("eliminar"),
                linkTo(methodOn(VentaControllerV2.class).actualizarEstado(venta.getId(), "nuevoEstado")).withRel("actualizar-estado")
        );
    }


}

