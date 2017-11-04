import processing.core.PImage;

import java.util.List;

final class EntityImageStore
{
   private String id;
   private List<PImage> images;
   private int imageIndex;

   public EntityImageStore(String id, List<PImage> images)
   {
      this.id = id;
      this.images = images;
   }
}
