import processing.core.PApplet;
import processing.core.PImage;

import java.util.Optional;

final class WorldView
{
   private PApplet screen;
   public WorldModel world;
   private int tileWidth;
   private int tileHeight;
   private Viewport viewport;

   public WorldView(int numRows, int numCols, PApplet screen, WorldModel world,
      int tileWidth, int tileHeight)
   {
      this.screen = screen;
      this.world = world;
      this.tileWidth = tileWidth;
      this.tileHeight = tileHeight;
      this.viewport = new Viewport(numRows, numCols);
   }
   public void shiftView(int colDelta, int rowDelta)
   {
      int newCol = Functions.clamp(this.viewport.col + colDelta, 0,
              this.world.numCols - this.viewport.numCols);
      int newRow = Functions.clamp(this.viewport.row + rowDelta, 0,
              this.world.numRows - this.viewport.numRows);

      Viewport.shift(this.viewport, newCol, newRow);
   }

   private void drawBackground()
   {
      for (int row = 0; row < this.viewport.numRows; row++)
      {
         for (int col = 0; col < this.viewport.numCols; col++)
         {
            Point worldPoint = Viewport.viewportToWorld(this.viewport, col, row);
            Optional<PImage> image = ImageStore.getBackgroundImage(this.world,
                    worldPoint);
            if (image.isPresent())
            {
               this.screen.image(image.get(), col * this.tileWidth,
                       row * this.tileHeight);
            }
         }
      }
   }

   private void drawEntities()
   {
      for (WorldEntity entity : this.world.entities)
      {
         Point pos = entity.getPos();

         if (Viewport.contains(this.viewport, pos))
         {
            Point viewPoint = Viewport.worldToViewport(this.viewport, pos.x, pos.y);
            this.screen.image(ImageStore.getCurrentImage(entity),
                    viewPoint.x * this.tileWidth, viewPoint.y * this.tileHeight);
         }
      }
   }

   public void drawViewport()
   {
      this.drawBackground();
      this.drawEntities();
   }

}
