package org.bettergenteam.bettergen.biome;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.block.Biome;

public class BiomePlains implements BiomeBase {

    public Biome getBukkitBiome() {
        return Biome.PLAINS;
    }

    public Column getColumn(World world, Random random, int x, int z) {
        Column column = new Column();
        
        return column;
    }
    
}
