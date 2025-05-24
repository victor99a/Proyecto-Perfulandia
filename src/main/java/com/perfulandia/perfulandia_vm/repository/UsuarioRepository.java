package com.perfulandia.perfulandia_vm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.perfulandia_vm.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}

