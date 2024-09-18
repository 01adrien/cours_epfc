public class Edge {
    public String to;
    public int weight;

    public Edge(String to, int weight) {
        this.to = to;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.to + "(" + this.weight + ") ";
    }

}
