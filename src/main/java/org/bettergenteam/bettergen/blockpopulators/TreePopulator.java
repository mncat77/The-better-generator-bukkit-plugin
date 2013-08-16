/*package org.bettergenteam.bettergen.blockpopulators;
 *
 * import java.util.Random;
 * import org.bukkit.Location;
 * import org.bukkit.Material;
 * import org.bukkit.TreeType;
 * import org.bukkit.World;
 * import org.bukkit.block.Block;
 * import org.bukkit.block.BlockFace;
 *
 * public class TreePopulator extends ConsistentPopulator {
 *
 * public TreePopulator() {
 * super(0, 20, 10);
 * }
 *
 * @Override
 * public void populate(World world, Random random, int realX, int realZ) {
 * Block start = new Location(world, realX, world.getHighestBlockYAt(realX, realZ), realZ).getBlock();
 * if(start.getRelative(BlockFace.DOWN).getType() != Material.GRASS) {
 * return;
 * }
 * int chance = 0;
 * int treetype = 0;
 * switch(start.getBiome()) {
 * case FOREST:
 * case FOREST_HILLS:
 * chance = 60;
 * treetype = random.nextBoolean()?0:2;
 * break;
 * case ICE_PLAINS:
 * case ICE_MOUNTAINS:
 * case TAIGA:
 * case TAIGA_HILLS:
 * chance = 15;
 * treetype = 1;
 * break;
 * case PLAINS:
 * chance = 25;
 * treetype = 2;
 * break;
 * case JUNGLE:
 * case JUNGLE_HILLS:
 * chance = 90;
 * treetype = 3;
 * break;
 * case EXTREME_HILLS:
 * chance = 7;
 * break;
 * case SWAMPLAND:
 * chance = 28;
 * treetype = 4;
 * }
 * if((random.nextInt(100) + 1) < chance) {
 * TreeType tt = TreeType.TREE;
 * switch(treetype) {
 * case 1:
 * tt = random.nextInt(3) == 0? TreeType.TALL_REDWOOD : TreeType.REDWOOD;
 * break;
 * case 2:
 * tt = random.nextInt(3) == 0? (random.nextInt(2) == 0? TreeType.BIRCH: TreeType.BIG_TREE) : TreeType.TREE;
 * break;
 * case 3:
 * tt = random.nextInt(2) == 0? (random.nextInt(2) == 0? TreeType.JUNGLE_BUSH: TreeType.JUNGLE) : TreeType.SMALL_JUNGLE;
 * break;
 * case 4:
 * tt = TreeType.SWAMP;
 * }
 * world.generateTree(start.getLocation(), tt);
 * }
 * }
 * }*/
package org.bettergenteam.bettergen.blockpopulators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class TreePopulator extends BlockPopulator {
    
    private final int seedModifier;
    private final int chance;
    private final int range;
    
    
    public TreePopulator() {
        this.seedModifier = 0;
        this.chance = 20;
        this.range = 10;
    }
    
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        int centerX = (chunk.getX() << 4) + 7;
        int centerZ = (chunk.getZ() << 4) + 7;
        long modifiedSeed = world.getSeed() + seedModifier;
        for(int x = 0; x <= 2*range; x++) {
            int rX = centerX + x - range;
            for(int z = 0; z <= 2*range; z++) {
                int rZ = centerZ + z - range;
                Random rand = getLocationRandom(modifiedSeed, centerX + x, centerZ + z);
                if(rand.nextInt(99) + 1 < chance)  {
                    Block start = new Location(world, rX, world.getHighestBlockYAt(rX, rZ), rZ).getBlock();
                    if(start.getRelative(BlockFace.DOWN).getType() != Material.GRASS) {
                        return;
                    }
                    int chance2 = 0;
                    int treetype = 0;
                    switch(start.getBiome()) {
                        case FOREST:
                        case FOREST_HILLS:
                            chance2 = 60;
                            treetype = random.nextBoolean()?0:2;
                            break;
                        case ICE_PLAINS:
                        case ICE_MOUNTAINS:
                        case TAIGA:
                        case TAIGA_HILLS:
                            chance2 = 15;
                            treetype = 1;
                            break;
                        case PLAINS:
                            chance2 = 25;
                            treetype = 2;
                            break;
                        case JUNGLE:
                        case JUNGLE_HILLS:
                            chance2 = 90;
                            treetype = 3;
                            break;
                        case EXTREME_HILLS:
                            chance2 = 7;
                            break;
                        case SWAMPLAND:
                            chance2 = 28;
                            treetype = 4;
                    }
                    if((random.nextInt(100) + 1) < chance2) {
                        TreeType tt = TreeType.TREE;
                        switch(treetype) {
                            case 1:
                                tt = random.nextInt(3) == 0? TreeType.TALL_REDWOOD : TreeType.REDWOOD;
                                break;
                            case 2:
                                tt = random.nextInt(3) == 0? (random.nextInt(2) == 0? TreeType.BIRCH: TreeType.BIG_TREE) : TreeType.TREE;
                                break;
                            case 3:
                                tt = random.nextInt(2) == 0? (random.nextInt(2) == 0? TreeType.JUNGLE_BUSH: TreeType.JUNGLE) : TreeType.SMALL_JUNGLE;
                                break;
                            case 4:
                                tt = TreeType.SWAMP;
                        }
                        world.generateTree(start.getLocation(), tt);
                    }
                }
            }
            
        }
    }
    
    private static Random getLocationRandom(long seed, int realX, int realZ) {
        /*seed *= seed * 6364136223846793005L + 1442695040888963407L;
         * seed += realX;
         * seed *= seed * 6364136223846793005L + 1442695040888963407L;
         * seed += realZ;
         * seed *= seed * 6364136223846793005L + 1442695040888963407L;
         * seed += realX;
         * seed *= seed * 6364136223846793005L + 1442695040888963407L;
         * seed += realZ;*/
        return new Random(seed+138*realX-realZ*20);
    }

}
