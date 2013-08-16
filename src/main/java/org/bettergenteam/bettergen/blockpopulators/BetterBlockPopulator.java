package org.bettergenteam.bettergen.blockpopulators;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public abstract class BetterBlockPopulator {
    
    public static void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
        try{
            //if the Block section the block is in hasn't been used yet, allocate it
            if (chunk[y >> 4] == null) {
                chunk[y >> 4] = new byte[16 * 16 * 16];
            }
            if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
                return;
            }
            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material.getId();
        }
        catch (Exception e) {}
    }
    
    public static byte getBlock(int x, int y, int z, byte[][] chunk) {
        try{//if the Block section the block is in hasn't been used yet, allocate it
            if (chunk[y >> 4] == null) {
                return 0;
            } //block is air as it hasnt been allocated
            if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
                return 0;
            }
            return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
        } catch (Exception e) {
            return 0;
        }
    }
    
    public abstract void populateChunk(World world, BiomeGrid biome, long seed, byte[][] chunk, int chunkX, int chunkZ);
    
    public static int getMaxYAt(byte[][] chunk, int x, int z) {
        for(int y = 256; y >= 0; y++) {
            if(getBlock(x, y, z, chunk) == 0) {
                return y+1;
            }
        }
        return 0;
    }

}
