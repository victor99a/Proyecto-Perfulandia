package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Inventario;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface InventarioService {
    List<Inventario> obtenerTodo();
    Inventario guardar (Inventario inventario);
    void eliminar(Long id);
    void descontarStock(Long id, int cantidad);
    Optional<Inventario> obtenerPorId(Long id);


}