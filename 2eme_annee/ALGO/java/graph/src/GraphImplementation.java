import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

class Vertex {
    public String name;
    public LinkedList<Edge> edgeList;
    public int distance;
    public boolean visited;

    public Vertex(String name) {
        this.name = name;
        edgeList = new LinkedList<>();
    }

}

class Edge {
    public int weight;
    public Vertex destVertex;

    public Edge(Vertex dest, int w) {
        this.destVertex = dest;
        this.weight = w;
    }

}

class Graph {
    public HashSet<Vertex> nodes;

    public Graph() {
        nodes = new HashSet<>();
    }

    public boolean AddEdge(Vertex v1, Vertex v2, int weight) {
        return v1.edgeList.add(new Edge(v2, weight));
    }

    public boolean AddVertex(Vertex v) {
        return nodes.add(v);
    }

    public void printGraph() {
        for (Vertex v : nodes) {
            System.out.print("vertex name: " + v.name + ": ");
            for (Edge e : v.edgeList) {
                System.out.print("destVertex: " + e.destVertex.name + " weight: " + e.weight + " | ");
            }
            System.out.print("\n");
        }
    }
}

public class GraphImplementation {

    public static void main(String[] args) {
        Graph ourGraph = new Graph();

        // vertices
        Vertex v0 = new Vertex("0");
        Vertex v1 = new Vertex("1");
        Vertex v2 = new Vertex("2");
        Vertex v3 = new Vertex("3");

        ourGraph.AddVertex(v0);
        ourGraph.AddVertex(v1);
        ourGraph.AddVertex(v2);
        ourGraph.AddVertex(v3);

        // edges
        ourGraph.AddEdge(v0, v1, 2);
        ourGraph.AddEdge(v1, v2, 3);
        ourGraph.AddEdge(v2, v0, 1);
        ourGraph.AddEdge(v2, v3, 1);
        ourGraph.AddEdge(v3, v2, 4);

        ourGraph.printGraph();
    }

    public static int dijkstra(Graph graph, Vertex src, Vertex dst) {
        List<Vertex> toVisit = new ArrayList<>();
        src.distance = 0;
        toVisit.add(src);
        while (!toVisit.isEmpty()) {
            Vertex next = getNextVertexToVisit(toVisit);
            if (next.name.equals(dst.name)) {
                return next.distance;
            }
            next.visited = true;
            Edge[] neighbors = next.edgeList.toArray(new Edge[0]);
            for (int i = 0; i < neighbors.length; i++) {
                Vertex v = neighbors[i].destVertex;
                Edge e = neighbors[i];
                if (!v.visited) {
                    toVisit.add(v);
                    v.distance = next.distance + e.weight;
                }
            }
        }
        return -1;
    }

    public static Vertex getNextVertexToVisit(List<Vertex> vertexs, Vertex from) {
        Vertex next = null;
        int weight = -1;
        for (Vertex vertex : vertexs) {
            for (Edge edge : from.edgeList) {
                if (edge.destVertex.name.equals(vertex.name)) {
                    if (weight == -1 || edge.weight < weight) {
                        weight = edge.weight;
                        next = vertex;
                    }
                }
            }
        }
        return next;
    }
}