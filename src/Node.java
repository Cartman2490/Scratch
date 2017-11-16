public class Node {
    private Node parent;
    private Point point;
    private int fScore;
    private int gScore;

    public Node(Node Parent, Point point){
        this.parent = null;
        this.point = point;
        //this.fScore = 9999;
        //this.gScore = 9999;
    }

    public Point getPoint(){
        return this.point;
    }

    public int getfScore(){
        return this.fScore;
    }

    public int getgScore(){
        return this.gScore;
    }

    public void setfScore(int newfScore){
        this.fScore = newfScore;
    }

    public void setgScore(int newgScore){
        this.gScore = newgScore;
    }

    public Node getParent(){
        return this.parent;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }
}
