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
public class Preinscripcion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String contacto;
    private String correo;
    private int edadAspirante;
    @Temporal(TemporalType.DATE)
    private Date fechasolicitud;
    private String gradoSolicitado;
    private String nombreAcudiente;
    private String nombreAspirante;
    @ManyToOne
    private Acudiente acudiente;

    public Preinscripcion() {
    }

    public Preinscripcion(int id, String contacto, String correo, int edadAspirante, Date fechasolicitud, String gradoSolicitado, String nombreAcudiente, String nombreAspirante, Acudiente acudiente) {
        this.id = id;
        this.contacto = contacto;
        this.correo = correo;
        this.edadAspirante = edadAspirante;
        this.fechasolicitud = fechasolicitud;
        this.gradoSolicitado = gradoSolicitado;
        this.nombreAcudiente = nombreAcudiente;
        this.nombreAspirante = nombreAspirante;
        this.acudiente = acudiente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getEdadAspirante() {
        return edadAspirante;
    }

    public void setEdadAspirante(int edadAspirante) {
        this.edadAspirante = edadAspirante;
    }

    public Date getFechasolicitud() {
        return fechasolicitud;
    }

    public void setFechasolicitud(Date fechasolicitud) {
        this.fechasolicitud = fechasolicitud;
    }

    public String getGradoSolicitado() {
        return gradoSolicitado;
    }

    public void setGradoSolicitado(String gradoSolicitado) {
        this.gradoSolicitado = gradoSolicitado;
    }

    public String getNombreAcudiente() {
        return nombreAcudiente;
    }

    public void setNombreAcudiente(String nombreAcudiente) {
        this.nombreAcudiente = nombreAcudiente;
    }

    public String getNombreAspirante() {
        return nombreAspirante;
    }

    public void setNombreAspirante(String nombreAspirante) {
        this.nombreAspirante = nombreAspirante;
    }

    public Acudiente getAcudiente() {
        return acudiente;
    }

    public void setAcudiente(Acudiente acudiente) {
        this.acudiente = acudiente;
    }
    
    
    
    
    
}
