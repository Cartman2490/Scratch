public interface ActiveInterface {
    void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler);
    void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
    Activity createActivityAction(WorldModel world, ImageStore imageStore);
    EntityKind getKind();
}
