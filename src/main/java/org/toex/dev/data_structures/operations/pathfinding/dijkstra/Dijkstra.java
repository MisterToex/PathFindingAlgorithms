package org.toex.dev.data_structures.operations.pathfinding.dijkstra;

import org.toex.dev.data_structures.graph.Edge;
import org.toex.dev.data_structures.graph.Vertex;

import java.util.*;
import java.util.function.ToDoubleFunction;

public class Dijkstra {
    private Map<Vertex, Double> distances;
    private Map<Vertex, Vertex> predecessors;
    private Set<Vertex> visitedNodes;
    private PriorityQueue<Vertex> priorityQueue;
    private static HashMap<Vertex, Dijkstra> cache = new HashMap<>();
    private static boolean useCache = false;

    private static long deltaTimeInit = 0;
    private static long deltaTimeCalculateCosts = 0;
    private static long deltaTimeFindPath = 0;

    private Dijkstra(Vertex source) {
        init(source);
        calculateCosts();

    }

    public static ArrayList<Vertex> shortestPath(Vertex source, Vertex target) {
        if (useCache) {
            if (cache.containsKey(source)){
                return cache.get(source).getShortestPathTo(target);
            } else {
                Dijkstra path = new Dijkstra(source);
                cache.put(source, path);
                return path.getShortestPathTo(target);
            }
        }
        return new Dijkstra(source).getShortestPathTo(target);
    }

    private void init(Vertex source) {
        long last = System.nanoTime();

        distances = new HashMap<>();
        predecessors = new HashMap<>();
        visitedNodes = new HashSet<>();
        ToDoubleFunction<Vertex> cost = distances::get;
        priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(cost));

        for (Vertex v : source.getGraph().getVertexGroup()) {
            distances.put(v, Double.MAX_VALUE);
        }

        distances.put(source, 0d);
        priorityQueue.add(source);

        deltaTimeInit = System.nanoTime() - last;
    }

    private void calculateCosts() {
        long last = System.nanoTime();

        Vertex currentVertex;

        while (!priorityQueue.isEmpty()) {
            currentVertex = priorityQueue.poll();

            if (visitedNodes.contains(currentVertex)) {
                continue;
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
    }

    private ArrayList<Vertex> getShortestPathTo(Vertex target) {
        long last = System.nanoTime();

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

        Collections.reverse(shortestPath);


        deltaTimeFindPath = System.nanoTime() - last;

        return shortestPath;
    }

    public static long[] getLastRunTime() {
        long[] toReturn = {deltaTimeInit, deltaTimeCalculateCosts, deltaTimeFindPath};
        return toReturn;
    }

    public static void setUseCache(boolean value) {
        useCache = value;
    }
}
