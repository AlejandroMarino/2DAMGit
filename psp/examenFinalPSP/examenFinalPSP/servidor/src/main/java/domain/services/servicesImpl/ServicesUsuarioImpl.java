package domain.services.servicesImpl;

import dao.DaoUsuarios;
import domain.model.errores.BadRequestException;
import domain.model.errores.DataModificationException;
import domain.model.errores.NotFoundException;
import domain.models.Usuario;
import domain.services.ServicesUsuario;
import jakarta.inject.Inject;
import jakarta.rest.Utils;
import jakarta.security.GenerateToken;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.List;

public class ServicesUsuarioImpl implements ServicesUsuario {
    private final DaoUsuarios dU;
    private final GenerateToken gT;
    private final Utils utils;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesUsuarioImpl(DaoUsuarios dU, GenerateToken gT, Utils utils, Pbkdf2PasswordHash passwordHash) {
        this.dU = dU;
        this.gT = gT;
        this.utils = utils;
        this.passwordHash = passwordHash;
    }

    @Override
    public Usuario register(Usuario usuario) {
        Usuario u = dU.get(usuario.getNombre());
        if (u != null) {
            throw new BadRequestException("Usuario ya registrado");
        } else {
            String password = utils.randomBytes();
            usuario.setPassword(passwordHash.generate(password.toCharArray()));
            dU.register(usuario);
            return new Usuario(usuario.getNombre(), password);
        }
    }

    @Override
    public Usuario login(Usuario usuario) {
        Usuario u = dU.get(usuario.getNombre());
        if (u != null) {
            if (passwordHash.verify(usuario.getPassword().toCharArray(), u.getPassword())) {
                return u;
            } else {
                return null;
            }
        } else {
            throw new NotFoundException("Usuario no encontrado");
        }
    }

    @Override
    public String generateJWS(Usuario usuario) {
        Usuario user = dU.get(usuario.getNombre());
        List<String> roles = user.getRoles();
        return gT.generateToken(user.getNombre(), roles);
    }
}
