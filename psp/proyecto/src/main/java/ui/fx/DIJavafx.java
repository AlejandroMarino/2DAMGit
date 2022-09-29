package ui.fx;

import com.squareup.moshi.Moshi;
import data.retrofit.ACApi;
import jakarta.enterprise.inject.se.SeContainer;
import jakarta.enterprise.inject.se.SeContainerInitializer;
import jakarta.enterprise.util.AnnotationLiteral;
import javafx.application.Application;
import javafx.stage.Stage;
import modelo.Fish;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

import java.io.IOException;

public class DIJavafx extends Application {

    public static void main(String[] args) throws IOException {

        Moshi moshi = new Moshi.Builder().build();

        OkHttpClient clientOK = new OkHttpClient.Builder()
                .connectionPool(new okhttp3.ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS))
                .build();

//        System.out.println(clientOK
//                .newCall(
//                        new Request.Builder()
//                                .url("https://acnhapi.com/v1/fish/").build())
//                .execute().body().string());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://acnhapi.com/v1/fish/")
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(clientOK)
                .build();

        ACApi api = retrofit.create(ACApi.class);

        Response<Fish> r = api.getFish().execute();

        System.out.println(r.body());

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        SeContainerInitializer initializer = SeContainerInitializer.newInstance();
        final SeContainer container = initializer.initialize();
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);
        primaryStage.setResizable(false);
        container.getBeanManager().fireEvent(primaryStage, new AnnotationLiteral<StartupScene>() {
        });
    }

}
