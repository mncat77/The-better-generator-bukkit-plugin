package org.BetterGenTeam.BetterGen.NamedBinaryTreeSupport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.BetterGenTeam.BetterGen.Exceptions.NoDataException;

/**
 * 
 * @author jtjj222
 * 
 * Represents an NBT tag that holds one byte
 *
 */
public class NBT_Tag_Byte extends NBT_Tag{
	
	public NBT_Tag_Byte(byte payload, String name) {
		super(1, name);
		this.PayLoad = payload;
	}
	
	private byte PayLoad;
	
	public byte getPayload() {
		return this.PayLoad;
	}
	
	public void setPayload(byte payload) {
		this.PayLoad = payload;
	}

	public void ReadPayload(DataInputStream s) throws IOException, NoDataException {
		short name_length = s.readShort();
		char[] name = new char[(int) name_length];
		
		for (int i= 0; i< name_length; i++) {
			name[i] = s.readChar();
		}
		
		byte payload = s.readByte();
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
		s.writeByte(this.PayLoad); //then the data
	}
}
