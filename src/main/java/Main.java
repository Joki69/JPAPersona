import controller.ArcanaController;
import controller.DebilidadController;
import controller.PersonaController;
import database.ConnectionFactory;
import model.Arcana;
import model.Debilidad;
import model.Persona;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import view.Menu;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/**
 * esta clase se encargara de lanzar el programa y hará en funcion de las necesidades del usuario
 */
public class Main {
    private static final Scanner scanner = new Scanner(System.in);

    static SessionFactory sessionFactoryObj;

    /**
     * Creamos un constructor vacio de Main (porque simplemente nos da un error en JavaDoc si no lo hacemos)
     *
     */
    public Main(){
    }
    /*
  private static SessionFactory buildSessionFactory() {
    // Creating Configuration Instance & Passing Hibernate Configuration File
    Configuration configObj = new Configuration();
    configObj.configure("hibernate.cfg.xml");
    // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
    ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();
    // Creating Hibernate SessionFactory Instance
    sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
    return sessionFactoryObj;
  } */

    /**
     * Construye un Object Hibernate para que podamos empezar a interactuar con la base de datos.
     *
     * @return Nos devuelve el Hibernate que hemos construido con una clase SessionFactory.
     */
    private static SessionFactory buildSessionFactory() {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
                    .configure("hibernate.cfg.xml").build();
            Metadata metadata = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            return metadata.getSessionFactoryBuilder().build();

        } catch (HibernateException he) {
            System.out.println("Session Factory creation failure");
            throw he;
        }
    }

    /**
     * Crea un EntityManagerFactory para interactuar con el framework.
     *
     * @return Devuelve el EntityManagerFactory.
     */
    public static EntityManagerFactory createEntityManagerFactory(){
        EntityManagerFactory emf;
        try {
            emf = Persistence.createEntityManagerFactory("JPAMagazines");
        } catch (Throwable ex) {
            System.err.println("Failed to create EntityManagerFactory object."+ ex);
            throw new ExceptionInInitializerError(ex);
        }
        return emf;
    }

    /**
     * Aqui basicamente muestra el menu interactuable con el usuario, donde podremos toquetear la base de datos.
     *
     * @param args Los argumentos que le pasamos por consola (no se usa)
     */
    public static void main(String[] args) {
        boolean salirMenu = false;

        ConnectionFactory connectionFactory = ConnectionFactory.getInstance();
        Connection c = connectionFactory.connect();

//    SessionFactory sessionFactory = buildSessionFactory();
        EntityManagerFactory entityManagerFactory = createEntityManagerFactory();
        //sessionObj = buildSessionFactory().openSession();

        //Creamos los 3 controladores que hemos creado para poder usar las tablas de la base de datos
        ArcanaController arcanaController = new ArcanaController(c, entityManagerFactory);
        DebilidadController debilidadController = new DebilidadController(c, entityManagerFactory);
        PersonaController personaController = new PersonaController(c, entityManagerFactory);

        Menu menu = new Menu();
        int opcio;


        //Nos saldra el menu infinitas veces, hasta que se presione la tecla 0 que nos cierra el programa.
        while(!salirMenu){
            opcio = menu.mainMenu();

            //Aqui se muestran todas las opciones del menu, cada opcion se encarga de lo que indicamos al usuario
            //por escrito en el menu
            switch (opcio) {

                case 1:

                    try{
                        arcanaController.createTableArcana();
                    }catch (Exception e){
                         e.printStackTrace();
                    }
                    try{
                        debilidadController.createTableDebilidad();
                    }catch (Exception e){
                       e.printStackTrace();
                    }
                    try{
                        personaController.createTablePersona();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    break;

                case 2:
                    personaController.setContadorNuevosPersonaID(226);
                    System.out.println("2");

                    try{
                        List<Arcana> arcanas = arcanaController.readArcana("src/main/resources/Arcanas.csv");
                        for (Arcana a :arcanas) {
                            try {
                                arcanaController.addArcana(a);
                            } catch (Exception e) {
                            }
                        }
                    }catch (Exception e){

                    }



                    try {
                        List<Debilidad> debilidades = debilidadController.readDebilidadesFile("src/main/resources/Debilidades.csv");
                        for (Debilidad d : debilidades) {
                            try {
                                debilidadController.addDebilidad(d);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                        List<Persona> personas = personaController.readDemoniosFile("src/main/resources/Demonios.csv","src/main/resources/Debilidades.csv");
                        for (Persona p: personas) {
                            try {
                                personaController.addPersona(p);
                            } catch (Exception e) {
                            }
                        }


                    } catch (NumberFormatException | IOException e) {

                        e.printStackTrace();
                    }

                    break;

                case 3:
                    personaController.listAllPersona();
                    break;

                case 4:
                    debilidadController.listAllDebilidades();
                    break;

                case 5:
                    arcanaController.listAllArcana();
                    break;


                case 6:

                    try{
                        personaController.dropTablePersona();
                    }catch (Exception e){
                        System.out.println("La tabla no se pudo borrar, probablemente porque no existe");
                    }
                    try{
                        debilidadController.dropTableDebilidad();
                    }catch (Exception e){
                        System.out.println("La tabla no se pudo borrar, probablemente porque no existe");
                    }
                    try{
                        arcanaController.dropTableArcana();
                    }catch (Exception e){
                        System.out.println("La tabla no se pudo borrar, probablemente porque no existe");
                    }


                    break;
                case 7:
                    System.out.println("Introduce el id del persona que quieres modificar y el nuevo nombre(sin espacios");

                    personaController.updatePersona(scanner.nextInt(),scanner.nextLine());
                    break;

                case 8:
                    System.out.println("Para crear un nuevo persona, rellena este formulario");
                    System.out.println();

                    System.out.println("Escribe el arcana del persona que quieras añadir");
                    String nombre_arcana =  menu.arcanaChek();
                    int idArcana=0;
                    String nombre_debilidad="";
                    switch (nombre_arcana) {
                        case "Fool":
                            idArcana = 1;
                            nombre_debilidad="Strike";
                            break;
                        case "Magician":
                            idArcana = 2;
                            nombre_debilidad="Psychokinesis";
                            break;
                        case "Priestess":
                            idArcana = 3;
                            nombre_debilidad="Bless";
                            break;
                        case "Empress":
                            idArcana = 4;
                            nombre_debilidad="Wind";
                            break;
                        case "Emperor":
                            idArcana = 5;
                            nombre_debilidad="Fire";
                            break;
                        case "Hierophant":
                            idArcana = 6;
                            nombre_debilidad="Psychokinesis";
                            break;
                        case "Lovers":
                            idArcana = 7;
                            nombre_debilidad="Wind";
                            break;
                        case "Chariot":
                            idArcana = 8;
                            nombre_debilidad="Ice";
                            break;
                        case "Justice":
                            idArcana = 9;
                            nombre_debilidad="Electricity";
                            break;
                        case "Hermit":
                            idArcana = 10;
                            nombre_debilidad="Strike";
                            break;
                        case "Fortune":
                            nombre_debilidad="Ice";
                            idArcana = 11;
                            break;
                        case "Strength":
                            nombre_debilidad="Psychokinesis";
                            idArcana = 12;
                            break;
                        case "Hanged Man":
                            idArcana = 13;
                            nombre_debilidad="Nuclear";
                            break;
                        case "Death":
                            idArcana = 14;
                            nombre_debilidad="Bless";
                            break;
                        case "Temperance":
                            idArcana = 15;
                            nombre_debilidad="Pierce";
                            break;
                        case "Devil":
                            idArcana = 16;
                            nombre_debilidad="Bless";
                            break;
                        case "Tower":
                            idArcana = 17;
                            nombre_debilidad="Electricity";
                            break;
                        case "Star":
                            idArcana = 18;
                            nombre_debilidad="Almighty";
                            break;
                        case "Moon":
                            idArcana = 19;
                            nombre_debilidad="Pierce";
                            break;
                        case "Sun":
                            idArcana = 20;
                            nombre_debilidad="Nuclear";
                            break;
                        case "Judgement":
                            idArcana = 21;
                            nombre_debilidad="Almighty";
                            break;
                    }
                    System.out.println("Escribe el nombre del persona que quieras añadir");
                    String nombre_persona = scanner.nextLine();
                    System.out.println("Escribe la historia del persona que quieras añadir");
                    String historia = scanner.nextLine();
                    Arcana arcana = new Arcana(idArcana,nombre_arcana);
                    Debilidad debilidad = new Debilidad(idArcana,nombre_debilidad);
                            personaController.createNewPersona(arcana,debilidad,nombre_persona,nombre_arcana,historia);
                    break;

                case 9:
                    System.out.println("Escribe el nombre del persona que quieres borrar");
                personaController.deletePersonaName(scanner.nextInt());
                break;


                default:
                    salirMenu = true;
                    //System.exit(1);
            }
        }
    }
}


/*
    static User userObj;
    static Session sessionObj;
    static SessionFactory sessionFactoryObj;
    private static SessionFactory buildSessionFactory() {
        // Creating Configuration Instance & Passing Hibernate Configuration File
        Configuration configObj = new Configuration();
        configObj.configure("hibernate.cfg.xml");
        // Since Hibernate Version 4.x, ServiceRegistry Is Being Used
        ServiceRegistry serviceRegistryObj = new StandardServiceRegistryBuilder().applySettings(configObj.getProperties()).build();
        // Creating Hibernate SessionFactory Instance
        sessionFactoryObj = configObj.buildSessionFactory(serviceRegistryObj);
        return sessionFactoryObj;
    }
    public static void main(String[] args) {
        System.out.println(".......Hibernate Maven Example.......\n");
        try {
            sessionObj = buildSessionFactory().openSession();
            sessionObj.beginTransaction();
            for(int i = 101; i <= 105; i++) {
                userObj = new User();
                userObj.setUserid(i);
                userObj.setUsername("Editor " + i);
                userObj.setCreatedBy("Administrator");
                userObj.setCreatedDate(new Date());
                sessionObj.save(userObj);
            }
            System.out.println("\n.......Records Saved Successfully To The Database.......\n");
            // Committing The Transactions To The Database
            sessionObj.getTransaction().commit();
        } catch(Exception sqlException) {
            if(null != sessionObj.getTransaction()) {
                System.out.println("\n.......Transaction Is Being Rolled Back.......");
                sessionObj.getTransaction().rollback();
            }
            sqlException.printStackTrace();
        } finally {
            if(sessionObj != null) {
                sessionObj.close();
            }
        }
    }
*/



