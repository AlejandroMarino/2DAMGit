package data.impl;

import data.DaoFish;
import data.retrofit.ACApi;
import io.vavr.control.Either;
import jakarta.inject.Inject;
import modelo.Fish;

import java.util.Map;

public class DaoFishImpl implements DaoFish {

    private final ACApi api;

    @Inject
    public DaoFishImpl(ACApi api) {
        this.api = api;
    }

    @Override
    public Either<String, Map<String, Fish>> getFishes() {
        try {
            return Either.right(api.getFishes().execute().body());
        } catch (Exception e) {
            return Either.left("No se ha podido conectar con la API");
        }
    }

    @Override
    public Either<String, Fish> getFish(int id) {
        try {
            return Either.right(api.getFish(id).execute().body());
        } catch (Exception e) {
            return Either.left("No se ha podido conectar con la API");
        }
    }

    @Override
    public String getImage(Fish fish) {
        return fish.getIcon_uri();
    }


}
