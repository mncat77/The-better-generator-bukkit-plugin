package org.bettergenteam.bettergen.layervanilla;

import org.bettergenteam.bettergen.biome.BiomeBase;

public class GenLayerMushroomShore extends GenLayer {

    public GenLayerMushroomShore(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }

    public int[] getInts(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getInts(realX - 1, realZ - 1, width + 2, length + 2);
        int[] buffer = new int[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int baseParentValue = parentValues[x + 1 + (z + 1) * (width + 2)];
                int parentValue2;
                int parentValue3;
                int parentvalue4;
                int parentValue5;

                if (baseParentValue == BiomeBase.MUSHROOM_ISLAND.id) {
                    parentValue2 = parentValues[x + 1 + (z + 1 - 1) * (width + 2)];
                    parentValue3 = parentValues[x + 1 + 1 + (z + 1) * (width + 2)];
                    parentvalue4 = parentValues[x + 1 - 1 + (z + 1) * (width + 2)];
                    parentValue5 = parentValues[x + 1 + (z + 1 + 1) * (width + 2)];
                    if (parentValue2 != BiomeBase.OCEAN.id && parentValue3 != BiomeBase.OCEAN.id && parentvalue4 != BiomeBase.OCEAN.id && parentValue5 != BiomeBase.OCEAN.id) {
                        buffer[x + z * width] = baseParentValue;
                    } else {
                        buffer[x + z * width] = BiomeBase.MUSHROOM_SHORE.id;
                    }
                } else if (baseParentValue != BiomeBase.OCEAN.id && baseParentValue != BiomeBase.RIVER.id && baseParentValue != BiomeBase.SWAMPLAND.id && baseParentValue != BiomeBase.EXTREME_HILLS.id) {
                    parentValue2 = parentValues[x + 1 + (z + 1 - 1) * (width + 2)];
                    parentValue3 = parentValues[x + 1 + 1 + (z + 1) * (width + 2)];
                    parentvalue4 = parentValues[x + 1 - 1 + (z + 1) * (width + 2)];
                    parentValue5 = parentValues[x + 1 + (z + 1 + 1) * (width + 2)];
                    if (parentValue2 != BiomeBase.OCEAN.id && parentValue3 != BiomeBase.OCEAN.id && parentvalue4 != BiomeBase.OCEAN.id && parentValue5 != BiomeBase.OCEAN.id) {
                        buffer[x + z * width] = baseParentValue;
                    } else {
                        buffer[x + z * width] = BiomeBase.BEACH.id;
                    }
                } else if (baseParentValue == BiomeBase.EXTREME_HILLS.id) {
                    parentValue2 = parentValues[x + 1 + (z + 1 - 1) * (width + 2)];
                    parentValue3 = parentValues[x + 1 + 1 + (z + 1) * (width + 2)];
                    parentvalue4 = parentValues[x + 1 - 1 + (z + 1) * (width + 2)];
                    parentValue5 = parentValues[x + 1 + (z + 1 + 1) * (width + 2)];
                    if (parentValue2 == BiomeBase.EXTREME_HILLS.id && parentValue3 == BiomeBase.EXTREME_HILLS.id && parentvalue4 == BiomeBase.EXTREME_HILLS.id && parentValue5 == BiomeBase.EXTREME_HILLS.id) {
                        buffer[x + z * width] = baseParentValue;
                    } else {
                        buffer[x + z * width] = BiomeBase.SMALL_MOUNTAINS.id;
                    }
                } else {
                    buffer[x + z * width] = baseParentValue;
                }
            }
        }

        return buffer;
    }
}
