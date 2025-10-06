package com.fiap.stock.gui;

import com.fiap.stock.controller.EstoqueController;
import com.fiap.stock.model.Usuario;
import com.fiap.stock.service.EstoqueService;
import com.fiap.stock.util.ConnectionFactory;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class LoginFrame extends JFrame {

    private final JTextField userField;
    private final JPasswordField passField;
    private final JButton loginButton;

    public LoginFrame() {
        setTitle("Login - Sistema Estoque");
        setSize(360, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(3, 2, 6, 6));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panel.add(new JLabel("Login:"));
        userField = new JTextField();
        panel.add(userField);

        panel.add(new JLabel("Senha:"));
        passField = new JPasswordField();
        panel.add(passField);

        loginButton = new JButton("Entrar");
        panel.add(new JLabel());
        panel.add(loginButton);

        add(panel);

        loginButton.addActionListener(e -> onLogin());
    }

    private void onLogin() {
        String login = userField.getText().trim();
        String senha = new String(passField.getPassword()).trim();

        if (login.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha login e senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            Connection conn = ConnectionFactory.getConnection();
            EstoqueService service = new EstoqueService(conn);
            EstoqueController controller = new EstoqueController(service);

            Usuario u = controller.autenticar(login, senha);
            if (u != null) {
                JOptionPane.showMessageDialog(this, "Bem-vindo, " + u.getNome());
                MainFrame main = new MainFrame(conn, u, controller);
                dispose();
                main.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Login ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao conectar: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
