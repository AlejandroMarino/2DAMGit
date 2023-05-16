package jakarta.security;

import common.Constants;
import io.jsonwebtoken.Jwts;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


public class GenerateToken {

    private final Key key;

    @Inject
    public GenerateToken(@Named(Constants.JWT) Key key) {
        this.key = key;
    }

    public String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(Constants.CLIENT)
                .setIssuer(Constants.SERVER)
                .claim(Constants.USERNAME, username)
                .claim(Constants.ROLES, roles)
                .setExpiration(Date.from(LocalDateTime.now().plusSeconds(1).atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(key).compact();
    }

}
