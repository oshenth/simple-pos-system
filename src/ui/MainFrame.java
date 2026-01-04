package ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends javax.swing.JFrame {
    
    public static BillPanel billPanel;

    public MainFrame() {
        
        setTitle("Hotel Water Garden POS System");
        setSize(1200, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        
        ((JComponent) getContentPane())
                .setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Bill panel
        billPanel = new BillPanel();
        billPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 10px gap

        // Items panel (scrollable)
        ItemsPanel itemsPanel = new ItemsPanel();
        JScrollPane itemsScroll = new JScrollPane(itemsPanel);
        
        itemsScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        itemsScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        itemsScroll.getVerticalScrollBar().setUnitIncrement(16);

        // Split pane: 75% items | 25% bill
        JSplitPane splitPane = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                itemsScroll,
                billPanel
        );
        splitPane.setResizeWeight(0.75); // Items panel gets 75%, BillPanel gets 25%
        splitPane.setDividerSize(0); // invisible divider
        splitPane.setContinuousLayout(true);

        add(splitPane, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
