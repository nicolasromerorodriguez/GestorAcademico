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

public class FrmSuperUsuarioInicio extends JFrame {

    public FrmSuperUsuarioInicio() {

        setTitle("Panel Super Usuario");
        setSize(400, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton btnCrear = new JButton("Crear Usuario");
        btnCrear.setBounds(50, 50, 150, 30);
        add(btnCrear);

        JButton btnSalir = new JButton("Cancelar");
        btnSalir.setBounds(220, 50, 120, 30);
        add(btnSalir);

        // Acción: ir al formulario
        btnCrear.addActionListener(e -> {
            FrmCrearUsuario frm = new FrmCrearUsuario();
            frm.setVisible(true);
            dispose();
        });

        btnSalir.addActionListener(e -> dispose());
    }

    public static void main(String[] args) {
        new FrmSuperUsuarioInicio().setVisible(true);
    }
}
