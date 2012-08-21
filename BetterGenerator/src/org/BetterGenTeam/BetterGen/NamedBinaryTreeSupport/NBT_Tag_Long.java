package org.BetterGenTeam.BetterGen.NamedBinaryTreeSupport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.BetterGenTeam.BetterGen.Exceptions.NoDataException;

/**
 * 
 * @author jtjj222
 * 
 * Represents an NBT tag that holds one Long
 *
 */
public class NBT_Tag_Long extends NBT_Tag{
	
	public NBT_Tag_Long(int payload, String name) {
		super(4, name);
		this.PayLoad = payload;
	}
	
	private long PayLoad;
	
	public long getPayload() {
		return this.PayLoad;
	}
	
	public void setPayload(long payload) {
		this.PayLoad = payload;
	}

	public void ReadPayload(DataInputStream s) throws IOException, NoDataException {
		short name_length = s.readShort();
		char[] name = new char[(int) name_length];
		
		for (int i= 0; i< name_length; i++) {
			name[i] = s.readChar();
		}
		
		long payload = s.readLong();
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
		s.writeLong(this.PayLoad); //then the data
	}
}
