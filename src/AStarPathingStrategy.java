import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class AStarPathingStrategy implements PathingStrategy{


    private List<Point> path = new LinkedList<>();

/*    public List<Point> computePath(Point start, Point goal, Predicate<Point> canPassThrough,
                            BiPredicate<Point, Point> withinReach,
                            Function<Point, Stream<Point>> potentialNeighbors) {
        // The set of nodes already evaluated
*/

    public static int guessFScore(Node source, Node goal){
        return source.getgScore() + Math.abs( ( goal.getPoint().x - source.getPoint().x ) + ( goal.getPoint().y - source.getPoint().y ) );
    }



    //Called computePath, actually returns a filtered list of real neighbors
    public List<Point> computePath(Point start, Point end,
                                   //Predicate<Point> canPassThrough,
                                   //BiPredicate<Point, Point> withinReach,
                                   Function<Point, Stream<Point>> potentialNeighbors,
                                   WorldModel world)
    {
      /* Does not check withinReach.  Since only a single step is taken
       * on each call, the caller will need to check if the destination
       * has been reached.
       */
        return potentialNeighbors.apply(start)

                .filter(pt -> !world.isOccupied(pt))    //Filters out occupied spaces from path

                //.filter(canPassThrough)
                .filter(pt ->
                        !pt.equals(start)
                                && !pt.equals(end)  )
                                //&& Math.abs(end.x - pt.x) <= Math.abs(end.x - start.x)
                                //&& Math.abs(end.y - pt.y) <= Math.abs(end.y - start.y))
                //.limit(1)
                .collect(Collectors.toList());
    }



    public static int distanceBetween(Node node1, Node node2){
        return Math.abs( ( node2.getPoint().x - node1.getPoint().x ) + ( node2.getPoint().y - node1.getPoint().y )  );


    }
    public ArrayList<Point> aStar(Node source, Node goal, WorldModel world) {
        System.out.println("First Line aStar");
        List<Node> closedSet = new LinkedList<Node>();


        // The set of currently discovered nodes that are not evaluated yet.
        // Initially, only the start node is known.

        //List<Node> openSet = new LinkedList<Node>();
        //openSet.add(source);

        Comparator<Node> comparator1 = new NodeComparator();
        PriorityQueue<Node> openSet = new PriorityQueue<>(1000, comparator1);

        source.setgScore(0);
        source.setfScore(guessFScore(source, goal));

        openSet.add(source);

        // For each node, which node it can most efficiently be reached from.
        // If a node can be reached from many nodes, cameFrom will eventually contain the
        // most efficient previous step.
        //cameFrom:=an empty map

        // For each node, the cost of getting from the start node to that node.
        //gScore:=map with default
        //value of Infinity

        // The cost of going from start to start is zero.
        //gScore[start] :=0


        // For each node, the total cost of getting from the start node to the goal
        // by passing by that node. That value is partly known, partly heuristic.
        //fScore:=map with default
        //value of Infinity

        // For the first node, that value is completely heuristic.
        //fScore[start] :=heuristic_cost_estimate(start, goal)

        System.out.println("EnteringWhileLoop");
        System.out.println(openSet);
        for (Node node : openSet){
            System.out.println(node.getPoint());
        }
        while (openSet.isEmpty() != true) {
            Node current = openSet.peek();
            System.out.println(current.getPoint());
            System.out.println(goal.getPoint());
            if (current.getPoint() == goal.getPoint()) {
                return reconstructPath(goal);
            }

            openSet.remove(current);
            closedSet.add(current);

            List<Point> neighbors = computePath(current.getPoint(), goal.getPoint(), CARDINAL_NEIGHBORS, world);

            for (Point neighbor : neighbors) {
                Node pointHolder = new Node(null, neighbor);
                //pointHolder.setgScore();

                if (closedSet.contains(pointHolder)) {
                    continue;        // Ignore the neighbor which is already evaluated.
                }


                if (openSet.contains(pointHolder) != true){
                    // Discover a new node
                    pointHolder.setgScore(current.getgScore()+1);
                    pointHolder.setfScore( current.getgScore() + guessFScore(pointHolder, goal) );
                    openSet.add(pointHolder);

                    pointHolder.setParent(current);
                    System.out.println("We built a node");
                }
                //System.out.println("We iterated thru all the neighbors, building 4 paths each now one larger");

                /*
                else{
                    int temp_g_score = current.getgScore() + distanceBetween(current, pointHolder);
                    if (temp_g_score < pointHolder.getgScore()){
                        //UPDATE PATH ???
                        pointHolder.setgScore(temp_g_score);
                        //pointHolder.setfScore(temp_g_score + guessFScore(temp, goal));       TEMP IS NOT A NODE
                        pointHolder.setParent(current);
                    }
                }
                */

                // The distance from start to a neighbor
                /*tentative_gScore:=gScore[current] + dist_between(current, neighbor)
                if tentative_gScore >= gScore[neighbor]
                continue        // This is not a better path.

                        // This path is the best until now. Record it!
                        cameFrom[neighbor] :=current
                gScore[neighbor] :=tentative_gScore
                fScore[neighbor] :=gScore[neighbor] + heuristic_cost_estimate(neighbor, goal)
                */
            }
            System.out.println("We iterated thru all the neighbors, building 4 paths each now one larger");
        }
        return null;
    }


    /*public LinkedList<Node> reconstructPath(Node goal){
        total_path:= [current]
        while current in cameFrom.Keys:
            current:=cameFrom[current]
            total_path.append(current)
        return total_path
    }
    */

    public static ArrayList<Point> reconstructPath(Node goal){
        ArrayList<Point> finalPath = new ArrayList<Point>();
        while (goal != null){
            finalPath.add(goal.getPoint());
            goal = goal.getParent();
        }
        Collections.reverse(finalPath);
        return finalPath;


    }


    public void testingAStar(Node source, Node goal, WorldModel world){
        System.out.println("Calling aStar");
        ArrayList<Point> finalPath = aStar(source, goal, world);
        System.out.println("Finished thru aStar");
        for (Point point : finalPath){
            System.out.println(point);
        }
    }

}
