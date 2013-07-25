package org.bettergenteam.bettergen.biome;

//For efficiency sake we should pass the whole byte[][] (chunk) and not just columns....
public class Column {
    
    private byte[] materials;
    
    
    /**
     *
     */
    public Column() {
        this.materials = new byte[256];
    }
    
    /**
     *
     * @param materials
     */
    public Column(byte[] materials) {
        this.materials = materials;
    }
    
    /**
     *
     * @param y The y-coordinate
     * @return The material
     */
    public byte getMaterial(int y) {
        return this.materials[y];
    }
    
    /**
     *
     * @return
     */
    public byte[] getMaterials() {
        return materials;
    }
    
    /**
     *
     * @param y The y-coordinate
     * @param m The material
     */
    public void setMaterial(int y, byte m) {
        this.materials[y] = m;
    }
    
    /**
     *
     * @param materials
     */
    public void setMaterials(byte[] materials) {
        this.materials = materials;
    }
    
}
