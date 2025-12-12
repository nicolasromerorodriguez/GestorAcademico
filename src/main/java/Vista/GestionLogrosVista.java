/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.Controladora;
import Modelo.CategoriaLogro;
import Modelo.Curso;
import Modelo.Logro;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GestionLogrosVista extends JFrame {

    private Controladora control;
    private Curso curso;

    private JPanel panelCategorias;
    private JList<String> listaLogros;
    private DefaultListModel<String> modeloListaLogros;

    private JButton btnCrearLogro;
    private JButton btnEliminarLogro;

    // Para saber cuál categoría está seleccionada actualmente
    private CategoriaLogro categoriaSeleccionada;

    public GestionLogrosVista(Controladora control, Curso curso) {
        this.control = control;
        this.curso = curso;

        setTitle("Gestión de Logros - Curso: " + curso.getNombre());
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarCategorias();

        setVisible(true);
    }

    private void inicializarComponentes() {

        setLayout(new BorderLayout());

        // ----------------- PANEL SUPERIOR: BOTONES DE CATEGORÍA -----------------
        panelCategorias = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelCategorias.setBorder(BorderFactory.createTitledBorder("Categorías"));

        add(panelCategorias, BorderLayout.NORTH);

        // ----------------- PANEL CENTRAL: LISTA DE LOGROS -----------------
        modeloListaLogros = new DefaultListModel<>();
        listaLogros = new JList<>(modeloListaLogros);
        listaLogros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollLogros = new JScrollPane(listaLogros);
        scrollLogros.setBorder(BorderFactory.createTitledBorder("Logros"));

        add(scrollLogros, BorderLayout.CENTER);

        // ----------------- PANEL INFERIOR: BOTONES -----------------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        btnCrearLogro = new JButton("Crear Logro");
        btnEliminarLogro = new JButton("Eliminar Logro");

        // Sin lógica por ahora
        btnCrearLogro.addActionListener(e ->
                JOptionPane.showMessageDialog(this, "Función Crear Logro aún no implementada")
        );
        btnEliminarLogro.addActionListener(e -> eliminarLogroSeleccionado());

        panelBotones.add(btnCrearLogro);
        panelBotones.add(btnEliminarLogro);

        add(panelBotones, BorderLayout.SOUTH);
    }

    // ------------------------------------------------------------
    // CARGAR CATEGORÍAS DEL CURSO
    // ------------------------------------------------------------
    private void cargarCategorias() {
        panelCategorias.removeAll();

        ArrayList<CategoriaLogro> categorias = curso.getCategorias();

        if (categorias == null || categorias.isEmpty()) {
            panelCategorias.add(new JLabel("El curso no tiene categorías de logros."));
            panelCategorias.revalidate();
            panelCategorias.repaint();
            return;
        }

        for (CategoriaLogro cat : categorias) {
            JButton btnCategoria = new JButton(cat.getNombre());
            btnCategoria.addActionListener(e -> mostrarLogrosDeCategoria(cat));
            panelCategorias.add(btnCategoria);
        }

        panelCategorias.revalidate();
        panelCategorias.repaint();
    }

    // ------------------------------------------------------------
    // MOSTRAR LOGROS SEGÚN CATEGORÍA SELECCIONADA
    // ------------------------------------------------------------
    private void mostrarLogrosDeCategoria(CategoriaLogro categoria) {

        this.categoriaSeleccionada = categoria;

        modeloListaLogros.clear();

        if (categoria.getListaLogros() == null || categoria.getListaLogros().isEmpty()) {
            modeloListaLogros.addElement("No hay logros en esta categoría.");
            return;
        }

        for (Logro logro : categoria.getListaLogros()) {
            modeloListaLogros.addElement(logro.getId() + " - " + logro.getNombre());
        }
    }
    
    
    private void eliminarLogroSeleccionado() {

    if (categoriaSeleccionada == null) {
        JOptionPane.showMessageDialog(this,
                "Seleccione una categoría primero.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    int index = listaLogros.getSelectedIndex();

    if (index == -1) {
        JOptionPane.showMessageDialog(this,
                "Seleccione un logro para eliminar.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Evitar el mensaje "No hay logros en esta categoría"
    if (categoriaSeleccionada.getListaLogros().isEmpty()) {
        JOptionPane.showMessageDialog(this,
                "Esta categoría no contiene logros.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        return;
    }

    Logro logro = categoriaSeleccionada.getListaLogros().get(index);

    int confirm = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de que desea eliminar el logro?\n" +
            logro.getNombre(),
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION
    );

    if (confirm != JOptionPane.YES_OPTION) return;

    // Llamar a la controladora para borrar el logro
    control.eliminarLogro(logro);

    // También eliminarlo de la lista en memoria
    categoriaSeleccionada.getListaLogros().remove(logro);

    // Refrescar lista
    mostrarLogrosDeCategoria(categoriaSeleccionada);

    JOptionPane.showMessageDialog(this,
            "Logro eliminado correctamente.",
            "Éxito",
            JOptionPane.INFORMATION_MESSAGE);
}

}
