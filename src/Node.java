public class Node {
    private Node parent;
    private Point point;
    private double fScore;
    private double gScore;

    public Node(Node Parent, Point point){
        this.parent = null;
        this.point = point;
        //this.fScore = 9999;
        //this.gScore = 9999;
    }

    public Point getPoint(){
        return this.point;
    }

    public double getfScore(){
        return this.fScore;
    }

    public double getgScore(){
        return this.gScore;
    }

    public void setfScore(double newfScore){
        this.fScore = newfScore;
    }

    public void setgScore(double newgScore){
        this.gScore = newgScore;
    }

    public Node getParent(){
        return this.parent;
    }

    public void setParent(Node parent){
        this.parent = parent;
    }
}
