package org.bettergenteam.bettergen.layer;

public class GenLayerCache extends GenLayer {
    
    private byte[][] cache = new byte[60000000][60000000];
    
    public GenLayerCache(GenLayer parent) {
        super(0);
        this.parent = parent;
    }
    
    @Override
    public int[] getValues(int realX, int realZ, int width, int length) {
        realX += 30000000;
        realZ += 30000000;
        int[] values = new int[width*length];
        for(int z = 0; z < width; z++) {
            int s = z*width;
            int r = realZ+z;
            for(int x = 0; x < width; x++) {
                int p = realX+x;
                int v = cache[p][r];
                if(v == 0) {
                    v = this.parent.getValues(p, r, 1, 1)[0];
                    values[x+s] = v;
                    cache[p][r] = (byte)(v == 0? 50 : v);
                }
                else {
                    values[x+s] = v == 50? 0 : v;
                }
            }
        }
        return values;
    }

}
