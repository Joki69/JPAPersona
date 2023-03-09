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
 * En la clase ArcanaController como su nombre indica contendrá los metodos necesarios para manipular o acceder a
 * la información de la base de datos de la tabla "arcana".
 *
 *  @author Jonathan Carralero - Joki69 in GitHub
 */
public class ArcanaController {
    private Connection connection;
    private Scanner scanner;

    /**
     * Con este constructor será lo que nos permita conectar a la base de datos y usar sus metodos desde el main
     *
     * @param connection
     */
    public ArcanaController(Connection connection) {
        this.connection = connection;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Con este metodo podremos borrar la tabla de arcanas.
     */
    public void borrarTablaArcana(){
        try{
            Statement st = connection.createStatement();

            st.executeUpdate("DROP TABLE arcana");
        }catch (SQLException e){
            System.out.println("No se ha podido borrar la tabla arcana");
        }
    }

    /**
     * Aqui podremos crear la tabla arcanas vacías
     */
    public void crearTablaArcana(){
        try{
            Statement st = connection.createStatement();

            st.executeUpdate("CREATE TABLE arcana (" +
                    "id_arcana serial," +
                    "nombre varchar(1000)," +
                    "primary key(id_arcana));");

            st.close();

        }catch (SQLException e){
            System.out.println("Error: No se pueden crear las tablas, fijate si ya estan creadas.");
        }
    }

    /**
     * Con este metodo podremos poblar la tabla de arcana con el csv que le corresponde
     */
    public void poblarArcana(){
        List<String[]> csvData = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/Arcanas.csv"));
            String line;

            while ((line = br.readLine()) != null){
                String[] data = line.split("\n");
                csvData.add(data);
            }

            for (String[] data : csvData) {
                try{
                    String nombre = data[0];

                    String sql = "INSERT INTO arcana " + "(nombre) VALUES(?)";

                    PreparedStatement pst = connection.prepareStatement(sql);
                    pst.setString(1,nombre);

                    pst.executeUpdate();
                    pst.close();
                }catch (SQLException e){
                    e.printStackTrace();
                }
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Con este metodo podremos mostrar toda la información que contiene la tabla de arcanas por la terminal
     */
    public void mostrarArcana(){
        System.out.println("\nARCANAS");
        ResultSet rs = null;
        String sql = "SELECT * FROM arcana";
        try{
            Statement st = connection.createStatement();

            rs = st.executeQuery(sql);

            while (rs.next()) {
                System.out.println("******************************************************" +
                        "\nID Arcana: " + rs.getString("id_arcana") +
                        "\nNombre Arcana: " + rs.getString("nombre") +
                        "\n******************************************************");
            }

            rs.close();
            st.close();

        }catch (SQLException e){
            System.out.println("Error: La tabla characters no existe");
        }
    }

    /**
     * Con este metodo podremos seleccionar uno de los nombres de nuestra tabla para eliminarlo
     */
    public void borrarTablaArcanaNombre(String entidad){
        ResultSet rs = null;
        try{
            Statement st = connection.createStatement();
            st.executeUpdate("DELETE FROM arcana WHERE nombre = '" + entidad + "'");
        }catch (SQLException e){
            System.out.println("No se ha podido borrar el parametro de arcana seleccionado");
        }
    }

}
