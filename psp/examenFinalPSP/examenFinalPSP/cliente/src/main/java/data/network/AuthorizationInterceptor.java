package data.network;

import common.Constants;
import jakarta.inject.Inject;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {

    private final CacheAuthorization ca ;

    @Inject
    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original;

        if (ca.getJwt() != null) {
            request = original.newBuilder()
                    .header(Constants.AUTHORIZATION, Constants.BEARER +ca.getJwt()).build();
        }

        Response response = chain.proceed(request);

        if (response.code() == Constants.INVALID_TOKEN)
        {
            response.close();
            request = original.newBuilder()
                    .header(Constants.AUTHORIZATION, Credentials.basic(ca.getUser(), ca.getPass())).build();
            response = chain.proceed(request);
            if (response.header(Constants.AUTHORIZATION) !=null)
                ca.setJwt(response.header(Constants.AUTHORIZATION));
        }

        return response;
    }
}
