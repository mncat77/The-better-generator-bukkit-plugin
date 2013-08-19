package org.bettergenteam.bettergen.biome.layer;

public class BiomeLayerZoom extends BiomeLayer {
    
    //This is pretty close to NMS, if you have a better solution replace
    
    private final int shifter;
    private final int factorSquared;
    
    
    public BiomeLayerZoom(long seed, BiomeLayer parent, int shifter) {
        super(seed);
        this.parent = parent;
        this.shifter = shifter;
        this.factorSquared = (int)Math.pow(Math.pow(2, this.shifter), 2);
    }
    
    @Override
    public byte[] getValues(int realX, int realZ, int width, int length) {
        int realXDiv = realX >> this.shifter;
        int realZDiv = realZ >> this.shifter;
        
        //The +3 is probably still wrong, if you use anything but 2 as shifter, not sure
        int widthDivOffset = (width >> this.shifter) + 3;
        int lengthDivOffset = (length >> this.shifter) + 3;
        byte[] parentValues = this.parent.getValues(realXDiv, realZDiv, widthDivOffset, lengthDivOffset);
        byte[] values = new byte[widthDivOffset * lengthDivOffset * factorSquared];
        int widthDivOffsetMul = widthDivOffset << this.shifter;
        
        int zMul;
        
        for (int z = 0; z < lengthDivOffset - 1; z++) {
            zMul = z << this.shifter;
            int indexZ = zMul * widthDivOffsetMul;
            byte parentValue1 = parentValues[z * widthDivOffset];
            byte parentValue2 = parentValues[(z + 1) * widthDivOffset];
            
            for (int x = 0; x < widthDivOffset - 1; x++) {
                byte parentValue3 = parentValues[x + 1 + z * widthDivOffset];
                byte parentValue4 = parentValues[x + 1 + (z + 1) * widthDivOffset];
                
                values[indexZ] = parentValue1;
                //values[indexZ++ + widthDivOffsetMul] = this.choose(parentValue1, parentValue2);
                //values[indexZ] = this.choose(parentValue1, parentValue3);
                //values[indexZ++ + widthDivOffsetMul] = this.modeOrRandom(parentValue1, parentValue3, parentValue2, parentValue4);
                parentValue1 = parentValue3;
                parentValue2 = parentValue4;
            }
        }
        
        byte[] finalValues = new byte[width * length];
        
        for (zMul = 0; zMul < length; ++zMul) {
            System.arraycopy(values, (zMul + (realZ & 1)) * (widthDivOffset << 1) + (realX & 1), finalValues, zMul * width, width);
        }
        
        return finalValues;
    }
    
    //private byte choose(byte option1, byte option2, int realX, int realZ) {
        //return this.nextByte(2, realX, realZ) == 0 ? option1 : option2;
    //}
    
    private byte modeOrRandom(byte option1, byte option2, byte option3, byte option4, int realX, int realZ) {
        if (option2 == option3 && option3 == option4) {
            return option2;
        } else if (option1 == option2 && option1 == option3) {
            return option1;
        } else if (option1 == option2 && option1 == option4) {
            return option1;
        } else if (option1 == option3 && option1 == option4) {
            return option1;
        } else if (option1 == option2 && option3 != option4) {
            return option1;
        } else if (option1 == option3 && option2 != option4) {
            return option1;
        } else if (option1 == option4 && option2 != option3) {
            return option1;
        } else if (option2 == option1 && option3 != option4) {
            return option2;
        } else if (option2 == option3 && option1 != option4) {
            return option2;
        } else if (option2 == option4 && option1 != option3) {
            return option2;
        } else if (option3 == option1 && option2 != option4) {
            return option3;
        } else if (option3 == option2 && option1 != option4) {
            return option3;
        } else if (option3 == option4 && option1 != option2) {
            return option3;
        } else if (option4 == option1 && option2 != option3) {
            return option3;
        } else if (option4 == option2 && option1 != option3) {
            return option3;
        } else if (option4 == option3 && option1 != option2) {
            return option3;
        } else {
            byte b1 = 0;//this.nextByte(4, realX, realZ);
            return b1 == 0 ? option1 : (b1 == 1 ? option2 : (b1 == 2 ? option3 : option4));
        }
    }
    
}
