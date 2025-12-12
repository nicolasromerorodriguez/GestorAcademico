/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import Controlador.Controladora;
import Modelo.Acudiente;
import Modelo.Direccion;
import Modelo.Profesor;
import Modelo.SuperUsuario;
import Modelo.Usuario;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FrmMostrarUsuarios extends javax.swing.JFrame {

    Controladora control = new Controladora();
    List<Usuario> listaUsuarios;

    public FrmMostrarUsuarios() {
        initComponents();
        cargarTablaUsuarios();
    }

    private void cargarTablaUsuarios() {
        try {
            listaUsuarios = control.traerUsuarios();

            DefaultTableModel modelo = new DefaultTableModel();
            modelo.setColumnIdentifiers(new Object[]{"ID", "Nombre", "Tipo", "Correo", "Activo"});

            for (Usuario u : listaUsuarios) {

                String tipo = u instanceof SuperUsuario ? "SuperUsuario"
                        : u instanceof Profesor ? "Profesor"
                        : u instanceof Acudiente ? "Acudiente"
                        : u instanceof Direccion ? "Dirección"
                        : "Desconocido";

                modelo.addRow(new Object[]{
                    u.getId(),
                    u.getPrimernombre() + " " + u.getPrimerApellido(),
                    tipo,
                    u.getCorreo(),
                    u.isEstado()
                });
            }

            tablaUsuarios.setModel(modelo);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión a la base de datos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        txtBuscarId = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        btnDeshabilitar = new javax.swing.JButton();
        btnHabilitar = new javax.swing.JButton();   // ← BOTÓN AGREGADO
        btnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mostrar Usuarios");

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel());
        jScrollPane1.setViewportView(tablaUsuarios);

        btnBuscar.setText("Buscar por ID");
        btnBuscar.addActionListener(evt -> buscarUsuario());

        btnDeshabilitar.setText("Deshabilitar seleccionado");
        btnDeshabilitar.addActionListener(evt -> deshabilitarSeleccionado());

        btnHabilitar.setText("Habilitar seleccionado");   // ← NUEVO TEXTO
        btnHabilitar.addActionListener(evt -> habilitarSeleccionado());  // ← NUEVA ACCIÓN

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(evt -> volverMenu());

        // DISEÑO
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBuscarId, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeshabilitar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnHabilitar)  // ← BOTÓN NUEVO EN LA FILA
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnCancelar)
                        .addGap(0, 12, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscarId, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar)
                    .addComponent(btnDeshabilitar)
                    .addComponent(btnHabilitar)
                    .addComponent(btnCancelar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void buscarUsuario() {
        try {
            String id = txtBuscarId.getText();

            Usuario u = control.traerUsuario(id);

            if (u == null) {
                JOptionPane.showMessageDialog(this,
                        "El usuario no existe",
                        "No encontrado",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            mostrarDatosUsuario(u);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión a la base de datos",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarDatosUsuario(Usuario u) {

        String nombreCompleto
                = u.getPrimernombre() + " "
                + (u.getSegundoNombre() != null ? u.getSegundoNombre() + " " : "")
                + u.getPrimerApellido() + " "
                + (u.getSegundoApellido() != null ? u.getSegundoApellido() : "");

        String tipo = u instanceof SuperUsuario ? "SuperUsuario"
                : u instanceof Profesor ? "Profesor"
                : u instanceof Acudiente ? "Acudiente"
                : u instanceof Direccion ? "Dirección"
                : "Desconocido";

        String estadoTexto = u.isEstado() ? "Activo" : "Inactivo";

        JOptionPane.showMessageDialog(this,
                "ID: " + u.getId() + "\n"
                + "Nombre completo: " + nombreCompleto + "\n"
                + "Correo: " + u.getCorreo() + "\n"
                + "Teléfono: " + u.getTelefono() + "\n"
                + "Tipo de usuario: " + tipo + "\n"
                + "Estado: " + estadoTexto,
                "Información del usuario",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void deshabilitarSeleccionado() {
        int fila = tablaUsuarios.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un usuario",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) tablaUsuarios.getValueAt(fila, 0);

        control.deshabilitarUsuario(id);

        JOptionPane.showMessageDialog(this, "Usuario deshabilitado correctamente");
        cargarTablaUsuarios();
    }

    private void habilitarSeleccionado() {
        int fila = tablaUsuarios.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this,
                    "Debe seleccionar un usuario",
                    "Advertencia",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        String id = (String) tablaUsuarios.getValueAt(fila, 0);

        control.habilitarUsuario(id);

        JOptionPane.showMessageDialog(this, "Usuario habilitado correctamente");
        cargarTablaUsuarios();
    }

    private void volverMenu() {
        FrmSuperUsuarioInicio frm = new FrmSuperUsuarioInicio(control);
        frm.setVisible(true);
        this.dispose();
    }

    // Variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnDeshabilitar;
    private javax.swing.JButton btnHabilitar;   // ← VARIABLE NUEVA
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaUsuarios;
    private javax.swing.JTextField txtBuscarId;
}
