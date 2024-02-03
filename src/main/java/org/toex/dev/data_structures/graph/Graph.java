package org.toex.dev.data_structures.graph;

import org.toex.dev.app.handler.Handler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Graph {

    private String id;

    private String name;

    private ArrayList<Edge> edgeGroup;

    private ArrayList<Vertex> vertexGroup;

    private Handler handler;

    public Graph(Handler handler) {
        this.handler = handler;
        this.edgeGroup = new ArrayList<Edge>();
        this.vertexGroup = new ArrayList<Vertex>();
    }

    public boolean idExists(String id) {
        Iterator<Vertex> vit = vertexGroup.iterator();
        while(vit.hasNext()) {
            Vertex v = vit.next();
            if (v.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        Iterator<Edge> eit = edgeGroup.iterator();
        while(eit.hasNext()) {
            Edge e = eit.next();
            if (e.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a vertex by id.
     * @param id
     * @return
     */
    public Vertex getVertexByID(String id) {
        Vertex toReturn = null;
        Iterator<Vertex> it = vertexGroup.iterator();
        while(it.hasNext()) {
            Vertex v = it.next();
            if(v.getId().equalsIgnoreCase(id)){
                toReturn = v;
                break;
            }
        }
        if(toReturn == null)
            System.out.println("No vertex found by given id");
        return toReturn;
    }

    /**
     * Returns an edge by id.
     * @param id
     * @return
     */
    public Edge getEdgeByID(String id) {
        Edge toReturn = null;
        Iterator<Edge> it = edgeGroup.iterator();
        while(it.hasNext()) {
            Edge e = it.next();
            if(e.getId().equalsIgnoreCase(id)){
                toReturn = e;
                break;
            }
        }
        if(toReturn == null)
            System.out.println("No edge found by given id");
        return toReturn;
    }

    public void createVertex(String id, String tag, double x, double y) {
        if(idExists(id)) {
            System.out.println("A vertex already exists with given id.");
        } else {
            vertexGroup.add(new Vertex(id, x, y, this));
        }
    }

    /**
     * Connects two vertices creating an edge object.
     * @param v1
     * @param v2
     */
    public void connect(Vertex v1, Vertex v2) {
        Edge e = new Edge(v1, v2);
        edgeGroup.add(e);
        v1.getEdges().add(e);
        v2.getEdges().add(e);
    }

    public void disconnect(Edge e){
        if(edgeGroup.contains(e)) {
            e.getVertex1().getEdges().remove(e);
            e.getVertex2().getEdges().remove(e);
            edgeGroup.remove(e);
        }
    }

    public void disconnect(Vertex v1, Vertex v2) {
        for(Edge e: v1.getEdges()){
            if(v2.equals(e.getVertex2())) {
                disconnect(e);
            }
        }
    }

    public void resetMarks(){
        Iterator<Vertex> vit = vertexGroup.iterator();
        while(vit.hasNext()) {
            vit.next().setMark(0);
        }
        Iterator<Edge> eit = edgeGroup.iterator();
        while(eit.hasNext()) {
            eit.next().setMark(0);
        }
    }

    public void render(Graphics2D g2d) {
        Iterator<Edge> eit = edgeGroup.iterator();
        while(eit.hasNext()) {
            Edge e = eit.next();
            e.render(handler.getCamera(), g2d);
        }

        Iterator<Vertex> vit = vertexGroup.iterator();
        while(vit.hasNext()) {
            Vertex v = vit.next();
            v.render(handler.getCamera(), g2d);
        }
    }

    public Rectangle2D.Double getBounds() {
        double left = Double.MAX_VALUE;
        double right = 0;
        double top = Double.MAX_VALUE;
        double bottom = 0;

        Iterator<Vertex> vit = vertexGroup.iterator();

        while(vit.hasNext()) {
            Vertex v = vit.next();
            left = java.lang.Math.min(left, v.getX());
            right = java.lang.Math.max(right, v.getX());
            top = java.lang.Math.min(top, v.getY());
            bottom = java.lang.Math.max(bottom, v.getY());
        }

        Rectangle2D.Double toReturn = new Rectangle2D.Double(left, top, right, bottom);
        return toReturn;
    }

    public void parseGraphFromXML(String pathname) {
        // Specify the XML file to parse
        File xmlFile = new File(pathname);
        // Create a DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            // Create a DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file and create a Document object
            Document document = builder.parse(xmlFile);

            // Normalize the document structure
            document.getDocumentElement().normalize();

            // Get the root element
            Element graph = document.getDocumentElement();

            // Get the graph's name attribute
            this.name = graph.getAttribute("name");

            // Get the vertexList and edgeList elements
            Element vertexList = (Element) graph.getElementsByTagName("vertexList").item(0);
            Element edgeList = (Element) graph.getElementsByTagName("edgeList").item(0);

            // Parse vertices
            NodeList vertices = vertexList.getElementsByTagName("vertex");
            for (int i = 0; i < vertices.getLength(); i++) {
                Element vertex = (Element) vertices.item(i);
                String id = vertex.getAttribute("id");
                String tag = vertex.getAttribute("tag");
                double x = Double.parseDouble(vertex.getAttribute("x"));
                double y = Double.parseDouble(vertex.getAttribute("y"));

                createVertex(id, tag, x, y);
            }

            // Parse edges
            NodeList edges = edgeList.getElementsByTagName("edge");
            for (int i = 0; i < edges.getLength(); i++) {
                Element edge = (Element) edges.item(i);
                String tag = edge.getAttribute("tag");
                String source = edge.getAttribute("source");
                String target = edge.getAttribute("target");

                connect(getVertexByID(source), getVertexByID(target));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Graph createGraphfromXML(String path, Handler handler) {
        Graph toReturn = new Graph(handler);
        toReturn.parseGraphFromXML(path);
        return toReturn;
    }

    public ArrayList<Edge> getEdgeGroup() {
        return edgeGroup;
    }

    public ArrayList<Vertex> getVertexGroup() {
        return vertexGroup;
    }
}
