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
public class OwnerDTO implements MySerializableClass
{
    private String OwnerName;
    private String PhoneNumber;
    private String OwnerID;
    private String OwnerPW;

    public OwnerDTO()
    {

    }

    public OwnerDTO(String ownerName, String phoneNumber, String ownerID, String ownerPW)
    {
        OwnerName = ownerName;
        PhoneNumber = phoneNumber;
        OwnerID = ownerID;
        OwnerPW = ownerPW;
    }
    public static OwnerDTO readOwner(DataInputStream dis) throws IOException
    {
        String OwnerName = dis.readUTF();
        String PhoneNumber = dis.readUTF();
        String OwnerID = dis.readUTF();
        String OwnerPW = dis.readUTF();

        return new OwnerDTO(OwnerName, PhoneNumber,OwnerID,OwnerPW);
    }
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(OwnerName);
        dos.writeUTF(PhoneNumber);
        dos.writeUTF(OwnerID);
        dos.writeUTF(OwnerPW);

        return buf.toByteArray();
    }
}
