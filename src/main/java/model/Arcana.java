package model;

import javax.persistence.*;
import java.io.Serializable;

/**
        * Esta clase nos ayuda a representar los tipos de arcana
        *
        * @author Joki69
        */
@Entity
@Access(AccessType.FIELD)
@Table(name = "arcana" +
        "")


public class Arcana implements Serializable {
    /**
     * El identificador del arcana
     *
     */
    @Id
    @Column(name = "id_arcana")
    private int arcanaId;

    /**
     * El nombre del arcana
     *
     */
    @Column(name = "nombre", length = 1000)
    private String nombreArcana;

    /**
     * Construye un nuevo tipo de arcana con su ID y su nombre
     *
     @param arcanaId El identificador del tipo
     @param nombreArcana Su nombre
     */
    public Arcana(int arcanaId, String nombreArcana) {
        super();
        this.nombreArcana= nombreArcana;
        this.arcanaId = arcanaId;
    }

    /**
     * Constructor vacío para que no genere errores
     */
    public Arcana(){}

    /**
     * Devuelve el identificador del arcana
     *
     @return La ID del arcana
     */
    public int getArcanaId() {
        return arcanaId;
    }
    /**
     * Editamos el identificador del arcana
     *
     * @param arcanaId la nueva ID
     */
    public void setArcanaId(int arcanaId) {
        this.arcanaId = arcanaId;
    }
    /**
     * Nos devuelve el nombre del arcana
     *
     * @return el nombre del arcana
     */
    public String getNombreArcana() {
        return nombreArcana;
    }

    /**
     * Editamos el nombre del arcana
     *
     * @param nombreArcana su nuevo nombre
     */
    public void setNombreArcana(String nombreArcana) {
        this.nombreArcana = nombreArcana;
    }

    @Override
    public String toString() {
        return "Arcana{" +
                "arcanaId=" + arcanaId +
                ", nombreArcana='" + nombreArcana + '\'' +
                '}';
    }
}
