package org.bettergenteam.bettergen.layervanilla;

import org.bettergenteam.bettergen.biome.BiomeBase;

public class GenLayerSwampRivers extends GenLayer {

    public GenLayerSwampRivers(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }

    public int[] getInts(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getInts(realX - 1, realZ - 1, width + 2, length + 2);
        int[] buffer = new int[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int parentValue = parentValues[x + 1 + (z + 1) * (width + 2)];

                if ((parentValue != BiomeBase.SWAMPLAND.id || this.nextInt(6) != 0) && (parentValue != BiomeBase.JUNGLE.id && parentValue != BiomeBase.JUNGLE_HILLS.id || this.nextInt(8) != 0)) {
                    buffer[x + z * width] = parentValue;
                } else {
                    buffer[x + z * width] = BiomeBase.RIVER.id;
                }
            }
        }

        return buffer;
    }
}
