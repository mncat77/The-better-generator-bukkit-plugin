package org.bettergenteam.bettergen.layer;

public class GenLayerSmooth extends GenLayer {
    
    /*
     * Action: Smooth everything out
     * Values: same as parent
     */

    public GenLayerSmooth(long i, GenLayer genlayer) {
        super(i);
        super.parent = genlayer;
    }

    public int[] getValues(int realX, int realZ, int width, int length) {
        int realXOffset = realX - 1;
        int realZOffset = realZ - 1;
        int widthOffset = width + 2;
        int lengthOffset = length + 2;
        int[] parentValues = this.parent.getValues(realXOffset, realZOffset, widthOffset, lengthOffset);
        int[] values = new int[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                int parentValue1 = parentValues[x + (z + 1) * widthOffset];
                int parentValue2 = parentValues[x + 2 + (z + 1) * widthOffset];
                int parentValue3 = parentValues[x + 1 + z * widthOffset];
                int parentValue4 = parentValues[x + 1 + (z + 2) * widthOffset];
                int parentValue5 = parentValues[x + 1 + (z + 1) * widthOffset];

                if (parentValue1 == parentValue2 && parentValue3 == parentValue4) {
                    this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                    if (this.nextInt(2) == 0) {
                        parentValue5 = parentValue1;
                    } else {
                        parentValue5 = parentValue3;
                    }
                } else {
                    if (parentValue1 == parentValue2) {
                        parentValue5 = parentValue1;
                    }

                    if (parentValue3 == parentValue4) {
                        parentValue5 = parentValue3;
                    }
                }

                values[x + z * width] = parentValue5;
            }
        }

        return values;
    }
}
