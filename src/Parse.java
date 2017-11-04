import java.util.Scanner;

public class Parse {

    public static final int PROPERTY_KEY = 0;

    public static final String BGND_KEY = "background";
    public static final int BGND_NUM_PROPERTIES = 4;
    public static final int BGND_ID = 1;
    public static final int BGND_COL = 2;
    public static final int BGND_ROW = 3;

    public static final String MINER_KEY = "miner";

    public static final String OBSTACLE_KEY = "obstacle";

    public static final String ORE_KEY = "ore";

    public static final String SMITH_KEY = "blacksmith";

    public static final String VEIN_KEY = "vein";



    public static void load(Scanner in, WorldModel world, ImageStore imageStore)
    {
        int lineNumber = 0;
        while (in.hasNextLine())
        {
            try
            {
                if (!processLine(in.nextLine(), world, imageStore))
                {
                    System.err.println(String.format("invalid entry on line %d",
                            lineNumber));
                }
            }
            catch (NumberFormatException e)
            {
                System.err.println(String.format("invalid entry on line %d",
                        lineNumber));
            }
            catch (IllegalArgumentException e)
            {
                System.err.println(String.format("issue on line %d: %s",
                        lineNumber, e.getMessage()));
            }
            lineNumber++;
        }
    }
    public static boolean processLine(String line, WorldModel world,
                                      ImageStore imageStore)
    {
        String[] properties = line.split("\\s");
        if (properties.length > 0)
        {
            switch (properties[PROPERTY_KEY])
            {
                case BGND_KEY:
                    return parseBackground(properties, world, imageStore);
                case MINER_KEY:
                    return Miner_Not_Full.parseMiner(properties, world, imageStore);
                case OBSTACLE_KEY:
                    return Obstacle.parseObstacle(properties, world, imageStore);
                case ORE_KEY:
                    return Ore.parseOre(properties, world, imageStore);
                case SMITH_KEY:
                    return Blacksmith.parseSmith(properties, world, imageStore);
                case VEIN_KEY:
                    return Vein.parseVein(properties, world, imageStore);
            }
        }

        return false;
    }

    public static boolean parseBackground(String [] properties,
                                          WorldModel world, ImageStore imageStore)
    {
        if (properties.length == BGND_NUM_PROPERTIES)
        {
            Point pt = new Point(Integer.parseInt(properties[BGND_COL]),
                    Integer.parseInt(properties[BGND_ROW]));
            String id = properties[BGND_ID];
            world.setBackground(pt,
                    new Background(id, imageStore.getImageList(id)));
        }

        return properties.length == BGND_NUM_PROPERTIES;
    }

}
