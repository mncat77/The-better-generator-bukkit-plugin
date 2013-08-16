package org.bettergenteam.bettergen.blockpopulators;

import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public abstract class ConsistentPopulator extends BlockPopulator {
    
    private final int seedModifier;
    private final int chance;
    private final int range;
    
    
    public ConsistentPopulator(int seedModifier, int chance, int range) {
        this.seedModifier = seedModifier;
        this.chance = chance;
        this.range = range;
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
                    this.populate(world, rand, rX, rZ);
                }
            }
        }
        
    }
    
    public abstract void populate(World world, Random random, int realX, int realZ);
    
    private static Random getLocationRandom(long seed, int realX, int realZ) {
        /*seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realX;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realZ;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realX;
        seed *= seed * 6364136223846793005L + 1442695040888963407L;
        seed += realZ;*/
        return new Random(seed+138*realX-realZ*20);
    }

}
