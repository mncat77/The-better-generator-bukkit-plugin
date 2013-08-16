package org.bettergenteam.bettergen.layer;

public class GenLayerRNGBase extends GenLayer {
    
    /*
     * Parent: n/a
     * Action: Create base values: fill with 1's and 0's (more 0's than 1's)
     * Values: 0, 1
     */
    
    private static final int OCEAN_FREQUENCY = 10;
    
    
    public GenLayerRNGBase(long seed) {
        super(seed);
    }
    
    @Override
    public int[] getValues(int realX, int realZ, int width, int length) {
        int[] values = new int[width * length];
        
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                this.initChunkSeed((long) (realX + x), (long) (realZ + z));
                values[x + z * width] = this.nextInt(OCEAN_FREQUENCY) == 0 ? 1 : 0;
            }
        }
        
        return values;
    }

}
