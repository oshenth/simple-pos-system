package ui;

import model.CartItem;
import util.PDFGenerator;
import javax.swing.*;
import java.awt.*;
import java.util.*;

public class BillPanel extends javax.swing.JPanel {

    private JTextArea area = new JTextArea();
    private JLabel totalLbl = new JLabel("Total: 0.00");
    private Map<String, CartItem> cart = new HashMap<>();

    public BillPanel() {
        setPreferredSize(new Dimension(300,0));
        setLayout(new BorderLayout());

        JButton printBtn = new JButton("Print Bill");
        printBtn.addActionListener(e -> PDFGenerator.generate(cart));

        add(new JScrollPane(area), BorderLayout.CENTER);
        add(totalLbl, BorderLayout.NORTH);
        add(printBtn, BorderLayout.SOUTH);
    }

    public void addItem(String name, double price) {
        cart.putIfAbsent(name, new CartItem(name, price));
        cart.get(name).qty++;
        refresh();
    }

    private void refresh() {
        area.setText("");
        double total = 0;

        for (CartItem i : cart.values()) {
            area.append(i.name + " x" + i.qty +
                " = " + i.total() + "\n");
            total += i.total();
        }
        totalLbl.setText("Total: " + total);

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
