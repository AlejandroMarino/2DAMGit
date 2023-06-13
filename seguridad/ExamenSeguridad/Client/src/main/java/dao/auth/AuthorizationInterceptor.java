package dao.auth;

import commonclient.Constants;
import io.reactivex.rxjava3.annotations.NonNull;
import jakarta.inject.Inject;
import model.error.Error;
import model.exceptions.BadRequestException;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {
    private final CacheAuthorization ca;

    @Inject
    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    @NonNull
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();

        if (ca.getJwt() != null) {
            request = request.newBuilder()
                    .header(Constants.HEADER_AUTHORIZATION, Constants.HEADER_BEARER + ca.getJwt()).build();
        }

        Response response = chain.proceed(request);
        if (response.header(Constants.HEADER_AUTHORIZATION) != null) {
            ca.setJwt(response.header(Constants.HEADER_AUTHORIZATION));
        }

        if (!response.isSuccessful() && response.code() == Error.EXCEPTION_TOKEN_EXPIRED.getCode()) {
            throw new BadRequestException("Token expirado");
        }

        return response;
    }
}
