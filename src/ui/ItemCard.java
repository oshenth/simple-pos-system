package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ItemCard extends javax.swing.JPanel {
    
    
    private final Color normal = Color.WHITE;
    private final Color hover = new Color(230, 245, 255);
    private boolean hovered = false;

    private BufferedImage img;
    private String name;
    private double price;

    public ItemCard(String name, double price, String imgPath) {
        this.name = name;
        this.price = price;

        setOpaque(false);
        setLayout(new BorderLayout(0, 0));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        // Load image
        if (imgPath != null && !imgPath.isEmpty()) {
            try {
                img = ImageIO.read(new File(imgPath));
            } catch (Exception e) {
                System.out.println("Failed to load image: " + imgPath);
            }
        }

        // Label panel with background color #49d6fa
        JPanel labelPanel = new JPanel();
        labelPanel.setBackground(new Color(0x49d6fa)); // your color
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
        labelPanel.setOpaque(true);

        // Name label
        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLbl.setAlignmentX(CENTER_ALIGNMENT);
        nameLbl.setForeground(Color.WHITE);
        nameLbl.setBorder(BorderFactory.createEmptyBorder(5, 2, 2, 2));

        // Price label
        JLabel priceLbl = new JLabel("Rs. " + price);
        priceLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        priceLbl.setAlignmentX(CENTER_ALIGNMENT);
        priceLbl.setForeground(Color.WHITE);
        priceLbl.setBorder(BorderFactory.createEmptyBorder(2, 2, 5, 2));

        labelPanel.add(nameLbl);
        labelPanel.add(priceLbl);

        add(labelPanel, BorderLayout.SOUTH); // labels at bottom

        // Hover + click
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                hovered = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hovered = false;
                repaint();
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.billPanel.addItem(name, price);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int arc = 20;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(hovered ? hover : normal);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        // Border
        g2.setColor(hovered ? new Color(0, 120, 215) : new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        // Draw image stretched in available area
        if (img != null) {
            int labelHeight = 40; // approximate space for label panel
            int availableWidth = getWidth() - 4;
            int availableHeight = getHeight() - labelHeight - 4;

            g2.drawImage(img, 2, 2, availableWidth, availableHeight, null);
        }

        g2.dispose();
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
