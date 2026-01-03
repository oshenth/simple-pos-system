package ui;
import db.DB;
import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ItemsPanel extends javax.swing.JPanel {

    public ItemsPanel() {
                setLayout(new GridLayout(0,3,10,10));
        loadItems();
    }
    
     private void loadItems() {
        try {
            Connection c = DB.getConnection();
            ResultSet rs = c.createStatement()
                .executeQuery("SELECT * FROM items");

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String img = rs.getString("image_path");

                JButton btn = new JButton(name,
                    new ImageIcon(img));
                btn.addActionListener(e ->
                    MainFrame.billPanel.addItem(name, price)
                );
                add(btn);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
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
