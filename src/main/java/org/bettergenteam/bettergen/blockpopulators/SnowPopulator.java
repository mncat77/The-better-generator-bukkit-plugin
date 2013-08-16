package org.bettergenteam.bettergen.blockpopulators;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.generator.BlockPopulator;

public class SnowPopulator extends BlockPopulator {
    
    private static final Set<Biome> coldBiomes = new HashSet<Biome>(){{add(Biome.ICE_MOUNTAINS);add(Biome.ICE_PLAINS);add(Biome.TAIGA);add(Biome.TAIGA_HILLS);add(Biome.FROZEN_RIVER);add(Biome.FROZEN_OCEAN);}};
    
    @Override
    public void populate(World world, Random random, Chunk source) {
        int startX = source.getX() << 4;
        int startZ = source.getZ() << 4;
        for(int x=0; x < 16; x++) {
            int rX = startX + x;
            for(int z=0; z < 16; z++) {
                int rZ = startZ + z;
                if(coldBiomes.contains(world.getBiome(rX, rZ))) {
                    Block block = new Location(world, rX, world.getHighestBlockYAt(rX, rZ), rZ).getBlock();
                    if(block.getRelative(BlockFace.DOWN).getType() != Material.ICE) {
                        block.setType(Material.SNOW);
                    }
                }
            }
        }
    }

}
