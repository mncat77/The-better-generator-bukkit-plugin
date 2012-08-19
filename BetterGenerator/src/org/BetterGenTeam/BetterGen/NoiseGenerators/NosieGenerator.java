package org.BetterGenTeam.BetterGen.NoiseGenerators;

/**
 * 
 * @author jtjj222
 * use this to maintain compatibility with bukkit's noise generators
 *
 */
//TODO
abstract public interface NosieGenerator {

	public double getNoise(int x, double frequency, double amplitude);
	public double getNoise(int x, int z, double frequency, double amplitude);
	public double getNoise(int x, int y, int z, double frequency, double amplitude);

	public void setScale(double scale);
	public void setXScale(double scale);
	public void setYScale(double scale);
	public void setZScale(double scale);
	
}
