package org.bettergenteam.bettergen.layervanilla;

import org.bettergenteam.bettergen.biome.BiomeBase;

public class GenLayerRiver extends GenLayer {

    public GenLayerRiver(long i, GenLayer genlayer) {
        super(i);
        super.parent = genlayer;
    }

    public int[] getInts(int realX, int realZ, int width, int length) {
        int realXOffset = realX - 1;
        int realZOffset = realZ - 1;
        int widthOffset = width + 2;
        int lengthOffset = length + 2;
        int[] parentValues = this.parent.getInts(realXOffset, realZOffset, widthOffset, lengthOffset);
        int[] buffer = new int[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                int parentValue1 = parentValues[x + (z + 1) * widthOffset];
                int parentValue2 = parentValues[x + 2 + (z + 1) * widthOffset];
                int parentValue3 = parentValues[x + 1 + z * widthOffset];
                int parentValue4 = parentValues[x + 1 + (z + 2) * widthOffset];
                int parentValue5 = parentValues[x + 1 + (z + 1) * widthOffset];

                if (parentValue5 != 0 && parentValue1 != 0 && parentValue2 != 0 && parentValue3 != 0 && parentValue4 != 0 && parentValue5 == parentValue1 && parentValue5 == parentValue3 && parentValue5 == parentValue2 && parentValue5 == parentValue4) {
                    buffer[x + z * width] = -1;
                } else {
                    buffer[x + z * width] = BiomeBase.RIVER.id;
                }
            }
        }

        return buffer;
    }
}