package org.BetterGenTeam.BetterGen.SchematicsLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import org.BetterGenTeam.BetterGen.Exceptions.NoDataException;

/**
 * 
 * @author jtjj222
 * 
 * Represents an NBT tag that holds one int
 *
 */
public class NBT_Tag_Int extends NBT_Tag{

	public NBT_Tag_Int() {
		super(3);
	}
	
	public NBT_Tag_Int(int payload) {
		super(3);
		this.PayLoad = payload;
	}
	
	private int PayLoad;
	
	public int getPayload() {
		return this.PayLoad;
	}
	
	public void setPayload(int payload) {
		this.PayLoad = payload;
	}

	public void ReadPayload(DataInputStream s) throws IOException, NoDataException {
		int payload = s.readInt();
		if (payload != -1) this.PayLoad = payload;
		else throw new NoDataException();
	}
	
	public void WritePayload(DataOutputStream s) throws IOException {
		s.writeByte(this.ID); //write the id first
		s.writeInt(this.PayLoad); //then the data
	}
}
