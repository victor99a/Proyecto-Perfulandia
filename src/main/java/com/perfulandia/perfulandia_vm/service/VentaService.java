package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Venta;
import com.perfulandia.perfulandia_vm.repository.VentaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    public String crearVenta(Venta venta) {
        try {
            ventaRepository.save(venta);
            return "Venta registrada con éxito";
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error al registrar la venta: " + e.getMessage());
        }
    }

    public List<Venta> obtenerVentas() {
        return ventaRepository.findAll();
    }

    public String eliminarVenta(Long id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no encontrada");
        }
        ventaRepository.deleteById(id);
        return "Venta anulada con éxito";
    }

    public Venta obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));
    }

    public List<Venta> obtenerVentasPorEstado(String estado) {
        return ventaRepository.findByEstado(estado);
    }

    public String actualizarEstado(Long id, String nuevoEstado) {
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        venta.setEstado(nuevoEstado);
        ventaRepository.save(venta);
        return "Estado de la venta actualizado con éxito";
    }

    public Venta crearVentaEntidad(Venta venta) {
        return ventaRepository.save(venta);
    }

}
