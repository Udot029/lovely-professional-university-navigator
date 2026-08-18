import java.util.*;
public class Main {
    
    // Initialize graph with campus connections based on the map
    static void initializeGraph(Graphlpu graph) {
        // Connections based on campus proximity
        graph.addEdge(1, 2, 50);    // LIM - Campus Cafe
        graph.addEdge(2, 3, 30);    // Campus Cafe - Auditorium
        graph.addEdge(3, 14, 40);   // Auditorium - Business Block
        graph.addEdge(14, 18, 35);  // Business Block - Education
        graph.addEdge(18, 25, 45);  // Education - Engineering
        graph.addEdge(18, 26, 40);  // Education - Engineering
        graph.addEdge(25, 26, 15);  // Engineering - Engineering
        graph.addEdge(26, 27, 20);  // Engineering - Engineering
        graph.addEdge(27, 28, 20);  // Engineering - Engineering
        graph.addEdge(28, 29, 20);  // Engineering - Engineering
        graph.addEdge(14, 15, 30);  // Business Block - Lovely Mall
        graph.addEdge(15, 17, 25);  // Lovely Mall - Mall-II
        graph.addEdge(1, 4, 60);    // LIM - LIT Engineering
        graph.addEdge(4, 5, 40);    // LIT Eng - LIT Pharmacy
        graph.addEdge(5, 6, 35);    // LIT Pharmacy - LIT Architecture
        graph.addEdge(30, 31, 30);  // Chancellor - Admin Block
        graph.addEdge(31, 32, 20);  // Admin Block - Admin Block
        graph.addEdge(20, 30, 50);  // LSB - Chancellor
        graph.addEdge(20, 31, 60);  // LSB - Admin Block
    }
    
    public static void main(String[] args) {
        HashMap<Integer, String> locations = locationdata.getLocations();
        Graphlpu graph = new Graphlpu(55);
        Scanner scanner = new Scanner(System.in);
        
        // Auto-load campus map connections
        initializeGraph(graph);
        System.out.println("Campus map loaded with connections!\n");
        
        System.out.println("===== LPU SMART CAMPUS NAVIGATOR =====\n");
        
        // Display all locations
        System.out.println("Available Locations:");
        for (int i = 1; i <= 55; i++) {
            if (locations.containsKey(i)) {
                System.out.println(i + " -> " + locations.get(i));
            }
        }
        
        boolean running = true;
        while (running) {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Add Area");
            System.out.println("2. View Graph");
            System.out.println("3. Shortest Path (with Distance)");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");
            
            int choice = scanner.nextInt();
            
            switch (choice) {
                case 1:
                    System.out.print("Enter source location (1-55): ");
                    int source = scanner.nextInt();
                    System.out.print("Enter destination location (1-55): ");
                    int dest = scanner.nextInt();
                    System.out.print("Enter weight/distance: ");
                    int weight = scanner.nextInt();
                    
                    if (source > 0 && source <= 55 && dest > 0 && dest <= 55) {
                        graph.addEdge(source, dest, weight);
                        System.out.println("✓ Edge added: " + locations.get(source) + 
                                         " <-> " + locations.get(dest) + " (weight: " + weight + ")");
                    } else {
                        System.out.println("Invalid location numbers!");
                    }
                    break;
                    
                case 2:
                    System.out.println("\n===== GRAPH STRUCTURE =====");
                    graph.printGraph(locations);
                    break;
                    
                case 3:
                    System.out.print("Enter source location (1-55): ");
                    int sourceLocation = scanner.nextInt();
                    System.out.print("Enter destination location (1-55): ");
                    int destLocation = scanner.nextInt();
                    
                    if (sourceLocation > 0 && sourceLocation <= 55 && locations.containsKey(sourceLocation) &&
                        destLocation > 0 && destLocation <= 55 && locations.containsKey(destLocation)) {
                        ShortestPath.findPath(graph, sourceLocation, destLocation, locations);
                    } else {
                        System.out.println("Invalid location!");
                    }
                    break;
                    
                case 4:
                    running = false;
                    System.out.println("Thank you! Exiting...");
                    break;
                    
                default:
                    System.out.println("Invalid option!");
            }
        }
        
        scanner.close();
    }
}
