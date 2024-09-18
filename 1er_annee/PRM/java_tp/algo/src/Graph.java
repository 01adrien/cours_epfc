import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    public String[] vertices;
    public boolean isOriented;
    HashMap<String, ArrayList<Edge>> adjencyList;

    public Graph(String[] vertices, boolean isOriented) {
        this.vertices = vertices;
        this.isOriented = isOriented;
        this.adjencyList = new HashMap<String, ArrayList<Edge>>();
        for (String s : vertices) {
            adjencyList.put(s, new ArrayList<Edge>());
        }
    }

    public Graph addEdge(String from, String to, int weight) {
        Edge e = new Edge(to, weight);
        if (this.isOriented) {
            this.adjencyList.get(from).add(e);
        } else {
            this.adjencyList.get(from).add(e);
            this.adjencyList.get(to).add(new Edge(from, weight));
        }
        return this;
    }

    @Override
    public String toString() {
        String str = "";
        for (String v : this.adjencyList.keySet()) {
            str += "From " + v + ":\n";
            for (Edge e : this.adjencyList.get(v)) {
                str += e.toString();
            }
            str += "\n";
        }
        return str;
    }

}
