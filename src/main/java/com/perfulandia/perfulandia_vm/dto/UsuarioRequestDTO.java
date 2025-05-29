package com.perfulandia.perfulandia_vm.dto;


import lombok.Data;

@Data
public class UsuarioRequestDTO {

    private String nombre;
    private String apellido;
    private String correo;
    private int telefono;
    private String contrasena;

}
