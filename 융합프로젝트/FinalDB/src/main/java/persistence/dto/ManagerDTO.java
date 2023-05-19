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
public class ManagerDTO implements MySerializableClass
{
    private int pk;
    private String ManagerID;
    private String ManagerPW;
    private int ManagerAge;
    private String ManagerName;

    public ManagerDTO(int pk, String managerID, String managerPW, int managerAge, String managerName)
    {
        this.pk = pk;
        ManagerID = managerID;
        ManagerPW = managerPW;
        ManagerAge = managerAge;
        ManagerName = managerName;
    }

    public static ManagerDTO readManager(DataInputStream dis) throws IOException
    {
        int pk = dis.readInt();
        String ManagerID = dis.readUTF();
        String ManagerPW = dis.readUTF();
        int ManagerAge = dis.readInt();
        String ManagerName = dis.readUTF();

        return new ManagerDTO(pk, ManagerID, ManagerPW,ManagerAge,ManagerName);
    }
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeInt(pk);
        dos.writeUTF(ManagerID);
        dos.writeUTF(ManagerPW);
        dos.writeInt(ManagerAge);
        dos.writeUTF(ManagerName);

        return buf.toByteArray();
    }
}
