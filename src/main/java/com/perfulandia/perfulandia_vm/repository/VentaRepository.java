package com.perfulandia.perfulandia_vm.repository;

import com.perfulandia.perfulandia_vm.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {

    List<Venta> findByEstado(String estado);
}



