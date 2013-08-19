package org.bettergenteam.bettergen.biome;

import java.util.Random;
import org.bettergenteam.bettergen.biome.layer.BiomeLayer;
import org.bukkit.World;
import org.bukkit.block.Biome;

public class BiomeForestHills extends BiomeBase {
    
    public BiomeForestHills(int id) {
        super(id);
    }

    public Biome getBukkitBiome() {
        return Biome.FOREST_HILLS;
    }
    
    public int getMaxY(World world, Random random, int realX, int realZ, BiomeLayer layer) {
        int maxY = WATER_LEVEL + (int)Math.round(32*convertValue(BiomeBase.simplex[1].noise(realX, realZ, FREQ, AMP))) + 1;
        DistanceLocationWrapper dw = getDistanceFactorBiome(belowHills, realX, realZ, 16, layer);
        double d = dw.getFactor();
        if(d > 0) {
            int rX2 = dw.getRealX();
            int rZ2 = dw.getRealZ();
            BiomeBase b = BiomeBase.byId[dw.getValue()];
            int rMaxY = b.getMaxY(world, random, rX2, rZ2, layer);
            maxY -= (int) Math.round((maxY - rMaxY) * (1-d*d));
        }
        return maxY;
    }

    public void generateColumn(World world, Random random, byte[][] chunk, int realX, int realZ, int x, int z, BiomeLayer layer) {
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
