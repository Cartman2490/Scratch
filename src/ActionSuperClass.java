import processing.core.PImage;

import java.util.List;

public abstract class ActionSuperClass extends WorldEntity implements ActiveInterface {

    private int actionPeriod;



    public ActionSuperClass(String id, Point position, List<PImage> images, int actionPeriod) {
        super(id, position, images);
        this.actionPeriod = actionPeriod;
    }

    public Activity createActivityAction(WorldModel world,
                                         ImageStore imageStore)
    {
        return new Activity(this, world, imageStore, 0);
    }

    public int getActionPeriod() { return this.actionPeriod; }
}