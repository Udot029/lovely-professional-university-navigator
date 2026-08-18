public class LpuGraph{

    public static void buildGraph(Graphlpu graph) {

        /*
         * LPU Campus Graph
         *
         * Format:
         *
         * graph.addEdge(source, destination, distance);
         */

        // Main campus hub connections
        graph.addEdge(1, 2, 150);   // LIM to Campus Cafe
        graph.addEdge(1, 3, 300);   // LIM to Auditorium
        graph.addEdge(2, 3, 200);   // Campus Cafe to Auditorium
        
        // Engineering blocks (4, 25-38)
        graph.addEdge(4, 25, 100);  // LIT Engineering to Engineering 1
        graph.addEdge(25, 26, 50);  // Engineering 1 to 2
        graph.addEdge(26, 27, 50);  // Engineering 2 to 3
        graph.addEdge(27, 28, 50);  // Engineering 3 to 4
        graph.addEdge(28, 33, 60);  // Engineering 4 to 5
        graph.addEdge(33, 34, 50);  // Engineering 5 to 6
        graph.addEdge(34, 35, 50);  // Engineering 6 to 7
        graph.addEdge(35, 36, 50);  // Engineering 7 to 8
        graph.addEdge(36, 37, 50);  // Engineering 8 to 9
        graph.addEdge(37, 38, 50);  // Engineering 9 to 10
        
        // Pharmacy connections (5, 7)
        graph.addEdge(5, 7, 100);   // LIT Pharmacy to Pharmacy
        graph.addEdge(4, 5, 80);    // LIT Engineering to Pharmacy
        
        // Architecture connection (6)
        graph.addEdge(4, 6, 200);   // LIT Engineering to Architecture
        
        // Hospital connection (8)
        graph.addEdge(1, 8, 400);   // LIM to Hospital
        graph.addEdge(8, 5, 250);   // Hospital to Pharmacy
        
        // Girls Hostels (9-12, 21-22)
        graph.addEdge(9, 10, 150);  // Girls Hostel 1 to 2
        graph.addEdge(10, 11, 150); // Girls Hostel 2 to 3
        graph.addEdge(11, 12, 150); // Girls Hostel 3 to 4
        graph.addEdge(21, 22, 150); // Girl Hostel 5 to 6
        graph.addEdge(12, 21, 200); // Girls Hostel 4 to 5
        graph.addEdge(1, 9, 350);   // LIM to Girls Hostel 1
        
        // Boys Hostels (43-46)
        graph.addEdge(43, 44, 150); // Boys Hostel 1 to 2
        graph.addEdge(44, 45, 100); // Boys Hostel 2 to 2
        graph.addEdge(45, 46, 150); // Boys Hostel 2 to 3
        graph.addEdge(1, 43, 400);  // LIM to Boys Hostel 1
        
        // Educational blocks (18, 14, 16)
        graph.addEdge(1, 14, 250);  // LIM to Business Block
        graph.addEdge(14, 18, 180); // Business Block to Education
        graph.addEdge(14, 16, 220); // Business Block to Hotel Mgt
        
        // Polytechnic (13)
        graph.addEdge(13, 25, 150); // LIT Polytechnic to Engineering 1
        graph.addEdge(1, 13, 280);  // LIM to Polytechnic
        
        // Shopping areas (15, 17)
        graph.addEdge(15, 17, 300); // Lovely Mall to Mall II
        graph.addEdge(2, 15, 400);  // Campus Cafe to Lovely Mall
        
        // Administrative areas (30-32)
        graph.addEdge(1, 30, 200);  // LIM to Chancellor Office
        graph.addEdge(30, 31, 100); // Chancellor Office to Admin Block 1
        graph.addEdge(31, 32, 50);  // Admin Block 1 to 2
        graph.addEdge(1, 31, 250);  // LIM to Admin Block
        
        // Support facilities (39-42)
        graph.addEdge(39, 40, 100); // STP to Store
        graph.addEdge(40, 41, 150); // Store to Staff Residence 1
        graph.addEdge(41, 42, 100); // Staff Residence 1 to 2
        graph.addEdge(1, 39, 500);  // LIM to STP
        
        // Cross connections for accessibility
        graph.addEdge(3, 14, 250);  // Auditorium to Business Block
        graph.addEdge(18, 25, 180); // Education to Engineering 1
        graph.addEdge(2, 9, 400);   // Campus Cafe to Girls Hostel
        graph.addEdge(20, 1, 300);  // LSB to LIM

    }
}