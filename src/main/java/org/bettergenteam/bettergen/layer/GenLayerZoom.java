package org.bettergenteam.bettergen.layer;

public class GenLayerZoom extends GenLayer {
    
    /*
     * Parent: differs
     * Action: Zooms in, smoothes and changes the seed
     * Values: unchanged
     */
    
    public GenLayerZoom(long i, GenLayer genlayer) {
        super(i);
        super.parent = genlayer;
    }
    
    public int[] getValues(int realX, int realZ, int width, int length) {
        int realXDiv = realX >> 1;
        int realZDiv = realZ >> 1;
        int widthDivOffset = (width >> 1) + 3;
        int lengthDivOffset = (length >> 1) + 3;
        int[] parentValues = this.parent.getValues(realXDiv, realZDiv, widthDivOffset, lengthDivOffset);
        int[] values = new int[widthDivOffset * 2 * lengthDivOffset * 2];
        int widthDivOffsetMul = widthDivOffset << 1;
        
        int zMul;
        
        for (int z = 0; z < lengthDivOffset - 1; z++) {
            zMul = z << 1;
            int indexZ = zMul * widthDivOffsetMul;
            int parentValue1 = parentValues[z * widthDivOffset];
            int parentValue2 = parentValues[(z + 1) * widthDivOffset];
            
            for (int x = 0; x < widthDivOffset - 1; x++) {
                this.initChunkSeed((long) (x + realXDiv << 1), (long) (z + realZDiv << 1));
                int parentValue3 = parentValues[x + 1 + z * widthDivOffset];
                int parentValue4 = parentValues[x + 1 + (z + 1) * widthDivOffset];
                
                values[indexZ] = parentValue1;
                values[indexZ++ + widthDivOffsetMul] = this.choose(parentValue1, parentValue2);
                values[indexZ] = this.choose(parentValue1, parentValue3);
                values[indexZ++ + widthDivOffsetMul] = this.modeOrRandom(parentValue1, parentValue3, parentValue2, parentValue4);
                parentValue1 = parentValue3;
                parentValue2 = parentValue4;
            }
        }
        
        int[] finalValues = new int[width * length];
        
        for (zMul = 0; zMul < length; ++zMul) {
            System.arraycopy(values, (zMul + (realZ & 1)) * (widthDivOffset << 1) + (realX & 1), finalValues, zMul * width, width);
        }
        
        return finalValues;
    }
    
    protected int choose(int option1, int option2) {
        return this.nextInt(2) == 0 ? option1 : option2;
    }
    
    protected int modeOrRandom(int option1, int option2, int option3, int option4) {
        if (option2 == option3 && option3 == option4) {
            return option2;
        } else if (option1 == option2 && option1 == option3) {
            return option1;
        } else if (option1 == option2 && option1 == option4) {
            return option1;
        } else if (option1 == option3 && option1 == option4) {
            return option1;
        } else if (option1 == option2 && option3 != option4) {
            return option1;
        } else if (option1 == option3 && option2 != option4) {
            return option1;
        } else if (option1 == option4 && option2 != option3) {
            return option1;
        } else if (option2 == option1 && option3 != option4) {
            return option2;
        } else if (option2 == option3 && option1 != option4) {
            return option2;
        } else if (option2 == option4 && option1 != option3) {
            return option2;
        } else if (option3 == option1 && option2 != option4) {
            return option3;
        } else if (option3 == option2 && option1 != option4) {
            return option3;
        } else if (option3 == option4 && option1 != option2) {
            return option3;
        } else if (option4 == option1 && option2 != option3) {
            return option3;
        } else if (option4 == option2 && option1 != option3) {
            return option3;
        } else if (option4 == option3 && option1 != option2) {
            return option3;
        } else {
            int i1 = this.nextInt(4);
            
            return i1 == 0 ? option1 : (i1 == 1 ? option2 : (i1 == 2 ? option3 : option4));
        }
    }
    
    public static GenLayer magnify(long seedAdjust, GenLayer genlayer, int times) {
        GenLayer layer = genlayer;
        
        for (int i = 0; i < times; ++i) {
            layer = new GenLayerZoom(seedAdjust + (long) i, layer);
        }
        
        return layer;
    }
}
