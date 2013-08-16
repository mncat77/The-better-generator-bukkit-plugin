package org.bettergenteam.bettergen.layer;

public class GenLayerRiver extends GenLayer {
    
    /*
     * Action: Add rivers
     * Values: -1, 0, 7
     */
    
    public GenLayerRiver(long i, GenLayer genlayer) {
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
        
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                int parentValue1 = parentValues[x + (z + 1) * widthOffset];
                int parentValue2 = parentValues[x + 2 + (z + 1) * widthOffset];
                int parentValue3 = parentValues[x + 1 + z * widthOffset];
                int parentValue4 = parentValues[x + 1 + (z + 2) * widthOffset];
                int parentValue5 = parentValues[x + 1 + (z + 1) * widthOffset];
                
                if (parentValue5 != 0 && parentValue1 != 0 && parentValue2 != 0 && parentValue3 != 0 && parentValue4 != 0 && parentValue5 == parentValue1 && parentValue5 == parentValue3 && parentValue5 == parentValue2 && parentValue5 == parentValue4) {
                    values[x + z * width] = -1;
                } else {
                    values[x + z * width] = 7/*BiomeBase.RIVER.id*/;
                }
            }
        }
        
        return values;
    }
}