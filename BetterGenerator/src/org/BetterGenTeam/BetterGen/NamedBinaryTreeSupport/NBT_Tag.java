package org.BetterGenTeam.BetterGen.NamedBinaryTreeSupport;

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
	
	short name_length;
	char[] name;
	
	public NBT_Tag(int i, String name) {
		this.ID = (byte) i;
		this.name = name.toCharArray();
		this.name_length = (short) name.length();
	}
	public byte getID() {
		return this.ID;
	}
	
	public String getName() {
		return this.name.toString();
	}
	
	public void setName(String name) {
		this.name = name.toCharArray();
		this.name_length = (short) name.length();
	}
	
}
