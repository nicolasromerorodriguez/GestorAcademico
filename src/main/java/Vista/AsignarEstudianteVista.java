/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.Controladora;
import Modelo.Curso;
import Modelo.Estudiante;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author nicol
 */
public class AsignarEstudianteVista extends JFrame {

    private Controladora control;
    private Curso cursoSeleccionado;

    private JTextField txtBuscar;
    private JButton btnBuscar;
    private JButton btnSeleccionarDeLista;
    private JList<String> listaEstudiantes;
    private DefaultListModel<String> modeloLista;

    public AsignarEstudianteVista(Controladora control, Curso cursoSeleccionado) {
        this.control = control;
        this.cursoSeleccionado = cursoSeleccionado;

        setTitle("Asignar Estudiante al Curso: " + cursoSeleccionado.getNombre());
        setSize(500, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel arriba = new JPanel(new FlowLayout());
        txtBuscar = new JTextField(15);
        btnBuscar = new JButton("Buscar estudiante");
        btnSeleccionarDeLista = new JButton("Lista inicial");

        arriba.add(txtBuscar);
        arriba.add(btnBuscar);
        arriba.add(btnSeleccionarDeLista);

        modeloLista = new DefaultListModel<>();
        listaEstudiantes = new JList<>(modeloLista);

        JButton btnAsignar = new JButton("Asignar al curso");

        add(arriba, BorderLayout.NORTH);
        add(new JScrollPane(listaEstudiantes), BorderLayout.CENTER);
        add(btnAsignar, BorderLayout.SOUTH);

        // EVENTOS
        btnBuscar.addActionListener(e -> buscar());
        btnSeleccionarDeLista.addActionListener(e -> cargarListaInicial());
        btnAsignar.addActionListener(e -> asignar());

        setVisible(true);
    }

    private void buscar() {
        String nombre = txtBuscar.getText();
        Estudiante e = control.buscarEstudiante(nombre);

        modeloLista.clear();
        if (e == null) {
            JOptionPane.showMessageDialog(this, "El estudiante no existe");
        } else {
            modeloLista.addElement(e.getId() + " - " + e.getPrimerNombre() + " " + e.getPrimerApellido());
        }
    }

    private void cargarListaInicial() {
        modeloLista.clear();
        List<Estudiante> lista = control.obtenerEstudiantesSinCurso();
        for (Estudiante e : lista) {
            modeloLista.addElement(e.getId() + " - " + e.getPrimerNombre() + " " + e.getPrimerApellido());
        }
    }

    private void asignar() {
        String seleccionado = listaEstudiantes.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un estudiante");
            return;
        }

        int id = Integer.parseInt(seleccionado.split(" - ")[0]);
        String resultado = control.asignarEstudianteACurso(id, cursoSeleccionado.getId());

        JOptionPane.showMessageDialog(this, resultado);
        dispose(); // regresa a CursosPorGradoVista
    }
}

