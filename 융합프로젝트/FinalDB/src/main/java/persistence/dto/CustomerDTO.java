package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

@Getter
@Setter
@ToString
public class CustomerDTO implements MySerializableClass
{
    private int ClientAge;
    private String ClientID;
    private String ClientName;
    private String PhoneNumber;
    private String Adress;
    private String ClientPW;

    public CustomerDTO()
    {

    }
    public static CustomerDTO readCustomerDTO(DataInputStream dis) throws IOException
    {
        int ClientAge = dis.readInt();
        String ClientID = dis.readUTF();
        String ClientName = dis.readUTF();
        String PhoneNumber = dis.readUTF();
        String Adress = dis.readUTF();
        String ClientPW = dis.readUTF();

        return new CustomerDTO(ClientAge, ClientID, ClientName,PhoneNumber,Adress, ClientPW);
    }

    public CustomerDTO(int clientAge, String clientID, String clientName, String phoneNumber, String adress, String clientPW) {
        ClientAge = clientAge;
        ClientID = clientID;
        ClientName = clientName;
        PhoneNumber = phoneNumber;
        Adress = adress;
        ClientPW = clientPW;
    }

    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(ClientAge);
        dos.writeUTF(ClientID);
        dos.writeUTF(ClientName);
        dos.writeUTF(PhoneNumber);
        dos.writeUTF(Adress);
        dos.writeUTF(ClientPW);

        return buf.toByteArray();
    }
}