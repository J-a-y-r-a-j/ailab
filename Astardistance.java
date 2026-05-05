import java.util.*;

class Astardistance {
    
    static class Node implements Comparable<Node> {
        String name;
        Node parent;
        int g; // actual cost from start
        int h; // heuristic to goal
        
        Node(String name, Node parent, int g, int h) {
            this.name = name;
            this.parent = parent;
            this.g = g;
            this.h = h;
        }
        
        int f() { return g + h; }
        
        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f(), other.f());
        }
        
        void print() {
            System.out.println(name + " | g=" + g + ", h=" + h + ", f=" + f());
        }
    }
    
    static Map<String, Map<String, Integer>> graph = new HashMap<>();
    static Map<String, Integer> heuristic = new HashMap<>();
    
    public static void solveAStar(String start, String goal) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Map<String, Integer> bestG = new HashMap<>();
        
        Node startNode = new Node(start, null, 0, heuristic.get(start));
        openSet.add(startNode);
        bestG.put(start, 0);
        
        System.out.println("=== A* SHORTEST ROUTE FINDER ===\n");
        System.out.println("From: " + start + " To: " + goal);
        System.out.println("Heuristic: Estimated distance to goal\n");
        
        int nodesExplored = 0;
        
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            nodesExplored++;
            
            System.out.println("Exploring: " + current.name + " (f=" + current.f() + ")");
            
            if (current.name.equals(goal)) {
                System.out.println("\n✓ DESTINATION REACHED!\n");
                printPath(current);
                System.out.println("\n--- STATISTICS ---");
                System.out.println("Nodes explored: " + nodesExplored);
                System.out.println("Total distance: " + current.g + "km");
                return;
            }
            
            for (Map.Entry<String, Integer> neighbor : graph.get(current.name).entrySet()) {
                String nextCity = neighbor.getKey();
                int roadDist = neighbor.getValue();
                int tentativeG = current.g + roadDist;
                
                if (!bestG.containsKey(nextCity) || tentativeG < bestG.get(nextCity)) {
                    bestG.put(nextCity, tentativeG);
                    Node nextNode = new Node(nextCity, current, tentativeG, heuristic.get(nextCity));
                    openSet.add(nextNode);
                }
            }
        }
        
        System.out.println("No path found!");
    }
    
    static void printPath(Node goal) {
        Stack<Node> path = new Stack<>();
        Node current = goal;
        
        while (current != null) {
            path.push(current);
            current = current.parent;
        }
        
        System.out.println("=== BEST ROUTE ===");
        int step = 0;
        while (!path.isEmpty()) {
            Node n = path.pop();
            if (step == 0) {
                System.out.println("Start at " + n.name);
            } else {
                int segment = n.g - (path.isEmpty() ? 0 : 
                    (path.peek() != null ? path.peek().g : n.g - (step > 1 ? 100 : 0)));
                System.out.println("→ " + n.name + " (distance: " + (n.g - (step > 1 ? path.peek().g : 0)) + "km)");
            }
            step++;
        }
    }
    
    public static void main(String[] args) {
        // Build simplified graph
        graph.put("Arad", new HashMap<>());
        graph.put("Bucharest", new HashMap<>());
        graph.put("Craiova", new HashMap<>());
        graph.put("Fagaras", new HashMap<>());
        graph.put("Pitesti", new HashMap<>());
        graph.put("RimnicuVilcea", new HashMap<>());
        graph.put("Sibiu", new HashMap<>());
        graph.put("Timisoara", new HashMap<>());
        graph.put("Zerind", new HashMap<>());
        
        // Add edges
        graph.get("Arad").put("Sibiu", 140);
        graph.get("Arad").put("Timisoara", 118);
        graph.get("Arad").put("Zerind", 75);
        
        graph.get("Bucharest").put("Fagaras", 211);
        graph.get("Bucharest").put("Pitesti", 101);
        
        graph.get("Craiova").put("Pitesti", 138);
        graph.get("Craiova").put("RimnicuVilcea", 146);
        
        graph.get("Fagaras").put("Bucharest", 211);
        graph.get("Fagaras").put("Sibiu", 99);
        
        graph.get("Pitesti").put("Bucharest", 101);
        graph.get("Pitesti").put("Craiova", 138);
        graph.get("Pitesti").put("RimnicuVilcea", 97);
        
        graph.get("RimnicuVilcea").put("Craiova", 146);
        graph.get("RimnicuVilcea").put("Pitesti", 97);
        graph.get("RimnicuVilcea").put("Sibiu", 80);
        
        graph.get("Sibiu").put("Arad", 140);
        graph.get("Sibiu").put("Fagaras", 99);
        graph.get("Sibiu").put("RimnicuVilcea", 80);
        
        graph.get("Timisoara").put("Arad", 118);
        
        graph.get("Zerind").put("Arad", 75);
        
        // Heuristic (straight-line distance to Bucharest)
        heuristic.put("Arad", 366);
        heuristic.put("Bucharest", 0);
        heuristic.put("Craiova", 160);
        heuristic.put("Fagaras", 176);
        heuristic.put("Pitesti", 100);
        heuristic.put("RimnicuVilcea", 193);
        heuristic.put("Sibiu", 253);
        heuristic.put("Timisoara", 329);
        heuristic.put("Zerind", 374);
        
        solveAStar("Arad", "Bucharest");
    }
}