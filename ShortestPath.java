import java.util.*;

public class ShortestPath {
    
    // RouteResult class to return path information
    public static class RouteResult {
        public String source;
        public String destination;
        public int distance;
        public List<Integer> path;
        
        public RouteResult(String source, String destination, int distance, List<Integer> path) {
            this.source = source;
            this.destination = destination;
            this.distance = distance;
            this.path = path;
        }
        
        public String getSource() {
            return source;
        }
        
        public String getDestination() {
            return destination;
        }
        
        public int getDistance() {
            return distance;
        }
        
        public List<Integer> getPath() {
            return path;
        }
    }
    
    // Node class for PriorityQueue
    static class Node {
        int vertex;
        int distance;
        
        Node(int vertex, int distance) {
            this.vertex = vertex;
            this.distance = distance;
        }
    }
    
    public static void findPath(
            Graphlpu graph,
            int source,
            int destination,
            HashMap<Integer, String> locations) {
        
        ArrayList<ArrayList<edges>> adj = graph.getGraph();
        
        // Check if nodes exist
        if (!locations.containsKey(source) || !locations.containsKey(destination)) {
            System.out.println("Invalid location!");
            return;
        }
        
        int[] distance = new int[56];
        int[] parent = new int[56];
        
        // Initialize arrays
        Arrays.fill(distance, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        
        // Priority Queue: min-heap by distance
        PriorityQueue<Node> pq = new PriorityQueue<>(
            Comparator.comparingInt(node -> node.distance)
        );
        
        distance[source] = 0;
        pq.add(new Node(source, 0));
        
        // Dijkstra's Algorithm with Priority Queue
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentVertex = current.vertex;
            int currentDistance = current.distance;
            
            // Ignore outdated entries
            if (currentDistance > distance[currentVertex]) {
                continue;
            }
            
            // Explore neighbors
            for (edges edge : adj.get(currentVertex)) {
                int neighbor = edge.destination;
                int weight = edge.weight;
                
                int newDistance = currentDistance + weight;
                
                if (newDistance < distance[neighbor]) {
                    distance[neighbor] = newDistance;
                    parent[neighbor] = currentVertex;
                    pq.add(new Node(neighbor, newDistance));
                }
            }
        }
        
        // No path exists
        if (distance[destination] == Integer.MAX_VALUE) {
            System.out.println("No path exists from " + locations.get(source) + " to " + locations.get(destination));
            return;
        }
        
        // Reconstruct path
        ArrayList<Integer> path = new ArrayList<>();
        int current = destination;
        while (current != -1) {
            path.add(0, current);
            current = parent[current];
        }
        
        // Print the path
        System.out.println("\n===== STEP-BY-STEP DIRECTIONS =====");
        System.out.println("======================================");
        System.out.println("START: " + locations.get(source));
        
        // Print each step with distance
        int distanceCovered = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            int currentLoc = path.get(i);
            int nextLoc = path.get(i + 1);
            
            // Find distance between current and next
            int segmentDistance = 0;
            for (edges edge : adj.get(currentLoc)) {
                if (edge.destination == nextLoc) {
                    segmentDistance = edge.weight;
                    break;
                }
            }
            
            distanceCovered += segmentDistance;
            System.out.println("\nStep " + (i + 1) + ":");
            System.out.println("  Go to: " + locations.get(nextLoc));
            System.out.println("  Distance: " + segmentDistance + " meters");
            System.out.println("  Total so far: " + distanceCovered + " meters");
        }
        
        System.out.println("\n======================================");
        System.out.println("DESTINATION: " + locations.get(destination));
        System.out.println("======================================");
        System.out.println("Total Distance: " + distance[destination] + " meters");
        System.out.println("Total Walking Time: ~" + (distance[destination] / 2) + " seconds");
        System.out.println("Total Stops: " + (path.size() - 1));
    }
}
