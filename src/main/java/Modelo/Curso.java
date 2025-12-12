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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author nicol
 */
@Entity
public class Curso implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private int capacidad;
    private boolean estado;
    private String nombre;
    private Profesor profesor;

    @ManyToOne
    private Grado grado;
    @OneToMany (mappedBy = "cursoEstudiante")
    private ArrayList<Estudiante> listaEstudiantes;
    @ManyToMany
    @JoinTable(
        name = "curso_categoria",                         // nombre de la tabla intermedia
        joinColumns = @JoinColumn(name = "curso_id"),     // FK a Curso
        inverseJoinColumns = @JoinColumn(name = "categoria_id") // FK a CategoriaLogro
    )
    private ArrayList<CategoriaLogro> categorias;

    public Curso() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Grado getGrado() {
        return grado;
    }

    public void setGrado(Grado grado) {
        this.grado = grado;
    }

    public ArrayList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(ArrayList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public ArrayList<CategoriaLogro> getCategorias() {
        return categorias;
    }

    public void setCategorias(ArrayList<CategoriaLogro> categorias) {
        this.categorias = categorias;
    }
    
}
