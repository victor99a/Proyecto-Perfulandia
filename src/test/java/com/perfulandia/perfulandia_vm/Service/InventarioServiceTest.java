package com.perfulandia.perfulandia_vm.Service;

import com.perfulandia.perfulandia_vm.model.Inventario;
import com.perfulandia.perfulandia_vm.repository.InventarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;
import com.perfulandia.perfulandia_vm.service.InventarioServiceImpl;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventarioServiceTest {

    @Mock
    private InventarioRepository inventarioRepository;

    @InjectMocks
    private InventarioServiceImpl inventarioService;

    private Inventario inventario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        inventario = new Inventario(1L, "Perfume X", "Aroma suave", 10, 49990.0);
    }

    @Test
    void testGuardarInventario() {
        when(inventarioRepository.save(any(Inventario.class))).thenReturn(inventario);

        Inventario guardado = inventarioService.guardar(inventario);
        assertNotNull(guardado);
        assertEquals("Perfume X", guardado.getNombre());
    }

    @Test
    void testObtenerTodo() {
        List<Inventario> lista = Arrays.asList(inventario);
        when(inventarioRepository.findAll()).thenReturn(lista);

        List<Inventario> resultado = inventarioService.obtenerTodo();
        assertEquals(1, resultado.size());
        assertEquals("Perfume X", resultado.get(0).getNombre());
    }

    @Test
    void testEliminarExistente() {
        when(inventarioRepository.existsById(1L)).thenReturn(true);
        doNothing().when(inventarioRepository).deleteById(1L);

        assertDoesNotThrow(() -> inventarioService.eliminar(1L));
        verify(inventarioRepository).deleteById(1L);
    }

    @Test
    void testEliminarNoExistente() {
        when(inventarioRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> inventarioService.eliminar(99L));
    }
}

