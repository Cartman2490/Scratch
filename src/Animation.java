public class Animation implements ActionInterface{
    private ActionKind kind = ActionKind.ANIMATION;
    private AnimatedInterface animated;
    private WorldEntity entity;
    private WorldModel world;
    private ImageStore imageStore;
    private int repeatCount;

    public Animation(AnimatedInterface animated, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.kind = ActionKind.ANIMATION;
        this.animated = animated;
        this.world = world;
        this.imageStore = imageStore;
        this.repeatCount = repeatCount;
    }

    private Animation createAnimationAction(int repeatCount)
    {
        return new Animation(animated,null, null, repeatCount);
    }

    private void executeAnimationAction(EventScheduler scheduler)
    {

        this.animated.nextImage();

        if (this.repeatCount != 1)
        {
            scheduler.scheduleEvent(this.entity,
                    this.createAnimationAction(Math.max(this.repeatCount - 1, 0)),
                    this.getAnimationPeriod());
        }
    }

    private int getAnimationPeriod()
    {
        switch (this.animated.getKind())
        {
            case MINER_FULL:
            case MINER_NOT_FULL:
            case ORE_BLOB:
            case QUAKE:
                return animated.getAnimationPeriod();
            default:
                throw new UnsupportedOperationException(
                        String.format("getAnimationPeriod not supported for %s",
                                this.kind));
        }
    }

    public void executeAction(EventScheduler scheduler){
        this.executeAnimationAction(scheduler);
    }

    public ActionKind getKind(){
        return this.kind;
    }
}
