import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import processing.core.PImage;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PApplet;

public class GetCurrentImage implements ImageVisitor {
    private Map<String, List<PImage>> images;
    private List<PImage> defaultImages;
    private PImage currentImage;

    public GetCurrentImage(){
    }

    public void visit(WorldEntity worldEntity){
        currentImage = worldEntity.getCurrentImage();
    }

    public void visit(Background background){
        //System.out.println("visitor");
        currentImage = background.images
                .get(background.imageIndex);
    }

    public PImage getTheImage(){
        return currentImage;
    }

}
