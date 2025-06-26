package com.perfulandia.perfulandia_vm.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.perfulandia.perfulandia_vm.controller.InventarioController;
import com.perfulandia.perfulandia_vm.model.Inventario;
import com.perfulandia.perfulandia_vm.service.InventarioService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventarioController.class)
public class InventarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventarioService inventarioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testObtenerInventario() throws Exception {
        Mockito.when(inventarioService.obtenerTodo()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/inventario"))
                .andExpect(status().isOk());
    }

    @Test
    void testCrearInventario() throws Exception {
        Inventario inventario = new Inventario(null, "Perfume de Rosas", "Aroma floral", 10, 12990.0);

        Mockito.when(inventarioService.guardar(Mockito.any(Inventario.class))).thenReturn(inventario);

        mockMvc.perform(post("/api/inventario")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Perfume de Rosas"));
    }

}