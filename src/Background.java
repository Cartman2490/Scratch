import java.util.List;
import processing.core.PImage;

final class Background implements AcceptImage
{
   public String id;
   public List<PImage> images;
   public int imageIndex;

   public Background(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }

   public void accept(ImageVisitor visitor) {
      visitor.visit(this);
   }
}
