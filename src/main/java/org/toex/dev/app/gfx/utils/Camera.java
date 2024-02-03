package org.toex.dev.app.gfx.utils;

import org.toex.dev.app.handler.Handler;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Camera {
    private Handler handler;

    private int marginLeft;
    private int marginTop;
    private int marginBottom;
    private int marginRight;

    private Point2D.Double offset;

    private double zoom;

    public Camera(Handler handler) {
        this.handler = handler;
        this.marginLeft = 0;
        this.marginTop = 0;
        this.marginRight = 0;
        this.marginBottom = 0;
        this.offset = new Point2D.Double(0, 0);
        this.zoom = 1;
    }

    public void center() {
        Rectangle2D.Double bounds = handler.getGraph().getBounds();
        double pw = handler.getDisplay().getGraphPanel().getWidth() - (marginLeft + marginRight);
        double ph = handler.getDisplay().getGraphPanel().getHeight() - (marginTop + marginBottom);
        double gw = bounds.getWidth() - bounds.getX();
        double gh = bounds.getHeight() - bounds.getY();

        offset.x = -bounds.getX();
        offset.y = -bounds.getHeight();

        if(pw / gw < ph / gh) {
            zoom = pw / gw;
            offset.y += (ph / zoom - gh) / 2;
        } else {
            zoom = ph / gh;
            offset.x += (pw / zoom - gw) / 2;
        }
    }

    public Point getRenderPosition(double x, double y) {
        return new Point((int) (marginLeft + ((x + offset.x) * zoom)), (int) (marginTop - (y + offset.y) * zoom));
    }

    public void setMargins(int marginLeft, int marginTop, int marginBottom, int marginRight) {
        this.marginLeft = marginLeft;
        this.marginTop = marginTop;
        this.marginRight = marginRight;
        this.marginBottom = marginBottom;
    }
}
