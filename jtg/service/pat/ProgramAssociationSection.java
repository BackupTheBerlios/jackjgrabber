package service.pat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import control.ControlMain;

public class ProgramAssociationSection extends PSISection {
	// ISO 13818-1; 2.4.4.3 Program association table; Table 2-26
	int programListlength;
	int[] programList;

	public ProgramAssociationSection() {
		programList = new int[257];
	}

	public int read(int[] Buffer, int Offset) {
		if (Buffer[Offset] != (byte)ids) return 0;
		if (0 == super.read(Buffer, Offset)) return 0;
		
		
		if (((sectionlength - 5) % 4) != 0) return -4;
		programListlength = (sectionlength - 5) / 4 - 1;
		 
		for (int i = 0; i < programListlength; i++) {
		    
		    programList[i] = Buffer[Offset + 8  + 4 * i]<<24 | 
            Buffer[Offset + 9  + 4 * i]<<16 |
            Buffer[Offset + 10 + 4 * i]<<8 |
            Buffer[Offset + 11 + 4 * i];
		}
		return sectionlength + 3;
	}
	  	
	public int transportStreamId(){
	    return word2;
	}
	public int programListlength()	{
	    return programListlength;
	}
	public int serviceId(int Pos) {
		if (Pos >= programListlength || Pos < 0) return -1;
		return (int)(programList[Pos]>>16);
	}
	public String pid(int Pos) {
		if (Pos >= programListlength || Pos < 0) return new String();
		int temp = programList[Pos] & 0x00001fff;
		return Integer.toHexString(temp);
	}
	public String ToString() {
		StringBuilder sb = new StringBuilder("program association section\n");
		sb.append("   transport stream id: "+transportStreamId()+"\n");
		sb.append(super.ToString());
		for (int i = 0; i < programListlength; i++) {
			sb.append("   service id/pid:      {0:x4} {1:x4}\n", serviceId(i), Integer.parseInt(pid(i)));
		}
		return sb.toString();
	}
	public String findPid(int serviceId) throws Exception {
		for (int i = 0; i < programListlength; i++) {
			if (serviceId(i) == serviceId) return pid(i);
		}
		throw new Exception();
	}
	
	public static String GetPmtPid(String id) {
		int tcpPort = 31338;
		int serviceId = Integer.parseInt(id, 16);
			

		byte[] temp = new byte[300];
		int[] buffer = new int[300];
        try {
            Socket socket = new Socket(ControlMain.getBoxIpOfActiveBox(), tcpPort);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            String requestString = "GET /0 HTTP/1.0\r\n\r\n";
            out.write(requestString);
            out.flush();
            
            BufferedInputStream in = new  BufferedInputStream(socket.getInputStream());
            in.read(temp, 0, 42);
            
            for (int i=0; i<buffer.length; i++) {
                buffer[i]=in.read()&0xff;
            }

            socket.close();
        }catch (IOException e) {}

		
		ProgramAssociationSection PAT = new ProgramAssociationSection();
		int length = PAT.read(buffer, 1);
		if (length != 0) {
			if (serviceId == -1) {
				System.out.println(PAT.ToString());
			} else {
				String pid;
				try {
					pid = PAT.findPid(serviceId);
				} catch (Exception ex) {
					System.out.println("unknwon service id");
					System.out.println();
					System.out.println(PAT.ToString());
					return null;
				}
				return "0x"+pid;
			}	
		} else {
			System.out.println("PAT parsing error");
			for (int i = 0; i < 16; i++) {
				for (int j = 0; j < 16; j++) {
					System.out.println(buffer[i*16 + j]);
				}
				return null;
			}			
		}
		return null;
	}
}

