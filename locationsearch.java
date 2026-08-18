import java.util.*;

public class locationsearch {

    private HashMap<Integer, String> locations;

    public locationsearch(HashMap<Integer, String> locations) {
        this.locations = locations;
    }

    public void search(String query) {

        query = query.toLowerCase();

        boolean found = false;

        for (Map.Entry<Integer, String> entry : locations.entrySet()) {

            int id = entry.getKey();
            String name = entry.getValue();

            if (name.toLowerCase().contains(query)) {

                System.out.println(id + " -> " + name);
                found = true;
            }
        }

        if (!found) {
            System.out.println("Location not found.");
        }
    }
}