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

/**
 *
 * @author nicol
 */
@Entity
public class HojaDeVida implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Estudiante estudiante;
    private String direccion;
    private String estadoSalud;
    private ArrayList<String> alergias;
    private ArrayList<String> observacionesAprendizaje;

    public HojaDeVida() {
    }

    public HojaDeVida(int id, Estudiante estudiante, String direccion, String estadoSalud, ArrayList<String> alergias, ArrayList<String> observacionesAprendizaje) {
        this.id = id;
        this.estudiante = estudiante;
        this.direccion = direccion;
        this.estadoSalud = estadoSalud;
        this.alergias = alergias;
        this.observacionesAprendizaje = observacionesAprendizaje;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEstadoSalud() {
        return estadoSalud;
    }

    public void setEstadoSalud(String estadoSalud) {
        this.estadoSalud = estadoSalud;
    }

    public ArrayList<String> getAlergias() {
        return alergias;
    }

    public void setAlergias(ArrayList<String> alergias) {
        this.alergias = alergias;
    }

    public ArrayList<String> getObservacionesAprendizaje() {
        return observacionesAprendizaje;
    }

    public void setObservacionesAprendizaje(ArrayList<String> observacionesAprendizaje) {
        this.observacionesAprendizaje = observacionesAprendizaje;
    }
    
    
    
}
