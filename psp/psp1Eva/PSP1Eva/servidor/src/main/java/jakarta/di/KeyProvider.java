package jakarta.di;

import java.security.Key;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

public class KeyProvider {

    @Produces
    @Singleton
    @Named("JWT")
    public Key key()
    {
        return Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }
}
