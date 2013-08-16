package org.bettergenteam.bettergen.biome;

import java.util.Random;
import org.bettergenteam.bettergen.layer.GenLayer;
import org.bukkit.World;
import org.bukkit.block.Biome;

public class BiomeLake extends BiomeBase {
    
    public BiomeLake(int id) {
        super(id);
    }

    public Biome getBukkitBiome() {
        return Biome.RIVER;
    }

    public int getMaxY(World world, Random random, int realX, int realZ, GenLayer layer) {
        return WATER_LEVEL;
    }
    
    public void generateColumn(World world, Random random, byte[][] chunk, int realX, int realZ, int x, int z, GenLayer layer) {
        int y = 0;
        int maxY = WATER_LEVEL - (int)Math.round(16*convertValue(BiomeBase.simplex[0].noise(realX, realZ, FREQ_O, AMP_O))) - 20;
        DistanceLocationWrapper dw = getDistanceFactorBiome(aboveFluids, realX, realZ, 25, layer);
        double d = dw.getFactor();
        if(d > 0) {
            maxY += (int) Math.round((WATER_LEVEL - maxY + 1) * (2.2/(d+1)-1.1));
        }
        maxY = maxY >= WATER_LEVEL? WATER_LEVEL - 1 : maxY;
        for(;y < random.nextInt(3)+1; y++) {
            setBlock(x, y, z, chunk, 7);
        }
        for(;y < maxY - random.nextInt(3) - 1 ; y++) {
            setBlock(x, y, z, chunk, 1);
        }
        for(;y <= maxY ; y++) {
            setBlock(x, y, z, chunk, 3);
        }
        for(;y <= WATER_LEVEL ; y++) {
            setBlock(x, y, z, chunk, 8);
        }
        if(random.nextInt(31) == 0) {
            setBlock(x, WATER_LEVEL+1, z, chunk, 111);
        }
    }
    
}