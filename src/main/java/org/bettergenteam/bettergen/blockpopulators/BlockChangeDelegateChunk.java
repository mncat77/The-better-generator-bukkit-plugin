package org.bettergenteam.bettergen.blockpopulators;

import org.bukkit.BlockChangeDelegate;

public class BlockChangeDelegateChunk implements BlockChangeDelegate {
    
    private byte[][] chunk;
    
    
    public BlockChangeDelegateChunk(byte[][] chunk) {
        this.chunk = chunk;
    }
    
    public boolean setRawTypeId(int i, int i1, int i2, int i3) {
        this.setBlock(i, i1, i2, i3);
        return true;
    }
    
    public boolean setRawTypeIdAndData(int i, int i1, int i2, int i3, int i4) {
        return setRawTypeId(i, i1, i2, i3);
    }
    
    public boolean setTypeId(int i, int i1, int i2, int i3) {
        return setRawTypeId(i, i1, i2, i3);
    }
    
    public boolean setTypeIdAndData(int i, int i1, int i2, int i3, int i4) {
        return setRawTypeId(i, i1, i2, i3);
    }
    
    public int getTypeId(int i, int i1, int i2) {
        return getBlock(i, i1, i2);
    }
    
    public int getHeight() {
        return 256;
    }
    
    public boolean isEmpty(int i, int i1, int i2) {
        return getBlock(i, i1, i2) == 0;
    }
    
    public void setBlock(int x, int y, int z, int id) {
        //if the Block section the block is in hasn't been used yet, allocate it
        if (chunk[y >> 4] == null) {
            chunk[y >> 4] = new byte[16 * 16 * 16];
        }
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return;
        }
        try {
            chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x] = (byte)id;
        } catch (Exception e) {
            // do nothing
        }
    }
    
    public byte getBlock(int x, int y, int z) {
        //if the Block section the block is in hasn't been used yet, allocate it
        if (chunk[y >> 4] == null) {
            return 0;
        } //block is air as it hasnt been allocated
        if (!(y <= 256 && y >= 0 && x <= 16 && x >= 0 && z <= 16 && z >= 0)) {
            return 0;
        }
        try {
            return chunk[y >> 4][((y & 0xF) << 8) | (z << 4) | x];
        } catch (Exception e) {
            return 0;
        }
    }
    
    public byte[][] getChunk() {
        return chunk;
    }

}
