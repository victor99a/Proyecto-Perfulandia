package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Inventario;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface InventarioService {
    List<Inventario> obtenerTodo();
    Inventario guardar (Inventario inventario);
    void eliminar(Long id);




}