package com.perfulandia.perfulandia_vm.assemblers;

import com.perfulandia.perfulandia_vm.controller.InventarioControllerV2;
import com.perfulandia.perfulandia_vm.model.Inventario;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class InventarioModelAssembler implements RepresentationModelAssembler<Inventario, EntityModel<Inventario>> {

    @Override
    public EntityModel<Inventario> toModel(Inventario inventario) {
        return EntityModel.of(inventario,
                // Enlace a GET /api/inventario-v2/{id}
                linkTo(methodOn(InventarioControllerV2.class).uno(inventario.getId())).withSelfRel(),
                // Enlace a GET /api/inventario-v2
                linkTo(methodOn(InventarioControllerV2.class).todos()).withRel("todos"),
                // Enlace a PUT /api/inventario-v2/{id}
                linkTo(methodOn(InventarioControllerV2.class).actualizar(inventario.getId(), inventario)).withRel("actualizar"),
                // Enlace a DELETE /api/inventario-v2/{id}
                linkTo(methodOn(InventarioControllerV2.class).eliminar(inventario.getId())).withRel("eliminar")
        );
    }
}
