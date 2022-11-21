package jakarta.di;

import com.google.gson.*;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Singleton;

import java.time.LocalDate;

public class Producers {


    @Singleton
    @Produces
    public Gson getGson() {
        return new GsonBuilder().registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, type, jsonDeserializationContext) -> LocalDate
                                .parse(json.getAsJsonPrimitive().getAsString().substring(0, 16)))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>)
                        (json, type, jsonDeserializationContext) -> LocalDate
                                .parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>)
                        (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString()))
                .create();
    }


}
