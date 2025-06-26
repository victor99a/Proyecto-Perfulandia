package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Inventario;

import java.util.List;

public interface InventarioService {
    List<Inventario> obtenerTodo();
    Inventario guardar(Inventario inventario);
    String eliminar(Long id);
    Inventario actualizar(Long id, Inventario inventario);

    // CORREGIDO: ahora devuelve Optional
    Inventario buscarPorId(Long id);
}
