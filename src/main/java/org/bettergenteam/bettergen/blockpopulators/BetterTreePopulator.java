package org.bettergenteam.bettergen.blockpopulators;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.TreeType;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class BetterTreePopulator extends BetterBlockPopulator {
    
    @Override
    public void populateChunk(World world, BiomeGrid biome, long seed, byte[][] chunk, int chunkX, int chunkZ) {
        int range = 10;
        int chance = 20;
        int centerX = (chunkX << 4) + 7;
        int centerZ = (chunkZ << 4) + 7;
        for(int x = 0; x <= 2*range; x++) {
            int rX = centerX + x - range;
            for(int z = 0; z <= 2*range; z++) {
                int rZ = centerZ + z - range;
                Random random = getLocationRandom(seed, centerX + x, centerZ + z);
                if(random.nextInt(99) + 1 < chance)  {
                    int maxY = 0;
                    for(int y = 256; y >= 0; y++) {
                        if(getBlock(x, y, z, chunk) == 0) {
                            maxY = y;
                        }
                    }
                    Block start = new Location(world, rX, maxY, rZ).getBlock();
                    if(getBlock(x-range, maxY, z-range, chunk) != 2) {
                        return;
                    }
                    int chance2 = 25;
                    //int treetype = 0;
                    /*switch(biome.getBiome(x-range, z-range)) {
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
                    }*/
                    if((random.nextInt(100) + 1) < chance2) {
                        TreeType tt = TreeType.TREE;
                        /*switch(treetype) {
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
                        }*/
                        BlockChangeDelegateChunk d = new BlockChangeDelegateChunk(chunk);
                        world.generateTree(start.getLocation(), tt, d);
                        chunk = d.getChunk();
                    }
                }
            }
            
        }
    }
    
    private static Random getLocationRandom(long seed, int realX, int realZ) {
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realX;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realZ;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realX;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realZ;
        return new Random(seed);
    }

}
