package com.perfulandia.perfulandia_vm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandia_vm.model.Venta;
import com.perfulandia.perfulandia_vm.service.VentaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VentaController.class)
class VentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VentaService ventaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearVenta() throws Exception {
        Venta venta = new Venta(); // Agrega datos si es necesario
        Mockito.when(ventaService.crearVenta(any(Venta.class))).thenReturn("Venta creada correctamente");

        mockMvc.perform(post("/api/v1/ventas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(venta)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Venta creada correctamente"));
    }

    @Test
    void testObtenerVentasVacia() throws Exception {
        Mockito.when(ventaService.obtenerVentas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/ventas"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testObtenerVentaPorId() throws Exception {
        Venta venta = new Venta(); venta.setId(1L);
        Mockito.when(ventaService.obtenerVentaPorId(1L)).thenReturn(venta);

        mockMvc.perform(get("/api/v1/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    void testEliminarVenta() throws Exception {
        Mockito.when(ventaService.eliminarVenta(1L)).thenReturn("Venta eliminada");

        mockMvc.perform(delete("/api/v1/ventas/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Venta eliminada"));
    }

    @Test
    void testActualizarEstado() throws Exception {
        Map<String, String> body = new HashMap<>();
        body.put("estado", "Entregado");

        Mockito.when(ventaService.actualizarEstado(1L, "Entregado"))
                .thenReturn("Estado actualizado");

        mockMvc.perform(put("/api/v1/ventas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body)))
                .andExpect(status().isOk())
                .andExpect(content().string("Estado actualizado"));
    }
}

