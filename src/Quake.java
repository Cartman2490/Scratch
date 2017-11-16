import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Quake extends AnimatedSuperClass implements ActiveInterface, AnimatedInterface{


    public EntityKind kind = EntityKind.QUAKE;


    private static final String QUAKE_ID = "quake";
    private static final int QUAKE_ACTION_PERIOD = 1100;
    private static final int QUAKE_ANIMATION_PERIOD = 100;
    private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
    private static final int ORE_REACH = 1;


    private Quake(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod);
        this.kind = EntityKind.QUAKE;
  //MIGHT BE default as 0 instead of animationPeriod
    }


    public static WorldEntity createQuake(Point position, List<PImage> images)
    {
        return new Quake(QUAKE_ID, position, images, QUAKE_ACTION_PERIOD, QUAKE_ANIMATION_PERIOD);
    }



    public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler){


            scheduler.unscheduleAllEvents(this);
            world.removeEntity(this);  ///trying to remove a Vein?
        }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this, this.createActivityAction(world, imageStore), this.getActionPeriod());



        //scheduleEvent(scheduler, entity, createActivityAction(entity, world, imageStore), entity.actionPeriod);
        scheduler.scheduleEvent(this, this.createAnimationAction(QUAKE_ANIMATION_REPEAT_COUNT), this.getAnimationPeriod());
    }


    public static Optional<Point> findOpenAround(WorldModel world, Point pos) {
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

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}
