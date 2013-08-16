package org.bettergenteam.bettergen.layer;

public class GenLayerHills extends GenLayer {
    
    public GenLayerHills(long seed, GenLayer genlayer) {
        super(seed);
        this.parent = genlayer;
    }
    
    public int[] getValues(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getValues(realX - 1, realZ - 1, width + 2, length + 2);
        int[] values = new int[width * length];
        
        for (int x = 0; x < width; x++) {
            for (int z = 0; z < length; z++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                int baseParentValue = parentValues[x + 1 + (z + 1) * (width + 2)];
                
                if (this.nextInt(4) == 0) {
                    int l1 = baseParentValue;
                    
                    if (baseParentValue == 2/*BiomeBase.DESERT.id*/) {
                        if(this.nextInt(15) == 0) {
                            l1 = 25;
                        }
                        else {
                            l1 = 17/*BiomeBase.DESERT_HILLS.id*/;
                        }
                    }
                    else if (baseParentValue == 4/*BiomeBase.FOREST.id*/) {
                        l1 = 18/*BiomeBase.FOREST_HILLS.id*/;
                    }
                    else if (baseParentValue == 5/*BiomeBase.TAIGA.id*/) {
                        l1 = 19/*BiomeBase.TAIGA_HILLS.id*/;
                    }
                    else if (baseParentValue == 1/*BiomeBase.PLAINS.id*/) {
                        //This is not exactly a hill, but meh
                        l1 = 4/*BiomeBase.FOREST.id*/;
                    }
                    else if (baseParentValue == 12/*BiomeBase.ICE_PLAINS.id*/) {
                        l1 = 13/*BiomeBase.ICE_MOUNTAINS.id*/;
                    }
                    else if (baseParentValue == 21/*BiomeBase.JUNGLE.id*/) {
                        l1 = 22/*BiomeBase.JUNGLE_HILLS.id*/;
                    }
                    
                    if (l1 == baseParentValue) {
                        values[x + z * width] = baseParentValue;
                    }
                    else {
                        int parentValue2 = parentValues[x + 1 + (z + 1 - 1) * (width + 2)];
                        int parentValue3 = parentValues[x + 1 + 1 + (z + 1) * (width + 2)];
                        int parentValue4 = parentValues[x + 1 - 1 + (z + 1) * (width + 2)];
                        int parentValue5 = parentValues[x + 1 + (z + 1 + 1) * (width + 2)];
                        
                        if (parentValue2 == baseParentValue && parentValue3 == baseParentValue && parentValue4 == baseParentValue && parentValue5 == baseParentValue) {
                            values[x + z * width] = l1;
                        }
                        else {
                            values[x + z * width] = baseParentValue;
                        }
                    }
                }
                else {
                    values[x + z * width] = baseParentValue;
                }
            }
        }
        
        return values;
    }
}
