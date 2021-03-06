package me.jtjj222.SchematicsLoader;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBT_Tag_Short extends NBT_Tag{
	
	public short payload;

	public NBT_Tag_Short(String name){
		super(2, name);
	}
	public NBT_Tag_Short(String name, short payload){
		super(8, name);
		this.payload = payload;
	}

	@Override
	public void readTagPayload(DataInput in) throws IOException {
		this.payload = in.readShort();		
	}
	
	public void writeTag(DataOutput out) throws IOException {
		out.write(this.id);
		out.writeUTF(this.name);
		this.writePayload(out);
	}

	public void writePayload(DataOutput out) throws IOException {
		out.writeShort(this.payload);
	}
	
}
