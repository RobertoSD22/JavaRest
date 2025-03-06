package com.gatosapp.service;

import com.gatosapp.Gatos;
import com.google.gson.Gson;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class GatosService {

    @SuppressWarnings("deprecation")
    public static void verGatitos() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search?limit=1").get().build();

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
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0");
            connection.setRequestMethod("GET");

            try (InputStream inputStream = connection.getInputStream()) {
                imagen = ImageIO.read(inputStream);
            }

            ImageIcon fondoGato = new ImageIcon(imagen);

            if (fondoGato.getIconWidth() > 800) {
                //redimensionamos
                Image fondo = fondoGato.getImage();
                Image modificada = fondo.getScaledInstance(300,200, Image.SCALE_SMOOTH);
                fondoGato = new ImageIcon(modificada);
            }

            String menu = "Opciones: \n"
                    + " 1. Ver otra imagen \n"
                    + " 2. Favorito \n"
                    + " 3. Volver \n";

            String[] botones = {"Ver otra imagen", "Favorito", "Volver"};

            String id_gato = gatos.getId();
            String opcion = (String) JOptionPane.
                    showInputDialog(null, menu, id_gato, JOptionPane.INFORMATION_MESSAGE, fondoGato, botones, botones[0]);

            int seleccion = -1;
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    seleccion = i;
                }
            }

            switch (seleccion) {
                case 0:
                    verGatitos();
                    break;
                case 1:
                    favoritoGato(gatos);
                    break;
                default:
                    break;
            }

        } catch(IOException ie) {
            System.err.println(ie.getMessage());
        }
    }

    public static void favoritoGato(Gatos gato) {

    }

}
