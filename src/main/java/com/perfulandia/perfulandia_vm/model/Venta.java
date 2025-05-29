//package com.perfulandia.perfulandia_vm.model;

//import jakarta.persistence.*;
//import lombok.*;

//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Venta {
   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
    //private String producto;
    //private int cantidad;
    //private double total;
//}

package com.perfulandia.perfulandia_vm.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.perfulandia.perfulandia_vm.model.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_venta", nullable = false)
    private Date fechaVenta;

    @Column(nullable = false)
    private double total;

    @Column(nullable = false, length = 20)
    private String estado;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "run_usuario", referencedColumnName = "id")
    private Usuario usuario;

    @JsonManagedReference
    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<DetalleVenta> detalleVentas;


}
