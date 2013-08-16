package org.bettergenteam.bettergen.layer;

public class GenLayerBaseZoom extends GenLayer {
    
    /*
     * Parent: Base values from the RNGBase
     * Action: Zoom and smooth parent
     * Values: 0, 1
     */

    public GenLayerBaseZoom(long i, GenLayer genlayer) {
        super(i);
        this.parent = genlayer;
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
                values[indexZ++ + widthDivOffsetMul] = this.choose(parentValue1, parentValue3, parentValue2, parentValue4);
                parentValue1 = parentValue3;
                parentValue2 = parentValue4;
            }
        }
        
        int[] finalValues = new int[width * length];

        for (int zIterator = 0; zIterator < length; zIterator++) {
            System.arraycopy(values, (zIterator + (realZ & 1)) * (widthDivOffset << 1) + (realX & 1), finalValues, zIterator * width, width);
        }

        return finalValues;
    }
    
    private int choose(int option1, int option2) {
        return this.nextInt(2) == 0 ? option1 : option2;
    }
    
    private int choose(int option1, int option2, int option3, int option4) {
        int r = this.nextInt(4);
        
        return r == 0 ? option1 : (r == 1 ? option2 : (r == 2 ? option3 : option4));
    }
}