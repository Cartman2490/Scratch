interface EntityVisitor<R>
{
    R visit(Blacksmith blacksmith);
    R visit(Miner_Full miner_full);
    R visit(Miner_Not_Full miner_not_full);
    R visit(Obstacle obstacle);
    R visit(Ore ore);
    R visit(Ore_Blob ore_blob);
    R visit(Quake quake);
    R visit(Vein vein);
}
