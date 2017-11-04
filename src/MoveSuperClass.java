import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MoveSuperClass extends AnimatedSuperClass {

    public EntityKind kind;
    private int resourceCount;


    public MoveSuperClass(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod, int resourceCount) {
        super(id, position, images, actionPeriod, animationPeriod);
        this.resourceCount = resourceCount;


    }

    public boolean moveTo(WorldModel world,
                          WorldEntity target, EventScheduler scheduler) {
        if (Functions.adjacent(this.getPos(), target.getPos())) {
            String classValue = this.getClass().getName();
            if (classValue == "Miner_Not_Full") {
                this.resourceCount += 1;
                world.removeEntity(target);
                scheduler.unscheduleAllEvents(target);
            }
            if (classValue == "Ore_Blob") {
                world.removeEntity(target);
                scheduler.unscheduleAllEvents(target);
            }

            return true;
        }
        else {
            Point nextPos = this.nextPositionMiner(world, target.getPos());

            if (!this.getPos().equals(nextPos)) {
                Optional<WorldEntity> occupant = world.getOccupant(nextPos);
                if (occupant.isPresent()) {
                    scheduler.unscheduleAllEvents(occupant.get());
                }
                //System.out.println("Third If True");
                world.moveEntity(this, nextPos);
            }
            return false;
        }
    }


    private Point nextPositionMiner(WorldModel world,
                                   Point destPos) {
        int horiz = Integer.signum(destPos.x - this.getPos().x);
        Point newPos = new Point(this.getPos().x + horiz,
                this.getPos().y);

        if (horiz == 0 || world.isOccupied(newPos)) {
            int vert = Integer.signum(destPos.y - this.getPos().y);
            newPos = new Point(this.getPos().x,
                    this.getPos().y + vert);

            if (vert == 0 || world.isOccupied(newPos)) {
                newPos = this.getPos();
            }
        }

        return newPos;
    }
    public int getResourceCount() { return this.resourceCount; }

    public void setResourceCount(int integer) {this.resourceCount = integer; }
}