import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node>
{
    static ArrayList<Node> nodes = new ArrayList<>();
    int ID;
    int score = Integer.MAX_VALUE;
    ArrayList<Edge> edges = new ArrayList<>();
    Node(int ID)
    {
        this.ID=ID;
    }
    Node(){}
    public static Node getNodeByID(int ID)
    {
        for(int i=0; i<nodes.size(); i++)
            if(nodes.get(i).ID==ID)
                return nodes.get(i);
        return null;
    }
    public static void resetScores()
    {
        for(int i=0; i<nodes.size(); i++)
            nodes.get(i).score=Integer.MAX_VALUE;
    }
    public int compareTo(Node other)
    {
        if(other.score>this.score)
            return -1;
        else if(other.score==this.score)
            return 0;
        else
            return 1;
    }
}
