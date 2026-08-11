import java.util.*;
class Graphlpu {
    private int vertices;
    private ArrayList<ArrayList<edges>> graph;
    public Graphlpu(int vertices) {
        this.vertices = vertices;
        graph = new ArrayList<>();
        for (int i = 0; i <= vertices; i++) {
            graph.add(new ArrayList<>());
        }
    }
    public void addEdge(int source, int destination, int weight) {
        graph.get(source).add(
            new edges(destination, weight)
        );
        graph.get(destination).add(
            new edges(source, weight)
        );
    }
    public ArrayList<ArrayList<edges>> getGraph() {
        return graph;
    }
    public void printGraph(
            HashMap<Integer, String> locations) {
        for (int i = 1; i <= vertices; i++) {
            System.out.print(
                i + " (" + locations.get(i) + ") -> "
            );
            for (edges edge : graph.get(i)) {
                System.out.print(
                    edge.destination
                    + "("
                    + locations.get(edge.destination)
                    + ", "
                    + edge.weight
                    + ") "
                );
            }
            System.out.println();
        }
    }
}
