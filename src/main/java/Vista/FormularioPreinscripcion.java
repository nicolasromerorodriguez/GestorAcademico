/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

/**
 *
 * @author nicol
 */



import Controlador.Controladora;
import java.util.Date;
import javax.swing.JOptionPane;

import Modelo.Preinscripcion;

import Modelo.Preinscripcion;

public class FormularioPreinscripcion extends javax.swing.JFrame {

    Controladora control = new Controladora();

    public FormularioPreinscripcion(Controladora control1) {
        initComponents();
        this.setLocationRelativeTo(null);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Formulario de Preinscripción");

        lblTitulo = new javax.swing.JLabel("Formulario de Preinscripción");
        lblTitulo.setFont(new java.awt.Font("Dialog", 1, 20));

        lblNombreAcudiente = new javax.swing.JLabel("Nombre Acudiente:");
        txtNombreAcudiente = new javax.swing.JTextField();

        lblNombreAspirante = new javax.swing.JLabel("Nombre Aspirante:");
        txtNombreAspirante = new javax.swing.JTextField();

        lblCorreo = new javax.swing.JLabel("Correo:");
        txtCorreo = new javax.swing.JTextField();

        lblContacto = new javax.swing.JLabel("Contacto:");
        txtContacto = new javax.swing.JTextField();

        lblEdad = new javax.swing.JLabel("Edad del Aspirante:");
        txtEdad = new javax.swing.JTextField();

        lblGrado = new javax.swing.JLabel("Grado solicitado:");
        txtGrado = new javax.swing.JTextField();

        btnEnviar = new javax.swing.JButton("Enviar");
        btnVolver = new javax.swing.JButton("Volver al Menú");

        btnEnviar.addActionListener(evt -> {
            enviarPreinscripcion();
        });

        btnVolver.addActionListener(evt -> {
            volverMenuPrincipal();
        });

        // --- Layout (rápido con GroupLayout) ---
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addComponent(lblTitulo)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup()
                    .addComponent(lblNombreAcudiente)
                    .addComponent(lblNombreAspirante)
                    .addComponent(lblCorreo)
                    .addComponent(lblContacto)
                    .addComponent(lblEdad)
                    .addComponent(lblGrado))
                .addGroup(layout.createParallelGroup()
                    .addComponent(txtNombreAcudiente)
                    .addComponent(txtNombreAspirante)
                    .addComponent(txtCorreo)
                    .addComponent(txtContacto)
                    .addComponent(txtEdad)
                    .addComponent(txtGrado)))
            .addComponent(btnEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(btnVolver, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
            .addGap(10)
            .addComponent(lblTitulo)
            .addGap(15)
            .addGroup(layout.createParallelGroup()
                .addComponent(lblNombreAcudiente)
                .addComponent(txtNombreAcudiente))
            .addGroup(layout.createParallelGroup()
                .addComponent(lblNombreAspirante)
                .addComponent(txtNombreAspirante))
            .addGroup(layout.createParallelGroup()
                .addComponent(lblCorreo)
                .addComponent(txtCorreo))
            .addGroup(layout.createParallelGroup()
                .addComponent(lblContacto)
                .addComponent(txtContacto))
            .addGroup(layout.createParallelGroup()
                .addComponent(lblEdad)
                .addComponent(txtEdad))
            .addGroup(layout.createParallelGroup()
                .addComponent(lblGrado)
                .addComponent(txtGrado))
            .addGap(15)
            .addComponent(btnEnviar)
            .addComponent(btnVolver)
        );

        pack();
    }

    private void enviarPreinscripcion() {
        Preinscripcion pre = new Preinscripcion();

        pre.setNombreAcudiente(txtNombreAcudiente.getText());
        pre.setNombreAspirante(txtNombreAspirante.getText());
        pre.setCorreo(txtCorreo.getText());
        pre.setContacto(txtContacto.getText());
        pre.setEdadAspirante(Integer.parseInt(txtEdad.getText()));
        pre.setGradoSolicitado(txtGrado.getText());
        pre.setFechasolicitud(new Date());

        control.crearPreinscripcion(pre);

        int opcion = JOptionPane.showConfirmDialog(
                this,
                "¿Desea ingresar otra preinscripción?",
                "Registro Exitoso",
                JOptionPane.YES_NO_OPTION
        );

        if (opcion == JOptionPane.YES_OPTION) {
            limpiarFormulario();
        } else {
            volverMenuPrincipal();
        }
    }

    private void limpiarFormulario() {
        txtNombreAcudiente.setText("");
        txtNombreAspirante.setText("");
        txtCorreo.setText("");
        txtContacto.setText("");
        txtEdad.setText("");
        txtGrado.setText("");
    }

    private void volverMenuPrincipal() {
        // Aquí usaré la clase Menu cuando me la envíes
        this.dispose();
    }

    // Variables
    private javax.swing.JButton btnEnviar;
    private javax.swing.JButton btnVolver;
    private javax.swing.JLabel lblTitulo, lblNombreAcudiente, lblNombreAspirante,
            lblCorreo, lblEdad, lblContacto, lblGrado;
    private javax.swing.JTextField txtCorreo, txtEdad, txtContacto, txtNombreAcudiente, txtNombreAspirante, txtGrado;
}
