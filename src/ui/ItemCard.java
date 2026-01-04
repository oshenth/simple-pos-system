package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class ItemCard extends JPanel {

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

        // Bottom panel with name, price, and buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(true);
        bottomPanel.setBackground(new Color(0x49d6fa));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Name + Price in one line
        JPanel namePricePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 0));
        namePricePanel.setOpaque(false);

        JLabel nameLbl = new JLabel(name);
        nameLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLbl.setForeground(Color.WHITE);

        JLabel priceLbl = new JLabel("Rs. " + price);
        priceLbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        priceLbl.setForeground(Color.WHITE);

        namePricePanel.add(nameLbl);
        namePricePanel.add(priceLbl);

        // Buttons panel
        JPanel btnPanel = new JPanel();
        btnPanel.setOpaque(false);
        btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));

        JButton plusBtn = createCircleButton("+", new Color(0x4CAF50));
        JButton minusBtn = createCircleButton("−", new Color(0xF44336));

        plusBtn.addActionListener(e -> MainFrame.billPanel.addItem(name, price));
        minusBtn.addActionListener(e -> MainFrame.billPanel.removeItem(name));

        btnPanel.add(minusBtn);
        btnPanel.add(plusBtn);

        bottomPanel.add(namePricePanel);
        bottomPanel.add(btnPanel);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    // Circular button creation
    private JButton createCircleButton(String text, Color bgColor) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bgColor);
                g2.fillOval(0, 0, getWidth(), getHeight());
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int stringWidth = fm.stringWidth(getText());
                int stringHeight = fm.getAscent();
                g2.drawString(getText(), (getWidth() - stringWidth) / 2, (getHeight() + stringHeight) / 2 - 2);
                g2.dispose();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(30, 30);
            }

            @Override
            public boolean contains(int x, int y) {
                int radius = getWidth() / 2;
                int centerX = radius;
                int centerY = radius;
                return (x - centerX) * (x - centerX) + (y - centerY) * (y - centerY) <= radius * radius;
            }
        };
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int arc = 20;

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Background
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        // Border
        g2.setColor(new Color(200, 200, 200));
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        // Draw image
        if (img != null) {
            int bottomHeight = 60; // space for name, price, and buttons
            int availableWidth = getWidth() - 4;
            int availableHeight = getHeight() - bottomHeight - 4;

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
