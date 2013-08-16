package org.bettergenteam.bettergen.layervanilla;

import org.bettergenteam.bettergen.biome.BiomeBase;

public class GenLayerRegionHills extends GenLayer {
    
    public GenLayerRegionHills(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }
    
    public int[] getInts(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getInts(realX - 1, realZ - 1, width + 2, length + 2);
        int[] buffer = new int[width * length];
        
        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int baseParentValue = parentValues[x + 1 + (z + 1) * (width + 2)];
                
                if (this.nextInt(3) == 0) {
                    int l1 = baseParentValue;
                    
                    if (baseParentValue == BiomeBase.DESERT.id) {
                        l1 = BiomeBase.DESERT_HILLS.id;
                    } else if (baseParentValue == BiomeBase.FOREST.id) {
                        l1 = BiomeBase.FOREST_HILLS.id;
                    } else if (baseParentValue == BiomeBase.TAIGA.id) {
                        l1 = BiomeBase.TAIGA_HILLS.id;
                    } else if (baseParentValue == BiomeBase.PLAINS.id) {
                        l1 = BiomeBase.FOREST.id;
                    } else if (baseParentValue == BiomeBase.ICE_PLAINS.id) {
                        l1 = BiomeBase.ICE_MOUNTAINS.id;
                    } else if (baseParentValue == BiomeBase.JUNGLE.id) {
                        l1 = BiomeBase.JUNGLE_HILLS.id;
                    }
                    
                    if (l1 == baseParentValue) {
                        buffer[x + z * width] = baseParentValue;
                    } else {
                        int parentValue2 = parentValues[x + 1 + (z + 1 - 1) * (width + 2)];
                        int parentValue3 = parentValues[x + 1 + 1 + (z + 1) * (width + 2)];
                        int parentValue4 = parentValues[x + 1 - 1 + (z + 1) * (width + 2)];
                        int parentValue5 = parentValues[x + 1 + (z + 1 + 1) * (width + 2)];
                        
                        if (parentValue2 == baseParentValue && parentValue3 == baseParentValue && parentValue4 == baseParentValue && parentValue5 == baseParentValue) {
                            buffer[x + z * width] = l1;
                        } else {
                            buffer[x + z * width] = baseParentValue;
                        }
                    }
                } else {
                    buffer[x + z * width] = baseParentValue;
                }
            }
        }
        
        return buffer;
    }
}
    