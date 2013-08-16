package org.bettergenteam.bettergen.layer;

public class GenLayerIcePlains extends GenLayer {
    
    /*
     * Action: Adds the IcePlains biome
     * Values: 0, 1, 12
     */

    public GenLayerIcePlains(long i, GenLayer genlayer) {
        super(i);
        this.parent = genlayer;
    }

    public int[] getValues(int realX, int realZ, int width, int length) {
        int realXOffset = realX - 1;
        int realZOffset = realZ - 1;
        int widthOffset = width + 2;
        int lengthOffset = length + 2;
        int[] parentValues = this.parent.getValues(realXOffset, realZOffset, widthOffset, lengthOffset);
        int[] values = new int[width * length];

        for (int z = 0; z < length; ++z) {
            for (int x = 0; x < width; ++x) {
                int parentValue = parentValues[x + 1 + (z + 1) * widthOffset];

                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                if (parentValue == 0) {
                    values[x + z * width] = 0;
                } else {
                    int r5 = this.nextInt(5);

                    if (r5 == 0) {
                        r5 = 12/*BiomeBase.ICE_PLAINS.id*/;
                    } else {
                        r5 = 1;
                    }

                    values[x + z * width] = r5;
                }
            }
        }

        return values;
    }
}