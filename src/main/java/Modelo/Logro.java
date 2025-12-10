/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author nicol
 */
@Entity
public class Logro implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String nombre;
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;
    @ManyToOne
    private CategoriaLogro categoriaLogro;

    public Logro() {
    }

    public Logro(int id, String nombre, Date fechaCreacion, CategoriaLogro categoriaLogro) {
        this.id = id;
        this.nombre = nombre;
        this.fechaCreacion = fechaCreacion;
        this.categoriaLogro = categoriaLogro;
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

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public CategoriaLogro getCategoriaLogro() {
        return categoriaLogro;
    }

    public void setCategoriaLogro(CategoriaLogro categoriaLogro) {
        this.categoriaLogro = categoriaLogro;
    }
    
    
    
}
