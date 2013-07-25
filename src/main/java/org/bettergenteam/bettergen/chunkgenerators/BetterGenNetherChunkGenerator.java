package org.bettergenteam.bettergen.chunkgenerators;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class BetterGenNetherChunkGenerator extends BetterGenChunkGenerator {
    
    @Override
    public byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, ChunkGenerator.BiomeGrid biome) {
        byte[][] chunk = new byte[world.getMaxHeight() / 16][];
        
        return chunk;
    }
    
}