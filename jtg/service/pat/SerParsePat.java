package service.pat;
/*
PPAT.java by Alexander Geist

This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2, or (at your option)
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.  

*/ 
import java.text.MessageFormat;

// ISO 138181-1; 2.4.4 Program specific information
abstract class PSISection
{
	// Table 2-27
	public int ids; //{ProgramAssociation = 0, ConditionalAccess, ProgramMap};
	
	int word1;
	protected int word2;
	int byte3;
	int sectionNumber;
	int lastSectionNumber;
	protected int sectionlength;
	
	public int read(int[] Buffer, int Offset) {
		word1 = Buffer[Offset + 1]<<8 | Buffer[Offset + 2];
		if ((word1 & 0x8000) != 0x8000) return 0; 
		sectionlength =  word1 & 0x0fff;
		if (sectionlength > 1021) return 0;
		word2 = Buffer[Offset + 3]<<8 | Buffer[Offset + 4];
		byte3             = Buffer[Offset + 5];
		sectionNumber     = Buffer[Offset + 6];
		lastSectionNumber = Buffer[Offset + 7];
		return 8;
	}
	public int versionNumber() {
		return (byte3 & 0x3e)>>1;
	}
	public boolean isCurrent() {
	    return (byte3 & 0x01) == 0x01;
	}
	public int sectionNumber() {
		return sectionNumber;
	}
	public int lastSectionNumber(){
	    return lastSectionNumber;
	}
	public String ToString() {
	    Object[] args = {new Integer(versionNumber()), Boolean.toString(isCurrent()), 
	            new Integer(sectionNumber), new Integer(lastSectionNumber)};
	    return MessageFormat.format("   version number:      {0}\n" +
			       "   current:             {1}\n" +
			       "   section:             {2}\n" +
			       "   last section:        {3}\n", 
			       args);
	}

}
