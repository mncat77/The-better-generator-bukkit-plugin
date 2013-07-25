package org.bettergenteam.bettergen.biome;

import org.bukkit.World;

public interface BiomeProvider {
    
    /**
     *
     * @param world The assinged world
     * @param realX The x-coordinate of the column
     * @param realZ The z-coordinate of the column
     * @return The biome instance
     */
    public BiomeBase getBiomeAt(World world, int realX, int realZ);
    
}
