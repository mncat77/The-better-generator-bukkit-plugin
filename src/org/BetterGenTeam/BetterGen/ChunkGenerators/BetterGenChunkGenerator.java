package org.BetterGenTeam.BetterGen.ChunkGenerators;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.generator.ChunkGenerator;
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
	
	private ArrayList<SimplexOctaveGenerator> getGenerators(Biome biome, long seed) {
		
		ArrayList<SimplexOctaveGenerator> gens = new ArrayList<SimplexOctaveGenerator>();
		
		gens.add(new SimplexOctaveGenerator(seed, 16));
		gens.get(0).setScale(1/256.0);
		
		gens.add(new SimplexOctaveGenerator(seed, 8));
		gens.get(1).setScale(1/128.0);
		
		gens.add(new SimplexOctaveGenerator(seed, 4));
		gens.get(2).setScale(1/64.0);
		
		gens.add(new SimplexOctaveGenerator(seed, 2));
		gens.get(3).setScale(1/32.0);
		
		gens.add(new SimplexOctaveGenerator(seed, 2));
		gens.get(4).setScale(1/16.0);
		
		return gens;
	}
	
	private int getHeight(ArrayList<SimplexOctaveGenerator> gens, int x, int z) {
		double sumOf2dNoises = 0;
		//loop through all of the generators
		for (SimplexOctaveGenerator gen : gens) {
			sumOf2dNoises += gen.noise(x, z, 0.5, 0.5);
		}
		
		sumOf2dNoises = sumOf2dNoises * 2 + 64;
		return (int) sumOf2dNoises;
	}

	@Override
	public byte[][] generateBlockSections(World world, Random rand, int ChunkX,
			int ChunkZ, BiomeGrid biome) {
		//where we will store our blocks
		byte[][] chunk = new byte[world.getMaxHeight() / 16][];
		
		ArrayList<SimplexOctaveGenerator> gens = getGenerators(null,world.getSeed());
		
		SimplexOctaveGenerator gen3d = new SimplexOctaveGenerator(world,8);
		gen3d.setXScale(1/24.0);
		gen3d.setZScale(1/24.0);
		gen3d.setYScale(1/32.0);
		gen3d.setWScale(1/32.0);
		
		for (int x=0; x< 16; x++) {
			for (int z=0; z<16; z++) {
				int realX = x + ChunkX * 16;
				int realZ = z + ChunkZ * 16;

				int height = getHeight(gens, realX, realZ);
				
				for (int y=1; y < height && y < 256; y++) {
					setBlock(x,y,z,chunk,Material.STONE);
				}				
			}
		}
		
		return chunk;
	}
	
}
