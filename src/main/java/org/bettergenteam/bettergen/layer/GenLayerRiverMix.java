package org.bettergenteam.bettergen.layer;

public class GenLayerRiverMix extends GenLayer {

    private GenLayer biomes;
    private GenLayer rivers;

    public GenLayerRiverMix(long seed, GenLayer biomes, GenLayer rivers) {
        super(seed);
        this.biomes = biomes;
        this.rivers = rivers;
    }

    @Override
    public void initWorldGenSeed(long seed) {
        this.biomes.initWorldGenSeed(seed);
        this.rivers.initWorldGenSeed(seed);
        super.initWorldGenSeed(seed);
    }

    public int[] getValues(int realX, int realZ, int width, int length) {
        int[] biomeValues = this.biomes.getValues(realX, realZ, width, length);
        int[] riverValues = this.rivers.getValues(realX, realZ, width, length);
        int[] buffer = new int[width * length];

        for (int i = 0; i < width * length; i++) {
            if (biomeValues[i] == 0) {
                buffer[i] = biomeValues[i];
            } else if (riverValues[i] >= 0) {
                if (biomeValues[i] == 12) {
                    buffer[i] = 11;
                } else if (biomeValues[i] != 14 && biomeValues[i] != 15) {
                    buffer[i] = riverValues[i];
                } else {
                    buffer[i] = 15;
                }
            } else {
                buffer[i] = biomeValues[i];
            }
        }

        return buffer;
    }
}