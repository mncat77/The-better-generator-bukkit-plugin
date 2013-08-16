package org.bettergenteam.bettergen.blockpopulators;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;

public class PopulatorCollectionOverworld extends BlockPopulator {
    
    private final List<BlockPopulator> populators;
    
    
    public PopulatorCollectionOverworld() {
        populators = new ArrayList<BlockPopulator>();
        populators.add(new TreePopulator());
        populators.add(new SnowPopulator());
    }
    
    @Override
    public void populate(World world, Random random, Chunk chunk) {
        for(BlockPopulator populator : populators) {
            populator.populate(world, random, chunk);
        }
    }

}
