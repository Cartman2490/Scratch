import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Vein extends ActionSuperClass implements ActiveInterface{


    public EntityKind kind = EntityKind.VEIN;


    private static final String VEIN_KEY = "vein";
    private static final int VEIN_NUM_PROPERTIES = 5;
    private static final int VEIN_ID = 1;
    private static final int VEIN_COL = 2;
    private static final int VEIN_ROW = 3;
    private static final int VEIN_ACTION_PERIOD = 4;

    private static final String ORE_ID_PREFIX = "ore -- ";
    private static final int ORE_CORRUPT_MIN = 20000;
    private static final int ORE_CORRUPT_MAX = 30000;
    private static final int ORE_REACH = 1;
    private static final String ORE_KEY = "ore";

    private Vein(String id, Point position, List<PImage> images, int actionPeriod){
        super(id, position, images, actionPeriod);
        this.kind = EntityKind.VEIN;


    }

    private static Vein createVein(String id, Point position, int actionPeriod,
                                 List<PImage> images)
    {
        return new Vein(id, position, images,
                actionPeriod);
    }


    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){
        {
            Optional<Point> openPt = findOpenAround(world, this.getPos());

            if (openPt.isPresent())
            {
                WorldEntity ore = Ore.createOre(ORE_ID_PREFIX + this.getId(),
                        openPt.get(), ORE_CORRUPT_MIN +
                                Functions.rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
                        imageStore.getImageList(ORE_KEY));
                world.addEntity(ore);
                Point newposition = new Point(1, 2);


                ((Ore)ore).scheduleActions(scheduler, world, imageStore);
            }

            scheduler.scheduleEvent(this,
                    this.createActivityAction(world, imageStore),
                    this.getActionPeriod());
        }
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
    }
    public Activity createActivityAction(WorldModel world,
                                       ImageStore imageStore)
    {
        return new Activity(this, world, imageStore, 0);
    }

    private static Optional<Point> findOpenAround(WorldModel world, Point pos) {
        for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
            for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
                Point newPt = new Point(pos.x + dx, pos.y + dy);
                if (world.withinBounds(newPt) &&
                        !world.isOccupied(newPt)) {
                    return Optional.of(newPt);
                }
            }
        }
        return Optional.empty();
    }


    public EntityKind getKind(){
        return this.kind;
    }

    public static boolean parseVein(String [] properties, WorldModel world,
                                    ImageStore imageStore)
    {
        if (properties.length == VEIN_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[VEIN_COL]),
                    Integer.parseInt(properties[VEIN_ROW]));
            WorldEntity entity = createVein(properties[VEIN_ID],
                    pt,
                    Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
                    imageStore.getImageList(VEIN_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == VEIN_NUM_PROPERTIES;
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}
