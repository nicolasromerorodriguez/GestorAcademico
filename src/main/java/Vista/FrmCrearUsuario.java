/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author nicol
 */
import javax.swing.*;
import java.awt.*;
import Controlador.Controladora;

public class FrmCrearUsuario extends JFrame {

    private JTextField txtId, txtCorreo, txtNombre, txtApellido, txtRol;
    private JPasswordField txtContrasena;
    private JComboBox<String> cmbTipo;

    Controladora control = new Controladora();

    public FrmCrearUsuario() {

        setTitle("Crear Usuario");
        setSize(450, 350);
        setLayout(new GridLayout(8, 2, 10, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Campos del formulario
        add(new JLabel("ID del usuario:"));
        txtId = new JTextField();
        add(txtId);

        add(new JLabel("Correo:"));
        txtCorreo = new JTextField();
        add(txtCorreo);

        add(new JLabel("Contraseña:"));
        txtContrasena = new JPasswordField();
        add(txtContrasena);

        add(new JLabel("Primer Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Primer Apellido:"));
        txtApellido = new JTextField();
        add(txtApellido);

        add(new JLabel("ID del Rol:"));
        txtRol = new JTextField();
        add(txtRol);

        add(new JLabel("Tipo de Usuario:"));
        cmbTipo = new JComboBox<>(new String[]{"SUPER", "PROF", "DIR", "ACUD"});
        add(cmbTipo);

        JButton btnAceptar = new JButton("Aceptar");
        JButton btnCancelar = new JButton("Cancelar");

        add(btnAceptar);
        add(btnCancelar);

        // Acciones
        btnAceptar.addActionListener(e -> crearUsuario());
        btnCancelar.addActionListener(e -> cancelar());
    }

    private void cancelar() {
        new FrmSuperUsuarioInicio(control).setVisible(true);
        dispose();
    }

    private void crearUsuario() {

        // Capturar datos
        String id = txtId.getText();
        String correo = txtCorreo.getText();
        String contrasena = String.valueOf(txtContrasena.getPassword());
        String nombre = txtNombre.getText();
        String apellido = txtApellido.getText();
        int idRol = Integer.parseInt(txtRol.getText());
        String tipo = (String) cmbTipo.getSelectedItem();

        // Llamar a la lógica (diagrama completo)
        String resultado = control.crearUsuario(
                id,
                correo,
                contrasena,
                idRol,
                nombre,
                apellido,
                tipo
        );

        // Mostrar el mensaje (parte del diagrama)
        JOptionPane.showMessageDialog(this, resultado);

        // Si todo fue bien se vuelve al inicio
        if (resultado.contains("éxito")) {
            new FrmSuperUsuarioInicio(control).setVisible(true);
            dispose();
        }
    }
}
