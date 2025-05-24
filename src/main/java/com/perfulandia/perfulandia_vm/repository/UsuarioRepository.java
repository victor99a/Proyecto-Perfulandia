<<<<<<< HEAD
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.stereotype.Repository;

//@Repository
//public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Métodos personalizados si es necesario
//
=======
package com.perfulandia.perfulandia_vm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.perfulandia.perfulandia_vm.model.Usuario;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

}
>>>>>>> origin/master
