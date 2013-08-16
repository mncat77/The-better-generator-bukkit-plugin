package org.bettergenteam.bettergen.layervanilla;

public class LayerIsland extends GenLayer {
    
    public LayerIsland(long i) {
        super(i);
    }
    
    public int[] getInts(int realX, int realZ, int width, int length) {
        int[] buffer = new int[width * length];
        
        for (int x = 0; x < length; x++) {
            for (int z = 0; z < width; z++) {
                this.initChunkSeed((long) (realX + z), (long) (realZ + x));
                buffer[z + x * width] = this.nextInt(10) == 0 ? 1 : 0;
            }
        }
        
        if (realX > -width && realX <= 0 && realZ > -length && realZ <= 0) {
            buffer[-realX + -realZ * width] = 1;
        }
        
        return buffer;
    }

}
