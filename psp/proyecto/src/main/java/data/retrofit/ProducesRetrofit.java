package data.retrofit;

import com.squareup.moshi.Moshi;
import config.Configuracion;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;

public class ProducesRetrofit {

    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder().build();
    }


    @Produces
    @Singleton
    public Retrofit retrofit(Moshi moshi, Configuracion config) {

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS))
                .build();

        return new Retrofit.Builder()
                .baseUrl(config.getPathApi())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOK)
                .build();
    }
    @Produces
    public ACApi getACApi(Retrofit retrofit) {
        return retrofit.create(ACApi.class);
    }

}
