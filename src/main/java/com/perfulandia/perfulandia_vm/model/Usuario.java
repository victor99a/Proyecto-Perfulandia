package com.perfulandia.perfulandia_vm.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuario")
@Data //metodos
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Autoincrementacion
    private Integer id;

    @Column(nullable = false)
    private String nombre;
    @Column(nullable = false)
    private String apellido;
    @Column(nullable = false)
    private String correo;
    @Column(nullable = false)
    private int telefono;
    @Column(nullable = false)
    private String contrasena;

    //@JsonManagedReference
    //@OneToMany(mappedBy = "usuario")
    //private List<Venta> ventas;

}
