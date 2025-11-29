/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author nicol
 */
@Entity
public class Acudiente extends Usuario{
    
    private boolean aceptado;
    @OneToMany (mappedBy = "acudiente")
    private ArrayList<Preinscripcion> listaPrenscripciones;
    @OneToMany (mappedBy = "acudienteEstudiante")
    private ArrayList<Estudiante> listaEstudiantes;
    
    public Acudiente(){
        
    }

    public Acudiente(boolean aceptado, ArrayList<Preinscripcion> listaPrenscripciones, ArrayList<Estudiante> listaEstudiantes) {
        this.aceptado = aceptado;
        this.listaPrenscripciones = listaPrenscripciones;
        this.listaEstudiantes = listaEstudiantes;
    }

    public Acudiente(boolean aceptado, ArrayList<Preinscripcion> listaPrenscripciones, ArrayList<Estudiante> listaEstudiantes, String id, String codigo, String correo, boolean estado, String primernombre, String segundoNombre, String primerApellido, String segundoApellido, String telefono, Token token) {
        super(id, codigo, correo, estado, primernombre, segundoNombre, primerApellido, segundoApellido, telefono, token);
        this.aceptado = aceptado;
        this.listaPrenscripciones = listaPrenscripciones;
        this.listaEstudiantes = listaEstudiantes;
    }

    public ArrayList<Preinscripcion> getListaPrenscripciones() {
        return listaPrenscripciones;
    }

    public void setListaPrenscripciones(ArrayList<Preinscripcion> listaPrenscripciones) {
        this.listaPrenscripciones = listaPrenscripciones;
    }

    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(ArrayList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }
    
    

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }
    
    
    
    
}
