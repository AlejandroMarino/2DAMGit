package cliente.data.network;

import cliente.config.Configuration;
import com.squareup.moshi.Moshi;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class ProducesRetrofit {

    @Produces
    @Singleton
    public Moshi getMoshi() {
        return new Moshi.Builder().build();
    }


    @Produces
    @Singleton
    public Retrofit retrofit(Moshi moshi, Configuration config) {

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
    public GamesApi getACApi(Retrofit retrofit) {
        return retrofit.create(GamesApi.class);
    }

}
