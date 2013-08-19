package org.bettergenteam.bettergen.biome.layer;

import org.bukkit.util.noise.SimplexOctaveGenerator;

public class BiomeLayerRoughBiomes extends BiomeLayer {
    
    private final SimplexOctaveGenerator humidity;
    private final SimplexOctaveGenerator temperature;
    
    public BiomeLayerRoughBiomes(long seed, BiomeLayer parent) {
        super(seed);
        this.parent = parent;
        this.humidity = new SimplexOctaveGenerator(seed, 8);
        this.temperature = new SimplexOctaveGenerator(seed + 1, 8);
        this.humidity.setScale(.1);
        this.temperature.setScale(.1);
    }
    
    @Override
    public byte[] getValues(int realX, int realZ, int width, int length) {
        byte[] values = new byte[width*length];
        int pL = length + 2;
        byte[] parentValues = this.parent.getValues(realX - 1, realZ - 1, width + 2, pL);
        for(int x = 0; x < width; x++) {
            int s = x * length;
            int pS0 =  x    * pL;
            int pS1 = (x+1) * pL;
            int pS2 = (x+2) * pL;
            int rX = realX + x;
            for(int z = 0; z < length; z++) {
                int rZ = realZ + z;
                int z1 = z+1;
                int z2 = z+2;
                byte parentUpperLeft = parentValues[z + pS0];
                byte parentUpper = parentValues[z + pS1];
                byte parentUpperRight = parentValues[z + pS2];
                byte parentLeft = parentValues[z1 + pS0];
                byte self = parentValues[z1 + pS1];
                byte parentRight = parentValues[z1 + pS2];
                byte parentLowerLeft = parentValues[z2 + pS0];
                byte parentLower = parentValues[z2 + pS1];
                byte parentLowerRight = parentValues[z2 + pS2];
                
                values[z + s] = self;
            }
        }
        return values;
    }

}
