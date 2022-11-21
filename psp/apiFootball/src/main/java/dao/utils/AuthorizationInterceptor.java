package dao.utils;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {

    private String jwt;



    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request.Builder builder1 = original.newBuilder()
                .header("X-Auth-Token", "2deee83e549c4a6e9709871d0fd58a0a")
                .url(original.url().newBuilder()
                        .addQueryParameter("ts","1")
                        .addQueryParameter("apikey","a26d34b6ea64ce618360835be5888f91")
                        .addQueryParameter("hash","073e520a55d710ef1b77df866349e689")
                        .build());


        Request request = builder1.build();
        request.newBuilder().header("Authorization", Credentials.basic("user","password")).build();
        Response response = chain.proceed(request);
        Response response2 = chain.proceed(request);
        response.header("jwt");

        return response;
    }
}
