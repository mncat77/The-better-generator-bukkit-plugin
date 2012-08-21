package org.BetterGenTeam.BetterGen.NamedBinaryTreeSupport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.BetterGenTeam.BetterGen.Exceptions.NoDataException;

/**
 * 
 * @author jtjj222
 * 
 * Represents an NBT tag that holds one double
 *
 */
public class NBT_Tag_Double extends NBT_Tag{

	public NBT_Tag_Double(double payload, String name) {
		super(6,name);
		this.PayLoad = payload;
	}
	
	private double PayLoad;
	
	public double getPayload() {
		return this.PayLoad;
	}
	
	public void setPayload(double payload) {
		this.PayLoad = payload;
	}

	public void ReadPayload(DataInputStream s) throws IOException, NoDataException {
		short name_length = s.readShort();
		char[] name = new char[(int) name_length];
		
		for (int i= 0; i< name_length; i++) {
			name[i] = s.readChar();
		}
		short payload = s.readShort();
		if (payload != -1) {
			this.PayLoad = payload;
			this.name_length = name_length;
			this.name = name;
		}
		else throw new NoDataException();
	}
	
	public void WritePayload(DataOutputStream s) throws IOException {
		s.writeByte(this.ID); //write the id first
		s.writeShort(name_length);
		s.writeChars(name.toString());
		s.writeDouble(this.PayLoad); //then the data
	}
}
