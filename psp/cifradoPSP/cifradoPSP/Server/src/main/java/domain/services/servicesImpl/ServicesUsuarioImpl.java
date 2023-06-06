package domain.services.servicesImpl;

import data.dao.DaoUsuario;
import domain.models.Contrato;
import domain.models.Usuario;
import domain.services.ServicesUsuario;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;

import java.util.List;

public class ServicesUsuarioImpl implements ServicesUsuario {

    private DaoUsuario dU;

    private final Pbkdf2PasswordHash passwordHash;

    @Inject
    public ServicesUsuarioImpl(DaoUsuario dU, Pbkdf2PasswordHash passwordHash) {
        this.dU = dU;
        this.passwordHash = passwordHash;
    }


    @Override
    public Usuario register(Usuario usuario) {
        String pass = usuario.getPassword();
        usuario.setPassword(passwordHash.generate(pass.toCharArray()));
        return dU.add(usuario);
    }

    @Override
    public Usuario login(Usuario usuario) {
        char[] pass = usuario.getPassword().toCharArray();
        Usuario user = dU.get(usuario.getNombre());
        if (user == null) {
            return null;
        } else {
            if (passwordHash.verify(pass, user.getPassword())) {
                return user;
            } else {
                return null;
            }
        }
    }

    @Override
    public List<Usuario> getSicariosFilterHabilidad(int habilidad) {
        return dU.getSicariosFilterHabilidad(habilidad);
    }

    @Override
    public List<Usuario> getSicariosContrato(Contrato contrato) {
        return dU.getSicarios(contrato);
    }


}
