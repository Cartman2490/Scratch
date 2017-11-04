import processing.core.PImage;
import java.util.List;
import java.util.Optional;
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

public class Ore_Blob extends MoveSuperClass implements MobileInterface, ActiveInterface, AnimatedInterface {


    public EntityKind kind = EntityKind.ORE_BLOB;
    /*
    public String id;
    private Point position;
    public List<PImage> images;
    public int imageIndex;
*/




    private static final String QUAKE_KEY = "quake";





    private Ore_Blob(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod){
        super(id, position, images, actionPeriod, animationPeriod, 0);
        this.kind = EntityKind.ORE_BLOB;
    }

    public static WorldEntity createOreBlob(String id, Point position,
                                       int actionPeriod, int animationPeriod, List<PImage> images)
    {
        return new Ore_Blob(id, position, images, actionPeriod, animationPeriod);
    }

    private Point nextPositionOreBlob(WorldModel world,
                                     Point destPos)
    {
        int horiz = Integer.signum(destPos.x - this.getPos().x);
        Point newPos = new Point(this.getPos().x + horiz,
                this.getPos().y);

        Optional<WorldEntity> occupant = world.getOccupant(newPos);

        if (horiz == 0 ||
                (occupant.isPresent() && !(occupant.get().getClass().getName() == "Ore")))
        {
            int vert = Integer.signum(destPos.y - this.getPos().y);
            newPos = new Point(this.getPos().x, this.getPos().y + vert);
            occupant = world.getOccupant(newPos);

            if (vert == 0 ||
                    (occupant.isPresent() && !(occupant.get().getClass().getName() == "Ore")))
            {
                newPos = this.getPos();
            }
        }

        return newPos;
    }


    public EntityKind getKind(){
        return this.kind;
    }




    public void executeActivity(WorldModel world,
                                       ImageStore imageStore, EventScheduler scheduler)
    {
        Optional<WorldEntity> blobTarget = Functions.findNearest(world,
                this.getPos(), "Vein");
        long nextPeriod = this.getActionPeriod();

        if (blobTarget.isPresent())
        {
            Point tgtPos = blobTarget.get().getPos();

            if (this.moveToOreBlob(world, blobTarget.get(), scheduler))
            {
                WorldEntity quake = Quake.createQuake(tgtPos,
                        imageStore.getImageList(QUAKE_KEY));

                world.addEntity(quake);
                nextPeriod += this.getAnimationPeriod();
                ((Quake)quake).scheduleActions(scheduler, world, imageStore);
                //scheduler.unscheduleAllEvents(this);
                //world.removeEntity(this);
            }
        }

        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                nextPeriod);
    }
    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                this.createAnimationAction(0), this.getAnimationPeriod());
    }


    private boolean moveToOreBlob(WorldModel world,
                                 WorldEntity target, EventScheduler scheduler)
    {
        if (Functions.adjacent(this.getPos(), target.getPos()))
        {
            world.removeEntity(target);
            scheduler.unscheduleAllEvents(target);
            return true;
        }
        else
        {
            Point nextPos = this.nextPositionOreBlob(world, target.getPos());

            if (!this.getPos().equals(nextPos))
            {
                Optional<WorldEntity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent())
                {
                    scheduler.unscheduleAllEvents(occupant.get());
                }

                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


}
