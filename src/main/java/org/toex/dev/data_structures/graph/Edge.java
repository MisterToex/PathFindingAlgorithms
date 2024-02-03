package org.toex.dev.data_structures.graph;

import org.toex.dev.app.gfx.utils.Camera;

import java.awt.*;

public class Edge {

    private String id;

    private Vertex vertex1;

    private Vertex vertex2;

    private int mark = 0;

    public Edge(Vertex vertex1, Vertex vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    /**
     * Calculates the weight of edge
     * @return
     */
    public double getWeight() {
        return getVertex1().getDistanceTo(getVertex2());
    }

    public void render(Camera cam, Graphics2D g2d) {
        Point drawPos1 = cam.getRenderPosition(vertex1.getX(), vertex1.getY());
        Point drawPos2 = cam.getRenderPosition(vertex2.getX(), vertex2.getY());
        g2d.setColor(
                mark == 0 ? Color.BLACK :
                mark == 1 ? Color.ORANGE :
                mark == 2 ? Color.BLUE :
                mark == 3 ? Color.GREEN :
                Color.GRAY);

        Stroke defStroke = g2d.getStroke();
        Stroke stroke1 = new BasicStroke(1.5f);
        Stroke stroke2 = new BasicStroke(2f);

        switch(mark) {
            case 0:
                g2d.setColor(Color.BLACK);
                break;
            case 1:
                g2d.setColor(Color.ORANGE);
                g2d.setStroke(stroke1);
                break;
            case 2:
                g2d.setColor(Color.BLUE);
                g2d.setStroke(stroke1);
                break;
            case 3:
                g2d.setColor(Color.GREEN);
                g2d.setStroke(stroke2);
                break;
            default:
                g2d.setColor(Color.GRAY);
                break;
        }

        g2d.drawLine(drawPos1.x, drawPos1.y, drawPos2.x, drawPos2.y);
        g2d.setStroke(defStroke);
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vertex getVertex1() {
        return vertex1;
    }

    public Vertex getVertex2() {
        return vertex2;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }
}