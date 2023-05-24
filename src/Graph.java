import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.io.File;
import java.io.FileNotFoundException;
public class Graph {
    static int nodeCount;
    static Map<Node, List<Edge>> graph = new HashMap<>();
    public static void readGraph()///////////////////////////////////
    {
        int counter = 0;
        try (Scanner scanner = new Scanner(new File("graph.txt"))) {
            String[] firstRow = scanner.nextLine().split(" ");
            nodeCount = Integer.parseInt(firstRow[0]);
            for (int i = 1; i < nodeCount + 1; i++) {
                Node created = new Node(i);
                Node.nodes.add(created);
                List<Edge> relatedEdges = new ArrayList<>();
                graph.put(created, relatedEdges);
            }
            while (scanner.hasNextLine()) {
                String[] tokens = scanner.nextLine().split(" ");
                int startID = Integer.parseInt(tokens[0]);
                int endID = Integer.parseInt(tokens[1]);
                int weight = Integer.parseInt(tokens[2]);
                counter++;
                Edge NEW = new Edge(counter, Node.getNodeByID(startID), Node.getNodeByID(endID), weight);
                Edge.edges.add(NEW);
                List<Edge> to_be_updated = graph.get(Node.getNodeByID(endID));
                to_be_updated.add(NEW);
                to_be_updated = graph.get(Node.getNodeByID(startID));
                to_be_updated.add(NEW);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public static List<Node> findPath(Map<Node, List<Edge>> graph, Node start, Node goal) ////////////////////////
    {
        PriorityQueue<Node> queue = new PriorityQueue<>();
        Map<Node, Integer> gScores = new HashMap<>();
        for (int i = 1; i < nodeCount + 1; i++)
            gScores.put(Node.getNodeByID(i), 0);
        Map<Node, Node> parents = new HashMap<>();

        start.score = 0;
        queue.add(start);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            if (current == goal) {
                List<Node> path = new ArrayList<>();
                while (parents.containsKey(current)) {
                    path.add(current);
                    current = parents.get(current);
                }
                path.add(start);
                Collections.reverse(path);
                return path;
            }
            for (Edge neighbor : graph.get(current)) {
                int tentativeGScore = gScores.getOrDefault(current, Integer.MAX_VALUE) + neighbor.weight;
                Node end = new Node();
                if (neighbor.node1 == current)
                    end = neighbor.node2;
                else if (neighbor.node2 == current)
                    end = neighbor.node1;
                if (tentativeGScore < end.score) {
                    end.score = tentativeGScore;
                    gScores.put(end, tentativeGScore);
                    parents.put(end, current);
                    queue.add(end);
                }
            }
        }
        return null;
    }
    public static void showPath(int ID1, int ID2)////////////////////
    {
        Node.resetScores();
        readGraph();
        Node start = Node.getNodeByID(ID1);
        Node end = Node.getNodeByID(ID2);
        List<Node> path = findPath(graph, start, end);
        for (int i = 0; i < Objects.requireNonNull(path).size(); i++)
            System.out.print(path.get(i).ID + " -> ");
        System.out.println("Destination reached");
        System.out.println("Estimated Time: "+end.score+"m");
    }
    public static int calculateTime(int ID1, int ID2)////////////////////
    {
        Node.resetScores();
        readGraph();
        Node start = Node.getNodeByID(ID1);
        Node end = Node.getNodeByID(ID2);
        List<Node> path = findPath(graph, start, end);
        return end.score;
    }

    public static void showMap() {
        readGraph();
        for (Map.Entry<Node, List<Edge>> entry : graph.entrySet()) {
            Node node = entry.getKey();
            List<Edge> edges = entry.getValue();
            System.out.println("Node ID: " + node.ID);
            System.out.println("Connected edges: ");
            for (int i = 0; i < edges.size(); i++)
                System.out.println("Start: " + edges.get(i).node1.ID + " End: " + edges.get(i).node2.ID + " Weight: " + edges.get(i).weight);
            ///this must be handled properly in graphical phase
        }
    }
}
