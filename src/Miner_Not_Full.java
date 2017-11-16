import org.omg.PortableInterceptor.ACTIVE;
import processing.core.PImage;

import java.util.List;
import java.util.Optional;

public class Miner_Not_Full extends MinerSuperClass implements  MobileInterface, ActiveInterface, AnimatedInterface {


    private Miner_Not_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
        super(EntityKind.MINER_NOT_FULL, id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);

    }

    public static Miner_Not_Full createMinerNotFull(String id, int resourceLimit,
                                             Point position, int actionPeriod, int animationPeriod,
                                             List<PImage> images)
    {
        return new Miner_Not_Full(id, position, images,
                resourceLimit, 0, actionPeriod, animationPeriod);
    }



    public static boolean parseMiner(String [] properties, WorldModel world,
                                     ImageStore imageStore)
    {
        if (properties.length == MINER_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[MINER_COL]),
                    Integer.parseInt(properties[MINER_ROW]));
            WorldEntity entity = createMinerNotFull(properties[MINER_ID],
                    Integer.parseInt(properties[MINER_LIMIT]),
                    pt,
                    Integer.parseInt(properties[MINER_ACTION_PERIOD]),
                    Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
                    imageStore.getImageList(MINER_KEY));
            world.tryAddEntity(entity);
        }

        return properties.length == MINER_NUM_PROPERTIES;
    }

    public <R> R accept(EntityVisitor<R> visitor)
    {
        return visitor.visit(this);
    }
}

