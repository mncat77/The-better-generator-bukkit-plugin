package org.bettergenteam.bettergen.biome;

public class DistanceLocationWrapper {
    private final double factor;
    private final double distance;
    private final int realX;
    private final int realZ;
    private final int value;
    
    public DistanceLocationWrapper(double factor, double distance, int realX, int realZ, int v) {
        this.factor = factor;
        this.distance = distance;
        this.realX = realX;
        this.realZ = realZ;
        this.value = v;
    }
    
    public double getDistance() {
        return distance;
    }
    
    public double getFactor() {
        return factor;
    }
    
    public int getRealX() {
        return realX;
    }
    
    public int getRealZ() {
        return realZ;
    }

    public int getValue() {
        return value;
    }
    
}