package org.bettergenteam.bettergen.biome;

import java.util.Random;
import org.bettergenteam.bettergen.layer.GenLayer;
import org.bukkit.World;
import org.bukkit.block.Biome;

public class BiomeMushroomIsland extends BiomeBase {
    
    public BiomeMushroomIsland(int id) {
        super(id);
    }

    public Biome getBukkitBiome() {
        return Biome.MUSHROOM_ISLAND;
    }
    
    public int getMaxY(World world, Random random, int realX, int realZ, GenLayer layer) {
        return WATER_LEVEL + (int)Math.round(32*convertValue(BiomeBase.simplex[1].noise(realX, realZ, FREQ, AMP))) + 5;
    }

    public void generateColumn(World world, Random random, byte[][] chunk, int realX, int realZ, int x, int z, GenLayer layer) {
        int y = 0;
        int maxY = WATER_LEVEL + (int)Math.round(32*convertValue(BiomeBase.simplex[1].noise(realX, realZ, FREQ, AMP))) + 5;
        for(;y < random.nextInt(3)+1; y++) {
            setBlock(x, y, z, chunk, 7);
        }
        for(;y < maxY - random.nextInt(3) - 1 ; y++) {
            setBlock(x, y, z, chunk, 1);
        }
        for(;y < maxY ; y++) {
            setBlock(x, y, z, chunk, 3);
        }
        setBlock(x, maxY, z, chunk, 110);
    }
    
}
