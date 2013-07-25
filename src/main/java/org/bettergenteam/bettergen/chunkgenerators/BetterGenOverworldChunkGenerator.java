package org.bettergenteam.bettergen.chunkgenerators;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class BetterGenOverworldChunkGenerator extends BetterGenChunkGenerator {
    
    public byte[][] generateBlockSections(World world, Random rand, int chunkX, int chunkZ, BiomeGrid biome) {
        //where we will store our blocks
        byte[][] chunk = new byte[world.getMaxHeight() / 16][];
        
        PerlinOctaveGenerator base = new PerlinOctaveGenerator(world,10);
        base.setScale(1/128.0);
        
        PerlinOctaveGenerator tops = new PerlinOctaveGenerator(world,2);
        tops.setScale(1/32.0);
        
        SimplexOctaveGenerator gen3d = new SimplexOctaveGenerator(world,8);
        gen3d.setScale(1/64.0);
        
        for (int x=0; x< 16; x++) {
            for (int z=0; z<16; z++) {
                int realX = x + chunkX * 16;
                int realZ = z + chunkZ * 16;
                
                int height = (int) (base.noise(realX, realZ, 0.5, 0.5)*64) + 64;
                
                for (int y=1; y <= height && y < 256; y++) {
                    setBlock(x,y,z,chunk,Material.STONE);
                }
                
                int top = (int) (tops.noise(realX, realZ, 0.5, 0.5)*16) + height + 32;
                
                for (int y=height; y < top && y < 256; y++) {
                    double density = gen3d.noise(realX, y, realZ, 0.5, 0.5) + (1/(y-height+1));
                    if (density > 0.5) {
                        setBlock(x,y,z,chunk,Material.GRASS);
                    }
                }
            }
        }
        return chunk;
    }
    
}