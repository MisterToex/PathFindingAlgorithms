package org.toex.dev.app.main.test;

import org.toex.dev.app.handler.Handler;
import org.toex.dev.data_structures.graph.Graph;
import org.toex.dev.data_structures.graph.Vertex;
import org.toex.dev.data_structures.operations.pathfinding.dijkstra.Dijkstra;
import org.toex.dev.data_structures.operations.pathfinding.dijkstra.Dijkstra2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Test {

    public static void main(String[] args) {
        String directoryPath = "...";
        File directory = new File(directoryPath);
        File[] graphs = directory.listFiles();
        int nr = 1;
        for (File graphXML : graphs) {
            Graph graph = Graph.createGraphfromXML(graphXML.getAbsolutePath(), null);
            File csvFile = new File("...");

            try {
                FileWriter fileWriter = new FileWriter(csvFile);
                StringBuilder header = new StringBuilder();
                header.append("Test nr.; Algorithmus; Knoten; t_init; t_rechnen; t_pfad; t; kuerzester Pfad");
                header.append("\n");

                fileWriter.write(header.toString());

                for (int testNr = 1; testNr <= 10; testNr++) {

                    Random random = new Random();
                    int graphSize = graph.getVertexGroup().size();
                    Vertex source = graph.getVertexByID("v" + (random.nextInt(graphSize) + 1));
                    Vertex target;
                    do {
                         target = graph.getVertexByID("v" + (random.nextInt(graphSize) + 1));
                    } while(source.equals(target));

                    // Dijkstra
                    ArrayList<Vertex> shortestPath = Dijkstra.shortestPath(source, target);
                    String sp1 = "{";

                    Iterator<Vertex> i = shortestPath.iterator();
                    while(i.hasNext()) {
                        Vertex v = i.next();
                        sp1 = sp1 + v.getId() + (i.hasNext() ? ", " : "}");
                    }

                    StringBuilder line1 = new StringBuilder();

                    line1.append(testNr + ";");
                    line1.append("Dijkstra" + ";");
                    line1.append(source.getId()+ ", "+ target.getId() + ";");
                    line1.append(String.format("%.4f", (double) Dijkstra.getLastRunTime()[0] / 1000000) + "; ");
                    line1.append(String.format("%.4f", (double) Dijkstra.getLastRunTime()[1] / 1000000) + "; ");
                    line1.append(String.format("%.4f", (double) Dijkstra.getLastRunTime()[2] / 1000000) + "; ");
                    line1.append(String.format("%.4f", (double) (Dijkstra.getLastRunTime()[0] + Dijkstra.getLastRunTime()[1] + Dijkstra.getLastRunTime()[2]) / 1000000) + "; ");
                    line1.append(sp1);

                    line1.append("\n");
                    fileWriter.write(line1.toString());

                    // Modified Dijkstra
                    shortestPath =  Dijkstra2.shortestPath(source, target);
                    String sp2 = "{";

                    i = shortestPath.iterator();
                    while(i.hasNext()) {
                        Vertex v = i.next();
                        sp2 = sp2 + v.getId() + (i.hasNext() ? ", " : "}");
                    }

                    StringBuilder line2 = new StringBuilder();

                    line2.append(testNr + ";");
                    line2.append("Dijkstra (modifiziert)" + ";");
                    line2.append(source.getId()+ ", "+ target.getId() + ";");
                    line2.append(String.format("%.4f", (double) Dijkstra2.getLastRunTime()[0] / 1000000) + "; ");
                    line2.append(String.format("%.4f", (double) Dijkstra2.getLastRunTime()[1] / 1000000) + "; ");
                    line2.append(String.format("%.4f", (double) Dijkstra2.getLastRunTime()[2] / 1000000) + "; ");
                    line2.append(String.format("%.4f", (double) (Dijkstra2.getLastRunTime()[0] + Dijkstra2.getLastRunTime()[1] + Dijkstra2.getLastRunTime()[2]) / 1000000) + "; ");
                    line2.append(sp2);

                    line2.append("\n");
                    fileWriter.write(line2.toString());
                }
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        }


    }
}
