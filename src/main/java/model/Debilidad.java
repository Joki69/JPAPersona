package model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Esta clase nos ayuda a representar la debilidad de cada arcana
 *
 * @author Joki69
 */

@Entity
@Access(AccessType.FIELD)
@Table(name = "debilidad" +
        "")


public class Debilidad implements Serializable {
    /**
     * El identificador de la debilidad
     *
     */
    @Id
    @Column(name = "id_debilidad")
    int debilidadId;

    /**
     * El nombre de la debilidad
     *
     */
    @Column(name = "nombre_debilidad")
    String nombreDebilidad;

    /**
     * Construye una debilidad con nombre y el id del arcana al que pertenece
     *
     @param debilidadId El identificador de la debilidad
     @param nombreDebilidad El nombre del character
     */
    public Debilidad(int debilidadId, String nombreDebilidad ) {
        this.debilidadId = debilidadId;
        this.nombreDebilidad = nombreDebilidad ;
    }

    /**
     * Contructor vacio para no generar errores
     */
    public Debilidad(){}
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
     * Nos da el nombre de la debilidad
     *
     * @return devuelve el nombre de la debilidad
     */
    public String getNombreDebilidad() {
        return nombreDebilidad;
    }
    /**
     * Edita el nombre de la debilidad
     *
     @param nombreDebilidad Su nuevo nombre
     */
    public void setNombreDebilidad(String nombreDebilidad) {
        this.nombreDebilidad = nombreDebilidad;
    }

    @Override
    public String toString() {
        return "Debilidad{" +
                "debilidadId=" + debilidadId +
                ", nombreDebilidad='" + nombreDebilidad + '\'' +
                '}';
    }
}
