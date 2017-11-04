public interface ActionInterface {
    void executeAction(EventScheduler scheduler);
    ActionKind getKind();
}
