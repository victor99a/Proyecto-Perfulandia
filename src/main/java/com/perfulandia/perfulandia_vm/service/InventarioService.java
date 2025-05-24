package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Inventario;

import java.util.List;

public interface InventarioService {
    List<Inventario> obtenerTodo();
    Inventario guardar (Inventario inventario);
    void eliminar(Long id);
}