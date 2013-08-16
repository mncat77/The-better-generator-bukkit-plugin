package org.bettergenteam.bettergen.layer;

public abstract class GenLayer {
    
    protected GenLayer parent;
    
    private long worldGenSeed;
    private long chunkSeed;
    private long baseSeed;
    
    
    public static GenLayer initializeAllBiomeGenerators(long seed, int biomeSize) {
        long seed1 = seed + 1;
        long seed2 = seed1 + 1;
        long seed3 = seed - 1;
        GenLayer layer1 = new GenLayerRNGBase(seed);
        layer1 = new GenLayerBaseZoom(seed, layer1);
        layer1 = new GenLayerAddBlocksToIslands(seed, layer1);
        layer1 = new GenLayerZoom(seed, layer1);
        layer1 = new GenLayerAddBlocksToIslands(seed1, layer1);
        layer1 = new GenLayerIcePlains(seed, layer1);
        layer1 = new GenLayerZoom(seed1, layer1);
        layer1 = new GenLayerAddBlocksToIslands(seed2, layer1);
        layer1 = new GenLayerZoom(seed2, layer1);
        layer1 = new GenLayerAddBlocksToIslands(seed3, layer1);
        layer1 = new GenLayerMushroomInit(seed1, layer1);
        layer1 = GenLayerZoom.magnify(seed3, layer1, 0);
        
        GenLayer layer2 = new GenLayerMarkBiomeEdges(seed, layer1);
        layer2 = GenLayerZoom.magnify(seed3, layer2, biomeSize);
        layer2 = new GenLayerRiver(seed, layer2);
        //layer2 = GenLayerZoom.magnify(seed3, layer2, 1);
        layer2 = new GenLayerSmooth(seed, layer2);
        
        layer1 = new GenLayerBiome(seed, layer1);
        layer1 = GenLayerZoom.magnify(seed3, layer1, 2);
        layer1 = new GenLayerHills(1000L, layer1);
        
        for (int size = 0; size < biomeSize; size++) {
            layer1 = new GenLayerZoom((long) (1000 + size), layer1);
            if (size == 0) {
                layer1 = new GenLayerAddBlocksToIslands(seed3-1, layer1);
            }
            else if (size == 1) {
                layer1 = new GenLayerShore(seed, layer1);
                layer1 = new GenLayerSwampJungleLakes(seed, layer1);
            }
        }
        layer1 = new GenLayerSmooth(seed1, layer1);
        
        layer1 = new GenLayerRiverMix(seed3, layer1, layer2);
        layer1 = GenLayerZoom.magnify(seed3-4, layer1, 2);
        layer1 = new GenLayerSmooth(seed2, layer1);
        //layer1 = new GenLayerSmooth(seed2, layer1);
        //layer1 = new GenLayerSmooth(seed3, layer1);
        //layer1.initWorldGenSeed(seed);
        
        return layer1;
    }
    
    public GenLayer(long seed) {
        this.baseSeed = seed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += seed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += seed;
        this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
        this.baseSeed += seed;
    }
    
    public void initWorldGenSeed(long seed) {
        this.worldGenSeed = seed;
        
        if (this.parent != null) {
            this.parent.initWorldGenSeed(seed);
        }
        
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
        this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
        this.worldGenSeed += this.baseSeed;
    }
    
    public void initChunkSeed(long chunkX, long chunkZ) {
        this.chunkSeed = this.worldGenSeed;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += chunkX;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += chunkZ;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += chunkX;
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += chunkZ;
    }
    
    protected int nextInt(int max) {
        int var2 = (int)((this.chunkSeed >> 24) % (long)max);
        
        if (var2 < 0) {
            var2 += max;
        }
        
        this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
        this.chunkSeed += this.worldGenSeed;
        return var2;
    }
    
    public abstract int[] getValues(int realX, int realZ, int width, int length);

}
