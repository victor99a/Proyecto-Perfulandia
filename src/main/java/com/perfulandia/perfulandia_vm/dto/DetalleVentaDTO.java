package com.perfulandia.perfulandia_vm.dto;

import com.perfulandia.perfulandia_vm.model.DetalleVenta;
import lombok.Data;

@Data
public class DetalleVentaDTO {
    private Long id;
    private Integer cantidad;
    private double precioUnitario;

    // Constructor que mapea la entidad DetalleVenta a DetalleVentaDTO
    public DetalleVentaDTO(DetalleVenta detalleVenta) {
        this.id = detalleVenta.getId();
        this.cantidad = detalleVenta.getCantidad();
        this.precioUnitario = detalleVenta.getPrecioUnitario();
    }
}
