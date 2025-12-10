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

/**
 *
 * @author nicol
 */
@Entity
public class Boletin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
        @ManyToMany
    @JoinTable(
            name = "boletin_categoria",
            joinColumns = @JoinColumn(name = "id_boletin"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private ArrayList<CategoriaLogro> categoriaLogro;
    @ManyToOne
    private HistoriaAcademica historiaAcademica;

    public Boletin() {
    }

    public Boletin(int id, ArrayList<CategoriaLogro> categoriaLogro, HistoriaAcademica historiaAcademica) {
        this.id = id;
        this.categoriaLogro = categoriaLogro;
        this.historiaAcademica = historiaAcademica;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<CategoriaLogro> getCategoriaLogro() {
        return categoriaLogro;
    }

    public void setCategoriaLogro(ArrayList<CategoriaLogro> categoriaLogro) {
        this.categoriaLogro = categoriaLogro;
    }

    public HistoriaAcademica getHistoriaAcademica() {
        return historiaAcademica;
    }

    public void setHistoriaAcademica(HistoriaAcademica historiaAcademica) {
        this.historiaAcademica = historiaAcademica;
    }

    
    
}
