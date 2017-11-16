import processing.core.PImage;

import java.util.List;

public class WorldEntity implements AcceptImage{
    private String id;
    private Point position;
    private List<PImage> images;
    private int imageIndex;


    public WorldEntity(String id, Point position, List<PImage> images){
        this.id = id;
        this.position = position;
        this.images = images;
        this.imageIndex = 0;
    }



    public String getId() { return this.id; }

    public PImage getCurrentImage() { return this.images.get(this.imageIndex); }

    public void setPos(Point newPosition){
        this.position = newPosition;
    }

    public Point getPos(){
        return this.position;
    }

    public int getImageIndex() { return this.imageIndex; }

    public void setImageIndex(int newIndex) {this.imageIndex = newIndex; }

    public List<PImage> getImages() { return this.images; }

    public void accept(ImageVisitor visitor) {
        visitor.visit(this);
    }


}
