package org.bettergenteam.bettergen.chunkgenerators;

import java.util.Random;
import org.bettergenteam.bettergen.biome.BiomeProvider;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public abstract class BetterGenChunkGenerator extends ChunkGenerator {
    
    public BiomeProvider biomeProvider;
    
    public static void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
        //if the Block section the block is in hasn't been used yet, allocate it
        if (chunk[y >> 4] == null) {
            chunk[y >> 4] = new byte[16 * 16 * 16];
        }
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return;
        }
        try {
            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material.getId();
        } catch (Exception e) {
            // do nothing
        }
    }
    
    public static byte getBlock(int x, int y, int z, byte[][] chunk) {
        //if the Block section the block is in hasn't been used yet, allocate it
        if (chunk[y >> 4] == null) {
            return 0;
        } //block is air as it hasnt been allocated
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return 0;
        }
        try {
            return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    @Override
    public abstract byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biome);
    
}
