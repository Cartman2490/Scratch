public class Activity implements ActionInterface{
    public ActionKind kind = ActionKind.ACTIVITY;
    private ActiveInterface active;
    private WorldModel world;
    public ImageStore imageStore;

    public Activity(ActiveInterface active, WorldModel world, ImageStore imageStore, int repeatCount) {
        this.kind = ActionKind.ACTIVITY;
        this.active = active;
        this.world = world;
        this.imageStore = imageStore;

    }

    private void executeActivityAction(EventScheduler scheduler) {
        //System.out.println(this.active.getKind());
        this.active.executeActivity(this.world,
                this.imageStore, scheduler);
    }

    public void executeAction(EventScheduler scheduler){
        this.executeActivityAction(scheduler);
    }

    public ActionKind getKind(){
        return this.kind;
    }

}
