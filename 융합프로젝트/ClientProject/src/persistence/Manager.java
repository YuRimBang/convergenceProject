package persistence;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Manager implements MySerializableClass
{
    private int pk;
    private String ManagerID;
    private String ManagerPW;
    private int ManagerAge;
    private String ManagerName;

    public Manager(int pk, String managerID, String managerPW, int managerAge, String managerName)
    {
        this.pk = pk;
        ManagerID = managerID;
        ManagerPW = managerPW;
        ManagerAge = managerAge;
        ManagerName = managerName;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "pk=" + pk +
                ", ManagerID='" + ManagerID + '\'' +
                ", ManagerPW='" + ManagerPW + '\'' +
                ", ManagerAge=" + ManagerAge +
                ", ManagerName='" + ManagerName + '\'' +
                '}';
    }
    public static Manager readManager(DataInputStream dis) throws IOException
    {
        int pk = dis.readInt();
        String ManagerID = dis.readUTF();
        String ManagerPW = dis.readUTF();
        int ManagerAge = dis.readInt();
        String ManagerName = dis.readUTF();

        return new Manager(pk, ManagerID, ManagerPW,ManagerAge,ManagerName);
    }
    @Override
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
    public int getPk() {
        return pk;
    }

    public void setPk(int pk) {
        this.pk = pk;
    }

    public String getManagerID() {
        return ManagerID;
    }

    public void setManagerID(String managerID) {
        ManagerID = managerID;
    }

    public String getManagerPW() {
        return ManagerPW;
    }

    public void setManagerPW(String managerPW) {
        ManagerPW = managerPW;
    }

    public int getManagerAge() {
        return ManagerAge;
    }

    public void setManagerAge(int managerAge) {
        ManagerAge = managerAge;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        ManagerName = managerName;
    }

}
