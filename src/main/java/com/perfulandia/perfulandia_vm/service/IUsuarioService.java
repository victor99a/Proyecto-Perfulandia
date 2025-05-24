package com.perfulandia.perfulandia_vm.service;

import com.perfulandia.perfulandia_vm.model.Usuario;
import java.util.List;

public interface IUsuarioService {

    public List<Usuario> listarUsuarios();

    // Cambié el parámetro a Integer para buscar por ID directamente
    public Usuario buscarUsuarioPorId(Integer idUsuario);

    public void guardarUsuario(Usuario usuario);

    public void eliminarUsuario(Usuario usuario);
}
