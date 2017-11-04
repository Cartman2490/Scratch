import processing.core.PImage;

import java.util.List;
import java.util.Random;

public class Blacksmith extends WorldEntity {


    //public EntityKind kind = EntityKind.BLACKSMITH;

    private static final String SMITH_KEY = "blacksmith";
    private static final int SMITH_NUM_PROPERTIES = 4;
    private static final int SMITH_ID = 1;
    private static final int SMITH_COL = 2;
    private static final int SMITH_ROW = 3;



    private Blacksmith(String id, Point position, List<PImage> images){

        super(id, position, images);
        //this.kind = EntityKind.BLACKSMITH;
    }

    private static Blacksmith createBlacksmith(String id, Point position,
                                   List<PImage> images)
    {
        return new Blacksmith(id, position, images);
    }

    public static boolean parseSmith(String [] properties, WorldModel world,
                                     ImageStore imageStore)
    {
        if (properties.length == SMITH_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[SMITH_COL]),
                    Integer.parseInt(properties[SMITH_ROW]));
            WorldEntity entity = createBlacksmith(properties[SMITH_ID],
                    pt, imageStore.getImageList(SMITH_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == SMITH_NUM_PROPERTIES;
    }
}


