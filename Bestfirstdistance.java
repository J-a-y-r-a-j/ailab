import java.util.*;

class Bestfirstdistance {
    
    static class City {
        String name;
        Map<City, Integer> neighbors; // neighboring cities and distances
        
        City(String name) {
            this.name = name;
            this.neighbors = new HashMap<>();
        }
        
        void addNeighbor(City neighbor, int distance) {
            neighbors.put(neighbor, distance);
        }
    }
    
    static class State implements Comparable<State> {
        City city;
        State parent;
        int cost; // actual distance from start
        int heuristic; // straight-line distance to goal (heuristic)
        
        State(City city, State parent, int cost, int heuristic) {
            this.city = city;
            this.parent = parent;
            this.cost = cost;
            this.heuristic = heuristic;
        }
        
        int getTotalCost() {
            return cost + heuristic; // f(n) = g(n) + h(n) for A*, but for Best First we use just heuristic
        }
        
        @Override
        public int compareTo(State other) {
            // Best First Search: prioritize by heuristic (estimated distance to goal)
            return Integer.compare(this.heuristic, other.heuristic);
        }
        
        void print() {
            System.out.println(city.name + " (Actual: " + cost + "km, Heuristic: " + heuristic + "km)");
        }
    }
    
    static Map<String, Integer> straightLineDistances;
    
    public static void solveBestFirst(City start, City goal) {
        PriorityQueue<State> pq = new PriorityQueue<>();
        Set<String> visited = new HashSet<>();
        
        int startHeuristic = straightLineDistances.get(start.name);
        State startState = new State(start, null, 0, startHeuristic);
        pq.add(startState);
        
        System.out.println("=== SHORTEST ROUTE FINDING using BEST FIRST SEARCH ===\n");
        System.out.println("Goal: " + goal.name);
        System.out.println("Heuristic: Straight-line distance to goal\n");
        
        int nodesExplored = 0;
        
        while (!pq.isEmpty()) {
            State current = pq.poll();
            nodesExplored++;
            
            System.out.println("Exploring: " + current.city.name + " | Heuristic: " + current.heuristic + "km");
            
            if (current.city == goal) {
                System.out.println("\n✓ GOAL REACHED!\n");
                printPath(current);
                System.out.println("\n--- STATISTICS ---");
                System.out.println("Total distance: " + current.cost + "km");
                System.out.println("Nodes explored: " + nodesExplored);
                return;
            }
            
            if (visited.contains(current.city.name)) continue;
            visited.add(current.city.name);
            
            // Explore neighbors
            for (Map.Entry<City, Integer> neighbor : current.city.neighbors.entrySet()) {
                City nextCity = neighbor.getKey();
                int roadDistance = neighbor.getValue();
                int newCost = current.cost + roadDistance;
                int heuristic = straightLineDistances.get(nextCity.name);
                
                if (!visited.contains(nextCity.name)) {
                    State nextState = new State(nextCity, current, newCost, heuristic);
                    pq.add(nextState);
                }
            }
        }
        
        System.out.println("No path found to goal!");
    }
    
    static void printPath(State goal) {
        Stack<State> path = new Stack<>();
        State current = goal;
        
        while (current != null) {
            path.push(current);
            current = current.parent;
        }
        
        System.out.println("=== PATH FOUND ===");
        int step = 0;
        State prev = null;
        
        while (!path.isEmpty()) {
            State s = path.pop();
            if (step == 0) {
                System.out.println("Start at: " + s.city.name);
            } else {
                int segmentDist = s.cost - (prev != null ? prev.cost : 0);
                System.out.println("Go to: " + s.city.name + " (+" + segmentDist + "km)");
            }
            prev = s;
            step++;
        }
        System.out.println("\nTotal distance: " + goal.cost + "km");
    }
    
    public static void main(String[] args) {
        // Create cities
        City arad = new City("Arad");
        City bucharest = new City("Bucharest");
        City craiova = new City("Craiova");
        City dobreta = new City("Dobreta");
        City eforie = new City("Eforie");
        City fagaras = new City("Fagaras");
        City giurgiu = new City("Giurgiu");
        City hirsova = new City("Hirsova");
        City iasi = new City("Iasi");
        City lugoj = new City("Lugoj");
        City mehadia = new City("Mehadia");
        City neamt = new City("Neamt");
        City oradea = new City("Oradea");
        City pitesti = new City("Pitesti");
        City rimnicuVilcea = new City("Rimnicu Vilcea");
        City sibiu = new City("Sibiu");
        City timisoara = new City("Timisoara");
        City urziceni = new City("Urziceni");
        City vaslui = new City("Vaslui");
        City zerind = new City("Zerind");
        
        // Add roads (neighbors with distances)
        arad.addNeighbor(sibiu, 140);
        arad.addNeighbor(timisoara, 118);
        arad.addNeighbor(zerind, 75);
        
        bucharest.addNeighbor(fagaras, 211);
        bucharest.addNeighbor(giurgiu, 90);
        bucharest.addNeighbor(pitesti, 101);
        bucharest.addNeighbor(urziceni, 85);
        
        craiova.addNeighbor(dobreta, 120);
        craiova.addNeighbor(pitesti, 138);
        craiova.addNeighbor(rimnicuVilcea, 146);
        
        dobreta.addNeighbor(craiova, 120);
        dobreta.addNeighbor(mehadia, 75);
        
        eforie.addNeighbor(hirsova, 86);
        
        fagaras.addNeighbor(bucharest, 211);
        fagaras.addNeighbor(sibiu, 99);
        
        giurgiu.addNeighbor(bucharest, 90);
        
        hirsova.addNeighbor(eforie, 86);
        hirsova.addNeighbor(urziceni, 98);
        
        iasi.addNeighbor(neamt, 87);
        iasi.addNeighbor(vaslui, 92);
        
        lugoj.addNeighbor(mehadia, 70);
        lugoj.addNeighbor(timisoara, 111);
        
        mehadia.addNeighbor(dobreta, 75);
        mehadia.addNeighbor(lugoj, 70);
        
        neamt.addNeighbor(iasi, 87);
        
        oradea.addNeighbor(sibiu, 151);
        oradea.addNeighbor(zerind, 71);
        
        pitesti.addNeighbor(bucharest, 101);
        pitesti.addNeighbor(craiova, 138);
        pitesti.addNeighbor(rimnicuVilcea, 97);
        
        rimnicuVilcea.addNeighbor(craiova, 146);
        rimnicuVilcea.addNeighbor(pitesti, 97);
        rimnicuVilcea.addNeighbor(sibiu, 80);
        
        sibiu.addNeighbor(arad, 140);
        sibiu.addNeighbor(fagaras, 99);
        sibiu.addNeighbor(oradea, 151);
        sibiu.addNeighbor(rimnicuVilcea, 80);
        
        timisoara.addNeighbor(arad, 118);
        timisoara.addNeighbor(lugoj, 111);
        
        urziceni.addNeighbor(bucharest, 85);
        urziceni.addNeighbor(hirsova, 98);
        urziceni.addNeighbor(vaslui, 142);
        
        vaslui.addNeighbor(iasi, 92);
        vaslui.addNeighbor(urziceni, 142);
        
        zerind.addNeighbor(arad, 75);
        zerind.addNeighbor(oradea, 71);
        
        // Straight-line distances to Bucharest (heuristic)
        straightLineDistances = new HashMap<>();
        straightLineDistances.put("Arad", 366);
        straightLineDistances.put("Bucharest", 0);
        straightLineDistances.put("Craiova", 160);
        straightLineDistances.put("Dobreta", 242);
        straightLineDistances.put("Eforie", 161);
        straightLineDistances.put("Fagaras", 176);
        straightLineDistances.put("Giurgiu", 77);
        straightLineDistances.put("Hirsova", 151);
        straightLineDistances.put("Iasi", 226);
        straightLineDistances.put("Lugoj", 244);
        straightLineDistances.put("Mehadia", 241);
        straightLineDistances.put("Neamt", 234);
        straightLineDistances.put("Oradea", 380);
        straightLineDistances.put("Pitesti", 100);
        straightLineDistances.put("Rimnicu Vilcea", 193);
        straightLineDistances.put("Sibiu", 253);
        straightLineDistances.put("Timisoara", 329);
        straightLineDistances.put("Urziceni", 80);
        straightLineDistances.put("Vaslui", 199);
        straightLineDistances.put("Zerind", 374);
        
        // Find shortest route from Arad to Bucharest
        solveBestFirst(arad, bucharest);
    }
}