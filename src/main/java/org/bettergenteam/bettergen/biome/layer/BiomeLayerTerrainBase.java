package org.bettergenteam.bettergen.biome.layer;

public class BiomeLayerTerrainBase extends BiomeLayer {
    
    public BiomeLayerTerrainBase(long seed) {
        super(seed);
    }

    @Override
    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] values = new byte[width*length];
        for(int x = 0; x < width; x++) {
           int s = x * length;
           int rX = realX + x;
           for(int z = 0; z < length; z++) {
               values[z + s] = this.nextByte(OCEAN_FREQUENCY, rX, realZ + z) == 0? (byte)1 : 0;
           }
        }
        return values;
    }
    

    
}
