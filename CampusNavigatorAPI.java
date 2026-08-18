import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class CampusNavigatorAPI {
    
    private static NavigationService navService;
    
    public static void main(String[] args) throws IOException {
        navService = new NavigationService();
        
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // API endpoints
        server.createContext("/api/locations", new LocationsHandler());
        server.createContext("/api/search", new SearchHandler());
        server.createContext("/api/route", new RouteHandler());
        
        server.setExecutor(null);
        server.start();
        
        System.out.println("Campus Navigator API started on port 8080");
    }
    
    // Handler for /api/locations
    static class LocationsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCORS(exchange);
            
            if (exchange.getRequestMethod().equals("GET")) {
                HashMap<Integer, String> locations = navService.getLocations();
                String response = toJson(locations);
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
                exchange.close();
            }
        }
    }
    
    // Handler for /api/search
    static class SearchHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCORS(exchange);
            
            if (exchange.getRequestMethod().equals("GET")) {
                String query = exchange.getRequestURI().getQuery();
                String searchTerm = "";
                
                if (query != null && query.contains("query=")) {
                    searchTerm = query.split("query=")[1];
                    searchTerm = java.net.URLDecoder.decode(searchTerm, "UTF-8");
                }
                
                HashMap<Integer, String> results = navService.searchLocations(searchTerm);
                String response = toJson(results);
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
                exchange.close();
            }
        }
    }
    
    // Handler for /api/route
    static class RouteHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            enableCORS(exchange);
            
            if (exchange.getRequestMethod().equals("GET")) {
                String query = exchange.getRequestURI().getQuery();
                int from = 0, to = 0;
                
                if (query != null) {
                    String[] params = query.split("&");
                    for (String param : params) {
                        if (param.startsWith("from=")) {
                            from = Integer.parseInt(param.split("=")[1]);
                        }
                        if (param.startsWith("to=")) {
                            to = Integer.parseInt(param.split("=")[1]);
                        }
                    }
                }
                
                RouteResponse route = navService.findRoute(from, to);
                
                if (route != null) {
                    String response = routeToJson(route);
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    exchange.getResponseBody().write(response.getBytes(StandardCharsets.UTF_8));
                } else {
                    String error = "{\"error\": \"No route found\"}";
                    exchange.getResponseHeaders().set("Content-Type", "application/json");
                    exchange.sendResponseHeaders(404, error.getBytes(StandardCharsets.UTF_8).length);
                    exchange.getResponseBody().write(error.getBytes(StandardCharsets.UTF_8));
                }
                exchange.close();
            }
        }
    }
    
    // CORS headers
    private static void enableCORS(HttpExchange exchange) {
        exchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().set("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().set("Access-Control-Allow-Headers", "Content-Type");
        
        if (exchange.getRequestMethod().equals("OPTIONS")) {
            try {
                exchange.sendResponseHeaders(200, -1);
                exchange.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    private static String toJson(HashMap<Integer, String> map) {
        StringBuilder sb = new StringBuilder("{");
        int count = 0;
        for (Integer key : map.keySet()) {
            if (count > 0) sb.append(",");
            sb.append("\"").append(key).append("\":\"").append(map.get(key)).append("\"");
            count++;
        }
        sb.append("}");
        return sb.toString();
    }
    
    private static String routeToJson(RouteResponse route) {
        StringBuilder sb = new StringBuilder("{");
        sb.append("\"source\":\"").append(route.getSource()).append("\",");
        sb.append("\"destination\":\"").append(route.getDestination()).append("\",");
        sb.append("\"distance\":").append(route.getDistance()).append(",");
        sb.append("\"path\":[");
        
        java.util.List<String> path = route.getPath();
        for (int i = 0; i < path.size(); i++) {
            if (i > 0) sb.append(",");
            sb.append("\"").append(path.get(i)).append("\"");
        }
        
        sb.append("]}");
        return sb.toString();
    }
}
