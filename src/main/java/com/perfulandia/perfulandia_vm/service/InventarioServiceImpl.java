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
    public void eliminar(Long id) {
        if (!inventarioRepository.existsById(id)) {
            System.out.println("Producto no encontrado con ID: " + id);
            return;
        }

        inventarioRepository.deleteById(id);
    }
    @Override
    public void descontarStock(Long id, int cantidad) {
        Inventario inventario = inventarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + id));

        int nuevoStock = inventario.getStock() - cantidad;
        if (nuevoStock < 0) {
            throw new RuntimeException("Stock insuficiente para el producto con ID: " + id);
        }

        inventario.setStock(nuevoStock);
        inventarioRepository.save(inventario);
    }



}