package org.bettergenteam.bettergen.layervanilla;

import org.bettergenteam.bettergen.biome.BiomeBase;

public class GenLayerIsland extends GenLayer {

    public GenLayerIsland(long i, GenLayer genlayer) {
        super(i);
        this.parent = genlayer;
    }

    public int[] getInts(int realX, int realZ, int width, int length) {
        int realXOffset = realX - 1;
        int realZOffset = realZ - 1;
        int widthOffset = width + 2;
        int lengthOffset = length + 2;
        int[] parentValues = this.parent.getInts(realXOffset, realZOffset, widthOffset, lengthOffset);
        int[] buffer = new int[width * length];

        for (int z = 0; z < length; ++z) {
            for (int x = 0; x < width; ++x) {
                int parentValue1 = parentValues[x + z * widthOffset];
                int parentValue2 = parentValues[x + 2 + z * widthOffset];
                int parentValue3 = parentValues[x + (z + 2) * widthOffset];
                int parentValue4 = parentValues[x + 2 + (z + 2) * widthOffset];
                int parentValue5 = parentValues[x + 1 + (z + 1) * widthOffset];

                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                if (parentValue5 == 0 && (parentValue1 != 0 || parentValue2 != 0 || parentValue3 != 0 || parentValue4 != 0)) {
                    int l3 = 1;
                    int i4 = 1;

                    if (parentValue1 != 0 && this.nextInt(l3++) == 0) {
                        i4 = parentValue1;
                    }

                    if (parentValue2 != 0 && this.nextInt(l3++) == 0) {
                        i4 = parentValue2;
                    }

                    if (parentValue3 != 0 && this.nextInt(l3++) == 0) {
                        i4 = parentValue3;
                    }

                    if (parentValue4 != 0 && this.nextInt(l3++) == 0) {
                        i4 = parentValue4;
                    }

                    if (this.nextInt(3) == 0) {
                        buffer[x + z * width] = i4;
                    } else if (i4 == BiomeBase.ICE_PLAINS.id) {
                        buffer[x + z * width] = BiomeBase.FROZEN_OCEAN.id;
                    } else {
                        buffer[x + z * width] = 0;
                    }
                } else if (parentValue5 > 0 && (parentValue1 == 0 || parentValue2 == 0 || parentValue3 == 0 || parentValue4 == 0)) {
                    if (this.nextInt(5) == 0) {
                        if (parentValue5 == BiomeBase.ICE_PLAINS.id) {
                            buffer[x + z * width] = BiomeBase.FROZEN_OCEAN.id;
                        } else {
                            buffer[x + z * width] = 0;
                        }
                    } else {
                        buffer[x + z * width] = parentValue5;
                    }
                } else {
                    buffer[x + z * width] = parentValue5;
                }
            }
        }

        return buffer;
    }
}