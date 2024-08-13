package view;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import model.Despesa;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.stream.Collectors;

public class TelaListarDespesas extends JFrame {

    Border border = BorderFactory.createLineBorder(Color.green, 3);
    DefaultTableModel tableModel;
    JComboBox<String> comboBoxCategoria;
    JLabel labelSomaTotal;

    public TelaListarDespesas() {
        setTitle("Listar Despesas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.BLACK);

        JLabel labelTitulo = new JLabel("Despesas Cadastradas", SwingConstants.CENTER);
        labelTitulo.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelTitulo.setForeground(Color.WHITE);
        mainPanel.add(labelTitulo, BorderLayout.NORTH);

        JPanel panelTop = new JPanel();
        panelTop.setLayout(new FlowLayout());
        panelTop.setOpaque(true);
        panelTop.setBackground(Color.BLACK);

        comboBoxCategoria = new JComboBox<>(getCategorias());
        comboBoxCategoria.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarDespesasPorCategoria();
            }
        });

        JLabel textoSelect = new JLabel("Selecione a Categoria:");
        textoSelect.setForeground(Color.WHITE);
        panelTop.add(textoSelect);
        panelTop.add(comboBoxCategoria);

        mainPanel.add(panelTop, BorderLayout.NORTH);

        String[] columnNames = {"Nome", "Valor", "Placa", "Categoria", "Descrição"};
        tableModel = new DefaultTableModel(columnNames, 0);

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel();
        panelBottom.setOpaque(true);
        panelBottom.setBackground(Color.BLACK);
        panelBottom.setLayout(new BorderLayout());

        labelSomaTotal = new JLabel("Soma Total: R$ 0.00", SwingConstants.CENTER);
        labelSomaTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelSomaTotal.setForeground(Color.WHITE);
        panelBottom.add(labelSomaTotal, BorderLayout.CENTER);

        JButton buttonVoltar = new JButton("Voltar");
        buttonVoltar.setFont(new Font("SansSerif", Font.BOLD, 15));
        buttonVoltar.setBackground(new Color(10, 10, 10));
        buttonVoltar.setForeground(Color.WHITE);
        buttonVoltar.setBorder(border);
        buttonVoltar.setFocusable(false);
        buttonVoltar.addActionListener(this::voltar);
        panelBottom.add(buttonVoltar, BorderLayout.SOUTH);

        mainPanel.add(panelBottom, BorderLayout.SOUTH);

        add(mainPanel);

        carregarDespesas(MenuPrincipal.listaDespesas);

        setVisible(true);
    }

    private String[] getCategorias() {
        List<String> categorias = MenuPrincipal.listaDespesas.stream()
                .map(Despesa::getCategoria)
                .distinct()
                .collect(Collectors.toList());
        categorias.add(0, "Todas");
        return categorias.toArray(new String[0]);
    }

    private void carregarDespesas(List<Despesa> despesas) {
        tableModel.setRowCount(0);
        double somaTotal = 0;

        for (Despesa despesa : despesas) {
            Object[] row = {
                    despesa.getNome(),
                    despesa.getValor(),
                    despesa.getPlaca(),
                    despesa.getCategoria(),
                    despesa.getDescricao()
            };
            tableModel.addRow(row);
            somaTotal += despesa.getValor();
        }

        labelSomaTotal.setText(String.format("Soma Total: R$ %.2f", somaTotal));
    }

    private void filtrarDespesasPorCategoria() {
        String categoriaSelecionada = (String) comboBoxCategoria.getSelectedItem();
        List<Despesa> despesasFiltradas;

        if (categoriaSelecionada.equals("Todas")) {
            despesasFiltradas = MenuPrincipal.listaDespesas;
        } else {
            despesasFiltradas = MenuPrincipal.listaDespesas.stream()
                    .filter(d -> d.getCategoria().equals(categoriaSelecionada))
                    .collect(Collectors.toList());
        }

        carregarDespesas(despesasFiltradas);
    }

    public void voltar(ActionEvent actionEvent) {
        new MenuPrincipal();
        dispose();
    }
}