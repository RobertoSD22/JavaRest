package com.gatosapp;

import com.gatosapp.service.GatosService;

import javax.swing.*;
import java.io.IOException;

public class Inicio {
    public static void main(String[] args) throws IOException {

        int opcionMenu = -1;
        String[] botones = {
          "1.- Ver gatos",
          "2.- Ver favoritos",
          "3.- Salir"
        };
        do {

            String opcion = (String) JOptionPane.showInputDialog(
                            null,
                            "Gatitos Java",
                            "Menu Principal",
                            JOptionPane.INFORMATION_MESSAGE,
                            null,
                            botones,
                            botones[0]);

            // Validamos que opción selecciona el usuario
            for (int i = 0; i < botones.length; i++) {
                if (opcion.equals(botones[i])) {
                    opcionMenu = i;
                }
            }

            switch (opcionMenu) {
                case 0:
                    GatosService.verGatitos();
                    break;
                case 1:
                    Gatos gato = new Gatos();
                    GatosService.verGatosFavoritos(gato.getApiKey());
                    break;
                default:
                    break;
            }

        }while (opcionMenu != 1);
    }
}
