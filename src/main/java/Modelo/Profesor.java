/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

/**
 *
 * @author nicol
 */
@Entity
public class Profesor extends Usuario {
    @OneToOne
    private Curso curso;


    public Profesor(String id, String codigo, String correo, boolean estado, String primernombre, String segundoNombre, String primerApellido, String segundoApellido, String telefono, Token token) {
        super(id, codigo, correo, estado, primernombre, segundoNombre, primerApellido, segundoApellido, telefono, token);
    }
    
    
    
    public Profesor(){
        
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void add(Profesor profesor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void remove(Profesor profesor) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
