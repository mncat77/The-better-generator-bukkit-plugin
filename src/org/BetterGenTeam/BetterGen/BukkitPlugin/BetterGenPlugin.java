package org.BetterGenTeam.BetterGen.BukkitPlugin;

import org.BetterGenTeam.BetterGen.ChunkGenerators.BetterGenChunkGenerator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class BetterGenPlugin extends JavaPlugin{

	public void onEnable() {
		
	}
	
	public void onDisable() {
		
	}
	
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
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String GenId) {
		//TODO
		return new BetterGenChunkGenerator(); //just to test
	}
	
}
