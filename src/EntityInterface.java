import processing.core.PImage;

public interface EntityInterface {
    PImage getCurrentImage();
    void setPos(Point newPosition);
    Point getPos();
    EntityKind getKind();
    <R> R accept(EntityVisitor<R> visitor);
}
