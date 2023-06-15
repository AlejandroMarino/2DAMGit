package jakarta.security;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TokenExpiration {

    private static Map<String, Integer> llamadasPorToken = new HashMap<>();

    public void addToken(String token) {
        llamadasPorToken.put(token, 0);
    }

    public void sumarLlamada(String token) {
        llamadasPorToken.put(token, llamadasPorToken.get(token) + 1);
    }

    public boolean isExpired(String token) {
        return llamadasPorToken.get(token) >= 2;
    }


}
