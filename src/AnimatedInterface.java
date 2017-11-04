public interface AnimatedInterface {
    int getAnimationPeriod();
    void nextImage();
    Animation createAnimationAction(int repeatCount);
    EntityKind getKind();
}
