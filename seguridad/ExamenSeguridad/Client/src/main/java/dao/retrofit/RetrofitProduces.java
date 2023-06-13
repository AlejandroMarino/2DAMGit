package dao.retrofit;

import com.squareup.moshi.Moshi;
import config.Configuration;
import dao.auth.AuthorizationInterceptor;
import dao.retrofit.adapters.LocalDateAdapter;
import dao.retrofit.adapters.LocalDateTimeAdapter;
import dao.retrofit.calls.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

public class RetrofitProduces {
    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder()
                .add(new LocalDateAdapter())
                .add(new LocalDateTimeAdapter())
                .build();
    }

    @Produces
    @Singleton
    public Retrofit retrofit(Moshi moshi, Configuration config, OkHttpClient clientOK) {
        return new Retrofit.Builder()
                .baseUrl(config.getPathData())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }

    @Produces
    @Singleton
    public OkHttpClient getOkHttpClient(AuthorizationInterceptor authInterceptor) {
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        return new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectionPool(new ConnectionPool(1, 1, TimeUnit.SECONDS))
                .addInterceptor(authInterceptor)
                .cookieJar(new JavaNetCookieJar(cookieManager))
                .build();
    }

    @Produces
    public APIUser getLoginApi(Retrofit retrofit) {
        return retrofit.create(APIUser.class);
    }

    @Produces
    public APIRatones getRatonApi(Retrofit retrofit) {
        return retrofit.create(APIRatones.class);
    }

    @Produces
    public APIInformes getInformesApi(Retrofit retrofit) {
        return retrofit.create(APIInformes.class);
    }
}
