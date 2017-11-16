import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public abstract class MinerSuperClass extends MoveSuperClass implements ActiveInterface, AnimatedInterface {

    public static final String MINER_KEY = "miner";
    public static final int MINER_NUM_PROPERTIES = 7;
    public static final int MINER_ID = 1;
    public static final int MINER_COL = 2;
    public static final int MINER_ROW = 3;
    public static final int MINER_LIMIT = 4;
    public static final int MINER_ACTION_PERIOD = 5;
    public static final int MINER_ANIMATION_PERIOD = 6;

    public EntityKind kind;
    private int resourceLimit;


    public MinerSuperClass(EntityKind realKind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod) {

        super(id, position, images, actionPeriod, animationPeriod, resourceCount);
        this.resourceLimit = resourceLimit;
        this.kind = realKind;
    }

    public void executeActivity(WorldModel world,
                                ImageStore imageStore, EventScheduler scheduler)
    {
        String classValue = this.getClass().getName();
        if (classValue == "Miner_Not_Full") {
            Optional<WorldEntity> notFullTarget = Functions.findNearest(world, this.getPos(),
                    "Ore");


            ///Testing AStar
            if (notFullTarget.isPresent()){
                Node source = new Node(null, this.getPos());
                System.out.println("Source Point");
                System.out.println(source.getPoint());
                System.out.println("");

                Node goal = new Node(null, notFullTarget.get().getPos());

                System.out.println("Goal Point");
                System.out.println(goal.getPoint());


                AStarPathingStrategy aaa = new AStarPathingStrategy();
                aaa.testingAStar(source, goal, world);
            }
            ///




            if (!notFullTarget.isPresent() ||
                    !this.moveTo(world, notFullTarget.get(), scheduler) ||
                    !this.transform(world, scheduler, imageStore))
            {
                scheduler.scheduleEvent(this,
                        this.createActivityAction(world, imageStore),
                        this.getActionPeriod());
            }
        }

        if (classValue == "Miner_Full"){
            Optional<WorldEntity> fullTarget = Functions.findNearest(world, this.getPos(),
                    "Blacksmith");

            if (fullTarget.isPresent() &&
                    this.moveTo(world, fullTarget.get(), scheduler))
            {
                this.transform(world, scheduler, imageStore);
            }
            else
            {
                scheduler.scheduleEvent(this,
                        this.createActivityAction(world, imageStore),
                        this.getActionPeriod());
            }
        }
}

    private boolean transform(WorldModel world,
                               EventScheduler scheduler, ImageStore imageStore)
    {
        String classValue = this.getClass().getName();
        if (classValue == "Miner_Full") {
            WorldEntity miner = Miner_Not_Full.createMinerNotFull(this.getId(), this.getResourceLimit(),
                    this.getPos(), this.getActionPeriod(), this.getAnimationPeriod(),
                    this.getImages());


            world.removeEntity(this);
            scheduler.unscheduleAllEvents(this);

            world.addEntity(miner);
            ((Miner_Not_Full)miner).scheduleActions(scheduler, world, imageStore);
            return true;
        }
        if (classValue == "Miner_Not_Full") {
            if (this.getResourceCount() >= this.getResourceLimit()) {
                WorldEntity miner = Miner_Full.createMinerFull(this.getId(), this.getResourceLimit(),
                        this.getPos(), this.getActionPeriod(), this.getAnimationPeriod(),
                        this.getImages());
                world.removeEntity(this);
                scheduler.unscheduleAllEvents(this);

                world.addEntity(miner);
                ((Miner_Full)miner).scheduleActions(scheduler, world, imageStore);
                return true;
            }
        }

        return false;

    }

    public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore){
        scheduler.scheduleEvent(this,
                this.createActivityAction(world, imageStore),
                this.getActionPeriod());
        scheduler.scheduleEvent(this,
                this.createAnimationAction(0), this.getAnimationPeriod());
    }


    public EntityKind getKind(){
        return this.kind;
    }

    private int getResourceLimit() {return this.resourceLimit; }







}
