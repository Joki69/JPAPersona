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

                    System.out.println("2!!");

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
                    if(nombre_arcana.equals("Fool")) {
                        idArcana=1;
                    } else if (nombre_arcana.equals("Magician")) {
                        idArcana=2;
                    }
                    else if (nombre_arcana.equals("Priestess")) {
                        idArcana=3;
                    }
                    else if (nombre_arcana.equals("Empress")) {
                        idArcana=4;
                    }
                    else if (nombre_arcana.equals("Emperor")) {
                        idArcana=5;
                    }
                    else if (nombre_arcana.equals("Hierophant")) {
                        idArcana=6;
                    }
                    else if (nombre_arcana.equals("Lovers")) {
                        idArcana=7;
                    }
                    else if (nombre_arcana.equals("Chariot")) {
                        idArcana=8;
                    }
                    else if (nombre_arcana.equals("Justice")) {
                        idArcana=9;
                    }
                    else if (nombre_arcana.equals("Hermit")) {
                        idArcana=10;
                    }
                    else if (nombre_arcana.equals("Fortune")) {
                        idArcana=11;
                    }
                    else if (nombre_arcana.equals("Strength")) {
                        idArcana=12;
                    }
                    else if (nombre_arcana.equals("Hanged Man")) {
                        idArcana=13;
                    }
                    else if (nombre_arcana.equals("Death")) {
                        idArcana=14;
                    }
                    else if (nombre_arcana.equals("Temperance")) {
                        idArcana=15;
                    }
                    else if (nombre_arcana.equals("Devil")) {
                        idArcana=16;
                    }
                    else if (nombre_arcana.equals("Tower")) {
                        idArcana=17;
                    }
                    else if (nombre_arcana.equals("Star")) {
                        idArcana=18;
                    }
                    else if (nombre_arcana.equals("Moon")) {
                        idArcana=19;
                    }
                    else if (nombre_arcana.equals("Sun")) {
                        idArcana=20;
                    }
                    else if (nombre_arcana.equals("Judgement")) {
                        idArcana=21;
                    }
                    System.out.println("Escribe el nombre del persona que quieras añadir");
                    String nombre_persona = scanner.nextLine();
                    System.out.println("Escribe la historia del persona que quieras añadir");
                    String historia = scanner.nextLine();

                            personaController.createNewPersona(idArcana,nombre_persona,nombre_arcana,historia);
                    break;

                /*
                case 8:
                    characterController.orderCharactersByName();
                    break;

                case 9:
                    System.out.println("Que weapon quieres buscar?");
                    String weaponName = scanner.nextLine();

                    try{
                        weaponController.listAllWeaponsByName(weaponName);
                    }catch (Exception e){
                        System.out.println("No se ha encontrado ningun Weapon con el nombre que has proporcionado, intentalo de nuevo");
                    }

                    break;

                case 10:
                    System.out.println("Que ID tiene el character que quieres cambiar? Del 1 al 30");
                    int idCharacter = scanner.nextInt();
                    scanner.nextLine();
                    if(idCharacter >= 1 && idCharacter < 31){
                        System.out.print("Escribe el nombre nuevo para el character que quieres modificar: ");
                        String updateName = scanner.nextLine();

                        characterController.updateCharacter(idCharacter,updateName);
                    }
                    else{
                        System.out.println("La ID que estas intentando buscar no existe, recuerda que tiene que ser del 1 al 30!");
                    }
                    break;

                case 11:
                    System.out.println("Que ID tiene el weapon que quieres cambiar? Del 1 al 30");
                    int idWeapon = scanner.nextInt();
                    scanner.nextLine();
                    if(idWeapon >= 1 && idWeapon < 31){
                        System.out.print("Escribe el daño nuevo para el weapon que quieres modificar: ");
                        int updateDamage = scanner.nextInt();

                        weaponController.updateWeapon(idWeapon,updateDamage);
                    }
                    else{
                        System.out.println("La ID que estas intentando buscar no existe, recuerda que tiene que ser del 1 al 30!");
                    }
                    break;

                case 12:
                    System.out.println("Para crear un nuevo character, rellena este formulario");
                    System.out.println();

                    System.out.println("Que tipo de character es, 1 - Plant, 2 - Zombie");
                    int newCharacterTypeId = scanner.nextInt();
                    scanner.nextLine();
                    if(newCharacterTypeId != 1 && newCharacterTypeId != 2){
                        System.out.println("No puede ser otro numero, tiene que ser el 1 o el 2!");
                    }
                    else{
                        System.out.println("Que arma quieres tener en este character, del 1 al 30");
                        int newWeaponId = scanner.nextInt();
                        scanner.nextLine();
                        if(newWeaponId >= 1 && newWeaponId <= 30){
                            System.out.println("Que nombre tiene este character?");
                            String newCharacterName = scanner.nextLine();

                            System.out.println("Inserta la imagen del character, por ejemplo: hola.jpg o hola.png");
                            String newCharacterImage = scanner.nextLine();

                            System.out.println("Cuanta vida tiene este character?");
                            String newCharacterHealth = scanner.nextLine();

                            System.out.println("Que tipo de variante es este character?");
                            String newCharacterVariant = scanner.nextLine();

                            System.out.println("Que tipo de habilidad/es tiene este character?");
                            String newCharacterAbility = scanner.nextLine();

                            System.out.println("Que tipo de clase es este character?");
                            String newCharacterFPSClass = scanner.nextLine();

                            characterController.createCharacterManually(newCharacterTypeId,newWeaponId,newCharacterName,
                                    newCharacterImage,newCharacterHealth,newCharacterVariant,newCharacterAbility,
                                    newCharacterFPSClass);
                        }
                        else{
                            System.out.println("Esta arma no existe, debe de ser un arma del 1 al 30");
                        }
                    }

                    break;

                case 13:
                    System.out.println("Para crear un nuevo weapon, rellena este formulario");
                    System.out.println();

                    System.out.println("Que nombre quieres que tenga esta arma?");
                    String newWeaponName = scanner.nextLine();

                    System.out.println("Que daño quieres que haga esta nueva arma?");
                    int newWeaponDamage = scanner.nextInt();
                    scanner.nextLine();
                    weaponController.createWeaponManually(newWeaponName,newWeaponDamage);

                    break;

                case 14:
                    System.out.println("WARNING: Si quieres borrar un character, debes borrar su weapon primero! Ya que el weapon depende del character");
                    System.out.print("Inserta el nombre del character que quieres borrar: ");
                    String deleteNameCharacter = scanner.nextLine();


                    characterController.deleteCharacterByName(deleteNameCharacter);

                    break;


                case 15:
                    System.out.print("Inserta el nombre del weapon que quieres borrar: ");
                    String deleteNameWeapon = scanner.nextLine();

                    weaponController.deleteWeaponByName(deleteNameWeapon);
                    break;
*/

                default:
                    System.out.println("Acha luegor!!");
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



