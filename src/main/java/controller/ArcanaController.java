package controller;

import model.Arcana;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * En la clase ArcanaController como su nombre indica contendrá los metodos necesarios para manipular o acceder a
 * la información de la base de datos de la tabla "arcana".
 *
 *  @author Jonathan Carralero - Joki69 in GitHub
 */
public class ArcanaController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;


   // private Scanner scanner;

    /**
     * Con este constructor será lo que nos permita conectar a la base de datos y usar sus metodos desde el main
     *
     * @param connection
     */
    public ArcanaController(Connection connection) {
        this.connection = connection;
       // this.scanner = new Scanner(System.in);
    }
    /**
     * Creamos una nueva instancia del controlador de arcana usando la conexion de la base de datos
     *
     * @param connection Le pasamos la conexion de la base de datos
     * @param entityManagerFactory Le pasamos tambien el Hibernate que hemos creado
     */
    public ArcanaController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
     * base de datos con la informacion que saca del archivo.
     *
     * @param filename la ruta del archivo character_type que queremos leer
     * @return Una lista de character_type, que luego se meteran con ayuda de otros metodos
     * @throws IOException Devuelve este error si hay algun problema al leer los archivos
     */
    public List<Arcana> readArcana(String filename) throws IOException {
        int arcanaId;
        String nombreArcana;
        List<Arcana> arcanaList = new ArrayList();

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String linea = "";
        int contadorId=1;
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, "\n");
            nombreArcana = str.nextToken();
            arcanaList.add(new Arcana(contadorId,nombreArcana));
            contadorId++;
        }
        br.close();
        return arcanaList;
    }
    /**
     * Añade un arcana (que procesamos con el csv) y lo mete en la base de datos
     *
     * @param arcana El arcama que queremos añadir
     */
    public void addArcana(Arcana arcana) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Arcana characterTypeExists = (Arcana) em.find(Arcana.class, arcana.getArcanaId());
        if (characterTypeExists == null ){
            System.out.println("inserting arcana...");
            em.persist(arcana);
        }
        em.merge(arcana);
        em.getTransaction().commit();
        em.close();
    }


    /**
     * Ordena los arcama por su nombre y los lista
     */
    public void listAllArcana() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Arcana> result = em.createQuery("from Arcana", Arcana.class)
                .getResultList();

        for (Arcana arcana : result) {
            System.out.println(arcana.toString());
        }
        em.getTransaction().commit();
        em.close();
    }


    /**
     * Crea la tabla arcana con ayuda del schema SQL
     *
     */
    public void createTableArcana(){
        // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazine");

        // obtiene un EntityManager a partir del EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // comienza una transacción
        entityManager.getTransaction().begin();

        // crea la tabla Arcana
        entityManager.createNativeQuery(
                "CREATE TABLE arcana (\n" +
                        "id_arcana serial NOT NULL,\n" +
                        "nombre character varying(1000) NOT NULL,\n" +
                        "CONSTRAINT pk_arcana PRIMARY KEY(id_arcana))"
        ).executeUpdate();

        // finaliza la transacción
        entityManager.getTransaction().commit();

        // cierra el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }


    /**
     * Drop la tabla Arcana
     *
     @throws javax.persistence.PersistenceException Devuelve este error si hay un problema con el drop la tabla
     */
    public void dropTableArcana() {
        // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAPersona");

        // obtiene un EntityManager a partir del EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // comienza una transacción
        entityManager.getTransaction().begin();

        // dropea la tabla characters
        entityManager.createNativeQuery("DROP TABLE arcana").executeUpdate();

        // finaliza la transacción
        entityManager.getTransaction().commit();

        // cierra el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
}


