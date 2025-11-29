/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author nicol
 */
@Entity
public class HistoriaAcademica implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Estudiante estudiante;
    private String boletines;

    public HistoriaAcademica() {
    }

    public HistoriaAcademica(int id, Estudiante estudiante, String boletines) {
        this.id = id;
        this.estudiante = estudiante;
        this.boletines = boletines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public String getBoletines() {
        return boletines;
    }

    public void setBoletines(String boletines) {
        this.boletines = boletines;
    }
    
    
    
}
