package persistence;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Customer implements MySerializableClass
{
    private int ClientAge;
    private String ClientID;
    private String ClientName;
    private String PhoneNumber;
    private String Adress;
    private String ClientPW;

    public Customer(int clientAge, String clientID, String clientName, String phoneNumber, String adress, String clientPW) {
        ClientAge = clientAge;
        ClientID = clientID;
        ClientName = clientName;
        PhoneNumber = phoneNumber;
        Adress = adress;
        ClientPW = clientPW;
    }

    public static Customer readCustomer(DataInputStream dis) throws IOException
    {
        int ClientAge = dis.readInt();
        String ClientID = dis.readUTF();
        String ClientName = dis.readUTF();
        String PhoneNumber = dis.readUTF();
        String Adress = dis.readUTF();
        String ClientPW = dis.readUTF();

        return new Customer(ClientAge, ClientID, ClientName,PhoneNumber,Adress,ClientPW);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "ClientAge=" + ClientAge +
                ", ClientID='" + ClientID + '\'' +
                ", ClientName='" + ClientName + '\'' +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Adress='" + Adress + '\'' +
                ", ClientPW='" + ClientPW + '\'' +
                '}';
    }

    @Override
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

    public int getClientAge() {
        return ClientAge;
    }

    public void setClientAge(int clientAge) {
        ClientAge = clientAge;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getClientName() {
        return ClientName;
    }

    public void setClientName(String clientName) {
        ClientName = clientName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }
}
