package org.bettergenteam.bettergen.chunkgenerators;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import org.bettergenteam.bettergen.biome.BiomeBase;
import org.bettergenteam.bettergen.biome.layer.BiomeLayer;
import org.bettergenteam.bettergen.blockpopulators.BetterBlockPopulator;
import org.bettergenteam.bettergen.noise.VoronoiNoiseGenerator;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class BetterGenOverworldChunkGenerator extends BetterGenChunkGenerator {
    
    private static final Set<Biome> spawnBiomes = new HashSet<Biome>(){{add(Biome.BEACH);}};
    private static final List<BetterBlockPopulator> blockPopulators = new ArrayList<BetterBlockPopulator>(){{/*add(new BetterTreePopulator());add(new BetterSnowPopulator());*/}};
    
    private final BiomeLayer layer;
    
    public BetterGenOverworldChunkGenerator(long seed) {
        seed = Math.round(seed*1.125-418);
        //layer = GenLayer.initializeAllBiomeGenerators(seed, 3);
        layer = BiomeLayer.getBiomeProvidingLayer(seed, 3);
        BiomeBase.simplex = new SimplexOctaveGenerator[] {new SimplexOctaveGenerator(seed, 8), new SimplexOctaveGenerator(seed+1, 8)};
        BiomeBase.voronoi = new VoronoiNoiseGenerator[]{new VoronoiNoiseGenerator(seed, (short)0), new VoronoiNoiseGenerator(seed+1, (short)0), new VoronoiNoiseGenerator(seed+2, (short)0)};
        BiomeBase.simplex[0].setScale(1.0/64.0);
        BiomeBase.simplex[1].setScale(1.0/50.0);
    }
    
    public byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biome) {
        byte[][] chunk = new byte[world.getMaxHeight() / 16][];
        long seed = world.getSeed();
        int cRX = chunkX*16;
        int cRZ = chunkZ*16;
        byte[] values = layer.getValues(cRX, cRZ, 16, 16);
        for (int x=0; x< 16; x++) {
            int realX = cRX+x;
            int m = x*16;
            for (int z=0; z<16; z++) {
                int realZ = cRZ+z;
                BiomeBase biomeBase = BiomeBase.byId[values[z+m]];
                biomeBase.generateColumn(world, rand, chunk, realX, realZ, x, z, layer);
                biome.setBiome(x, z, biomeBase.getBukkitBiome());
            }
        }
        for(int i = 0; i <  blockPopulators.size() ;i++) {
            blockPopulators.get(i).populateChunk(world, biome, seed + i, chunk, chunkX, chunkZ);
        }
        return chunk;
    }

    @Override
    public boolean canSpawn(World world, int x, int z) {
        return super.canSpawn(world, x, z) && (spawnBiomes.contains(world.getBiome(x, z)));
    }

}