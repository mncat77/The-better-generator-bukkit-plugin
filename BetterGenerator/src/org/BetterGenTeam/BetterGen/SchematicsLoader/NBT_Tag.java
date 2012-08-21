package org.BetterGenTeam.BetterGen.SchematicsLoader;

/**
 * 
 * @author jtjj222
 * 
 * http://www.minecraftwiki.net/wiki/NBT_Format
 * Used for reading scematics files
 * 
 * This class represents one tag (or data entry)
 * This class is useless by itself, 
 * just a base to work from when
 * writing the support classes for
 * the types of tags.
 *
 */
public class NBT_Tag {

	byte ID;
	
	String name;
	
	public NBT_Tag(int i) {
		this.ID = (byte) i;
	}
	public byte getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setID(byte id) {
		this.ID = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
}
