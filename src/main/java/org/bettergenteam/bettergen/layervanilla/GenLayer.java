package org.bettergenteam.bettergen.layervanilla;

public abstract class GenLayer {
    
    protected GenLayer parent;
    
    private long worldGenSeed;
    private long chunkSeed;
    private long baseSeed;
    
    
    public static GenLayer[] initializeAllBiomeGenerators(long seed, boolean largeBiomes) {
        //Seems to be pretty much just a RNG with a 1 in 11 chance for 1, else 0 and one special 1
        LayerIsland layerIsland = new LayerIsland(1L);
        
        //Zooms in previous layer creating lots of little islands
        GenLayerZoomFuzzy genLayerZoomFuzzy = new GenLayerZoomFuzzy(2000L, layerIsland);
        
        //Adding more blocks to Islands
        GenLayerIsland genLayerIsland = new GenLayerIsland(1L, genLayerZoomFuzzy);
        
        //Zooms in again
        GenLayerZoom genLayerZoom = new GenLayerZoom(2001L, genLayerIsland);
        
        //Adding more blocks to Islands
        genLayerIsland = new GenLayerIsland(2L, genLayerZoom);
        
        //Adds IcePlains Biome
        GenLayerIcePlains genLayerIcePlains = new GenLayerIcePlains(2L, genLayerIsland);
        
        //Zooms in again
        genLayerZoom = new GenLayerZoom(2002L, genLayerIcePlains);
        
        //Adds more blocks to Islands; replaces IcePlains biome near the water with FrozenOcean
        genLayerIsland = new GenLayerIsland(3L, genLayerZoom);
        
        //Another zoom
        genLayerZoom = new GenLayerZoom(2003L, genLayerIsland);
        
        //Adds more blocks to Islands
        genLayerIsland = new GenLayerIsland(4L, genLayerZoom);
        
        //Adds (lots of) spots for Mushroom Islands
        GenLayerMushroomIsland genLayerMushroomIsland = new GenLayerMushroomIsland(5L, genLayerIsland);
        byte biomeSize = 4;
        
        if (largeBiomes) {
            biomeSize = 6;
        }
        
        //---------------------------------------
        
        //Zooms in again
        GenLayer zoomed = GenLayerZoom.magnify(1000L, genLayerMushroomIsland, 0);
        
        //Marks biome edges for rivers
        GenLayerRiverInit genLayerRiverInit = new GenLayerRiverInit(100L, zoomed);
        
        //Zooms in reall far
        zoomed = GenLayerZoom.magnify(1000L, genLayerRiverInit, biomeSize + 2);
        
        //Divides everthing into Ocean/River/Land
        GenLayerRiver genLayerRiver = new GenLayerRiver(1L, zoomed);
        
        //Smoothes out edges
        GenLayerSmooth genLayerSmooth = new GenLayerSmooth(1000L, genLayerRiver);
        
        //-----------------------------------------
        
        //Back to older layer, zooms in
        GenLayer zoomed2 = GenLayerZoom.magnify(1000L, genLayerMushroomIsland, 0);
        
        //Adds all the other biomes
        GenLayerBiome genLayerBiome = new GenLayerBiome(200L, zoomed2);
        
        zoomed2 = GenLayerZoom.magnify(1000L, genLayerBiome, 2);
        
        //Adds the sub hill biomes like DesertHills, ForestHills, ...
        GenLayer layer = new GenLayerRegionHills(1000L, zoomed2);
        
        for (int size = 0; size < biomeSize; ++size) {
            //Zooms in
            layer = new GenLayerZoom((long) (1000 + size), (GenLayer) layer);
            if (size == 0) {
                //Adds more blocks to islands
                layer = new GenLayerIsland(3L, (GenLayer) layer);
            }
            
            if (size == 1) {
                //Choses very few of the many MushroomIsland spots to actually become a mushroom island
                layer = new GenLayerMushroomShore(1000L, (GenLayer) layer);
            }
            
            if (size == 1) {
                //Adds swamp and jungle rivers
                layer = new GenLayerSwampRivers(1000L, (GenLayer) layer);
            }
        }
        
        
        //These are the 3 output layers:
        
        //Smooth everything out a last time
        GenLayerSmooth genlayersmooth2 = new GenLayerSmooth(1000L, layer);
        
        //---------------------------------------
        
        //Add the rivers created a while ago to the layer with the biomes
        GenLayerRiverMix genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth2, genLayerSmooth);
        
        //----------------------------------------
        
        //Zoom in, not exactly sure what this does little confusing
        GenLayerZoomVoronoi genlayerzoomvoronoi = new GenLayerZoomVoronoi(10L, genlayerrivermix);
        
        genlayerrivermix.initWorldGenSeed(seed);
        genlayerzoomvoronoi.initWorldGenSeed(seed);
        
        return new GenLayer[] {genlayerrivermix, genlayerzoomvoronoi, genlayerrivermix};
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
    
    public abstract int[] getInts(int realX, int realZ, int width, int length);

}
