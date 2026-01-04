package ui;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;

public class RoundedButtonUI extends BasicButtonUI {

    @Override
    public void installUI(JComponent c) {
        super.installUI(c);
        AbstractButton button = (AbstractButton) c;
        button.setOpaque(false);          // remove default background
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
    }

    @Override
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton) c;
        Graphics2D g2 = (Graphics2D) g.create();

        // Enable anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw rounded background
        g2.setColor(b.getBackground());
        g2.fillRoundRect(0, 0, b.getWidth(), b.getHeight(), 15, 15); // 15px corner radius

        // Draw button text centered
        FontMetrics fm = g2.getFontMetrics();
        Rectangle r = new Rectangle(b.getWidth(), b.getHeight());
        String text = b.getText();
        int x = (r.width - fm.stringWidth(text)) / 2;
        int y = (r.height - fm.getHeight()) / 2 + fm.getAscent();

        g2.setColor(b.getForeground());
        g2.drawString(text, x, y);

        g2.dispose();
    }
}
