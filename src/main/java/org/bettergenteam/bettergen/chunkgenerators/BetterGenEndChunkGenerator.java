package org.bettergenteam.bettergen.chunkgenerators;

import java.util.Random;
import org.bukkit.World;

public class BetterGenEndChunkGenerator extends BetterGenChunkGenerator {
    
    @Override
    public byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biome) {
        byte[][] chunk = new byte[world.getMaxHeight() / 16][];
        
        return chunk;
    }
    
}