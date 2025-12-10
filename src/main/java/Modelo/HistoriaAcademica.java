/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
    @OneToMany (mappedBy = "historiaAcademica")
    private ArrayList<Boletin> listaBoletines;

    public HistoriaAcademica(int id, Estudiante estudiante, ArrayList<Boletin> listaBoletines) {
        this.id = id;
        this.estudiante = estudiante;
        this.listaBoletines = listaBoletines;
    }

    public HistoriaAcademica() {
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

    public ArrayList<Boletin> getListaBoletines() {
        return listaBoletines;
    }

    public void setListaBoletines(ArrayList<Boletin> listaBoletines) {
        this.listaBoletines = listaBoletines;
    }

    
    
    
    
    
}
