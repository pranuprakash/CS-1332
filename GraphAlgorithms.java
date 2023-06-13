import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Queue;
import java.util.PriorityQueue;

/**
 * Your implementation of various different graph algorithms.
 *
 * @author Pranu Prakash
 * @userid pprakash49
 * @GTID 903703245
 * @version 1.0
 */
public class GraphAlgorithms {

    /**
     * Performs a breadth first search (bfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * You may import/use java.util.Set, java.util.List, java.util.Queue, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for BFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the bfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> bfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null. Cannot do BFS algorithm");
        }

        if (graph == null) {
            throw new IllegalArgumentException("Graph is null. Cannot do BFS algorithm");
        }

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist in graph. Cannot do BFS algorithm");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> mapVertex = graph.getAdjList();
        Set<Vertex<T>> visitedSet = new HashSet<>();

        List<Vertex<T>> returnArr = new LinkedList<>();
        Queue<Vertex<T>> graphQueue = new LinkedList<>(); // linkedQueue

        visitedSet.add(start);
        graphQueue.add(start);

        while (!(graphQueue.isEmpty())) {
            Vertex<T> vertex = graphQueue.remove();
            returnArr.add(vertex);
            for (VertexDistance<T> w : mapVertex.get(vertex)) {
                if (!(visitedSet.contains(w.getVertex()))) {
                    visitedSet.add(w.getVertex());
                    graphQueue.add(w.getVertex());
                }
            }
        }

        return returnArr;
    }

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * the parameterized starting vertex.
     *
     * When exploring a vertex, explore in the order of neighbors returned by
     * the adjacency list. Failure to do so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * all points for this method.
     *
     * You may import/use java.util.Set, java.util.List, and
     * any classes that implement the aforementioned interfaces, as long as they
     * are efficient.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for DFS (storing the adjacency list in a variable is fine).
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph to search through
     * @return list of vertices in visited order
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph
     */
    public static <T> List<Vertex<T>> dfs(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null. Cannot do DFS algorithm");
        }

        if (graph == null) {
            throw new IllegalArgumentException("Graph is nul. Cannot do DFS algorithm");
        }

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist in graph. Cannot do DFS algorithm");
        }

        Map<Vertex<T>, List<VertexDistance<T>>> mapVertex = graph.getAdjList();
        Set<Vertex<T>> visitedSet = new HashSet<>();
        List<Vertex<T>> returnArr = new LinkedList<>();

        rDFS(start, graph, mapVertex, visitedSet, returnArr);

        return returnArr;

    }

    /**
     * DFS recursive call (helper method)
     * @param v vertex to start
     * @param graph inputted graph
     * @param map map extracted from graph
     * @param visited set of visited nodes
     * @param finalArr final array to be outputted
     * @param <T> type of data that is being traversed
     */
    private static <T> void rDFS(Vertex<T> v, Graph<T> graph,
            Map<Vertex<T>, List<VertexDistance<T>>> map,
                                 Set<Vertex<T>> visited,
                                 List<Vertex<T>> finalArr) {
        if (visited.size() == graph.getVertices().size()) {
            return;
        }

        visited.add(v);
        finalArr.add(v);

        for (VertexDistance<T> w : map.get(v)) {
            if (!(visited.contains(w.getVertex()))) {
                rDFS(w.getVertex(), graph, map, visited, finalArr);
            }
        }
    }

    /**
     * Finds the single-source shortest distance between the start vertex and
     * all vertices given a weighted graph (you may assume non-negative edge
     * weights).
     *
     * Return a map of the shortest distances such that the key of each entry
     * is a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing
     * infinity) if no path exists.
     *
     * You may import/use java.util.PriorityQueue,
     * java.util.Map, and java.util.Set and any class that
     * implements the aforementioned interfaces, as long as your use of it
     * is efficient as possible.
     *
     * You should implement the version of Dijkstra's where you use two
     * termination conditions in conjunction.
     *
     * 1) Check if all of the vertices have been visited.
     * 2) Check if the PQ is empty.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * @param <T>   the generic typing of the data
     * @param start the vertex to begin the Dijkstra's on (source)
     * @param graph the graph we are applying Dijkstra's to
     * @return a map of the shortest distances from start to every
     * other node in the graph
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Map<Vertex<T>, Integer> dijkstras(Vertex<T> start,
                                                        Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null. Cannot do dijkstras algorithm");
        }

        if (graph == null) {
            throw new IllegalArgumentException("Graph is nul. Cannot do dijkstras algorithm");
        }

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist in graph. Cannot do dijkstras algorithm");
        }

        Set<Vertex<T>> vs = new HashSet<>();
        Map<Vertex<T>, List<VertexDistance<T>>> mapVertex = graph.getAdjList();
        Map<Vertex<T>, Integer> distanceMap = new HashMap<>();
        PriorityQueue<VertexDistance<T>> pQueue = new PriorityQueue<>();

        for (Vertex<T> v : mapVertex.keySet()) {
            if (v.equals(start)) {
                distanceMap.put(v, 0);
            } else {
                distanceMap.put(v, Integer.MAX_VALUE);
            }
        }

        pQueue.add(new VertexDistance<>(start, 0));
        while (!(pQueue.isEmpty()) && (vs.size() <= graph.getVertices().size())) {
            VertexDistance<T> v = pQueue.remove();

            if (!(vs.contains(v.getVertex()))) {
                vs.add(v.getVertex());
                distanceMap.put(v.getVertex(), v.getDistance());
                for (VertexDistance<T> vd : mapVertex.get(v.getVertex())) {
                    if (!(vs.contains(vd.getVertex()))) {
                        pQueue.add(new VertexDistance<>(vd.getVertex(), vd.getDistance() + v.getDistance()));
                    }
                }
            }
        }

        return distanceMap;
    }

    /**
     * Runs Prim's algorithm on the given graph and returns the Minimum
     * Spanning Tree (MST) in the form of a set of Edges. If the graph is
     * disconnected and therefore no valid MST exists, return null.
     *
     * You may assume that the passed in graph is undirected. In this framework,
     * this means that if (u, v, 3) is in the graph, then the opposite edge
     * (v, u, 3) will also be in the graph, though as a separate Edge object.
     *
     * The returned set of edges should form an undirected graph. This means
     * that every time you add an edge to your return set, you should add the
     * reverse edge to the set as well. This is for testing purposes. This
     * reverse edge does not need to be the one from the graph itself; you can
     * just make a new edge object representing the reverse edge.
     *
     * You may assume that there will only be one valid MST that can be formed.
     *
     * You should NOT allow self-loops or parallel edges in the MST.
     *
     * You may import/use PriorityQueue, java.util.Set, and any class that 
     * implements the aforementioned interface.
     *
     * DO NOT modify the structure of the graph. The graph should be unmodified
     * after this method terminates.
     *
     * The only instance of java.util.Map that you may use is the
     * adjacency list from graph. DO NOT create new instances of Map
     * for this method (storing the adjacency list in a variable is fine).
     *
     * @param <T> the generic typing of the data
     * @param start the vertex to begin Prims on
     * @param graph the graph we are applying Prims to
     * @return the MST of the graph or null if there is no valid MST
     * @throws IllegalArgumentException if any input is null, or if start
     *                                  doesn't exist in the graph.
     */
    public static <T> Set<Edge<T>> prims(Vertex<T> start, Graph<T> graph) {
        if (start == null) {
            throw new IllegalArgumentException("Start is null. Cannot do dijkstras algorithm");
        }

        if (graph == null) {
            throw new IllegalArgumentException("Graph is nul. Cannot do dijkstras algorithm");
        }

        if (!graph.getVertices().contains(start)) {
            throw new IllegalArgumentException("Start does not exist in graph. Cannot do dijkstras algorithm");
        }

        Set<Vertex<T>> vs = new HashSet<>();
        Set<Edge<T>> returnSet = new HashSet<>();

        Map<Vertex<T>, List<VertexDistance<T>>> mapVertex = graph.getAdjList();
        PriorityQueue<Edge<T>> pQueue = new PriorityQueue<>();

        vs.add(start);

        for (VertexDistance<T> v1 : mapVertex.get(start)) {
            pQueue.add(new Edge<>(start, v1.getVertex(), v1.getDistance()));
        }

        while (!(pQueue.isEmpty())) {
            Edge<T> currEdge = pQueue.remove();

            if (!(vs.contains(currEdge.getV()))) {
                returnSet.add(currEdge);
                returnSet.add(new Edge<>(currEdge.getV(), currEdge.getU(), currEdge.getWeight()));
                vs.add(currEdge.getV());

                for (Edge<T> e : graph.getEdges()) {
                    if (!(vs.contains(e.getV()))) {
                        pQueue.add(new Edge<>(e.getU(), e.getV(), e.getWeight()));
                    }
                }
            }
        }

        if (vs.size() != mapVertex.size()) {
            return null;
        }

        return returnSet;
    }
}