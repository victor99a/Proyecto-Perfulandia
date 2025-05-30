package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Inventario;
import com.perfulandia.perfulandia_vm.repository.InventarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;



@Service
public class InventarioServiceImpl implements InventarioService{

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override

    public List<Inventario> obtenerTodo(){
        return inventarioRepository.findAll();

    }

    @Override
    public Inventario guardar (Inventario inventario){
        return inventarioRepository.save(inventario);
    }

    @Override
    public String eliminar(Long id) {
        if (!inventarioRepository.existsById(id)) {
            throw new RuntimeException("Producto no encontrado con ID:"+ id);

    }    inventarioRepository.deleteById(id);
        return "Producto eliminado exitosamente.";
    }
    @Override
    public Inventario actualizar(Long id, Inventario inventario) {
        Inventario existente = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        existente.setNombre(inventario.getNombre());
        existente.setDescripcion(inventario.getDescripcion());
        existente.setStock(inventario.getStock());
        existente.setPrecio(inventario.getPrecio());

        return inventarioRepository.save(existente);
    }




}