package com.fiap.stock.gui;

import com.fiap.stock.controller.EstoqueController;
import com.fiap.stock.model.Material;
import com.fiap.stock.model.Usuario;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MainFrame extends JFrame {

    private final EstoqueController controller;
    private final Usuario usuario;
    private final DefaultTableModel model;
    private final JTable table;

    public MainFrame(java.sql.Connection conn, Usuario usuario, EstoqueController controller) {
        this.controller = controller;
        this.usuario = usuario;

        setTitle("Controle de Estoque - Usuário: " + usuario.getNome() + " (" + usuario.getPerfil() + ")");
        setSize(950, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new Object[]{"ID", "Nome", "Quantidade", "Unidade", "Ponto Reposição"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(model);

        // renderer para low stock
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                try {
                    double qtd = Double.parseDouble(String.valueOf(t.getModel().getValueAt(row, 2)));
                    double ponto = Double.parseDouble(String.valueOf(t.getModel().getValueAt(row, 4)));
                    if (!isSelected) {
                        if (qtd <= ponto) c.setBackground(new Color(255, 220, 220));
                        else c.setBackground(Color.WHITE);
                    }
                } catch (Exception ex) { c.setBackground(Color.WHITE); }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(table);

        JButton btnNovo = new JButton("Novo Material");
        JButton btnEntrada = new JButton("Registrar Entrada");
        JButton btnSaida = new JButton("Registrar Saída");
        JButton btnRemover = new JButton("Remover Material");
        JButton btnRefresh = new JButton("Atualizar");

        JPanel btnPanel = new JPanel();
        btnPanel.add(btnNovo);
        btnPanel.add(btnEntrada);
        btnPanel.add(btnSaida);
        btnPanel.add(btnRemover);
        btnPanel.add(btnRefresh);

        add(scroll, BorderLayout.CENTER);
        add(btnPanel, BorderLayout.SOUTH);

        // permissões: se OPERADOR, desabilita criar material
        if ("OPERADOR".equalsIgnoreCase(usuario.getPerfil())) {
            btnNovo.setEnabled(false);
            btnRemover.setEnabled(false);
        }

        btnNovo.addActionListener(e -> criarMaterial());
        btnEntrada.addActionListener(e -> registrarMovimento("ENTRADA"));
        btnSaida.addActionListener(e -> registrarMovimento("SAIDA"));
        btnRemover.addActionListener(e -> removerMaterial());
        btnRefresh.addActionListener(e -> carregarMateriais());

        carregarMateriais();
    }

    private void criarMaterial() {
        try {
            String nome = JOptionPane.showInputDialog(this, "Nome:");
            if (nome == null || nome.trim().isEmpty()) return;
            String qtdStr = JOptionPane.showInputDialog(this, "Quantidade inicial (ex: 100):");
            String unidade = JOptionPane.showInputDialog(this, "Unidade (ex: ml, un):");
            String pontoStr = JOptionPane.showInputDialog(this, "Ponto de reposição:");
            double qtd = Double.parseDouble(qtdStr);
            double ponto = Double.parseDouble(pontoStr);
            controller.adicionarMaterial(nome, qtd, unidade, ponto);
            JOptionPane.showMessageDialog(this, "Material criado com sucesso.");
            carregarMateriais();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Quantidade/ponto inválido.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao criar material: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void carregarMateriais() {
        try {
            model.setRowCount(0);
            List<Material> list = controller.listarMateriais();
            for (Material m : list) {
                model.addRow(new Object[]{m.getId(), m.getNome(), m.getQuantidade(), m.getUnidade(), m.getPontoReposicao()});
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar materiais: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void registrarMovimento(String tipo) {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um material.");
            return;
        }

        try {
            Long id = Long.valueOf(String.valueOf(model.getValueAt(row, 0)));
            String nome = String.valueOf(model.getValueAt(row, 1));
            double qtdAtual = Double.parseDouble(String.valueOf(model.getValueAt(row, 2)));
            String unidade = String.valueOf(model.getValueAt(row, 3));
            double ponto = Double.parseDouble(String.valueOf(model.getValueAt(row, 4)));

            String input = JOptionPane.showInputDialog(this, "Quantidade:");
            if (input == null) return;
            int qtd = Integer.parseInt(input);
            if (qtd <= 0) { JOptionPane.showMessageDialog(this, "Quantidade deve ser > 0"); return; }

            Material m = new Material(id, nome, qtdAtual, unidade, ponto);

            if ("ENTRADA".equals(tipo)) {
                controller.registrarEntrada(m, qtd, usuario);
                JOptionPane.showMessageDialog(this, "Entrada registrada.");
            } else {
                controller.registrarSaida(m, qtd, usuario);
                JOptionPane.showMessageDialog(this, "Saída registrada.");
            }
            carregarMateriais();
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Quantidade inválida.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void removerMaterial() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um material para remover.");
            return;
        }

        try {
            Long id = Long.valueOf(String.valueOf(model.getValueAt(row, 0)));
            String nome = String.valueOf(model.getValueAt(row, 1));
            double qtd = Double.parseDouble(String.valueOf(model.getValueAt(row, 2)));
            String unidade = String.valueOf(model.getValueAt(row, 3));
            double ponto = Double.parseDouble(String.valueOf(model.getValueAt(row, 4)));

            Material m = new Material(id, nome, qtd, unidade, ponto);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o material '" + nome + "'?",
                    "Confirmar remoção",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                controller.removerMaterial(m);
                JOptionPane.showMessageDialog(this, "Material removido com sucesso.");
                carregarMateriais();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erro ao remover material: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
