import processing.core.PImage;

import java.util.List;

public abstract class AnimatedSuperClass extends ActionSuperClass implements AnimatedInterface {

    private int animationPeriod;



    public AnimatedSuperClass(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
        super(id, position, images, actionPeriod);
        this.animationPeriod = animationPeriod;
    }

    public void nextImage()
    {

        int index = (this.getImageIndex() + 1) % this.getImages().size();
        this.setImageIndex(index);
    }

    public int getAnimationPeriod(){
        return this.animationPeriod;
    }

    public Animation createAnimationAction(int repeatCount)
    {
        return new Animation(this, null, null, repeatCount);
    }
}
