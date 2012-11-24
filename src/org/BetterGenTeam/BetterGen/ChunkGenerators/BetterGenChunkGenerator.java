package org.BetterGenTeam.BetterGen.ChunkGenerators;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.util.noise.PerlinOctaveGenerator;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class BetterGenChunkGenerator extends ChunkGenerator{

	void setBlock(int x, int y, int z, byte[][] chunk, Material material) {
		//if the Block section the block is in hasn't been used yet, allocate it
		if (chunk[y >> 4] == null)
			chunk[y >> 4] = new byte[16 * 16 * 16];
		if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
			return;
		try {
			chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material
					.getId();
		} catch (Exception e) {
			// do nothing
		}
	}
	
	byte getBlock(int x, int y, int z, byte[][] chunk) {
		//if the Block section the block is in hasn't been used yet, allocate it
		if (chunk[y >> 4] == null)
			return 0; //block is air as it hasnt been allocated
		if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0))
			return 0;
		try {
			return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	@Override
	public byte[][] generateBlockSections(World world, Random rand, int ChunkX,
			int ChunkZ, BiomeGrid biome) {
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
				int realX = x + ChunkX * 16;
				int realZ = z + ChunkZ * 16;

				int height = (int) (base.noise(realX, realZ, 0.5, 0.5)*64) + 64;
				
				for (int y=1; y <= height && y < 256; y++) {
					setBlock(x,y,z,chunk,Material.STONE);
				}
				
				int top = (int) (tops.noise(realX, realZ, 0.5, 0.5)*16) + height + 32;
				
				for (int y=height; y < top && y < 256; y++) {
					double density = gen3d.noise(realX, y, realZ, 0.5, 0.5) + (1/(y-height+1));
					if (density > 0.5) setBlock(x,y,z,chunk,Material.GRASS);
				}
			}
		}		
		return chunk;
	}
	
}
