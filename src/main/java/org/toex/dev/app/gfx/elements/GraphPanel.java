package org.toex.dev.app.gfx.elements;

import org.toex.dev.app.handler.Handler;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {

    private Handler handler;

    public GraphPanel(Handler handler) {
        this.handler = handler;
        setPreferredSize(new Dimension(640, 480));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEtchedBorder());
        setDoubleBuffered(true);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(new RenderingHints(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON));
        handler.getApp().render(g2d);
    }
}
