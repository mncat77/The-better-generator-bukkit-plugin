package org.bettergenteam.bettergen.layer;

public class GenLayerZoomVoronoi extends GenLayer {
    
    public GenLayerZoomVoronoi(long seed, GenLayer genlayer) {
        super(seed);
        super.parent = genlayer;
    }
    
    public int[] getValues(int realX, int realZ, int width, int length) {
        realX -= 2;
        realZ -= 2;
        byte factor = 2;
        int oneShift2 = 1 << factor;
        int realXShifted = realX >> factor;
        int realZShifted = realZ >> factor;
        int widthShifted = (width >> factor) + 3;
        int lengthShifted = (length >> factor) + 3;
        int[] parentValues = this.parent.getValues(realXShifted, realZShifted, widthShifted, lengthShifted);
        int widthShifted2 = widthShifted << factor;
        int lengthShifted2 = lengthShifted << factor;
        int[] value = new int[widthShifted2 * lengthShifted2];
        
        int parentValue1;
        
        for (int z = 0; z < lengthShifted - 1; z++) {
            parentValue1 = parentValues[z * widthShifted];
            int parentValue2 = parentValues[z * widthShifted];
            
            for (int x = 0; x < widthShifted - 1; x++) {
                double portion = (double) oneShift2 * 0.9D;
                
                this.initChunkSeed((long) (x + realXShifted << factor), (long) (z + realZShifted << factor));
                double rand1 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                double rand2 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                
                this.initChunkSeed((long) (x + realXShifted + 1 << factor), (long) (z + realZShifted << factor));
                double rand3 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                double rand4 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                
                this.initChunkSeed((long) (x + realXShifted << factor), (long) (z + realZShifted + 1 << factor));
                double rand5 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                double rand6 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                
                this.initChunkSeed((long) (x + realXShifted + 1 << factor), (long) (z + realZShifted + 1 << factor));
                double rand7 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                double rand8 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                int parentValue3 = parentValues[x + 1 + z * widthShifted];
                int parentValue4 = parentValues[x + 1 + (z + 1) * widthShifted];
                
                for (int i1 = 0; i1 < oneShift2; i1++) {
                    int a = ((z << factor) + i1) * widthShifted2 + (x << factor);
                    
                    for (int i2 = 0; i2 < oneShift2; i2++) {
                        double combRand12 = ((double) i1 - rand2) * ((double) i1 - rand2) + ((double) i2 - rand1) * ((double) i2 - rand1);
                        double combRand34 = ((double) i1 - rand4) * ((double) i1 - rand4) + ((double) i2 - rand3) * ((double) i2 - rand3);
                        double combRand56 = ((double) i1 - rand6) * ((double) i1 - rand6) + ((double) i2 - rand5) * ((double) i2 - rand5);
                        double combRand78 = ((double) i1 - rand8) * ((double) i1 - rand8) + ((double) i2 - rand7) * ((double) i2 - rand7);
                        
                        if (combRand12 < combRand34 && combRand12 < combRand56 && combRand12 < combRand78) {
                            value[a++] = parentValue1;
                        } else if (combRand34 < combRand12 && combRand34 < combRand56 && combRand34 < combRand78) {
                            value[a++] = parentValue3;
                        } else if (combRand56 < combRand12 && combRand56 < combRand34 && combRand56 < combRand78) {
                            value[a++] = parentValue2;
                        } else {
                            value[a++] = parentValue4;
                        }
                    }
                }
                
                parentValue1 = parentValue3;
                parentValue2 = parentValue4;
            }
        }
        
        int[] finalValues = new int[width * length];
        
        for (parentValue1 = 0; parentValue1 < length; ++parentValue1) {
            System.arraycopy(value, (parentValue1 + (realZ & oneShift2 - 1)) * (widthShifted << factor) + (realX & oneShift2 - 1), finalValues, parentValue1 * width, width);
        }
        
        return finalValues;
    }
}
