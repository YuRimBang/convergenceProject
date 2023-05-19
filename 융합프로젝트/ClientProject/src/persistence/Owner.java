package persistence;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Owner implements MySerializableClass
{
    private String OwnerName;
    private String PhoneNumber;
    private String OwnerID;
    private String OwnerPW;

    public Owner(String ownerName, String phoneNumber, String ownerID, String ownerPW)
    {
        OwnerName = ownerName;
        PhoneNumber = phoneNumber;
        OwnerID = ownerID;
        OwnerPW = ownerPW;
    }
    public static Owner readOwner(DataInputStream dis) throws IOException
    {
        String OwnerName = dis.readUTF();
        String PhoneNumber = dis.readUTF();
        String OwnerID = dis.readUTF();
        String OwnerPW = dis.readUTF();

        return new Owner(OwnerName, PhoneNumber,OwnerID,OwnerPW);
    }
    @Override
    public String toString() {
        return "Owner{" +
                ", OwnerName='" + OwnerName + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", OwnerID='" + OwnerID + '\'' +
                ", OwnerPW='" + OwnerPW + '\'' +
                '}';
    }
    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(OwnerName);
        dos.writeUTF(PhoneNumber);
        dos.writeUTF(OwnerID);
        dos.writeUTF(OwnerPW);

        return buf.toByteArray();
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String ownerID) {
        OwnerID = ownerID;
    }

    public String getOwnerPW() {
        return OwnerPW;
    }

    public void setOwnerPW(String ownerPW) {
        OwnerPW = ownerPW;
    }

}
