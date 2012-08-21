package org.BetterGenTeam.BetterGen.SchematicsLoader;

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

	public NBT_Tag_Byte() {
		super(1);
	}
	
	public NBT_Tag_Byte(byte payload) {
		super(1);
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
		byte payload = s.readByte();
		if (payload != -1) this.PayLoad = payload;
		else throw new NoDataException();
	}
	
	public void WritePayload(DataOutputStream s) throws IOException {
		s.writeByte(this.ID); //write the id first
		s.writeByte(this.PayLoad); //then the data
	}
}
