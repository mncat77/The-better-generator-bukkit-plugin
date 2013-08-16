package org.bettergenteam.bettergen.layer;

public class GenLayerMarkBiomeEdges extends GenLayer {
    
    /*
     * Action: Adds biomes as preperation for rivers using th edges of those
     * Values: 0, 1, 2, 3, 4, 10, 12, 14
     */
    
    public GenLayerMarkBiomeEdges(long i, GenLayer genlayer) {
        super(i);
        this.parent = genlayer;
    }
    
    public int[] getValues(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getValues(realX, realZ, width, length);
        
        int[] buffer = new int[width * length];
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                buffer[x + z * width] = parentValues[x + z * width] != 0 ? this.nextInt(2) + 2 : 0;
            }
        }
        
        return buffer;
    }
}