
public class App {
    public static void main(String[] args) throws Exception {
        Graph graph = new Graph(new String[] { "A", "B", "C" }, false)
                .addEdge("A", "B", 5)
                .addEdge("A", "C", 9);
        System.out.println(graph.toString());
    }
}
