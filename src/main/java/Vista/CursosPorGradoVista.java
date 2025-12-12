/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
// Código actualizado de CursosPorGradoVista con lógica para crear curso

package Vista;

import Controlador.Controladora;
import Modelo.Curso;
import Modelo.Estudiante;
import Modelo.Grado;
import Modelo.Profesor;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.*;

public class CursosPorGradoVista extends JFrame {

    private Controladora control;
    private Grado gradoSeleccionado;
    private JList<String> listaCursos;
    private JButton btnCrearCurso;
    private JButton btnAgregarEstudiante;

    public CursosPorGradoVista(Controladora control, Grado grado) {
        this.control = control;
        this.gradoSeleccionado = grado;

        setTitle("Cursos del grado: " + grado.getNombre());
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarCursos();

        setVisible(true);
    }

    private void inicializarComponentes() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal);

        JLabel lblTitulo = new JLabel(
                "Cursos del Grado " + gradoSeleccionado.getNombre(),
                SwingConstants.CENTER
        );
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(18f));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        listaCursos = new JList<>();
        JScrollPane scroll = new JScrollPane(listaCursos);
        scroll.setPreferredSize(new Dimension(380, 250));
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        JPanel panelBotones = new JPanel();
        btnCrearCurso = new JButton("Crear curso");
        btnAgregarEstudiante = new JButton("Añadir estudiante a curso");

        panelBotones.add(btnCrearCurso);
        panelBotones.add(btnAgregarEstudiante);

        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        btnCrearCurso.addActionListener((ActionEvent e) -> crearCurso());
        
        btnAgregarEstudiante.addActionListener(e -> {
    Curso curso = obtenerCursoSeleccionado();
    if (curso == null) {
        JOptionPane.showMessageDialog(this, "Seleccione un curso primero");
        return;
    }
    new AsignarEstudianteVista(control, curso);
});

    }
    
    
    private Curso obtenerCursoSeleccionado() {
        String seleccionado = listaCursos.getSelectedValue();
        if (seleccionado == null) return null;

        // El formato es "id - nombre"
        try {
            int idCurso = Integer.parseInt(seleccionado.split(" - ")[0]);
            return control.obtenerCurso(idCurso);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private void cargarCursos() {
        List<Curso> cursos = control.obtenerCursosPorGrado(gradoSeleccionado.getId());

        DefaultListModel<String> modelo = new DefaultListModel<>();
        for (Curso c : cursos) {
            modelo.addElement(c.getId() + " - " + c.getNombre());
        }
        listaCursos.setModel(modelo);
    }

    private void crearCurso() {

    // 1. Solicitar nombre del curso
    String nombreCurso = JOptionPane.showInputDialog(
            this,
            "Ingrese el nombre del curso:",
            "Nuevo Curso",
            JOptionPane.PLAIN_MESSAGE
    );

    if (nombreCurso == null || nombreCurso.trim().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Debe ingresar un nombre para el curso.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 2. Obtener profesor disponible
    Profesor profesorDisponible = control.obtenerProfesorSinCurso();

    if (profesorDisponible == null) {
        JOptionPane.showMessageDialog(this,
                "No hay profesores disponibles para asignar al curso.",
                "Sin profesores",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // 3. Obtener estudiantes disponibles
    List<Estudiante> estudiantesLibres = control.obtenerEstudiantesSinCurso();

    // Evitar null y garantizar que sea mutable
    if (estudiantesLibres == null) {
        estudiantesLibres = new ArrayList<>();
    }

    if (estudiantesLibres.isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "No hay estudiantes disponibles para asignar.",
                "Sin estudiantes",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Mezclar lista para que sea aleatorio
    Collections.shuffle(estudiantesLibres);

    // Seleccionar hasta 10 estudiantes
    int cupo = Math.min(10, estudiantesLibres.size());
    List<Estudiante> estudiantesAsignados = new ArrayList<>(estudiantesLibres.subList(0, cupo));

    // 4. Crear objeto curso
    Curso nuevo = new Curso();
    nuevo.setNombre(nombreCurso);
    nuevo.setCapacidad(10);
    nuevo.setGrado(gradoSeleccionado); 
    nuevo.setProfesor(profesorDisponible);

    // Determinar estado
    boolean activo = cupo >= 5;
    nuevo.setEstado(activo);

    // 5. Persistir el curso
    control.crearCurso(nuevo);

    // 6. Asignar el curso al profesor
    profesorDisponible.setCurso(nuevo);
    control.editarProfesor(profesorDisponible);

    // 7. Asignar curso a los estudiantes seleccionados
    for (Estudiante est : estudiantesAsignados) {
        est.setCursoEstudiante(nuevo);
        control.editarEstudiante(est);
    }

    // 8. Mensaje final
    JOptionPane.showMessageDialog(this,
            "Curso creado correctamente.\n" +
            "Nombre: " + nombreCurso + "\n" +
            "Estudiantes asignados: " + cupo + "\n" +
            "Estado: " + (activo ? "Activo" : "Inactivo"),
            "Curso creado",
            JOptionPane.INFORMATION_MESSAGE);

    // 9. Actualizar tabla/lista en la vista
    cargarCursos();
}

}
