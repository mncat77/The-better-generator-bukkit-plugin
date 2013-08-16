package org.bettergenteam.bettergen.layervanilla;

public class GenLayerZoomVoronoi extends GenLayer {
    
    public GenLayerZoomVoronoi(long seed, GenLayer genlayer) {
        super(seed);
        super.parent = genlayer;
    }
    
    public int[] getInts(int realX, int realZ, int width, int length) {
        realX -= 2;
        realZ -= 2;
        byte shifter = 2;
        int oneShift2 = 1 << shifter;
        int realXShifted = realX >> shifter;
        int realZShifted = realZ >> shifter;
        int widthShifted = (width >> shifter) + 3;
        int lengthShifted = (length >> shifter) + 3;
        int[] parentValues = this.parent.getInts(realXShifted, realZShifted, widthShifted, lengthShifted);
        int widthShifted2 = widthShifted << shifter;
        int lengthShifted2 = lengthShifted << shifter;
        int[] buffer = new int[widthShifted2 * lengthShifted2];
        
        int parentValue1;
        
        for (int z = 0; z < lengthShifted - 1; z++) {
            parentValue1 = parentValues[z * widthShifted];
            int parentValue2 = parentValues[z * widthShifted];
            
            for (int x = 0; x < widthShifted - 1; x++) {
                double portion = (double) oneShift2 * 0.9D;
                
                this.initChunkSeed((long) (x + realXShifted << shifter), (long) (z + realZShifted << shifter));
                double rand1 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                double rand2 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                
                this.initChunkSeed((long) (x + realXShifted + 1 << shifter), (long) (z + realZShifted << shifter));
                double rand3 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                double rand4 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                
                this.initChunkSeed((long) (x + realXShifted << shifter), (long) (z + realZShifted + 1 << shifter));
                double rand5 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion;
                double rand6 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                
                this.initChunkSeed((long) (x + realXShifted + 1 << shifter), (long) (z + realZShifted + 1 << shifter));
                double rand7 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                double rand8 = ((double) this.nextInt(1024) / 1024.0D - 0.5D) * portion + (double) oneShift2;
                int parentValue3 = parentValues[x + 1 + z * widthShifted];
                int parentValue4 = parentValues[x + 1 + (z + 1) * widthShifted];
                
                for (int i1 = 0; i1 < oneShift2; i1++) {
                    int a = ((z << shifter) + i1) * widthShifted2 + (x << shifter);
                    
                    for (int i2 = 0; i2 < oneShift2; i2++) {
                        double combRand12 = ((double) i1 - rand2) * ((double) i1 - rand2) + ((double) i2 - rand1) * ((double) i2 - rand1);
                        double combRand34 = ((double) i1 - rand4) * ((double) i1 - rand4) + ((double) i2 - rand3) * ((double) i2 - rand3);
                        double combRand56 = ((double) i1 - rand6) * ((double) i1 - rand6) + ((double) i2 - rand5) * ((double) i2 - rand5);
                        double combRand78 = ((double) i1 - rand8) * ((double) i1 - rand8) + ((double) i2 - rand7) * ((double) i2 - rand7);
                        
                        if (combRand12 < combRand34 && combRand12 < combRand56 && combRand12 < combRand78) {
                            buffer[a++] = parentValue1;
                        } else if (combRand34 < combRand12 && combRand34 < combRand56 && combRand34 < combRand78) {
                            buffer[a++] = parentValue3;
                        } else if (combRand56 < combRand12 && combRand56 < combRand34 && combRand56 < combRand78) {
                            buffer[a++] = parentValue2;
                        } else {
                            buffer[a++] = parentValue4;
                        }
                    }
                }
                
                parentValue1 = parentValue3;
                parentValue2 = parentValue4;
            }
        }
        
        int[] buffer2 = new int[width * length];
        
        for (parentValue1 = 0; parentValue1 < length; ++parentValue1) {
            System.arraycopy(buffer, (parentValue1 + (realZ & oneShift2 - 1)) * (widthShifted << shifter) + (realX & oneShift2 - 1), buffer2, parentValue1 * width, width);
        }
        
        return buffer2;
    }
}
