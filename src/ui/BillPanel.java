package ui;

import model.CartItem;
import util.PDFGenerator;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionEvent;

public class BillPanel extends javax.swing.JPanel {

    private JTextArea area = new JTextArea();
    private JLabel totalLbl = new JLabel("Total: 0.00");
    private Map<String, CartItem> cart = new LinkedHashMap<>();
    
    @Override
    public Dimension getPreferredSize() {
        if (getParent() != null) {
            int width = (int) (getParent().getWidth() * 0.4);
            return new Dimension(width, getParent().getHeight());
        }
        return super.getPreferredSize();
    }
    
    public BillPanel() {
                setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // Bill text area
        area.setEditable(false); // non-editable
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        area.setMargin(new Insets(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(area);

        // Total label
        totalLbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        totalLbl.setForeground(new Color(0, 120, 215));
        totalLbl.setHorizontalAlignment(SwingConstants.RIGHT);
        totalLbl.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Print button
        JButton printBtn = new JButton("Print Bill");
        printBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        printBtn.setBackground(new Color(0x49d6fa));
        printBtn.setForeground(Color.WHITE);
        printBtn.setFocusPainted(false);
        printBtn.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        printBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Rounded corners for the button
        printBtn.setUI(new RoundedButtonUI());

        printBtn.addActionListener(e -> PDFGenerator.generate(cart));

        // Clear item button
        JButton clearBtn = new JButton("Clear Selected Item");
        clearBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        clearBtn.setBackground(new Color(220, 53, 69));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        clearBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearBtn.setUI(new RoundedButtonUI());
        clearBtn.addActionListener(this::clearItem);

        // Bottom panel for total + buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.add(totalLbl);
        bottomPanel.add(Box.createVerticalStrut(5));
        bottomPanel.add(printBtn);
        bottomPanel.add(Box.createVerticalStrut(5));
        bottomPanel.add(clearBtn);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    public void addItem(String name, double price) {
        if (cart.containsKey(name)) {
            cart.get(name).qty++;
        } else {
            cart.put(name, new CartItem(name, price));
        }
        refresh();
    }

    private void clearItem(ActionEvent e) {
        String item = JOptionPane.showInputDialog(this,
                "Enter the name of the item to remove:");
        if (item != null && cart.containsKey(item)) {
            cart.remove(item);
            refresh();
        }
    }

    private void refresh() {
        area.setText("");
        double total = 0;

        for (CartItem i : cart.values()) {
            area.append(String.format("%-15s x%-2d  =  %.2f%n", i.name, i.qty, i.total()));
            total += i.total();
        }

        totalLbl.setText("Total: Rs. " + String.format("%.2f", total));
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
