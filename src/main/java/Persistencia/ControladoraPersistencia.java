/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Persistencia;

import Modelo.Acudiente;
import Modelo.Direccion;
import Modelo.Preinscripcion;
import Modelo.Profesor;
import Modelo.Rol;
import Modelo.SuperUsuario;
import Modelo.Token;
import Modelo.Usuario;
import Persistencia.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;

/**
 *
 * @author nicol
 */
public class ControladoraPersistencia {
    
    TokenJpaController tokenJpa = new TokenJpaController();
    //UsuarioJpaController usuarioJpa = new UsuarioJpaController();
    RolJpaController rolJpa = new RolJpaController();
    
    AcudienteJpaController acudienteJpa = new AcudienteJpaController();
    DireccionJpaController direccionJpa = new DireccionJpaController();
    ProfesorJpaController profesorJpa = new ProfesorJpaController();
    SuperUsuarioJpaController superJpa = new SuperUsuarioJpaController();
    
    
    PreinscripcionJpaController preinscripcionJpa = new PreinscripcionJpaController();
    
    EstudianteJpaController estudianteJpa = new EstudianteJpaController();
    HistoriaAcademicaJpaController historiaJpa = new HistoriaAcademicaJpaController();

    public void crearToken(Token token) {
        try {
            tokenJpa.create(token);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarToken(String id) {
        try {
            tokenJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void editarToken(Token token) {
        try {
            tokenJpa.edit(token);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Token traerToken(String id) {
        return tokenJpa.findToken(id); 
    }

    public ArrayList<Token> traerListaTokens() {
        List<Token> lista =  tokenJpa.findTokenEntities();
        ArrayList<Token> listaTokens = new ArrayList<Token> (lista);
        return listaTokens;
    }
    
    
      public Rol traerRol(int idRol) {
        return rolJpa.findRol(idRol);
    }

    //----------------------------------------------------------------Usuario-----------------------------------------------------------------
    /*public void crearUsuario(Usuario usuario) {
        try {
            usuarioJpa.create(usuario);
        } catch (Exception ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarUsuario(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void editarUsuario(Usuario usuario) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public Token traerUsuario(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public ArrayList<Token> traerListaUsuarios() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }*/

    //-----------------------------------------------------------------Rol------------------------------------------------------------------------
    public void crearRol(Rol rol) {
        rolJpa.create(rol);
    }

    public void eliminarRol(int id) {
try {
            rolJpa.destroy(id);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
    
    
    
    //-------------------------------------------Login-------------------------------------------------
    
    
    
    public Token buscarToken(String usuario, String contrasena, int rolId) {
    try {
        EntityManager em = tokenJpa.getEntityManager();

        Token token = em.createQuery(
            "SELECT t FROM Token t "
          + "WHERE t.nombreUsuario = :usuario "
          + "AND t.contrasena = :pass "
          + "AND t.rol.id = :rolId",
            Token.class
        )
        .setParameter("usuario", usuario)
        .setParameter("pass", contrasena)
        .setParameter("rolId", rolId)
        .getSingleResult();

        return token;

    } catch (Exception e) {
        return null; // No encontrado
    }
}

//--------------------------------------------- Crear usuario--------------------
    
   public void crearUsuario(Usuario usu, String tipo) {

    switch(tipo){
        case "SUPER":
        {
            try {
                superJpa.create((SuperUsuario) usu);
            } catch (Exception ex) {
                Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;

        case "PROF":
        {
            try {
                profesorJpa.create((Profesor) usu);
            } catch (Exception ex) {
                Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;

        case "DIR":
        {
            try {
                direccionJpa.create((Direccion) usu);
            } catch (Exception ex) {
                Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;

        case "ACUD":
        {
            try {
                acudienteJpa.create((Acudiente) usu);
            } catch (Exception ex) {
                Logger.getLogger(ControladoraPersistencia.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
            break;

    }
}

    //---------------------------------------------------------Mostrar usuario---------------------------------------------
    
    public List<Usuario> traerUsuarios() {
    List<Usuario> lista = new ArrayList<>();

    lista.addAll(superJpa.findSuperUsuarioEntities());
    lista.addAll(profesorJpa.findProfesorEntities());
    lista.addAll(acudienteJpa.findAcudienteEntities());

    return lista;
}



    
   public Usuario traerUsuario(String id) {

    SuperUsuario su = superJpa.findSuperUsuario(id);
    if (su != null) return su;

    Profesor pr = profesorJpa.findProfesor(id);
    if (pr != null) return pr;

    Acudiente ac = acudienteJpa.findAcudiente(id);
    if (ac != null) return ac;

    return null;
}


public void deshabilitarUsuario(String id) {
    Usuario u = traerUsuario(id);

    if (u == null) return;

    u.setEstado(false);

    if (u instanceof SuperUsuario su) {
        try { superJpa.edit(su); } catch (Exception e) {}
    }
    else if (u instanceof Profesor pr) {
        try { profesorJpa.edit(pr); } catch (Exception e) {}
    }
    else if (u instanceof Acudiente ac) {
        try { acudienteJpa.edit(ac); } catch (Exception e) {}
    }
}

//-------------------------------------Habilitar------------------------------------------------------

public void habilitarUsuario(String id) {
    Usuario u = traerUsuario(id);

    if (u == null) return;

    u.setEstado(true);

    try {
        if (u instanceof SuperUsuario su) superJpa.edit(su);
        else if (u instanceof Profesor pr) profesorJpa.edit(pr);
        else if (u instanceof Acudiente ac) acudienteJpa.edit(ac);
        else if (u instanceof Direccion di) direccionJpa.edit(di);
    } catch (Exception e) {
        throw new RuntimeException("Error de conexión con la base de datos", e);
    }
}

//-----------------------------Preinscripcoon-----------------

public void crearPreinscripcion(Preinscripcion pre) {
    try {
        preinscripcionJpa.create(pre);
    } catch (Exception e) {
        throw new RuntimeException("Error guardando Preinscripción en la BD", e);
    }
}

}
