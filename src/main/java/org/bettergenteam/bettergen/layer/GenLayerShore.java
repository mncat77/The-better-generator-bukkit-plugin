package org.bettergenteam.bettergen.layer;

public class GenLayerShore extends GenLayer {
    
    public GenLayerShore(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }
    
    public int[] getValues(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getValues(realX - 1, realZ - 1, width + 2, length + 2);
        int[] values = new int[width * length];
        
        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int w2 = width + 2;
                int baseParentValue = parentValues[x + 1 + (z + 1) * w2];
                int parentValue2, parentValue3, parentValue4, parentValue5;
                
                if (baseParentValue == 14/*BiomeBase.MUSHROOM_ISLAND.id*/) {
                    parentValue2 = parentValues[x + 1 + z * w2];
                    parentValue3 = parentValues[x + 2 + (z + 1) * w2];
                    parentValue4 = parentValues[x + (z + 1) * w2];
                    parentValue5 = parentValues[x + 1 + (z + 2) * w2];
                    if (parentValue2 != 0 && parentValue3 != 0 && parentValue4 != 0 && parentValue5 != 0) {
                        values[x + z * width] = baseParentValue;
                    } else {
                        values[x + z * width] = 15/*BiomeBase.MUSHROOM_SHORE.id*/;
                    }
                } else if (baseParentValue != 0 && baseParentValue != 7 && baseParentValue != 6 && baseParentValue != 3) {
                    parentValue2 = parentValues[x + 1 + z * w2];
                    parentValue3 = parentValues[x + 2 + (z + 1) * w2];
                    parentValue4 = parentValues[x + (z + 1) * w2];
                    parentValue5 = parentValues[x + 1 + (z + 2) * w2];
                    if (parentValue2 != 0 && parentValue3 != 0 && parentValue4 != 0 && parentValue5 != 0) {
                        values[x + z * width] = baseParentValue;
                    } else {
                        values[x + z * width] = this.nextInt(7) == 0? 27 : 16;
                    }
                } else if (baseParentValue == 3) {
                    parentValue2 = parentValues[x + 1 + z * w2];
                    parentValue3 = parentValues[x + 2 + (z + 1) * w2];
                    parentValue4 = parentValues[x + (z + 1) * w2];
                    parentValue5 = parentValues[x + 1 + (z + 2) * w2];
                    if (parentValue2 == 3 && parentValue3 == 3 && parentValue4 == 3 && parentValue5 == 3) {
                        values[x + z * width] = baseParentValue;
                    } else {
                        values[x + z * width] = 20;
                    }
                } else {
                    values[x + z * width] = baseParentValue;
                }
            }
        }
        
        return values;
    }
}
