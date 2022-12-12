package ch.framsteg.hl7.drichem.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class Client {

	public static void main(String[] data) {

		if (data.length == 2) {
			String ipAddress = data[0];
			int portNumber = Integer.parseInt(data[1]);

			char END_OF_BLOCK = '\u001c';
			char START_OF_BLOCK = '\u000b';
			char CARRIAGE_RETURN = 13;
			StringBuffer testHL7Message = new StringBuffer();

			testHL7Message.append(START_OF_BLOCK).append(
					"MSH|^~\\&||4711|\"\"|\"\"|20200108155010||OUL^R22^OUL_R22|1|P|2.5.1||||||UNICODE UTF-8|||LAB-29^IHE")
					.append(CARRIAGE_RETURN)
					.append("SPM|1|41||SER^Serum^HL70487|||||||P^Patient^HL70369\n"
							+ "SAC|||123456799||||||||||||1^A^L")
					.append(CARRIAGE_RETURN).append("OBR||||U^Unspecified identifier^L").append(CARRIAGE_RETURN)
					.append("ORC|SC||||CM").append(CARRIAGE_RETURN)
					.append("OBX|1|NM|GLU^JC10||105|mg/dL^^UCUM|73-109|\"\"|||F|||||4711||SN0123456789|20200108153601")
					.append(CARRIAGE_RETURN).append("OBX|1|ST|MDT^GLU^JC10||123456,2018/12,  #        ,1,0,0||||||F")
					.append(CARRIAGE_RETURN).append("TCD|GLU^JC10|^1^:^1||||||O^Original, first run^HL70389")
					.append(CARRIAGE_RETURN)
					.append("OBX|2|NM|TCHO^JC10||150|mg/dL^^UCUM|\"\"|\"\"|||F|||||4711||SN0123456789|20200108153601")
					.append(CARRIAGE_RETURN).append("OBX|2|ST|MDT^TCHO^JC10||223344,2020/12,           ,1,0,0||||||F")
					.append(CARRIAGE_RETURN)
					.append("TCD|3F050000002329101^TCHO^JC10|^1^:^1||||||O^Original, first run^HL70389")
					.append(CARRIAGE_RETURN)
					.append("OBX|3|NM|HDL-C^JC10||50|mg/dL^^UCUM|\"\"|\"\"|||F|||||4711||SN0123456789|20200108153601")
					.append(CARRIAGE_RETURN).append("OBX|3|ST|MDT^HDL-C^JC10||234567,2020/12,           ,1,0,0||||||F")
					.append(CARRIAGE_RETURN)
					.append("TCD|3F070000002329101^HDL-C^JC10|^1^:^1||||||O^Original, first run^HL70389")
					.append(CARRIAGE_RETURN)
					.append("OBX|4|NM|non-HDL-C^JC10||100|mg/dL^^UCUM|\"\"|\"\"|||F|||||4711||SN0123456789|20200108153601")
					.append(CARRIAGE_RETURN).append("OBX|4|ST|MDT^non-HDL-C^JC10||,,           ,1,0,0||||||F")
					.append(CARRIAGE_RETURN)
					.append("TCD|3F069000002391901^non-HDL-C^JC10|^1^:^1||||||O^Original, first run^HL70389")
					.append(CARRIAGE_RETURN).append(END_OF_BLOCK).append(CARRIAGE_RETURN);

			try {
				Socket socket = new Socket(ipAddress, portNumber);
				OutputStream outputStream;
				OutputStreamWriter outputStreamWriter;
				InputStream inputStream;

				// retrieving output Stream and its writer, for sending request or
				// acknowledgement
				outputStream = socket.getOutputStream();
				outputStreamWriter = new OutputStreamWriter(outputStream);
				outputStreamWriter.write(testHL7Message.toString());
				outputStreamWriter.flush(); // request is sent

				inputStream = socket.getInputStream();
				byte[] byteBuffer = new byte[200];

				inputStream.read(byteBuffer);
				System.out.println("Here we are");
				System.out.println("Received from Server: ");
				System.out.println(new String(byteBuffer));
				socket.close();

			} catch (Exception exception) {
				System.out.println(exception);
			}
		} else {
			System.out.println("Missing parameters. The HAPI drichem client needs to be started like: java -jar CLIENT.jar IP_ADDRESS PORT");
		}
	}
}
