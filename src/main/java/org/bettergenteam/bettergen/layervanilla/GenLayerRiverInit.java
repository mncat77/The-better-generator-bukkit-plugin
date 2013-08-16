package org.bettergenteam.bettergen.layervanilla;

public class GenLayerRiverInit extends GenLayer {

    public GenLayerRiverInit(long i, GenLayer genlayer) {
        super(i);
        this.parent = genlayer;
    }

    public int[] getInts(int realX, int realZ, int width, int length) {
        int[] parentValues = this.parent.getInts(realX, realZ, width, length);
        int[] buffer = new int[width * length];

        for (int z = 0; z < length; z++) {
            for (int x = 0; x < width; x++) {
                this.initChunkSeed((long) (x + realX), (long) (z + realZ));
                buffer[x + z * width] = parentValues[x + z * width] > 0 ? this.nextInt(2) + 2 : 0;
            }
        }

        return buffer;
    }
}