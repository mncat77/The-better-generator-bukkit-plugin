package org.bettergenteam.bettergen.layer;

import org.bettergenteam.bettergen.biome.BiomeBase;

public class GenLayerBiome extends GenLayer {
    
    private static final int[] BIOMES = new int[]{ BiomeBase.DESERT.id, BiomeBase.FOREST.id, BiomeBase.EXTREME_HILLS.id, BiomeBase.SWAMPLAND.id, BiomeBase.PLAINS.id, BiomeBase.TAIGA.id, BiomeBase.JUNGLE.id, BiomeBase.WASTELAND.id, BiomeBase.VOLCANO.id};
    
    public GenLayerBiome(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }
    
    public int[] getValues(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getValues(realX, realZ, width, length);
        int[] values = new int[width * length];
        
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int parentValue = parentValues[x + z * width];
                int randId = BIOMES[this.nextInt(BIOMES.length)];
                if (parentValue == 0) {
                    values[x + z * width] = 0;
                } else if (parentValue == 14/*BiomeBase.MUSHROOM_ISLAND.id*/) {
                    values[x + z * width] = parentValue;
                } else if (parentValue == 1) {
                    if(((randId == 1) || (randId == 2)) && (this.nextInt(3) == 0)) {
                        randId = 23;
                    }
                    values[x + z * width] = randId;
                } else {
                    if (randId == 5/*BiomeBase.TAIGA.id*/) {
                        values[x + z * width] = randId;
                    } else {
                        values[x + z * width] = 12/*BiomeBase.ICE_PLAINS.id*/;
                    }
                }
            }
        }
        
        return values;
    }
}