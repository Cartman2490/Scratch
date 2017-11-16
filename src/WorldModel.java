import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

final class WorldModel
{
   public int numRows;
   public int numCols;
   public Background background[][];
   public WorldEntity occupancy[][];
   public Set<WorldEntity> entities;

   public WorldModel(int numRows, int numCols, Background defaultBackground)
   {
      this.numRows = numRows;
      this.numCols = numCols;
      this.background = new Background[numRows][numCols];
      this.occupancy = new WorldEntity[numRows][numCols];
      this.entities = new HashSet<>();

      for (int row = 0; row < numRows; row++)
      {
         Arrays.fill(this.background[row], defaultBackground);
      }
   }
   private void setOccupancyCell(Point pos,
                                       WorldEntity entity)
   {
      this.occupancy[pos.y][pos.x] = entity;
   }
   private WorldEntity getOccupancyCell(Point pos)
   {
      return this.occupancy[pos.y][pos.x];
   }

   public boolean withinBounds(Point pos)
   {
      return pos.y >= 0 && pos.y < this.numRows &&
              pos.x >= 0 && pos.x < this.numCols;
   }

   public Optional<WorldEntity> getOccupant(Point pos)
   {
      if (this.isOccupied( pos))
      {
         return Optional.of(this.getOccupancyCell(pos));
      }
      else
      {
         return Optional.empty();
      }
   }
   public boolean isOccupied(Point pos)
   {
      return this.withinBounds(pos) &&
              this.getOccupancyCell(pos) != null;
   }
   public void tryAddEntity(WorldEntity entity)
   {
      if (this.isOccupied(entity.getPos()))
      {
         // arguably the wrong type of exception, but we are not
         // defining our own exceptions yet
         throw new IllegalArgumentException("position occupied");
      }

      this.addEntity(entity);
   }
   public void moveEntity(WorldEntity entity, Point pos)
   {
      //System.out.println("in Move Entity");
      Point oldPos = entity.getPos();
      if (this.withinBounds(pos) && !pos.equals(oldPos))
      {
         this.setOccupancyCell(oldPos, null);
         this.removeEntityAt(pos);
         this.setOccupancyCell(pos, entity);
         entity.setPos(pos);
      }
   }

   public void removeEntity(WorldEntity entity)
   {
      removeEntityAt(entity.getPos());
   }

   private void removeEntityAt(Point pos)
   {
      if (this.withinBounds(pos)
              && this.getOccupancyCell(pos) != null)
      {
         WorldEntity entity = this.getOccupancyCell(pos);

         /* this moves the entity just outside of the grid for
            debugging purposes */
         entity.setPos(new Point(-1, -1));
         this.entities.remove(entity);
         this.setOccupancyCell(pos, null);

         //String classValue = this.getClass().getName();
         //if (classValue == "Miner_Full"){
            //System.out.println("hi");
         //}
      }
   }
   public void setBackground(Point pos,
                                    Background background)
   {
      if (this.withinBounds(pos))
      {
         this.setBackgroundCell(pos, background);
      }
   }


   public Background getBackgroundCell(Point pos)
   {
      return this.background[pos.y][pos.x];
   }

   private void setBackgroundCell(Point pos,
                                        Background background)
   {
      this.background[pos.y][pos.x] = background;
   }
   public void addEntity(WorldEntity entity)
   {
      if (this.withinBounds(entity.getPos()));
      {
         this.setOccupancyCell(entity.getPos(), entity);
         this.entities.add(entity);

      }
   }










}

