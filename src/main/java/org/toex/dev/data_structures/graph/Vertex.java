package org.toex.dev.data_structures.graph;

import org.toex.dev.app.gfx.utils.Camera;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

public class Vertex {

    private final int width = 32;

    private final int height = 32;

    private double x;

    private double y;

    private String id;

    private ArrayList<Edge> edges;

    private int mark = 0;

    private Graph parent;

    public Vertex(String id, double x, double y, Graph parent){
        this.id = id;
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.edges = new ArrayList<>();
    }

    public ArrayList<Vertex> getNeighbors() {
        ArrayList<Vertex> toReturn = new ArrayList<>();
        Iterator<Edge> it = edges.iterator();
        while(it.hasNext()) {
            Edge e = it.next();
            if(equals(e.getVertex1())){
                toReturn.add(e.getVertex2());
            } else if (equals(e.getVertex2())) {
                toReturn.add(e.getVertex1());
            }
        }
        return toReturn;
    }

    public double getDistanceTo(Vertex v) {
        double deltaX = v.getX() - this.getX();
        double deltaY = v.getY() - this.getY();
        return java.lang.Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
    }

    public void render(Camera cam, Graphics2D g2d) {
        Point drawPos = cam.getRenderPosition(x, y);
        g2d.setColor(
                mark == 0 ? new Color(223, 223, 223) :
                mark == 1 ? Color.ORANGE :
                mark == 2 ? Color.BLUE :
                mark == 3 ? Color.GREEN :
                Color.GRAY);
        g2d.fillOval(drawPos.x - (width / 2), drawPos.y - (height / 2), width, height);
        g2d.setColor(Color.black);
        g2d.drawOval(drawPos.x - (width / 2), drawPos.y - (height / 2), width, height);
        g2d.drawString(id, drawPos.x - 5, drawPos.y + 5);
    }

    public Graph getGraph() {
        return parent;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    @Override
    public String toString() {
        return "Vertex: " + id + " [" + String.format("%.2f", x) + ", " + String.format("%.2f", y) + "]";
    }
}