package org.bettergenteam.bettergen.layervanilla;

public class GenLayerZoomFuzzy extends GenLayer {

    public GenLayerZoomFuzzy(long i, GenLayer genlayer) {
        super(i);
        this.parent = genlayer;
    }
    
        public int[] getInts(int realX, int realZ, int width, int length) {
        int realXOffset = realX >> 1;
        int realZOffset = realZ >> 1;
        int widthOffset = (width >> 1) + 3;
        int lengthOffset = (length >> 1) + 3;
        int[] parentValues = this.parent.getInts(realXOffset, realZOffset, widthOffset, lengthOffset);
        int[] buffer = new int[widthOffset * 2 * lengthOffset * 2];
        int widthOffset2 = widthOffset << 1;

        int zOffset;

        for (int z = 0; z < lengthOffset - 1; z++) {
            zOffset = z << 1;
            int mOffset = zOffset * widthOffset2;
            int parentValue1 = parentValues[z * widthOffset];
            int parentValue2 = parentValues[(z + 1) * widthOffset];

            for (int x = 0; x < widthOffset - 1; x++) {
                this.initChunkSeed((long) (x + realXOffset << 1), (long) (z + realZOffset << 1));
                int parentValue3 = parentValues[x + 1 + z * widthOffset];
                int parentValue4 = parentValues[x + 1 + (z + 1) * widthOffset];

                buffer[mOffset] = parentValue1;
                buffer[mOffset++ + widthOffset2] = this.choose(parentValue1, parentValue2);
                buffer[mOffset] = this.choose(parentValue1, parentValue3);
                buffer[mOffset++ + widthOffset2] = this.choose(parentValue1, parentValue3, parentValue2, parentValue4);
                parentValue1 = parentValue3;
                parentValue2 = parentValue4;
            }
        }
        
        int[] buffer2 = new int[width * length];

        for (int zIterator = 0; zIterator < length; zIterator++) {
            System.arraycopy(buffer, (zIterator + (realZ & 1)) * (widthOffset << 1) + (realX & 1), buffer2, zIterator * width, width);
        }

        return buffer2;
    }

    protected int choose(int option1, int option2) {
        return this.nextInt(2) == 0 ? option1 : option2;
    }

    protected int choose(int option1, int option2, int option3, int option4) {
        int r = this.nextInt(4);
        
        return r == 0 ? option1 : (r == 1 ? option2 : (r == 2 ? option3 : option4));
    }
}