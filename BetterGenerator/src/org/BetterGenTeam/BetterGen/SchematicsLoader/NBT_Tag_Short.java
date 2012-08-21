package org.BetterGenTeam.BetterGen.SchematicsLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.BetterGenTeam.BetterGen.Exceptions.NoDataException;

/**
 * 
 * @author jtjj222
 * 
 * Represent an NBT tag that stores one short
 *
 */
public class NBT_Tag_Short extends NBT_Tag{
	public NBT_Tag_Short() {
		super(2);
	}
	
	public NBT_Tag_Short(short payload) {
		super(2);
		this.PayLoad = payload;
	}
	
	private short PayLoad;
	
	public short getPayload() {
		return this.PayLoad;
	}
	
	public void setPayload(short payload) {
		this.PayLoad = payload;
	}

	public void ReadPayload(DataInputStream s) throws IOException, NoDataException {
		short payload = s.readShort();
		if (payload != -1) this.PayLoad = payload;
		else throw new NoDataException();
	}
	
	public void WritePayload(DataOutputStream s) throws IOException {
		s.writeByte(this.ID); //write the id first
		s.writeShort(this.PayLoad); //then the data
	}
}
