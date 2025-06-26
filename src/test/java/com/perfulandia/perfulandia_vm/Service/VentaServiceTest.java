package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Venta;
import com.perfulandia.perfulandia_vm.repository.VentaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearVenta() {
        Venta venta = new Venta();
        when(ventaRepository.save(venta)).thenReturn(venta);

        String mensaje = ventaService.crearVenta(venta);

        assertEquals("Venta registrada con éxito", mensaje);
        verify(ventaRepository).save(venta);
    }

    @Test
    void testObtenerVentas() {
        List<Venta> ventas = List.of(new Venta(), new Venta());
        when(ventaRepository.findAll()).thenReturn(ventas);

        List<Venta> resultado = ventaService.obtenerVentas();

        assertEquals(2, resultado.size());
    }

    @Test
    void testEliminarVentaExitosa() {
        Long id = 1L;
        when(ventaRepository.existsById(id)).thenReturn(true);

        String mensaje = ventaService.eliminarVenta(id);

        assertEquals("Venta anulada con éxito", mensaje);
        verify(ventaRepository).deleteById(id);
    }

    @Test
    void testEliminarVentaNoExistente() {
        Long id = 1L;
        when(ventaRepository.existsById(id)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> ventaService.eliminarVenta(id));
        assertEquals("Venta no encontrada", ex.getMessage());
    }

    @Test
    void testObtenerVentaPorIdExistente() {
        Long id = 1L;
        Venta venta = new Venta();
        venta.setId(id);
        when(ventaRepository.findById(id)).thenReturn(Optional.of(venta));

        Venta resultado = ventaService.obtenerVentaPorId(id);

        assertEquals(id, resultado.getId());
    }

    @Test
    void testObtenerVentaPorIdNoExistente() {
        Long id = 1L;
        when(ventaRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> ventaService.obtenerVentaPorId(id));
        assertEquals("Venta no encontrada", ex.getMessage());
    }

    @Test
    void testObtenerVentasPorEstado() {
        String estado = "Entregado";
        List<Venta> ventas = List.of(new Venta());
        when(ventaRepository.findByEstado(estado)).thenReturn(ventas);

        List<Venta> resultado = ventaService.obtenerVentasPorEstado(estado);

        assertEquals(1, resultado.size());
    }

    @Test
    void testActualizarEstado() {
        Long id = 1L;
        Venta venta = new Venta();
        venta.setId(id);
        venta.setEstado("Pendiente");

        when(ventaRepository.findById(id)).thenReturn(Optional.of(venta));
        when(ventaRepository.save(any(Venta.class))).thenReturn(venta);

        String mensaje = ventaService.actualizarEstado(id, "Entregado");

        assertEquals("Estado de la venta actualizado con éxito", mensaje);
        assertEquals("Entregado", venta.getEstado());
    }

    @Test
    void testActualizarEstadoVentaNoExistente() {
        Long id = 1L;
        when(ventaRepository.findById(id)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> ventaService.actualizarEstado(id, "Entregado"));
        assertEquals("Venta no encontrada", ex.getMessage());
    }
}
