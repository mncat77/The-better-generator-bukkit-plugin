package org.BetterGenTeam.BetterGen.NamedBinaryTreeSupport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.BetterGenTeam.BetterGen.Exceptions.NoDataException;

/**
 * 
 * @author jtjj222
 * 
 * Represents an NBT tag that holds one float
 *
 */
public class NBT_Tag_Float extends NBT_Tag{
	
	public NBT_Tag_Float(float payload, String name) {
		super(5, name);
		this.PayLoad = payload;
	}
	
	private float PayLoad;
	
	public float getPayload() {
		return this.PayLoad;
	}
	
	public void setPayload(float payload) {
		this.PayLoad = payload;
	}

	public void ReadPayload(DataInputStream s) throws IOException, NoDataException {
		short name_length = s.readShort();
		char[] name = new char[(int) name_length];
		
		for (int i= 0; i< name_length; i++) {
			name[i] = s.readChar();
		}
		
		float payload = s.readFloat();
		if (payload != -1) {
			this.PayLoad = payload;
			this.name_length = name_length;
			this.name = name;
		}
		else throw new NoDataException();
	}
	
	public void WritePayload(DataOutputStream s) throws IOException {
		s.writeByte(this.ID); //write the id first
		s.writeShort(this.name_length);
		s.writeChars(this.name.toString());
		s.writeFloat(this.PayLoad); //then the data
	}
}
