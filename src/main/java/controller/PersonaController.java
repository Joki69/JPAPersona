package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Com esta clase podremos acceder y manipular la información almacenada dentro de la tabla persona
 *
 * @author Jonathan Carralero - Joki69 in GitHub
 */
public class PersonaController {
    private Connection connection;
    private Scanner scanner;

    /**
     * Con el contructor podremos conectarnos a la base de datos para hacer lo que pida el usuario dentro de la sopciones
     *
     * @param connection
     */
    public PersonaController(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Este metodo borrara la tabla persona
     */
    public void borrarTablaPersona() {
        try {
            Statement st = connection.createStatement();

            st.executeUpdate("DROP TABLE persona");
        } catch (SQLException e) {
            System.out.println("No se ha podido borrar persona");
        }
    }

    /**
     * Este metodo creara la tabla de persona vacías
     */
    public void crearTablaPersona() {
        try {
            Statement st = connection.createStatement();

            st.executeUpdate("CREATE TABLE persona (" +
                    "id_persona serial," +
                    "id_arcana integer," +
                    "nombre_arcana varchar(1000)," +
                    "nombre_persona text," +
                    "historia text," +
                    "PRIMARY KEY(id_persona)," +
                    "    CONSTRAINT fk_arcana" +
                    "      FOREIGN KEY(id_arcana)" +
                    "      REFERENCES arcana(id_arcana));");

            st.close();

        } catch (SQLException e) {
            System.out.println("Error: No se pueden crear las tablas, fijate si ya estan creadas.");
        }
    }

    /**
     * Con este metodo rellenaremos la tabla de persona con el csv correspondiente
     */
    public void poblarPersona() {
        List<String[]> csvData = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Demonios.csv"));
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split("\",\"");
                csvData.add(data);
            }

            for (String[] data : csvData) {
                try {
                    String nombre_arcana = data[0];
                    String nombre_persona = data[1];
                    String historia = data[2];

                    String sql = "INSERT INTO persona " + "(nombre_arcana,nombre_persona,historia,id_arcana) VALUES(?,?,?,?)";

                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1, nombre_arcana);
                    pst.setString(2, nombre_persona);
                    pst.setString(3, historia);
                    int id_arcana = 0;
                    switch (nombre_arcana) {

                        case "Fool":
                            id_arcana = 1;
                            break;
                        case "Magician":
                            id_arcana = 2;
                            break;
                        case "Priestess":
                            id_arcana = 3;
                            break;
                        case "Empress":
                            id_arcana = 4;
                            break;
                        case "Emperor":
                            id_arcana = 5;
                            break;
                        case "Hierophant":
                            id_arcana = 6;
                            break;
                        case "Lovers":
                            id_arcana = 7;
                            break;
                        case "Chariot":
                            id_arcana = 8;
                            break;
                        case "Justice":
                            id_arcana = 9;
                            break;
                        case "Hermit":
                            id_arcana = 10;
                            break;
                        case "Fortune":
                            id_arcana = 11;
                            break;
                        case "Strength":
                            id_arcana = 12;
                            break;
                        case "Hanged Man":
                            id_arcana = 13;
                            break;
                        case "Death":
                            id_arcana = 14;
                            break;
                        case "Temperance":
                            id_arcana = 15;
                            break;
                        case "Devil":
                            id_arcana = 16;
                            break;
                        case "Tower":
                            id_arcana = 17;
                            break;
                        case "Star":
                            id_arcana = 18;
                            break;
                        case "Moon":
                            id_arcana = 19;
                            break;
                        case "Sun":
                            id_arcana = 20;
                            break;
                        case "Judgement":
                            id_arcana = 21;
                            break;
                    }

                    pst.setInt(4, id_arcana);


                    pst.executeUpdate();
                    pst.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Con esta clase el usuario podra crear un nuevo persona para añadirlo a la tabla persona se le preguntara
     * el aracana el nombre y la historia del persona
     */
    public void insertNewPersona(ACBMenu menu) {
        ResultSet rs = null;
        System.out.println("Escribe el arcana del persona que quieras añadir");
        String nombre_arcana = menu.arcanaChek();
        System.out.println("Escribe el nombre del persona que quieras añadir");
        String nombre_persona = scanner.nextLine();
        System.out.println("Escribe la historia del persona que quieras añadir");
        String historia = scanner.nextLine();
        try {
            String sql = "INSERT INTO persona " + "(nombre_arcana,nombre_persona,historia) VALUES(?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, nombre_arcana);
            pst.setString(2, nombre_persona);
            pst.setString(3, historia);

            pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
            System.out.println("No se han podido modificar los datos");
        }
    }


    /**
     * Con este metodo podremos ver toda la información que contiene la tabla persona
     */
    public void mostrarPersona() {
        System.out.println("\nPERSONAS");
        ResultSet rs = null;
        String sql = "SELECT * FROM persona";
        try {
            Statement st = connection.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println("******************************************************" +
                        "\nID Persona: " + rs.getString("id_persona") +
                        "\nID Arcana: " + rs.getString("id_arcana") +
                        "\nNombre Arcana: " + rs.getString("nombre_arcana") +
                        "\nNombre Persona: " + rs.getString("nombre_persona") +
                        "\nHistoria del persona: " + rs.getString("historia") +
                        "\n******************************************************");
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Error: La tabla persona no existe");
        }
    }

    /**
     * Muestra la información de un persona pero el usuario tiene que conocer el nombre del persona que busca
     */
    public void mostrarPersonaNombre() {
        ResultSet rs = null;
        System.out.println("Introduce el nombre del persona:");
        String nombrePersona = scanner.nextLine();

        String sql = "SELECT * FROM persona WHERE nombre_persona = '" + nombrePersona + "'";
        try {
            Statement st = connection.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println("******************************************************" +
                        "\nID Persona: " + rs.getString("id_persona") +
                        "\nID Arcana: " + rs.getString("id_arcana") +
                        "\nNombre Arcana: " + rs.getString("nombre_arcana") +
                        "\nNombre Persona: " + rs.getString("nombre_persona") +
                        "\nHistoria del persona: " + rs.getString("historia") +
                        "\n******************************************************");
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Error: El parametro " + nombrePersona + " no existe");
        }
    }

    /**
     * Muestra todos los personas que compartan el mismo arcana
     *
     * @param arcana
     */
    public void mostrarPersonaArcanaNombre(String arcana) {
        ResultSet rs = null;
        String sql = "SELECT * FROM persona WHERE nombre_arcana = '" + arcana + "'";
        try {
            Statement st = connection.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println("******************************************************" +
                        "\nID Persona: " + rs.getString("id_persona") +
                        "\nID Arcana: " + rs.getString("id_arcana") +
                        "\nNombre Arcana: " + rs.getString("nombre_arcana") +
                        "\nNombre Persona: " + rs.getString("nombre_persona") +
                        "\nHistoria del persona: " + rs.getString("historia") +
                        "\n******************************************************");
            }

            rs.close();
            st.close();

        } catch (SQLException e) {
            System.out.println("Error: El parametro " + arcana + " no existe");
        }
    }

    /**
     * Modifica el nombre de un persona a uno nuevo que elija el usuario
     */
    public void modificarNombrePersona() {
        ResultSet rs = null;
        System.out.println("Escribe el nombre del persona que quieras modificar");
        String viejoNombre = scanner.nextLine();
        System.out.println("Ahora el nuevo nombre para el persona");
        String nuevoNombre = scanner.nextLine();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE persona SET nombre_persona = '" + nuevoNombre + "' WHERE nombre_persona = '" + viejoNombre + "'");
        } catch (SQLException e) {
            System.out.println("No se han podido modificar los datos");
        }
    }

    /**
     * Con este metodo el usuario podrá cambiar el arcana al que pertenezcan un grupo de persona por uno nuevo que el usuario elija
     *
     * @param menu
     */
    public void modificarNombrePersonaPorArcana(ACBMenu menu) {
        ResultSet rs = null;

        System.out.println("Escribe el arcana de los persona que quieras modificar");
        String viejoArcana = menu.arcanaChek();
        System.out.println("Ahora el nuevo arcana para los persona");
        String nuevoArcana = menu.arcanaChek();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate("UPDATE persona SET nombre_arcana = '" + nuevoArcana + "' WHERE nombre_arcana = '" + viejoArcana + "'");
        } catch (SQLException e) {
            System.out.println("No se han podido modificar los datos");
        }
    }

    /**
     * Este metodo borrara todos los persona que pertenezcan al mismo arcana
     *
     * @param entidad
     */
    public void borrarTablaPersonaPorArcana(String entidad) {
        ResultSet rs = null;

        try {
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM persona WHERE nombre_arcana = '" + entidad + "'");
        } catch (SQLException e) {
            System.out.println("No se han podido borrar los parametros seleccionados");
        }
    }
}

