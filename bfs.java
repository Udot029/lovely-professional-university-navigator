import java.util.*;

public class bfs {

    public static void traverse(
            Graphlpu graph,
            int start,
            HashMap<Integer, String> locations) {

        ArrayList<ArrayList<edges>> adj = graph.getGraph();

        boolean[] visited = new boolean[56];

        Queue<Integer> queue = new LinkedList<>();

        visited[start] = true;
        queue.add(start);

        System.out.println("BFS Traversal:");

        while (!queue.isEmpty()) {

            int current = queue.poll();

            System.out.print(
                    locations.get(current) + " -> "
            );

            for (edges edge : adj.get(current)) {

                int neighbour = edge.destination;

                if (!visited[neighbour]) {

                    visited[neighbour] = true;
                    queue.add(neighbour);
                }
            }
        }

        System.out.println("END");
    }
}