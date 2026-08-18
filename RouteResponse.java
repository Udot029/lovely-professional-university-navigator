import java.util.HashMap;
import java.util.List;

public class RouteResponse {
    private String source;
    private String destination;
    private int distance;
    private List<String> path;

    public RouteResponse(String source, String destination, int distance, List<String> path) {
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

    public List<String> getPath() {
        return path;
    }
}
