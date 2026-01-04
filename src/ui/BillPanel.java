package ui;

import model.CartItem;
import util.PDFGenerator;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class BillPanel extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel totalLbl = new JLabel("Total: Rs. 0.00");
    private JButton printBtn;
    private Map<String, CartItem> cart = new LinkedHashMap<>();

    public BillPanel() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Table model with columns: Item, Qty, Total
        tableModel = new DefaultTableModel(new Object[]{"Item", "Qty", "Total"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table non-editable
            }
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.getColumnModel().getColumn(0).setPreferredWidth(150); // Item
        table.getColumnModel().getColumn(1).setPreferredWidth(50);  // Qty
        table.getColumnModel().getColumn(2).setPreferredWidth(80);  // Total
        table.setShowGrid(true);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);

        // Total label
        totalLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLbl.setForeground(new Color(0, 120, 215));
        totalLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

//        // Print button
//        printBtn = new JButton("Print Bill");
//        printBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
//        printBtn.setBackground(new Color(0x49d6fa));
//        printBtn.setForeground(Color.WHITE);
//        printBtn.setFocusPainted(false);
//        printBtn.setEnabled(false); // initially disabled
//        printBtn.addActionListener(e -> {
//            PDFGenerator.generate(cart);
//            clearBill(); // clear after printing
//        });
        
        // Print button
        printBtn = new JButton("Print Bill");
        printBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        printBtn.setBackground(new Color(0x49d6fa));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFocusPainted(false);
        printBtn.setEnabled(false); // initially disabled

        // Set fixed width 400px, height flexible
        Dimension size = new Dimension(500, printBtn.getPreferredSize().height);
        printBtn.setPreferredSize(size);
        printBtn.setMaximumSize(size);

        printBtn.addActionListener(e -> {
            PDFGenerator.generate(cart);
            clearBill(); // clear after printing
        });

        // Bottom panel (total + button)
        JPanel bottomPanel = new JPanel(new BorderLayout(5, 5));
        bottomPanel.setOpaque(false);
        bottomPanel.add(totalLbl, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        btnPanel.setOpaque(false);
        btnPanel.add(printBtn);
        bottomPanel.add(btnPanel, BorderLayout.SOUTH);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Add an item
    public void addItem(String name, double price) {
        cart.compute(name, (k, v) -> v == null ? new CartItem(name, price) : new CartItem(v.name, v.price, v.qty + 1));
        refresh();
    }

    // Remove one quantity of an item
    public void removeItem(String name) {
        if (cart.containsKey(name)) {
            CartItem i = cart.get(name);
            if (i.qty > 1) i.qty--;
            else cart.remove(name);
            refresh();
        }
    }

    private void refresh() {
        tableModel.setRowCount(0); // clear table
        double total = 0;
        for (CartItem i : cart.values()) {
            tableModel.addRow(new Object[]{i.name, i.qty, String.format("%.2f", i.total())});
            total += i.total();
        }
        totalLbl.setText("Total: Rs. " + String.format("%.2f", total));

        // Enable print button only if cart is not empty
        printBtn.setEnabled(!cart.isEmpty());
    }

    // Clear all items from the bill
    private void clearBill() {
        cart.clear();
        refresh();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
