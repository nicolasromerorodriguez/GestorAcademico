/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.colegio1;

import Controlador.Controladora;
import Modelo.Token;
import Modelo.Usuario;
//import static Modelo.Token_.id;
import Persistencia.ControladoraPersistencia;
import Vista.FrmMostrarUsuarios;
import Vista.FrmSuperUsuarioInicio;
import Vista.PaginaInicio;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 *
 * @author nicol
 */
public class Colegio1 {

    public static void main(String[] args) {
        //ControladoraPersistencia controlPersist =new ControladoraPersistencia();
        Controladora control = new Controladora();
        
       

        // Luego creas la vista y le pasas la controladora:
        PaginaInicio inicio = new PaginaInicio(control);

        // Muestras la ventana:
        inicio.setVisible(true);
    
        
        //Token token = new Token("1", "Usuario1", "Administracion", "12345");
        //control.crearToken(token);
        //control.eliminarToken("1");
        
        //token.setRol("profesor");
        //control.editarToken(token);
        
        
        /*Token token = control.traerToken("1");
        System.out.print("El token es: " + token.toString());
        
        
        System.out.print(token);
        ArrayList<Token> listaTokens = control.traerListaTokens();
        for(Token tok : listaTokens){
            System.out.println("El token es: " + tok.toString());
        }
        */
        
        //Crear Token
        /*/Token token = new Token("25", "Simar2022", "profesor", "12345");
        
        //Guardar Token en BD
        control.crearToken(token);
        
        //Crear Usuario
        
        Usuario usuario1 = new Usuario("25", "2022", "a@a.com", true, "simar", "enrique", "nose1", "nose2", "300", token);
        
        
        //Guardar usuario en bd
        
        control.crearUsuario(usuario1);*/
        
        
        // crear lista de tokens
        ArrayList<Token> listaTokens = new ArrayList<Token>();
        
        
        
        //crear roles con lista de roles
        
        
        
        
        
        //Guardar Rol en BD
        
        
        //Vista
       
        
        //Temporal para crear usuario
        
         /* Esto asegura que Swing se ejecute en el hilo de interfaz */
        /*javax.swing.SwingUtilities.invokeLater(() -> {
            FrmSuperUsuarioInicio frm = new FrmSuperUsuarioInicio();
            frm.setVisible(true);
        });*/
        /*
        java.awt.EventQueue.invokeLater(new Runnable() {
        public void run() {
            new FrmMostrarUsuarios().setVisible(true);
        } 
    }); 
        */
     
        
        
        
    }
    
}
