package org.bettergenteam.bettergen.blockpopulators;

import java.util.HashSet;
import java.util.Set;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;


public class BetterSnowPopulator extends BetterBlockPopulator {
    
    private static final Set<Biome> coldBiomes = new HashSet<Biome>(){{add(Biome.ICE_MOUNTAINS);add(Biome.ICE_PLAINS);add(Biome.TAIGA);add(Biome.TAIGA_HILLS);add(Biome.FROZEN_RIVER);add(Biome.FROZEN_OCEAN);}};

    @Override
    public void populateChunk(World world, BiomeGrid biome, long seed, byte[][] chunk, int chunkX, int chunkZ) {
        for(int x=0; x < 16; x++) {
            for(int z=0; z < 16; z++) {
                if(coldBiomes.contains(biome.getBiome(x, z))) {
                    int maxY = getMaxYAt(chunk, x, z);
                    if(getBlock(x, maxY-1, z, chunk) != 79) {
                        setBlock(x, maxY, z, chunk, Material.SNOW);
                    }
                }
            }
        }
    }
}
