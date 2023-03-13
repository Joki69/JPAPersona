package controller;

import model.Debilidad;
import model.Persona;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

/**
 * Com esta clase podremos acceder y manipular la información almacenada dentro de la tabla persona
 *
 * @author Jonathan Carralero - Joki69 in GitHub
 */
public class PersonaController {
    private Connection connection;
    private EntityManagerFactory entityManagerFactory;

    private int contadorNuevosPersonaID=226;

    private ArcanaController arcanaController = new ArcanaController(connection);

    private DebilidadController debilidadController = new DebilidadController(connection, entityManagerFactory);
    /**
     * Creamos una nueva instancia del controlador de persona usando la conexion de la base de datos
     *
     * @param connection Le pasamos la conexion de la base de datos
     * @param entityManagerFactory Le pasamos tambien el Hibernate que hemos creado
     */
    public PersonaController(Connection connection, EntityManagerFactory entityManagerFactory) {
        this.connection = connection;
        this.entityManagerFactory = entityManagerFactory;
    }


    /**
     * Esta clase se encarga de leer el archivo CSV, y con este archivo rellenarnos toda la tabla de nuestra
     * base de datos con la informacion que saca del archivo.
     *
     * @param demoniosFile la ruta del archivo characters que queremos leer
     * @param debilidadesFile la ruta del archivo weapons que queremos leer
     * @return Una lista de characters, que luego se meteran con ayuda de otros metodos
     * @throws IOException Devuelve este error si hay algun problema al leer los archivos
     */
    public List<Persona> readDemoniosFile(String demoniosFile, String debilidadesFile) throws IOException {
        // Lee el archivo de demonios
        List<String> personaLines = Files.readAllLines(Paths.get(demoniosFile), StandardCharsets.UTF_8);
        // Lee el archivo de debilidades
        List<String> debilidadLines = Files.readAllLines(Paths.get(debilidadesFile), StandardCharsets.UTF_8);

        List<Persona> personas = new ArrayList<Persona>();

        // Crea un mapa para guardar las armas por ID
        Map<Integer, Debilidad> debilidadMap = new HashMap<Integer, Debilidad>();
        int contadorIdDebilidad=1;
        for (String debilidadLine : debilidadLines) {
            // Separa la línea en campos
            String[] fields = debilidadLine.split("\n");
            // Crea un objeto de debilidad con los campos correspondientes
            Debilidad debilidad= new Debilidad(contadorIdDebilidad, fields[0]);
            // Agrega el objeto de arma al mapa
            debilidadMap.put(debilidad.getDebilidadId(), debilidad);
            contadorIdDebilidad++;
        }
        int contadorPersona=1;
        // Crea los objetos de personaje con las armas correspondientes
        for (String personaLine : personaLines) {

            // Separa la línea en campos
            String[] fields = personaLine.split("\",\"");
            // Crea un objeto de persona con los campos correspondientes
            int id_arcana = 0;
            switch (fields[0]) {
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
            Persona persona = new Persona(contadorPersona,id_arcana,id_arcana,fields[1],fields[0],fields[2]);
            // Agrega el objeto de personaje a la lista
            personas.add(persona);
            contadorPersona++;
        }

        return personas;
    }

    /**
     * Añade un persona (que procesamos con el csv) y lo mete en la base de datos
     *
     * @param persona El persona que queremos añadir
     */
    public void addPersona(Persona persona) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Persona personaExists = (Persona) em.find(Persona.class, persona.getPersonaId());
        if (personaExists == null ){
            System.out.println("inserting persona...");
            em.persist(persona);
        }
        em.merge(persona);
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Lista todos los characters de la base de datos
     */
    public void listAllPersona() {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        List<Persona> result = em.createQuery("from Persona", Persona.class)
                .getResultList();

        for (Persona persona : result) {
            System.out.println(persona.toString());
        }
        em.getTransaction().commit();
        em.close();
    }

    /**
     * Crea la tabla persona con ayuda del schema SQL
     *
     */
    public void createTablePersona() {
        // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

        // obtiene un EntityManager a partir del EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // comienza una transacción
        entityManager.getTransaction().begin();

        // crea la tabla Persona
        entityManager.createNativeQuery(
                        "CREATE TABLE persona (\n" +
                        "id_persona serial NOT NULL,\n" +
                        "id_arcana integer NOT NULL,\n" +
                        "id_debilidad integer NOT NULL,\n" +
                        "nombre_arcana character varying(100)NOT NULL,\n" +
                        "nombre_persona character varying(100)NOT NULL,\n" +
                        "historia character varying(3000)NOT NULL,\n" +
                        "CONSTRAINT pk_persona PRIMARY KEY(id_persona),\n" +
                        "   CONSTRAINT fk_arcana\n" +
                        "      FOREIGN KEY(id_arcana) \n" +
                        "\t  REFERENCES arcana(id_arcana)\n" +
                        "\t  MATCH SIMPLE\n" +
                        "\t  ON UPDATE NO ACTION ON DELETE NO ACTION,\n" +
                        "   CONSTRAINT fk_debilidad\n" +
                        "      FOREIGN KEY(id_debilidad)\n" +
                        "      REFERENCES debilidad(id_debilidad)\n" +
                        "      MATCH SIMPLE\n" +
                        "      ON UPDATE NO ACTION ON DELETE CASCADE\n" +
                        ")"
        ).executeUpdate();

        // finaliza la transacción
        entityManager.getTransaction().commit();

        // cierra el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }


    /**
     * Dropea la tabla entera de persona
     *
     @throws javax.persistence.PersistenceException Devuelve este error si hay un problema dropeando la tabla
     */
    public void dropTablePersona() {
        // crea un EntityManagerFactory utilizando la configuración definida en persistence.xml
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("JPAMagazines");

        // obtiene un EntityManager a partir del EntityManagerFactory
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        // comienza una transacción
        entityManager.getTransaction().begin();

        // dropea la tabla persona
        entityManager.createNativeQuery("DROP TABLE persona").executeUpdate();

        // finaliza la transacción
        entityManager.getTransaction().commit();

        // cierra el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }
    /**
     * Actualiza el nombre del persona que buscaras con su ID
     *
     * @param personaId El ID del persona que quieres actualizar
     * @param updateName El nombre nuevo que le quieres poner a tu persona
     */
    public void updatePersona(int personaId, String updateName) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Persona persona = (Persona) em.find(Persona.class, personaId);
        persona.setPersonaNombre(updateName);
        em.merge(persona);
        em.getTransaction().commit();

        em.getTransaction().begin();
        persona = em.find(Persona.class, personaId);
        System.out.println("Informacion del persona despues de tu Update:");
        System.out.println(persona.toString());
        em.getTransaction().commit();

        em.close();
    }
    public void createNewPersona(int idArcanaDebilidad, String nombreNuevo, String arcanaNuevo, String historia) {
        EntityManager em = entityManagerFactory.createEntityManager();
        em.getTransaction().begin();
        Persona persona= new Persona();
        this.contadorNuevosPersonaID++;
        persona.setPersonaNombre(nombreNuevo);
        persona.setPersonaId(contadorNuevosPersonaID);
        persona.setArcanaId(idArcanaDebilidad);
        persona.setDebilidadId(idArcanaDebilidad);
        persona.setHistoria(historia);
        persona.setNombreArcana(arcanaNuevo);
        em.persist(persona);
        em.getTransaction().commit();
        em.close();
    }

}

