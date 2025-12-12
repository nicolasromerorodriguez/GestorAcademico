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
import Modelo.Curso;
import Modelo.Estudiante;
import Modelo.Profesor;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProfesorCursoVista extends JFrame {

    private Controladora control;
    private Profesor profesor;
    private Curso curso;

    private JLabel lblNombreCurso;
    private JLabel lblIdCurso;
    private JLabel lblCapacidad;
    private JLabel lblEstado;

    private JList<String> listaEstudiantes;
    private JButton btnGestionarLogros;

    public ProfesorCursoVista(Controladora control, Profesor profesor, Curso curso1) {
        this.control = control;
        this.profesor = profesor;
        this.curso = profesor.getCurso(); // El curso asignado al profesor

        setTitle("Panel del Profesor - Curso Asignado");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarDatosCurso();
        cargarListaEstudiantes();

        setVisible(true);
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        // ------------------- PANEL SUPERIOR (INFO DEL CURSO) -------------------
        JPanel panelCurso = new JPanel();
        panelCurso.setLayout(new BoxLayout(panelCurso, BoxLayout.Y_AXIS));
        panelCurso.setBorder(BorderFactory.createTitledBorder("Información del Curso"));

        lblNombreCurso = new JLabel();
        lblIdCurso = new JLabel();
        lblCapacidad = new JLabel();
        lblEstado = new JLabel();

        panelCurso.add(lblNombreCurso);
        panelCurso.add(lblIdCurso);
        panelCurso.add(lblCapacidad);
        panelCurso.add(lblEstado);

        add(panelCurso, BorderLayout.NORTH);

        // ------------------- PANEL CENTRAL (LISTA DE ESTUDIANTES) -------------------
        JPanel panelLista = new JPanel(new BorderLayout());
        panelLista.setBorder(BorderFactory.createTitledBorder("Estudiantes inscritos"));

        listaEstudiantes = new JList<>();
        JScrollPane scroll = new JScrollPane(listaEstudiantes);
        scroll.setPreferredSize(new Dimension(400, 250));

        panelLista.add(scroll, BorderLayout.CENTER);
        add(panelLista, BorderLayout.CENTER);

        // ------------------- PANEL INFERIOR (BOTÓN) -------------------
        JPanel panelBoton = new JPanel();
        btnGestionarLogros = new JButton("Gestionar Logros");

        
        btnGestionarLogros.addActionListener(e -> {
    new GestionLogrosVista(control, curso);
});

        panelBoton.add(btnGestionarLogros);
        add(panelBoton, BorderLayout.SOUTH);
    }

    private void cargarDatosCurso() {
        if (curso == null) {
            JOptionPane.showMessageDialog(this,
                    "El profesor no tiene un curso asignado.",
                    "Sin curso",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        lblNombreCurso.setText("Nombre: " + curso.getNombre());
        lblIdCurso.setText("ID del curso: " + curso.getId());
        lblCapacidad.setText("Capacidad: " + curso.getCapacidad());
        lblEstado.setText("Estado: " + (curso.isEstado() ? "Activo" : "Inactivo"));
    }

    private void cargarListaEstudiantes() {
        if (curso == null || curso.getListaEstudiantes() == null) {
            listaEstudiantes.setModel(new DefaultListModel<>());
            return;
        }

        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (Estudiante est : curso.getListaEstudiantes()) {
            modelo.addElement(est.getId() + " - " + est.getPrimerNombre() + " " + est.getPrimerApellido());
        }

        listaEstudiantes.setModel(modelo);
    }
}
