import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private  UsuarioRepository usuarioRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository, BCryptPasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario registrarUsuario(Usuario usuario) {
        // Encriptar la contraseña
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));

        // Asignar rol predeterminado si no se ha asignado
        if (usuario.getRol() == null) {
            usuario.setRol(Usuario.Rol.usuario); // Rol predeterminado
        }

        // Guardar el usuario en la base de datos
        return usuarioRepository.save(usuario);
    }
}
