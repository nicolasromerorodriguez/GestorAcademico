/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

/**
 *
 * @author nicol
 */
public class ValidadorCampos {

    public void validarCampos(String correo, String contrasena, String nombre, String apellido) 
            throws Exception {

        if (correo == null || !correo.contains("@"))
            throw new Exception("Correo inválido");

        if (contrasena == null || contrasena.length() < 8)
            throw new Exception("La contraseña debe tener al menos 8 caracteres");

        if (nombre == null || nombre.isBlank())
            throw new Exception("Debe ingresar nombre");

        if (apellido == null || apellido.isBlank())
            throw new Exception("Debe ingresar apellido");
    }
}

