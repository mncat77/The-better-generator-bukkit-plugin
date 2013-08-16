package org.bettergenteam.bettergen.layervanilla;

public class GenLayerZoom extends GenLayer {

    public GenLayerZoom(long i, GenLayer genlayer) {
        super(i);
        super.parent = genlayer;
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
                buffer[mOffset++ + widthOffset2] = this.modeOrRandom(parentValue1, parentValue3, parentValue2, parentValue4);
                parentValue1 = parentValue3;
                parentValue2 = parentValue4;
            }
        }

        int[] buffer2 = new int[width * length];

        for (zOffset = 0; zOffset < length; ++zOffset) {
            System.arraycopy(buffer, (zOffset + (realZ & 1)) * (widthOffset << 1) + (realX & 1), buffer2, zOffset * width, width);
        }

        return buffer2;
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
