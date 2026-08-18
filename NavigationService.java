import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NavigationService {
    
    private Graphlpu graph;
    private HashMap<Integer, String> locations;
    
    public NavigationService() {
        // Initialize graph
        this.graph = new Graphlpu(55);
        this.locations = locationdata.getLocations();
        initializeGraph();
    }
    
    private void initializeGraph() {
        // Main campus connections
        graph.addEdge(1, 2, 50);    // LIM - Campus Cafe
        graph.addEdge(2, 3, 30);    // Campus Cafe - Auditorium
        graph.addEdge(3, 14, 40);   // Auditorium - Business Block
        graph.addEdge(1, 30, 200);  // LIM - Chancellor Office
        graph.addEdge(30, 31, 30);  // Chancellor - Administrative Block
        graph.addEdge(31, 32, 20);  // Administrative Block - Administrative Block
        graph.addEdge(20, 30, 50);  // LSB - Chancellor
        graph.addEdge(20, 31, 60);  // LSB - Administrative Block
        graph.addEdge(1, 31, 250); // LIM - Administrative Block

        // Academic and admin corridors
        graph.addEdge(14, 18, 35);  // Business Block - Education
        graph.addEdge(18, 25, 45);  // Education - Engineering
        graph.addEdge(18, 26, 40);  // Education - Engineering
        graph.addEdge(25, 26, 15);  // Engineering - Engineering
        graph.addEdge(26, 27, 20);  // Engineering - Engineering
        graph.addEdge(27, 28, 20);  // Engineering - Engineering
        graph.addEdge(28, 29, 20);  // Engineering - Engineering
        graph.addEdge(14, 15, 30);  // Business Block - Lovely Mall
        graph.addEdge(15, 17, 25);  // Lovely Mall - Mall-II

        // LIT and architecture clusters
        graph.addEdge(1, 4, 60);    // LIM - LIT Engineering
        graph.addEdge(4, 5, 40);    // LIT Eng - LIT Pharmacy
        graph.addEdge(5, 6, 35);    // LIT Pharmacy - LIT Architecture
        graph.addEdge(4, 6, 200);   // LIT Engineering - LIT Architecture
        graph.addEdge(13, 25, 150); // LIT Polytechnic - Engineering
        graph.addEdge(1, 13, 280);  // LIM - LIT Polytechnic

        // Hostels and residential blocks
        graph.addEdge(1, 9, 350);   // LIM - Girls Hostel 1
        graph.addEdge(9, 10, 40);   // Girls Hostel 1 - 2
        graph.addEdge(10, 11, 35);  // Girls Hostel 2 - 3
        graph.addEdge(11, 12, 30);  // Girls Hostel 3 - 4
        graph.addEdge(12, 21, 200); // Girls Hostel 4 - Hostel 5
        graph.addEdge(21, 22, 150); // Girl Hostel 5 - 6

        graph.addEdge(1, 43, 400);  // LIM - Boys Hostel 1
        graph.addEdge(43, 44, 45);  // Boys Hostel 1 - 2
        graph.addEdge(44, 46, 40);  // Boys Hostel 2 - 3
        graph.addEdge(46, 47, 35);  // Boys Hostel 3 - 4
        graph.addEdge(47, 51, 50);  // Boys Hostel 4 - 5
        graph.addEdge(51, 52, 40);  // Boys Hostel 5 - 6

        // Academic blocks
        graph.addEdge(53, 54, 50);  // Academic Block 1 - 2
        graph.addEdge(54, 55, 45);  // Academic Block 2 - 3

        // Support facilities
        graph.addEdge(1, 39, 500);  // LIM - STP
        graph.addEdge(39, 40, 100); // STP - Store
        graph.addEdge(40, 41, 150); // Store - Staff Residence
        graph.addEdge(41, 42, 100); // Staff Residence - Staff Residence

        // Hospital and emergency access
        graph.addEdge(1, 8, 400);   // LIM - Hospital
        graph.addEdge(8, 5, 250);   // Hospital - Pharmacy
    }
    
    public HashMap<Integer, String> getLocations() {
        return locations;
    }
    
    public HashMap<Integer, String> searchLocations(String query) {
        HashMap<Integer, String> results = new HashMap<>();
        String lowerQuery = query.toLowerCase();
        
        for (Integer key : locations.keySet()) {
            if (locations.get(key).toLowerCase().contains(lowerQuery)) {
                results.put(key, locations.get(key));
            }
        }
        
        return results;
    }
    
    public RouteResponse findRoute(int from, int to) {
        if (!locations.containsKey(from) || !locations.containsKey(to)) {
            return null;
        }
        
        String source = locations.get(from);
        String destination = locations.get(to);
        
        // Run Dijkstra
        ShortestPath.RouteResult result = findShortestPath(from, to);
        
        if (result == null) {
            return null;
        }
        
        // Convert path integers to location names
        List<String> pathNames = new ArrayList<>();
        List<Integer> pathList = result.path;
        for (Integer locationId : pathList) {
            if (locations.containsKey(locationId)) {
                pathNames.add(locations.get(locationId));
            }
        }
        
        return new RouteResponse(source, destination, result.distance, pathNames);
    }
    
    private ShortestPath.RouteResult findShortestPath(int source, int destination) {
        ArrayList<ArrayList<edges>> adj = graph.getGraph();
        
        int[] distance = new int[56];
        int[] parent = new int[56];
        java.util.Arrays.fill(distance, Integer.MAX_VALUE);
        java.util.Arrays.fill(parent, -1);
        
        java.util.PriorityQueue<ShortestPath.Node> pq = 
            new java.util.PriorityQueue<>(
                java.util.Comparator.comparingInt(node -> node.distance)
            );
        
        distance[source] = 0;
        pq.add(new ShortestPath.Node(source, 0));
        
        while (!pq.isEmpty()) {
            ShortestPath.Node current = pq.poll();
            int currentVertex = current.vertex;
            int currentDistance = current.distance;
            
            if (currentDistance > distance[currentVertex]) {
                continue;
            }
            
            for (edges edge : adj.get(currentVertex)) {
                int neighbor = edge.destination;
                int weight = edge.weight;
                
                int newDistance = currentDistance + weight;
                
                if (newDistance < distance[neighbor]) {
                    distance[neighbor] = newDistance;
                    parent[neighbor] = currentVertex;
                    pq.add(new ShortestPath.Node(neighbor, newDistance));
                }
            }
        }
        
        if (distance[destination] == Integer.MAX_VALUE) {
            return null;
        }
        
        // Reconstruct path
        List<Integer> path = new ArrayList<>();
        int current = destination;
        while (current != -1) {
            path.add(0, current);
            current = parent[current];
        }
        
        return new ShortestPath.RouteResult(
            locations.get(source), 
            locations.get(destination), 
            distance[destination], 
            path
        );
    }
}
