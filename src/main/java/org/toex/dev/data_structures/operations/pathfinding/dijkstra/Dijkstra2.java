package org.toex.dev.data_structures.operations.pathfinding.dijkstra;

import org.toex.dev.data_structures.graph.*;

import java.util.*;
import java.util.function.ToDoubleFunction;

public class Dijkstra2 {

    private static long deltaTimeInit = 0;
    private static long deltaTimeCalculateCosts = 0;
    private static long deltaTimeFindPath = 0;

    public static ArrayList<Vertex> shortestPath(Vertex source, Vertex target) {
        long last;

        last = System.nanoTime();

        // Initialize
        Map<Vertex, Double> distances;
        Map<Vertex, Vertex> predecessors;
        Set<Vertex> visitedNodes;
        PriorityQueue<Vertex> priorityQueue;

        distances = new HashMap<>();
        predecessors = new HashMap<>();
        visitedNodes = new HashSet<>();
        ToDoubleFunction<Vertex> cost = v -> distances.get(v) + (v.getDistanceTo(target));
        priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(cost));

        // Initialize all variables with max value
        for (Vertex v : source.getGraph().getVertexGroup()) {
            distances.put(v, Double.MAX_VALUE);
        }

        // Set the cost of source vertex to 0 and start with it
        distances.put(source, 0d);
        priorityQueue.add(source);

        deltaTimeInit = System.nanoTime() - last;

        last = System.nanoTime();

        // Calculate the costs to other vertices
        Vertex currentVertex;
        while (!priorityQueue.isEmpty()) {
            currentVertex = priorityQueue.poll();

            // If we already visited this vertex, we will skip it
            if (visitedNodes.contains(currentVertex)) {
                continue;
            }

            // Different from normal dijkstra we finish the loop if we already find the target
            if (currentVertex.equals(target)) {
                break;
            }

            visitedNodes.add(currentVertex);
            currentVertex.setMark(2);

            Iterator<Edge> i = currentVertex.getEdges().iterator();
            while (i.hasNext()) {
                Edge edge = i.next();
                edge.setMark(2);
                Vertex neighbor = currentVertex.equals(edge.getVertex1()) ? edge.getVertex2() : edge.getVertex1();
                double newDistance = distances.get(currentVertex) + edge.getWeight();
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    predecessors.put(neighbor, currentVertex);
                    priorityQueue.add(neighbor);
                }
            }
        }

        deltaTimeCalculateCosts = System.nanoTime() - last;

        last = System.nanoTime();

        // Find the shortest path to target
        ArrayList<Vertex> shortestPath = new ArrayList<>();
        Vertex currentNode = target;
        while (currentNode != null) {
            currentNode.setMark(3);
            shortestPath.add(currentNode);
            Vertex predecessor = predecessors.get(currentNode);
            for(Edge e: currentNode.getEdges()) {
                if (predecessor != null && (predecessor.equals(e.getVertex1()) || predecessor.equals(e.getVertex2())))
                    e.setMark(3);
            }
            currentNode = predecessors.get(currentNode);
        }

        // Reverse the list and return
        Collections.reverse(shortestPath);

        deltaTimeFindPath = System.nanoTime() - last;

        return shortestPath;
    }

    public static long[] getLastRunTime() {
        long[] toReturn = {deltaTimeInit, deltaTimeCalculateCosts, deltaTimeFindPath};
        return toReturn;
    }
}