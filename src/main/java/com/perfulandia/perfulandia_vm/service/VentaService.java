package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.dto.DetalleVentaDTO;
import com.perfulandia.perfulandia_vm.model.Venta;
import com.perfulandia.perfulandia_vm.model.DetalleVenta;
import com.perfulandia.perfulandia_vm.repository.InventarioRepository;
import com.perfulandia.perfulandia_vm.repository.VentaRepository;
import com.perfulandia.perfulandia_vm.repository.DetalleVentaRepository;
import com.perfulandia.perfulandia_vm.model.Inventario;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private DetalleVentaRepository detalleVentaRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    public String crearVenta(Venta venta) {
        try {
            // Guardamos la venta
            Venta nuevaVenta = ventaRepository.save(venta);

            // Guardamos los detalles de la venta
            for (DetalleVenta detalle : venta.getDetalleVentas()) {
                detalle.setVenta(nuevaVenta);  // Asociamos el detalle a la venta

                // Guardamos el detalle
                detalleVentaRepository.save(detalle);

                // Actualizamos el stock
                actualizarStock(detalle.getInventario().getId(), detalle.getCantidad());
            }

            return "Venta registrada con éxito";
        } catch (Exception e) {
            // Registra el error y lanza una excepción controlada
            e.printStackTrace();
            throw new RuntimeException("Error al registrar la venta: " + e.getMessage());
        }
    }


    public List<Venta> obtenerVentas() {
        return ventaRepository.findAll();
    }

    public List<DetalleVentaDTO> obtenerDetallesDeVentaID(Long ventaId) {
        // Obtener los detalles de la venta desde el repositorio
        List<DetalleVenta> detalles = detalleVentaRepository.findByVentaId(ventaId);

        // Imprimir detalles para verificar que la entidad está siendo recuperada correctamente
        detalles.forEach(detalle -> System.out.println(detalle.getId())); // Agrega un log de depuración

        // Convertir las entidades DetalleVenta a DTO
        return detalles.stream()
                .map(DetalleVentaDTO::new)
                .collect(Collectors.toList());  // Recoger los DTOs en una lista
    }


    public String eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada");
        }
        ventaRepository.deleteById(id);
        return "Venta anulada con éxito";
    }

    // Actualizar el stock de un perfume después de la venta
    private void actualizarStock(Integer inventarioId, int cantidadVendida) {
        Inventario inventario = inventarioRepository.findById(inventarioId)
                .orElseThrow(() -> new RuntimeException("Perfume no encontrado"));
        int nuevoStock = inventario.getStock() - cantidadVendida;

        if (nuevoStock < 0) {
            throw new RuntimeException("No hay suficiente stock para el perfume: " + inventario.getNombre());
        }

        inventario.setStock(nuevoStock);
        inventarioRepository.save(inventario);  // Guardamos el perfume actualizado
    }

}


