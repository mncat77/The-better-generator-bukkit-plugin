package org.bettergenteam.bettergen.biome;

import org.bukkit.World;

public class OverworldBiomeProvider implements BiomeProvider {

    public BiomeBase getBiomeAt(World world, int realX, int realZ) {
        return new BiomePlains();
    }
    
}
