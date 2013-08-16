package org.bettergenteam.bettergen.chunkgenerators;

import java.util.Random;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;

public class BetterGenEndChunkGenerator extends BetterGenChunkGenerator {
    
    @Override
    public byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biome) {
        long seed = world.getSeed();
        byte[][] chunk = new byte[world.getMaxHeight() / 16][];
        for(int x = 0; x < 16; x++) {
            for(int z = 0; z < 16; z++) {
                int realX = chunkX * 16 + x;
                int realZ = chunkZ * 16 + z;
                biome.setBiome(x, z, Biome.SKY);
                for(int y = 0; y < 256; y++) {
                    if(getDistanceFromCenter(realX, y, realZ, seed) < 4000) {
                        setBlock(x, y, z, chunk, Material.ENDER_STONE);
                    }
                }
            }
        }
        return chunk;
    }
    
    private int getDistanceFromCenter(int x, int y, int z, long seed) {
        Random rand = new Random(seed);
        int xDist = rand.nextInt(1023) - 512 - x;
        int yDist = rand.nextInt(20)+60 - y;
        int zDist = rand.nextInt(1023) - 512 - z;
        yDist *= 1.35;
        if(seed % 2 == 0) {
            xDist *= .5;
        }
        else {
            zDist *= .5;
        }
        return xDist*xDist+yDist*yDist+zDist*zDist;
    }
    
}