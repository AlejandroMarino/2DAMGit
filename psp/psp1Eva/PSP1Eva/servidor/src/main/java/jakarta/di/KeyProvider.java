package jakarta.di;

import java.security.Key;

import common.Constants;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

public class KeyProvider {

    @Produces
    @Singleton
    @Named(Constants.JWT)
    public Key key()
    {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
