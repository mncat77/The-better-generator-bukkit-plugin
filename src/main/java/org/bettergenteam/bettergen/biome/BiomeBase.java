package org.bettergenteam.bettergen.biome;

import java.util.Random;
import org.bukkit.World;
import org.bukkit.block.Biome;

public interface BiomeBase {
    
    /**
     *
     * @return Bukkit biome
     */
    public Biome getBukkitBiome();
    
    /**
     *
     * @param world World reference
     * @param random Random instance
     * @param x X-Coordinate of the column
     * @param z Z-Coordinate of the column
     * @return The generated column
     */
    public Column getColumn(World world, Random random, int x, int z);
    
}