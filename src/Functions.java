import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Scanner;
import processing.core.PImage;
import processing.core.PApplet;

final class Functions
{


   public static final Random rand = new Random();


   public static boolean adjacent(Point p1, Point p2)
   {
      return (p1.x == p2.x && Math.abs(p1.y - p2.y) == 1) ||
         (p1.y == p2.y && Math.abs(p1.x - p2.x) == 1);
   }



   private static Optional<WorldEntity> nearestEntity(List<WorldEntity> entities,
      Point pos)
   {
      if (entities.isEmpty())
      {
         return Optional.empty();
      }
      else
      {
         WorldEntity nearest = entities.get(0);
         int nearestDistance = distanceSquared(nearest.getPos(), pos);

         for (WorldEntity other : entities)
         {
            int otherDistance = distanceSquared(other.getPos(), pos);

            if (otherDistance < nearestDistance)
            {
               nearest = other;
               nearestDistance = otherDistance;
            }
         }

         return Optional.of(nearest);
      }
   }

   private static int distanceSquared(Point p1, Point p2)
   {
      int deltaX = p1.x - p2.x;
      int deltaY = p1.y - p2.y;

      return deltaX * deltaX + deltaY * deltaY;
   }

   public static Optional<WorldEntity> findNearest(WorldModel world, Point pos,
      String className)
   {
      //System.out.println("inFindNearest");
      List<WorldEntity> ofType = new LinkedList<>();
      for (WorldEntity entity : world.entities)
      {
         //System.out.println("current kind" + entity.getKind());
         //System.out.println("Looking for" + kind);
         String classvalue = entity.getClass().getName();
         if (classvalue == className)
         {
            ofType.add(entity);
         }
      }
      //System.out.println("finished through list of entities");

      return nearestEntity(ofType, pos);
   }

   public static int clamp(int value, int low, int high)
   {
      return Math.min(high, Math.max(value, low));
   }



}
