package org.bettergenteam.bettergen.biome;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.bettergenteam.bettergen.layer.GenLayer;
import org.bettergenteam.bettergen.noise.VoronoiNoiseGenerator;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public abstract class BiomeBase {
    
    public static final BiomeBase OCEAN = new BiomeOcean(0);
    public static final BiomeBase PLAINS = new BiomePlains(1);
    public static final BiomeBase DESERT = new BiomeDesert(2);
    public static final BiomeBase EXTREME_HILLS = new BiomeExtremeHills(3);
    public static final BiomeBase FOREST = new BiomeForest(4);
    public static final BiomeBase TAIGA = new BiomeTaiga(5);
    public static final BiomeBase SWAMPLAND = new BiomeSwamp(6);
    public static final BiomeBase RIVER = new BiomeRiver(7);
    //Meh
    public static final BiomeBase HELL = new BiomePlains(8);
    //Same
    public static final BiomeBase SKY = new BiomePlains(9);
    public static final BiomeBase FROZEN_OCEAN = new BiomeFrozenOcean(10);
    public static final BiomeBase FROZEN_RIVER = new BiomeFrozenRiver(11);
    public static final BiomeBase ICE_PLAINS = new BiomeIcePlains(12);
    public static final BiomeBase ICE_MOUNTAINS = new BiomeIceMountains(13);
    public static final BiomeBase MUSHROOM_ISLAND = new BiomeMushroomIsland(14);
    public static final BiomeBase MUSHROOM_SHORE = new BiomeMushroomShore(15);
    public static final BiomeBase BEACH = new BiomeBeach(16);
    public static final BiomeBase DESERT_HILLS = new BiomeDesertHills(17);
    public static final BiomeBase FOREST_HILLS = new BiomeForestHills(18);
    public static final BiomeBase TAIGA_HILLS = new BiomeTaigaHills(19);
    //Extreme Hills' edge
    public static final BiomeBase SMALL_MOUNTAINS = new BiomeExtremeHillsEdge(20);
    public static final BiomeBase JUNGLE = new BiomeJungle(21);
    public static final BiomeBase JUNGLE_HILLS = new BiomeJungleHills(22);
    
    public static final BiomeBase SAVANNA = new BiomeSavanna(23);
    public static final BiomeBase WASTELAND = new BiomeSwamp(24);

    //Still have to tweak the rarity of those 2
    public static final BiomeBase OASIS = new BiomeDesert(25);
    public static final BiomeBase VOLCANO = new BiomeDesertHills(26);
    
    //Not sure about that one either
    public static final BiomeBase GRAVEL_BEACH = new BiomeGravelBeach(27);
    
    public static final BiomeBase LAKE = new BiomeLake(28);
    
    public static final BiomeBase[] byId = {OCEAN, PLAINS, DESERT, EXTREME_HILLS, FOREST, TAIGA, SWAMPLAND, RIVER, HELL, SKY, FROZEN_OCEAN, FROZEN_RIVER, ICE_PLAINS, ICE_MOUNTAINS
    , MUSHROOM_ISLAND, MUSHROOM_SHORE, BEACH, DESERT_HILLS, FOREST_HILLS, TAIGA_HILLS, SMALL_MOUNTAINS, JUNGLE, JUNGLE_HILLS, SAVANNA, WASTELAND, OASIS, VOLCANO, GRAVEL_BEACH, LAKE};
    
    public final int id;
    
    public static final int WATER_LEVEL = 52;
    public static final double FREQ = .02;
    public static final double AMP = .9;
    public static final double FREQ_O = .01;
    public static final double AMP_O = 1;
    
    public static SimplexOctaveGenerator[] simplex;
    public static VoronoiNoiseGenerator[] voronoi;
    
    public static final Set<Integer> fluids = new HashSet<Integer>(){{add(0);add(7);add(10);add(11);add(28);}};
    public static final Set<Integer> shores = new HashSet<Integer>(){{add(15);add(16);add(27);}};
    public static final Set<Integer> plains = new HashSet<Integer>(){{add(1);add(2);add(4);add(5);add(6);add(12);add(14);add(21);add(23);add(24);add(25);add(26);}};
    public static final Set<Integer> hills = new HashSet<Integer>(){{add(3);add(13);add(17);add(18);add(19);add(20);add(22);add(26);}};
    
    public static final Set<Integer> aboveFluids = new HashSet<Integer>(){{addAll(shores);addAll(plains);addAll(hills);}};
    public static final Set<Integer> belowHills = new HashSet<Integer>(){{addAll(fluids);addAll(shores);addAll(plains);}};
    
    public static final Set<Integer> sandy = new HashSet<Integer>(){{add(16);add(2);add(25);add(26);add(17);}};
    
    
    public BiomeBase(int id) {
        this.id = id;
    }
    
    public abstract Biome getBukkitBiome();
    
    public abstract int getMaxY(World world, Random random, int realX, int realZ, GenLayer layer);
        //int rX2 = dw.getRealX();
        //int rZ2 = dw.getRealZ();
        //int rMaxY = BiomeBase.byId[dw.getValue()].getMaxY(world, random, rX2, rZ2, layer);
    
    public abstract void generateColumn(World world, Random random, byte[][] chunk, int realX, int realZ, int x, int z, GenLayer layer);
    
    public static void setBlock(int x, int y, int z, byte[][] chunk, org.bukkit.Material material) {
        if (chunk[y >> 4] == null) {
            chunk[y >> 4] = new byte[16 * 16 * 16];
        }
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return;
        }
        try {
            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) material.getId();
        } catch (Exception e) {
        }
    }
    
    public static void setBlock(int x, int y, int z, byte[][] chunk, int i) {
        if (chunk[y >> 4] == null) {
            chunk[y >> 4] = new byte[16 * 16 * 16];
        }
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return;
        }
        try {
            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte) i;
        } catch (Exception e) {
        }
    }
    
    public static byte getBlock(int x, int y, int z, byte[][] chunk) {
        if (chunk[y >> 4] == null) {
            return 0;
        }
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return 0;
        }
        try {
            return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
        } catch (Exception e) {
            return 0;
        }
    }
    
    public double convertValue(double d) {
        return d / 2.0D + .5D;
    }
    
    
    public static DistanceLocationWrapper getDistanceFactorBiome(int biomeId, int centerX, int centerZ, int range, GenLayer layer) {
        int wl = 2*range+1;
        int[] values = layer.getValues(centerX - range, centerZ - range, wl, wl);
        int realX = 0;
        int realZ = 0;
        int v = -1;
        double minDist = Integer.MAX_VALUE;
        for(int i = 0; i < values.length ;i++) {
            if(values[i] == biomeId) {
                int x = i % wl;
                int z = (i-x)/wl;
                int xD = range - x;
                int zD = range-  z;
                int newDist = xD*xD+zD*zD;
                if(newDist < minDist) {
                    v = values[i];
                    realX = centerX + x;
                    realZ = centerZ + z;
                    minDist = newDist;
                }
            }
        }
        double d = Math.sqrt(minDist);
        d = d > range? -1 : d;
        double f = d/(double)range;
        f = f > 1? -1 : f;
        return new DistanceLocationWrapper(f, d, realX, realZ, v);
    }
    
    public static DistanceLocationWrapper getDistanceFactorBiome(Set<Integer> biomeIds, int centerX, int centerZ, int range, GenLayer layer) {
        int wl = 2*range+1;
        int[] values = layer.getValues(centerX - range, centerZ - range, wl, wl);
        double minDist = range*range+1;
        int realX = 0;
        int realZ = 0;
        int v = -1;
        double wlh = wl/2.0;
        for(int i = 0; i < values.length; i++) {
            if(biomeIds.contains(values[i])) {
                int x = i % wl;
                int z = (i-x)/wl;
                double xD = wlh - x;
                double zD = wlh - z;
                double newDist = xD*xD+zD*zD;
                if(newDist < minDist) {
                    v = values[i];
                    realX = centerX + x;
                    realZ = centerZ + z;
                    minDist = newDist;
                }
            }
        }
        double d = Math.sqrt(minDist);
        d = d > range? -1 : d;
        double f = d/(double)range;
        f = f > 1? -1 : f;
        return new DistanceLocationWrapper(f, d, realX, realZ, v);
    }
    
}