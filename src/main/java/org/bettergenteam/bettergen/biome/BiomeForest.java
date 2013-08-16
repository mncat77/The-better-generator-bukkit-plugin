package org.bettergenteam.bettergen.biome;

import java.util.Random;
import org.bettergenteam.bettergen.layer.GenLayer;
import org.bukkit.World;
import org.bukkit.block.Biome;

public class BiomeForest extends BiomeBase {
    
    public BiomeForest(int id) {
        super(id);
    }

    public Biome getBukkitBiome() {
        return Biome.FOREST;
    }

    public int getMaxY(World world, Random random, int realX, int realZ, GenLayer layer) {
        int maxY = WATER_LEVEL + (int)Math.round(16*convertValue(BiomeBase.simplex[1].noise(realX, realZ, FREQ, AMP))) + 1;
        double d = getDistanceFactorBiome(fluids, realX, realZ, 16, layer).getFactor();
        if(d > 0) {
            maxY -= (int) Math.round((maxY - WATER_LEVEL) * (1-d*d));
        }
        return maxY;
    }
    
    public void generateColumn(World world, Random random, byte[][] chunk, int realX, int realZ, int x, int z, GenLayer layer) {
        int y = 0;
        int maxY = getMaxY(world, random, realX, realZ, layer);
        for(;y < random.nextInt(3)+1; y++) {
            setBlock(x, y, z, chunk, 7);
        }
        for(;y < maxY - random.nextInt(3) - 1 ; y++) {
            setBlock(x, y, z, chunk, 1);
        }
        for(;y < maxY ; y++) {
            setBlock(x, y, z, chunk, 3);
        }
        setBlock(x, maxY, z, chunk, 2);
    }
    
}