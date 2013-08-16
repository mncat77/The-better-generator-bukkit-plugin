package org.bettergenteam.bettergen.layer;

public class GenLayerSwampJungleLakes extends GenLayer {
    
    public GenLayerSwampJungleLakes(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }
    
    public int[] getValues(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getValues(realX - 1, realZ - 1, width + 2, length + 2);
        int[] values = new int[width * length];
        
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int parentValue = parentValues[x + 1 + (z + 1) * (width + 2)];
                
                if ((parentValue != 6 || this.nextInt(6) != 0) && (parentValue != 21 && parentValue != 22 || this.nextInt(8) != 0)) {
                    values[x + z * width] = parentValue;
                } else {
                    values[x + z * width] = 28;
                }
            }
        }
        
        return values;
    }
}
