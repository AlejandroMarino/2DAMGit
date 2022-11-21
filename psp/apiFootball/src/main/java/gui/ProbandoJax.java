package gui;

import com.google.gson.Gson;
import dao.modelo.jax.Usuario;
import dao.modelo.marvel.ApiError;
import dao.modelo.marvel.MarvelCharacters;
import dao.retrofit.UsersApi;
import dao.utils.ConfigurationSingleton_OkHttpClientPruebas;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class ProbandoJax {

    public static void main(String[] args) throws IOException {


        UsersApi users = ConfigurationSingleton_OkHttpClientPruebas.getInstance().create(UsersApi.class);
        Response<List<Usuario>> response = users.getUsers().execute();

        if (response.isSuccessful())
        {
            System.out.println(response.body());
        }
        else
        {
            Gson g = new Gson();
            ApiError apierror = g.fromJson(response.errorBody().string(), ApiError.class);

            System.out.println("error Code"+ response.code());
            System.out.println("error Code"+ response.message());
            System.out.println("error " +apierror.getMessage());
        }



    }
}
