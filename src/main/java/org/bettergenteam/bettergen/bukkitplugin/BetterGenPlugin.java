package org.bettergenteam.bettergen.bukkitplugin;

import org.bettergenteam.bettergen.chunkgenerators.BetterGenEndChunkGenerator;
import org.bettergenteam.bettergen.chunkgenerators.BetterGenNetherChunkGenerator;
import org.bettergenteam.bettergen.chunkgenerators.BetterGenOverworldChunkGenerator;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterGenPlugin extends JavaPlugin{
    
    /**
     *
     * @param worldName
     * The name of the world the generator is being applied to
     * @param GenId
     * The id (if any) specified by the user. It can be used if the plugin
     * wants to have multiple generators in one plugin. More on this later.
     * @return
     * The ChunkGenerator that this plugin provides
     */
    @Override
    public ChunkGenerator getDefaultWorldGenerator(String worldName, String genId) {
        long seed = System.currentTimeMillis();
        if(genId == null) {
            return new BetterGenOverworldChunkGenerator(seed);
        }
        if(genId.equalsIgnoreCase("Nether") || genId.equalsIgnoreCase("Underworld")) {
            return new BetterGenNetherChunkGenerator();
        }
        else if(genId.equalsIgnoreCase("End") || genId.equalsIgnoreCase("The_End")) {
            return new BetterGenEndChunkGenerator();
        }
        else {
            return new BetterGenOverworldChunkGenerator(seed);
        }
    }
    
}
