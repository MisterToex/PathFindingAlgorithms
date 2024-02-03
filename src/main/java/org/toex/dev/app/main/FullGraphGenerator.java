package org.toex.dev.app.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class FullGraphGenerator {

    public static void main(String[] args) {
        try {
            // Create a new XML document
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Create the root <graph> element
            Element graphElement = doc.createElement("graph");
            graphElement.setAttribute("id", "fullGraph");
            graphElement.setAttribute("name", "10x10-full-graph");
            doc.appendChild(graphElement);

            // Create the <vertexList> and <edgeList> elements
            Element vertexListElement = doc.createElement("vertexList");
            Element edgeListElement = doc.createElement("edgeList");
            graphElement.appendChild(vertexListElement);
            graphElement.appendChild(edgeListElement);

            // Create 100 vertices and add them to the <vertexList>
            int i = 1;
            for (double y = 1; y <= 19; y++) {
                for (double x = 1; x <= 19; x++) {
                    Element vertexElement = doc.createElement("vertex");
                    vertexElement.setAttribute("id", "v" + i);
                    vertexElement.setAttribute("tag", Integer.toString(i));
                    vertexElement.setAttribute("x", Double.toString((x-10) * Math.pow((19*19)-((y-10)*(y-10)), 1.5) / (19*19*19) + (Math.random()-0.5)));
                    vertexElement.setAttribute("y", Double.toString((y-10) * Math.pow((19*19)-((x-10)*(x-10)), 1.5) / (19*19*19) + (Math.random()-0.5)));
                    vertexListElement.appendChild(vertexElement);
                    if(x > 1) {
                        Element edgeElement = doc.createElement("edge");
                        edgeElement.setAttribute("id", "e" + i + "-" + (i-1));
                        edgeElement.setAttribute("source", "v" + i);
                        edgeElement.setAttribute("target", "v" + (i-1));
                        edgeListElement.appendChild(edgeElement);
                    }
                    if(y > 1) {
                        Element edgeElement = doc.createElement("edge");
                        edgeElement.setAttribute("id", "e" + i + "-" + (i-19));
                        edgeElement.setAttribute("source", "v" + i);
                        edgeElement.setAttribute("target", "v" + (i-19));
                        edgeListElement.appendChild(edgeElement);
                    }
                    i++;
                }

            }

            // Write the XML document to a file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            LocalDateTime now = LocalDateTime.now();
            StreamResult result = new StreamResult(new File("test_graph" + dtf.format(now) + ".xml"));
            transformer.transform(source, result);

            System.out.println("Full graph saved to 'test_graph'" + dtf.format(now) + ".xml");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}