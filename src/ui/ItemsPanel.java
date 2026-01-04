package ui;

import db.DB;
//import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ItemsPanel extends javax.swing.JPanel {

    public ItemsPanel() {
        setLayout(new GridLayout(0, 4, 10, 10)); // 4 columns, unlimited rows
        setBackground(Color.WHITE);
        loadItems();
    }
    
     private void loadItems() {
        try {
            Connection c = DB.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM items order by name asc");

            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                String img = rs.getString("image_path");

                ItemCard card = new ItemCard(name, price, img);
                add(card);
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
