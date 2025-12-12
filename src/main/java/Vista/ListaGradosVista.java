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
import Modelo.Grado;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;
import javax.swing.*;

public class ListaGradosVista extends JFrame {

    private Controladora control;
    private JList<String> listaGrados;
    private JButton btnCrearCategoria;

    public ListaGradosVista(Controladora control) {
        this.control = control;

        setTitle("Gestión de Grados");
        setSize(400, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        inicializarComponentes();
        cargarGradosEnLista();

        setVisible(true);
    }

    private void inicializarComponentes() {
        // --- Panel principal ---
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        add(panelPrincipal);

        // --- Etiqueta ---
        JLabel lblTitulo = new JLabel("Lista de Grados", SwingConstants.CENTER);
        lblTitulo.setFont(lblTitulo.getFont().deriveFont(18f));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // --- Lista de grados ---
        listaGrados = new JList<>();
        JScrollPane scroll = new JScrollPane(listaGrados);
        scroll.setPreferredSize(new Dimension(350, 200));
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // --- Botón crear categoría de logros ---
        btnCrearCategoria = new JButton("Crear categoría de logros");
        panelPrincipal.add(btnCrearCategoria, BorderLayout.SOUTH);

        listaGrados.addMouseListener(new java.awt.event.MouseAdapter() {
    @Override
    public void mouseClicked(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 2) {   // doble clic en un grado
            int index = listaGrados.getSelectedIndex();
            if (index != -1) {
                Grado grado = control.obtenerTodosLosGrados().get(index);

                // Abrir nueva vista
                new CursosPorGradoVista(control, grado);
            }
        }
    }
});
    }

    private void cargarGradosEnLista() {
        // Obtener grados desde la controladora
        List<Grado> grados = control.obtenerTodosLosGrados();  
        

        DefaultListModel<String> modelo = new DefaultListModel<>();

        for (Grado g : grados) {
            modelo.addElement(g.getId() + " - " + g.getNombre());
        }

        listaGrados.setModel(modelo);
    }
}
