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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

/**
 *
 * @author nicol
 */
@Entity
public class CategoriaLogro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    @OneToMany (mappedBy = "categoriaLogro")
    private ArrayList<Logro> listaLogros;
    //  Relación ManyToMany inversa
    @ManyToMany(mappedBy = "categorias")
    private ArrayList<Curso> cursos;
    //  ManyToMany inversa (NO propietario)
    @ManyToMany(mappedBy = "categoriaLogro")
    private ArrayList<Boletin> boletines;

    public CategoriaLogro() {
    }

    public CategoriaLogro(int id, String nombre, ArrayList<Logro> listaLogros, ArrayList<Curso> cursos, ArrayList<Boletin> boletines) {
        this.id = id;
        this.nombre = nombre;
        this.listaLogros = listaLogros;
        this.cursos = cursos;
        this.boletines = boletines;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Logro> getListaLogros() {
        return listaLogros;
    }

    public void setListaLogros(ArrayList<Logro> listaLogros) {
        this.listaLogros = listaLogros;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public void setCursos(ArrayList<Curso> cursos) {
        this.cursos = cursos;
    }

    public ArrayList<Boletin> getBoletines() {
        return boletines;
    }

    public void setBoletines(ArrayList<Boletin> boletines) {
        this.boletines = boletines;
    }
    
    
}
