package org.bettergenteam.bettergen.biome.layer;

public abstract class BiomeLayer {
    
    //Rarity: 1 in n
    //Frequency: (n-1) in n
    public static final int OCEAN_FREQUENCY = 6;
    public static final int VOLCANO_RARITY = 20;
    public static final int MUSHROOM_ISLAND_RARITY = 95;
    public static final int GRAVEL_BEACH_RARITY = 20;
    
    
    protected BiomeLayer parent;
    
    protected long baseSeed;
    
    
    public BiomeLayer(long baseSeed) {
        this.baseSeed = baseSeed;
    }
    
    protected boolean hasParent() {
        return this.parent != null;
    }
    
    protected BiomeLayer getParent() {
        return this.parent;
    }
    
    public static BiomeLayer getBiomeProvidingLayer(long seed, int biomeSize) {
        BiomeLayer layer = new BiomeLayerTerrainBase(seed);
        layer = new BiomeLayerFormIslands(seed, layer);
        layer = new BiomeLayerRoughBiomes(seed, layer);
        
        return layer;
    }
    
    public abstract byte[] getValues(int realX, int realZ, int width, int length);
    
    protected byte nextByte(int max, int realX, int realZ) {
        long s = baseSeed * realX + 295123517634183L;
        s *= realZ + 9612482347241007L;
        s *= realX + 9612482347241007L;
        s *= realZ + 295123517634183L;
        return (byte)Math.abs(s % (max + 1));
    }

}
