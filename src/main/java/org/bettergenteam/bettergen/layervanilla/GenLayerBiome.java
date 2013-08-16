package org.bettergenteam.bettergen.layervanilla;

import org.bettergenteam.bettergen.biome.BiomeBase;

public class GenLayerBiome extends GenLayer {

    private BiomeBase[] b;

    public GenLayerBiome(long seed, GenLayer genlayer) {
        super(seed);
        this.b = new BiomeBase[] { BiomeBase.DESERT, BiomeBase.FOREST, BiomeBase.EXTREME_HILLS, BiomeBase.SWAMPLAND, BiomeBase.PLAINS, BiomeBase.TAIGA, BiomeBase.JUNGLE};
        this.parent = genlayer;
    }

    public int[] getInts(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getInts(realX, realZ, width, length);
        int[] buffer = new int[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int parentValue = parentValues[x + z * width];

                if (parentValue == 0) {
                    buffer[x + z * width] = 0;
                } else if (parentValue == BiomeBase.MUSHROOM_ISLAND.id) {
                    buffer[x + z * width] = parentValue;
                } else if (parentValue == 1) {
                    buffer[x + z * width] = this.b[this.nextInt(this.b.length)].id;
                } else {
                    int randId = this.b[this.nextInt(this.b.length)].id;

                    if (randId == BiomeBase.TAIGA.id) {
                        buffer[x + z * width] = randId;
                    } else {
                        buffer[x + z * width] = BiomeBase.ICE_PLAINS.id;
                    }
                }
            }
        }

        return buffer;
    }
}