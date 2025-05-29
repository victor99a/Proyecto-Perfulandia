package com.perfulandia.perfulandia_vm.dto;


import lombok.Data;

@Data
public class UsuarioResponseDTO {
    private Integer id;
    private String nombre;
    private String apellido;
    private int telefono;
    private String correo;
}
