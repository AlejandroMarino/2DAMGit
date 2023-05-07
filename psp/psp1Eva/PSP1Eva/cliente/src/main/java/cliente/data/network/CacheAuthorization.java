package cliente.data.network;

import jakarta.inject.Singleton;
import lombok.Data;


@Data
@Singleton
public class CacheAuthorization {

    private String jwt;
}
