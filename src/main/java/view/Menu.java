package view;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.Scanner;

/**
 * La clase menu representa un texto/menu que hace posible la interactuacion del usuario con la base de datos.
 *
 * @author Joki69
 */
public class Menu {
    private int option;


    /**
     *
     * Un constructor del menu vacio, donde le pasamos la clase super (Object)
     *
     */
    public Menu() {
        super();
    }

    /**
     * Muestra las opciones del menu a base de souts para poder seleccionar una opcion.
     *
     @return La opcion que ha escogido el usuario
     */
    public int mainMenu() {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        do {

            System.out.println(" \nMENU PRINCIPAL \n");

            System.out.println("1. Crear TODAS las tablas.");
            System.out.println("2. Rellenar los datos de las tablas con los CSV.");
            System.out.println("3. Listar toda la tabla persona.");
            System.out.println("4. Listar toda la tabla debilidad.");
            System.out.println("5. Listar toda la tabla arcana.");
            System.out.println("6. Hacer drop de todas las tablas.");
            System.out.println("7. Modificar el nombre de un persona");
            System.out.println("12. Crear un character manualmente");
            System.out.println("13. Crear un weapon manualmente");
            System.out.println("14. Borrar un character por su nombre.");
            System.out.println("15. Borrar un weapon por su nombre.");

            System.out.println("0. Sortir. ");

            System.out.println("Esculli opció: ");
            try {
                option = Integer.parseInt(br.readLine());
            } catch (NumberFormatException | IOException e) {
                System.out.println("valor no vàlid");
                e.printStackTrace();
            }
        } while (option != 1  && option != 2 && option != 3 && option != 4 && option != 5 && option != 6
                && option != 7 && option != 8 && option != 9 && option != 10 && option != 11 && option != 12
                && option != 13 && option != 14 && option != 15 && option != 16 && option != 0);

        return option;
    }
    public String arcanaChek() {
        Scanner scanner = new Scanner(System.in);
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
