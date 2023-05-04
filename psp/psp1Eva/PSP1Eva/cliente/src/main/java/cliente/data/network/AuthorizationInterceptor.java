package cliente.data.network;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {


    private final CacheAuthorization ca ;


    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original ;

        if (ca.getJwt() != null) {
//            request = original.newBuilder()
//                    .header("Authorization", Credentials.basic(ca.getUser(), ca.getPass())).build();
            request = original.newBuilder()
                    .header("JWT", "Bearer "+ca.getJwt()).build();

        }

        Response response = chain.proceed(request);
//        if (response.header("Authorization") !=null)
//            ca.setJwt(response.header("Authorization"));


//        if (!response.isSuccessful())
//        {
//            //reintentar
//            response.close();
//            request = original.newBuilder()
//                    .header("Authorization", Credentials.basic(ca.getUser(), ca.getPass())).build();
//            response = chain.proceed(request);
//            if (response.header("Authorization") !=null)
//                ca.setJwt(response.header("Authorization"));
//        }

        return response;
    }
}
