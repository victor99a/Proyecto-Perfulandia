package com.perfulandia.perfulandia_vm.repository;

import com.perfulandia.perfulandia_vm.model.Inventario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface InventarioRepository extends JpaRepository <Inventario, Long>{
}

