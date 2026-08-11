import java.util.*;
public class Main {
    public static void main(String[] args) {
        HashMap<Integer, String> locations = locationdata.getLocations();
        Graphlpu graph = new Graphlpu(55);
        System.out.println(
            "===== LPU SMART CAMPUS NAVIGATOR ====="
        );
        System.out.println();
        System.out.println("LPU Locations:");
        for (int i = 1; i <= 55; i++) {
            if (locations.containsKey(i)) {
                System.out.println(
                    i + " -> " + locations.get(i)
                );
            }
        }
    }
}
