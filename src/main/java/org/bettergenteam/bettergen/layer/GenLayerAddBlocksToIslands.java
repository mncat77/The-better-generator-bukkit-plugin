package org.bettergenteam.bettergen.layer;

public class GenLayerAddBlocksToIslands extends GenLayer {
    
    /* First Iteration:
     * Parent: Zoomed and smoothed base values from the BaseZoom
     * Action: Adds blocks to exsisting islands
     * Values: 0, 1
     */
    
    /* After Ice Plains have been added:
     * Action: Adds blocks to exsisting islands and converts edge IcePlains biome to FrozenOcean
     * Values: 0, 1, 10, 12
     */
    
    public GenLayerAddBlocksToIslands(long i, GenLayer genlayer) {
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
        
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
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
                    
                    //Replace edges of the ice plains with frozen ocean
                    if (this.nextInt(3) == 0) {
                        values[x + z * width] = i4;
                    } else if (i4 == 12/*BiomeBase.ICE_PLAINS.id*/) {
                        values[x + z * width] = 10/*BiomeBase.FROZEN_OCEAN.id*/;
                    } else {
                        values[x + z * width] = 0;
                    }
                } else if (parentValue5 > 0 && (parentValue1 == 0 || parentValue2 == 0 || parentValue3 == 0 || parentValue4 == 0)) {
                    if (this.nextInt(5) == 0) {
                        if (parentValue5 == 12/*BiomeBase.ICE_PLAINS.id*/) {
                            values[x + z * width] = 10/*BiomeBase.FROZEN_OCEAN.id*/;
                        } else {
                            values[x + z * width] = 0;
                        }
                    } else {
                        values[x + z * width] = parentValue5;
                    }
                } else {
                    values[x + z * width] = parentValue5;
                }
            }
        }
        
        return values;
    }

}