import java.util.*;

public class DFS {

    public static void traverse(
            Graphlpu graph,
            int start,
            HashMap<Integer, String> locations) {

        ArrayList<ArrayList<edges>> adj = graph.getGraph();

        boolean[] visited = new boolean[56];

        System.out.println("DFS Traversal:");

        dfs(start, adj, visited, locations);

        System.out.println("END");
    }

    private static void dfs(
            int current,
            ArrayList<ArrayList<edges>> adj,
            boolean[] visited,
            HashMap<Integer, String> locations) {

        // Mark current location as visited
        visited[current] = true;

        // Visit current location
        System.out.print(
                locations.get(current) + " -> "
        );

        // Visit all neighbouring locations
        for (edges edge : adj.get(current)) {

            int neighbour = edge.destination;

            if (!visited[neighbour]) {

                dfs(
                    neighbour,
                    adj,
                    visited,
                    locations
                );
            }
        }
    }
}