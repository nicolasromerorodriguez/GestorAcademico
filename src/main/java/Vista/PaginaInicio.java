/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import javax.swing.*;
import java.awt.*;
import Controlador.Controladora;
import Modelo.Token;

public class PaginaInicio extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JComboBox<String> comboRol;
    private JButton btnLogin;
    private JButton btnPreinscripcion;

    private Controladora control;

    public PaginaInicio(Controladora control) {
        this.control = control;

        setTitle("Sistema de Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Usuario
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Usuario:"), gbc);

        txtUsuario = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 0;
        panel.add(txtUsuario, gbc);

        // Contraseña
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Contraseña:"), gbc);

        txtPassword = new JPasswordField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        panel.add(txtPassword, gbc);

        // Rol
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Rol:"), gbc);

        comboRol = new JComboBox<>(new String[]{
                "1",
                "2",
                "3",
                "4"
        });
        gbc.gridx = 1; gbc.gridy = 2;
        panel.add(comboRol, gbc);

        // Botón Login
        btnLogin = new JButton("Ingresar");
        gbc.gridx = 1; gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(btnLogin, gbc);

        // Botón Preinscripción sin login
        btnPreinscripcion = new JButton("Preinscripción");
        gbc.gridx = 1; gbc.gridy = 4;
        panel.add(btnPreinscripcion, gbc);

        add(panel);

        // LISTENERS
        btnLogin.addActionListener(e -> iniciarSesion());
        btnPreinscripcion.addActionListener(e -> abrirFormularioPreinscripcion());
    }

    private void iniciarSesion() {
        String usuario = txtUsuario.getText().trim();
        String contrasena = new String(txtPassword.getPassword());
        int rolSeleccionado = Integer.parseInt(comboRol.getSelectedItem().toString());

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe llenar usuario y contraseña", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Token token = control.validarLogin(usuario, contrasena, rolSeleccionado);

        if (token == null) {
            JOptionPane.showMessageDialog(this, "Usuario, contraseña o rol incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso");
        abrirVentanaPrincipal();
        this.dispose();
    }

    private void abrirVentanaPrincipal() {
        JFrame ventana = new JFrame("Bienvenido al sistema");
        ventana.setSize(400, 200);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        ventana.add(new JLabel("Sesión iniciada correctamente", SwingConstants.CENTER));
        ventana.setVisible(true);
    }

    private void abrirFormularioPreinscripcion() {
        FormularioPreinscripcion form = new FormularioPreinscripcion(control);
        form.setVisible(true);
        this.dispose();
    }

}
