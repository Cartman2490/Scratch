import org.omg.PortableInterceptor.ACTIVE;
import processing.core.PImage;



import java.util.List;
import java.util.Optional;

public class Miner_Full extends MinerSuperClass implements MobileInterface, ActiveInterface, AnimatedInterface {


    private Miner_Full(String id, Point position, List<PImage> images, int resourceLimit, int resourceCount, int actionPeriod, int animationPeriod){
        super(EntityKind.MINER_FULL, id, position, images, resourceLimit, resourceCount, actionPeriod, animationPeriod);
    }

    public static Miner_Full createMinerFull(String id, int resourceLimit,
                                         Point position, int actionPeriod, int animationPeriod,
                                         List<PImage> images)
    {
        return new Miner_Full(id, position, images,
                resourceLimit, resourceLimit, actionPeriod, animationPeriod);
    }







}
