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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author nicol
 */
@Entity
public class Estudiante implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    private Acudiente acudienteEstudiante;
    private boolean aceptado;
    private String codigo;
    @OneToOne
    private HistoriaAcademica historiaAcademica;
    @ManyToOne
    private Curso cursoEstudiante;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
   @OneToOne
    private Observador observador;

    public Estudiante() {
    }

    public Estudiante(int id, Acudiente acudienteEstudiante, boolean aceptado, String codigo, HistoriaAcademica historiaAcademica, Curso cursoEstudiante, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, Observador observador) {
        this.id = id;
        this.acudienteEstudiante = acudienteEstudiante;
        this.aceptado = aceptado;
        this.codigo = codigo;
        this.historiaAcademica = historiaAcademica;
        this.cursoEstudiante = cursoEstudiante;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.observador = observador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Acudiente getAcudienteEstudiante() {
        return acudienteEstudiante;
    }

    public void setAcudienteEstudiante(Acudiente acudienteEstudiante) {
        this.acudienteEstudiante = acudienteEstudiante;
    }

    public boolean isAceptado() {
        return aceptado;
    }

    public void setAceptado(boolean aceptado) {
        this.aceptado = aceptado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public HistoriaAcademica getHistoriaAcademica() {
        return historiaAcademica;
    }

    public void setHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademica = historiaAcademica;
    }

    public Curso getCursoEstudiante() {
        return cursoEstudiante;
    }

    public void setCursoEstudiante(Curso cursoEstudiante) {
        this.cursoEstudiante = cursoEstudiante;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public void setPrimerNombre(String primerNombre) {
        this.primerNombre = primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public void setSegundoNombre(String segundoNombre) {
        this.segundoNombre = segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public void setPrimerApellido(String primerApellido) {
        this.primerApellido = primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public void setSegundoApellido(String segundoApellido) {
        this.segundoApellido = segundoApellido;
    }

    public Observador getObservador() {
        return observador;
    }

    public void setObservador(Observador observador) {
        this.observador = observador;
    }

    public void add(Estudiante estudiante) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void remove(Estudiante estudiante) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    
    
}
