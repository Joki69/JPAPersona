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

    /**
     * El identificador de la debilidad
     *
     */
    @Column(name = "id_debilidad")
    int debilidadId;

    /**
     * El identificador del arcana
     *
     */
    @Column(name = "id_arcana")
    int arcanaId;

    /**
     * El nombre del persona
     *
     */
    @Column(name = "nombre_persona")
    String personaNombre;

    /**
     * El nombre del arcana
     *
     */
    @Column(name = "nombre_arcana")
    String nombreArcana;

    /**
     * La historia del persona
     *
     */
    @Column(name = "historia")
    String historia;

    public Persona(int personaId, int debilidadId, int arcanaId, String personaNombre, String nombreArcana, String historia) {
        this.personaId = personaId;
        this.debilidadId = debilidadId;
        this.arcanaId = arcanaId;
        this.personaNombre = personaNombre;
        this.nombreArcana = nombreArcana;
        this.historia = historia;
    }

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
    public int getDebilidadId() {
        return debilidadId;
    }
    /**
     * Editamos el identificador de la debilidad
     *
     * @param debilidadId Le pasamos la nueva ID
     */
    public void setDebilidadId(int debilidadId) {
        this.debilidadId = debilidadId;
    }
    /**
     * Devuelve la ID de un arcana
     *
     @return Su identificador
     */
    public int getArcanaId() {
        return arcanaId;
    }
    /**
     * Editamos el identificador del arcana
     *
     * @param arcanaId Le pasamos la nueva ID
     */
    public void setArcanaId(int arcanaId) {
        this.arcanaId = arcanaId;
    }
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
     * Nos devuelve el nombre del arcana
     *
     * @return el nombre del arcana
     */
    public String getArcanaNombre() {
        return nombreArcana;
    }
    /**
     * Editamos el nombre del arcana
     *
     * @param arcanaNombre su nuevo nombre
     */
    public void setArcanaNombre(String arcanaNombre) {
        this.nombreArcana = arcanaNombre;
    }
    /**
     * Nos devuelve la historia del persona
     *
     * @return la historia del persona
     */
    public String getHistoria() {
        return historia;
    }

    public String getNombreArcana() {
        return nombreArcana;
    }

    public void setNombreArcana(String nombreArcana) {
        this.nombreArcana = nombreArcana;
    }

    /**
     * Editamos la historia del persona
     *
     * @param historiasu nueva historia
     */
    public void setHistoria(String historia) {
        this.historia = historia;
    }
}
