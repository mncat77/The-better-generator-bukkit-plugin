package org.bettergenteam.bettergen.biome.layer;

public class BiomeLayerFormIslands extends BiomeLayer {
    
    public BiomeLayerFormIslands(long seed, BiomeLayer parent) {
        super(seed);
        this.parent = parent;
    }
    
    @Override
    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] values = new byte[width*length];
        int pW = width + 2;
        int pL = length + 2;
        byte[] parentValues = this.parent.getValues(realX - 1, realZ - 1, pW, pL);
        for(int x = 0; x < width; x++) {
            int s = x * length;
            int pS0 =  x    * pL;
            int pS1 = (x+1) * pL;
            int pS2 = (x+2) * pL;
            for(int z = 0; z < length; z++) {
                //Little bit like game of life
                int z1 = z+1;
                int z2 = z+2;
                byte self = parentValues[z1 + pS1];
                byte c = (byte)(parentValues[z + pS0] + parentValues[z + pS1] + parentValues[z + pS2] + parentValues[z1 + pS0] + self + parentValues[z1 + pS2] + parentValues[z2 + pS0] + parentValues[z2 + pS1] + parentValues[z2 + pS2]);
                
                values[z + s] = c;
            }
        }
        
        return values;
    }

}
