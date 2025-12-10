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
import javax.persistence.OneToOne;

/**
 *
 * @author nicol
 */
@Entity
public class Observador implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private ArrayList<String> ListaAnotaciones;
    @OneToOne
    private HojaDeVida hojaDeVida;

    public Observador() {
    }

    public Observador(int id, ArrayList<String> ListaAnotaciones, HojaDeVida hojaDeVida) {
        this.id = id;
        this.ListaAnotaciones = ListaAnotaciones;
        this.hojaDeVida = hojaDeVida;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<String> getListaAnotaciones() {
        return ListaAnotaciones;
    }

    public void setListaAnotaciones(ArrayList<String> ListaAnotaciones) {
        this.ListaAnotaciones = ListaAnotaciones;
    }

    public HojaDeVida getHojaDeVida() {
        return hojaDeVida;
    }

    public void setHojaDeVida(HojaDeVida hojaDeVida) {
        this.hojaDeVida = hojaDeVida;
    }
    
    
}
