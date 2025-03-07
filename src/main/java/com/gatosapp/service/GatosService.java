package com.gatosapp.service;

import com.gatosapp.Gatos;
import com.gatosapp.GatosFav;
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

        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\n    \"image_id\": \"" + gato.getId() + "\"\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .post(body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApiKey())
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(null, "Gato añadido a favoritos", "Favorito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Algo salió mal", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void verGatosFavoritos(String apiKey) {

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .get()
                    .addHeader("x-api-key", apiKey)
                    .build();

            Response response = client.newCall(request).execute();

            String elJson = Objects.requireNonNull(response.body()).string();

            Gson gson = new Gson();
            GatosFav[] gatosArray = gson.fromJson(elJson, GatosFav[].class);

            if (gatosArray.length <= 0) {
               JOptionPane.showMessageDialog(null, "No hay gatos favoritos", "Favoritos", JOptionPane.INFORMATION_MESSAGE);
               return;
            }

            int min = 1;
            int max = gatosArray.length;
            int aleatorio = (int) (Math.random() * ((max - min) + 1)) + min;
            int indice = aleatorio - 1;

            GatosFav gatoFav = gatosArray[aleatorio];

            Image imagen = null;
            try {
                URL url = new URL(gatoFav.getImage().getUrl());
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
                        + " 2. Eliminar favorito \n"
                        + " 3. Volver \n";

                String[] botones = {"Ver otra imagen", "Eliminar favorito", "Volver"};

                String id_gato = gatoFav.getId();
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
                        verGatosFavoritos(apiKey);
                        break;
                    case 1:
                        eliminarFavorito(gatoFav);
                        break;
                    default:
                        break;
                }

            } catch(IOException ie) {
                System.err.println(ie.getMessage());
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void eliminarFavorito(GatosFav gatoFav) {

        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites/" + gatoFav.getId())
                    .delete()
                    .build();

            Response response = client.newCall(request).execute();

            if (response.isSuccessful()) {
                JOptionPane.showMessageDialog(null, "Gato eliminado de favoritos", "Favorito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Algo salió mal", "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
