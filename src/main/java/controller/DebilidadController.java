package controller;

import model.Arcana;
import model.Debilidad;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.util.*;

public class DebilidadController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;


    /**
     * Creamos una nueva instancia del controlador de Debilidades usando la conexion de la base de datos
     *
     * @param connection Le pasamos la conexion de la base de datos
     */
    public DebilidadController(Connection connection) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }

    /**
     * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
     * base de datos con la informacion que saca del archivo.
     *
     * @param debilidadesFile la ruta del archivo characters que queremos leer
     * @return Una lista de debilidades, que luego se meteran con ayuda de otros metodos
     * @throws IOException Devuelve este error si hay algun problema al leer los archivos
     */
    public List<Debilidad> readDebilidadesFile(String debilidadesFile) throws IOException {
        int id;
        String name;
        List<Debilidad> weaponsList = new ArrayList<Debilidad>();

        BufferedReader br = new BufferedReader(new FileReader(debilidadesFile));
        String linea = "";
        int contadorId=1;
        while ((linea = br.readLine()) != null) {
            StringTokenizer str = new StringTokenizer(linea, "\n");
            name = str.nextToken();
            // System.out.println(id + name + damage);
            weaponsList.add(new Debilidad(contadorId,name));

        }
        br.close();

        return weaponsList;
    }
    /**
     * Añade una debilidad (que procesamos con el csv) y lo mete en la base de datos
     *
     * @param debilidad La debilidad que queremos añadir
     */
    public void addDebilidad(Debilidad debilidad) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Debilidad debilidadExists = (Debilidad) em.find(Debilidad.class, debilidad.getDebilidadId());
        if (debilidadExists == null ){
            System.out.println("inserting debilidad...");
            em.persist(debilidad);
        }
        em.merge(debilidad);
        em.getTransaction().commit();
        em.close();
    }


    /**
     * Lista todas las debilidades de la base de datos
     */
    public void listAllDebilidades() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Debilidad> result = em.createQuery("from debilidad", Debilidad.class)
                .getResultList();

        for (Debilidad debilidad : result) {
            System.out.println(debilidad.toString());
        }
        em.getTransaction().commit();
        em.close();
    }


    /**
     * Crea la tabla debilidad con ayuda del schema SQL
     *
     */
    public void createTableDebilidad() {
        // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

        // obtiene un EntityManager a partir del EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // comienza una transacción
        entityManager.getTransaction().begin();

        // crea la tabla debilidad
        entityManager.createNativeQuery(
                "CREATE TABLE debilidad (\n" +
                        "id_debilidad serial NOT NULL,\n" +
                        "id_arcana integer NOT NULL,\n" +
                        "nombre_debilidad character varying(2000) NOT NULL,\n" +
                        "CONSTRAINT pk_debilidad PRIMARY KEY(id_debilidad),\n" +
                        "CONSTRAINT fk_arcana\n" +
                        "      FOREIGN KEY(id_arcana)\n" +
                        "\t  REFERENCES arcana(id_arcana)\n" +
                        "\t  MATCH SIMPLE\n" +
                        "\t  ON UPDATE NO ACTION ON DELETE NO ACTION,\n" +
                        ")"
        ).executeUpdate();

        // finaliza la transacción
        entityManager.getTransaction().commit();

        // cierra el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }

    /**
     * Drop de la tabla debilidad
     *
     @throws javax.persistence.PersistenceException Devuelve este error si hay un problema con el drop
     */
    public void dropTableDebilidad() {
        // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

        // obtiene un EntityManager a partir del EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // comienza una transacción
        entityManager.getTransaction().begin();

        // dropea la tabla characters
        entityManager.createNativeQuery("DROP TABLE debilidad").executeUpdate();

        // finaliza la transacción
        entityManager.getTransaction().commit();

        // cierra el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }

}
