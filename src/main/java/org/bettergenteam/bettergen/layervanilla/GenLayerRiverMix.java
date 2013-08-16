package org.bettergenteam.bettergen.layervanilla;

import org.bettergenteam.bettergen.biome.BiomeBase;

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

    public int[] getInts(int realX, int realZ, int width, int length) {
        int[] biomeValues = this.biomes.getInts(realX, realZ, width, length);
        int[] riverValues = this.rivers.getInts(realX, realZ, width, length);
        int[] buffer = new int[width * length];

        for (int i = 0; i < width * length; i++) {
            if (biomeValues[i] == BiomeBase.OCEAN.id) {
                buffer[i] = biomeValues[i];
            } else if (riverValues[i] >= 0) {
                if (biomeValues[i] == BiomeBase.ICE_PLAINS.id) {
                    buffer[i] = BiomeBase.FROZEN_RIVER.id;
                } else if (biomeValues[i] != BiomeBase.MUSHROOM_ISLAND.id && biomeValues[i] != BiomeBase.MUSHROOM_SHORE.id) {
                    buffer[i] = riverValues[i];
                } else {
                    buffer[i] = BiomeBase.MUSHROOM_SHORE.id;
                }
            } else {
                buffer[i] = biomeValues[i];
            }
        }

        return buffer;
    }
}