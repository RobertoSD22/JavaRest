package com.gatosapp.service;

import com.gatosapp.Gatos;
import com.google.gson.Gson;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class GatosService {

    @SuppressWarnings("deprecation")
    public static void verGatitos() throws IOException {

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        RequestBody body = RequestBody.create(mediaType, "");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search?limit=1")
                .method("GET", body)
                .build();
        Response response = client.newCall(request).execute();

        // Cortar corchetes
        String elJson = Objects.requireNonNull(response.body()).string();
        elJson = elJson.substring(1, elJson.length()-1);

        //Crear el objeto gato
        Gson gson = new Gson();
        Gatos gatos = gson.fromJson(elJson, Gatos.class);

        //Hacemos la imagen más chiquita para que se ve en la interfaz
        Image imagen = null;
        try {
            URL url = new URL(gatos.getUrl());
            imagen = ImageIO.read(url);

            ImageIcon fondoGato = new ImageIcon(imagen);

            if (fondoGato.getIconWidth() > 800) {
                //redimensionamos
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }
        } catch(IOException ie) {
            System.err.println(ie);
        }
    }
}
