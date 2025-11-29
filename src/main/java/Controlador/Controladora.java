/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Acudiente;
import Modelo.Direccion;
import Modelo.Profesor;
import Modelo.Rol;
import Modelo.SuperUsuario;
import Modelo.Token;
import Modelo.Usuario;
import Persistencia.ControladoraPersistencia;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author nicol
 */
public class Controladora {
    ControladoraPersistencia controlPersis = new ControladoraPersistencia();
    
    
    public void crearToken(Token token){
        
        controlPersis.crearToken(token);
    } 
    
    public void eliminarToken(String id){
        controlPersis.eliminarToken(id);
    }
    
    public void editarToken(Token token){
        controlPersis.editarToken(token);
    }
    
    public Token traerToken(String id){
        return controlPersis.traerToken(id);
    }
    
    public ArrayList<Token> traerListaTokens(){
        return controlPersis.traerListaTokens();
    }
    
    
    
    /*
    //Usuario
    public void crearUsuario(Usuario usuario){
        
        controlPersis.crearUsuario(usuario);
    } 
    
    public void eliminarUsuario(String id){
        controlPersis.eliminarUsuario(id);
    }
    
    public void editarUsuario(Usuario usuario){
        controlPersis.editarUsuario(usuario);
    }
    
    public Token traerUsuario(String id){
        return controlPersis.traerUsuario(id);
    }
    
    public ArrayList<Token> traerListaUsuarios(){
        return controlPersis.traerListaUsuarios();
    }
    */
    
    public Token validarLogin(String usuario, String contrasena, int rol) {
        return controlPersis.buscarToken(usuario, contrasena, rol);
    }
    
    //Rol
    public void crearRol(Rol rol){
        
        controlPersis.crearRol(rol);
    } 
    
    public void eliminarRol(int id){
        controlPersis.eliminarRol(id);
    }
    
    
    //------------------------------------------------------Crear usuario---------------------------------------
    
     ValidadorCampos validador = new ValidadorCampos();

    public String crearUsuario(
            String id, 
            String correo,
            String contrasena,
            int idRol,
            String nombre,
            String apellido,
            String tipoUsuario
    ) {

        try {

            // 1. Validación de datos (del diagrama)
            validador.validarCampos(correo, contrasena, nombre, apellido);

            // 2. Obtener el rol
            Rol rol = controlPersis.traerRol(idRol);
            if (rol == null) 
                throw new Exception("El rol seleccionado no existe");

            // 3. Crear token
            Token token = new Token();
            token.setId(UUID.randomUUID().toString());
            token.setNombreUsuario(correo);
            token.setContrasena(contrasena);
            token.setRol(rol);

            controlPersis.crearToken(token);

            // 4. Crear usuario según el tipo especificado
            Usuario usuario;

            switch (tipoUsuario) {
                case "SUPER":
                    usuario = new SuperUsuario();
                    break;
                case "PROF":
                    usuario = new Profesor();
                    break;
                case "DIR":
                    usuario = new Direccion();
                    break;
                case "ACUD":
                    usuario = new Acudiente();
                    break;
                default:
                    throw new Exception("Tipo de usuario no válido");
            }

            usuario.setId(id);
            usuario.setCorreo(correo);
            usuario.setPrimernombre(nombre);
            usuario.setPrimerApellido(apellido);
            usuario.setToken(token);
            usuario.setEstado(true);

            // 5. Guardar usuario (del diagrama: "Agregar usuario a BD")
            controlPersis.crearUsuario(usuario, tipoUsuario);

            // 6. Si todo sale bien
            return "Usuario creado con éxito";

        } catch (Exception e) {

            // Error de validación → mensaje "datos incorrectos"
            return "Error al crear usuario: " + e.getMessage();
        }
    }
    
    //---------------------------------------------------Mostrar usuario-------------------------------------------------
    
    
    public List<Usuario> traerUsuarios() {
    return controlPersis.traerUsuarios();
}

public Usuario traerUsuario(String id) {
    return controlPersis.traerUsuario(id);
}

public void deshabilitarUsuario(String id) {
    controlPersis.deshabilitarUsuario(id);
}

//-----------------------------Habilitar--------------------------
public void habilitarUsuario(String id) {
    controlPersis.habilitarUsuario(id);
}




}
