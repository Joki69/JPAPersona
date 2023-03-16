package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Esta clase representara todos los persona que vayamos metiendo en la tabla persona de nuestra
 * base de datos
 *
 @author Joki69
 */
@Entity
@Access(AccessType.FIELD)
@Table(name = "persona")
public class Persona implements Serializable {
    /**
     * El identificador del persona
     *
     */
    @Id
    @Column(name = "id_persona")
    int personaId;

    @ManyToOne
    @JoinColumn(name = "id_debilidad")
    public Debilidad debilidad;

    /**
     * El identificador del arcana
     *
     */
    @ManyToOne
    @JoinColumn(name = "id_arcana")
    public Arcana arcana;

    /**
     * El nombre del persona
     *
     */
    @Column(name = "nombre_persona")
    String personaNombre;

    /**
     * La historia del persona
     *
     */
    @Column(name = "historia")
    String historia;

    /**
     * Contructor del objeto persona
     * @param personaId id del persona
     * @param debilidad id de su debilidad
     * @param arcana id de su arcana
     * @param personaNombre nombre del persona
     * @param historia historia del persona
     */
    public Persona(int personaId,Debilidad debilidad, Arcana arcana, String personaNombre, String historia) {
        this.personaId = personaId;
        this.debilidad = debilidad;
        this.arcana = arcana;
        this.personaNombre = personaNombre;
        this.historia = historia;
    }

    /**
     * Constructor vacio para no tener problemas con el codigo
     */
    public Persona(){}

    /**
     * Devuelve la ID de un persona
     *
     @return Su identificador
     */
    public int getPersonaId() {
        return personaId;
    }
    /**
     * Editamos el identificador del persona
     *
     * @param personaId Le pasamos la nueva ID
     */
    public void setPersonaId(int personaId) {
        this.personaId = personaId;
    }
    /**
     * Devuelve la ID de una debilidad
     *
     @return Su identificador
     */

    /**
     * Editamos el identificador de la debilidad
     *
     * @param debilidadId Le pasamos la nueva ID
     */

    /**
     * Devuelve la ID de un arcana
     *
     @return Su identificador
     */

    /**
     * Editamos el identificador del arcana
     *
     * @param arcanaId Le pasamos la nueva ID
     */

    /**
     * Nos da el nombre del persona
     *
     * @return devuelve el nombre del persona
     */
    public String getPersonaNombre() {
        return personaNombre;
    }
    /**
     * Edita el nombre del persona
     *
     @param personaNombre Su nuevo nombre
     */
    public void setPersonaNombre(String personaNombre) {
        this.personaNombre = personaNombre;
    }

    /**
     * Nos devuelve la historia del persona
     *
     * @return la historia del persona
     */
    public String getHistoria() {
        return historia;
    }


    /**
     * Editamos la historia del persona
     *
     * @param historia nueva historia
     */
    public void setHistoria(String historia) {
        this.historia = historia;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "personaId=" + personaId +
                ", debilidad=" + debilidad +
                ", arcana=" + arcana +
                ", personaNombre='" + personaNombre + '\'' +
                ", historia='" + historia + '\'' +
                '}';
    }
}
