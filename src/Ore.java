import processing.core.PImage;
import java.util.List;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;
import processing.core.PImage;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Ore extends ActionSuperClass implements ActiveInterface{


    public EntityKind kind = EntityKind.ORE;

    public static final String BLOB_KEY = "blob";
    public static final String BLOB_ID_SUFFIX = " -- blob";
    public static final int BLOB_PERIOD_SCALE = 4;
    public static final int BLOB_ANIMATION_MIN = 50;
    public static final int BLOB_ANIMATION_MAX = 150;

    public static final String ORE_KEY = "ore";
    public static final int ORE_NUM_PROPERTIES = 5;
    public static final int ORE_ID = 1;
    public static final int ORE_COL = 2;
    public static final int ORE_ROW = 3;
    public static final int ORE_ACTION_PERIOD = 4;


    public Ore(String id, Point position, List<PImage> images, int actionPeriod){

        super(id, position, images, actionPeriod);
        this.kind = EntityKind.ORE;


    }

    public static Ore createOre(String id, Point position, int actionPeriod,
                                   List<PImage> images)
    {
        return new Ore(id, position, images,
                actionPeriod);
    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
    }

    public void executeActivity(WorldModel world,
                                   ImageStore imageStore, EventScheduler scheduler)
    {
        Point pos = this.getPos();  // store current position before removing

        world.removeEntity((WorldEntity)this);
        scheduler.unscheduleAllEvents((WorldEntity)this);

        WorldEntity blob = Ore_Blob.createOreBlob(this.getId() + BLOB_ID_SUFFIX,
                pos, this.getActionPeriod() / BLOB_PERIOD_SCALE,
                BLOB_ANIMATION_MIN +
                        Functions.rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
                imageStore.getImageList(BLOB_KEY));

        world.addEntity(blob);
        //world.removeEntity(blob);
        ((Ore_Blob)blob).scheduleActions(scheduler, world, imageStore);
    }

    public EntityKind getKind(){
        return this.kind;
    }

    public static boolean parseOre(String [] properties, WorldModel world,
                                   ImageStore imageStore)
    {
        if (properties.length == ORE_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[ORE_COL]),
                    Integer.parseInt(properties[ORE_ROW]));
            WorldEntity entity = createOre(properties[ORE_ID],
                    pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
                    imageStore.getImageList(ORE_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == ORE_NUM_PROPERTIES;
    }
}
