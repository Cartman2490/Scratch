import processing.core.PImage;
import java.util.List;
import java.util.Random;

public class Obstacle extends WorldEntity{



    private static final String OBSTACLE_KEY = "obstacle";
    private static final int OBSTACLE_NUM_PROPERTIES = 4;
    private static final int OBSTACLE_ID = 1;
    private static final int OBSTACLE_COL = 2;
    private static final int OBSTACLE_ROW = 3;




    private Obstacle(String id, Point position, List<PImage> images){

        super(id, position, images);
    }

    private static Obstacle createObstacle(String id, Point position,
                                        List<PImage> images)
    {
        return new Obstacle(id, position, images);
    }



    public static boolean parseObstacle(String [] properties, WorldModel world,
                                        ImageStore imageStore)
    {
        if (properties.length == OBSTACLE_NUM_PROPERTIES)
        {
            Point pt = new Point(
                    Integer.parseInt(properties[OBSTACLE_COL]),
                    Integer.parseInt(properties[OBSTACLE_ROW]));
            WorldEntity entity = Obstacle.createObstacle(properties[OBSTACLE_ID],
                    pt, imageStore.getImageList(OBSTACLE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == OBSTACLE_NUM_PROPERTIES;
    }

}


