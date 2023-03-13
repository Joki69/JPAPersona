package view;

import java.util.Locale;
import java.util.Scanner;

public class Menu {

    private Scanner scanner;

    public String arcanaChek() {
        while (true) {
            System.out.println("Elige uno de estos arcana:");
            System.out.println("Fool \n" +
                    "Magician\n" +
                    "Priestess\n" +
                    "Empress\n" +
                    "Emperor\n" +
                    "Hierophant\n" +
                    "Lovers\n" +
                    "Chariot\n" +
                    "Justice\n" +
                    "Hermit\n" +
                    "Fortune\n" +
                    "Strength\n" +
                    "Hanged Man\n" +
                    "Death\n" +
                    "Temperance\n" +
                    "Devil\n" +
                    "Tower\n" +
                    "Star\n" +
                    "Moon\n" +
                    "Sun\n" +
                    "Judgement");
            String entidad = scanner.nextLine();
            String comprobar = entidad.toLowerCase(Locale.ROOT);
            if (comprobar.equals("fool") || comprobar.equals("magician") || comprobar.equals("priestess") || comprobar.equals("empress")
                    || comprobar.equals("emperor") || comprobar.equals("hierophant") || comprobar.equals("lovers") || comprobar.equals("chariot")
                    || comprobar.equals("justice") || comprobar.equals("hermit") || comprobar.equals("fortune") || comprobar.equals("strength")
                    || comprobar.equals("hanged man") || comprobar.equals("death") || comprobar.equals("temperance") || comprobar.equals("devil")
                    || comprobar.equals("tower") || comprobar.equals("star") || comprobar.equals("moon") || comprobar.equals("sun") || comprobar.equals("judgement")) {
                return entidad;
            } else {
                System.out.println("No has seleccionado ninguna de las posibles opciones prueba otra vez\n");
            }

        }
    }
}
