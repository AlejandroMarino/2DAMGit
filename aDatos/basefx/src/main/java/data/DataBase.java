package data;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import config.Configuracion;
import gsonutils.RuntimeTypeAdapterFactory;
import lombok.extern.log4j.Log4j2;
import modelo.Cliente;
import modelo.Producto;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Log4j2
public class DataBase {


    private Gson gson;

    private Configuracion configuracion;

    public DataBase() {

        RuntimeTypeAdapterFactory<Cliente> adapterCliente =
                RuntimeTypeAdapterFactory
                        .of(Cliente.class, "Cliente", true);

        RuntimeTypeAdapterFactory<Producto> adapterProducto =
                RuntimeTypeAdapterFactory
                        .of(Producto.class, "Producto", true);


        this.gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) ->
                                LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class,
                        (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) ->
                                LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (localDateTime, type, jsonSerializationContext) ->
                                new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapterFactory(adapterCliente)
                .registerTypeAdapterFactory(adapterProducto)
                .create();


        this.configuracion = Configuracion.getInstance();
    }

    public DataBase(Gson gson, Configuracion configuracion) {
        this.gson = gson;
        this.configuracion = configuracion;
    }

    public Map<String, Cliente> loadClientes() {
        Type userListType = new TypeToken<Map<String, Cliente>>() {
        }.getType();

        Map<String, Cliente> clientes = null;
        try {
            clientes = gson.fromJson(
                    new FileReader(configuracion.getPathClientes()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return clientes;
    }

    public boolean saveClientes(Map<String, Cliente> clientes) {

        try (FileWriter w = new FileWriter(configuracion.getPathClientes())) {
            gson.toJson(clientes, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public List<Producto> loadProductos() {
        Type userListType = new TypeToken<ArrayList<Producto>>() {
        }.getType();

        List<Producto> productos = null;
        try {
            productos = gson.fromJson(
                    new FileReader(configuracion.getPathProductos()),
                    userListType);
        } catch (FileNotFoundException e) {
            log.error(e.getMessage(), e);
        }
        return productos;
    }

    public boolean saveProductos(List<Producto> productos) {

        try (FileWriter w = new FileWriter(configuracion.getPathProductos())) {
            gson.toJson(productos, w);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return false;
        }

        return true;
    }
}
