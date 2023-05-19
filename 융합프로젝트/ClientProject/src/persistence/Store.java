package persistence;

import protocol.MySerializableClass;

import java.io.*;

public class Store implements MySerializableClass
{
    private String StoreID;
    private String StoreName;
    private String Adress;
    private String StorePhoneNumber;
    private String OwnerID;
    private String Introduce;
    private String StorePW;
    private String Operatingtime;


    public Store(String storeID, String storeName, String adress, String storePhoneNumber, String ownerID, String introduce, String storePW, String operatingtime) {
        StoreID = storeID;
        StoreName = storeName;
        Adress = adress;
        StorePhoneNumber = storePhoneNumber;
        OwnerID = ownerID;
        Introduce = introduce;
        StorePW = storePW;
        Operatingtime = operatingtime;
    }
    public static Store readStore(DataInputStream dis) throws IOException
    {
        String StoreID = dis.readUTF();
        String StoreName = dis.readUTF();
        String Adress = dis.readUTF();
        String StorePhoneNumber = dis.readUTF();
        String OwnerID = dis.readUTF();
        String Introduce = dis.readUTF();
        String StorePW = dis.readUTF();
        String Operatingtime = dis.readUTF();

        return new Store(StoreID, StoreName, Adress,StorePhoneNumber,OwnerID,Introduce,StorePW,Operatingtime);//,store.owners
    }

    @Override
    public String toString() {
        return "Store{" +
                "StoreID='" + StoreID + '\'' +
                ", StoreName='" + StoreName + '\'' +
                ", Adress='" + Adress + '\'' +
                ", StorePhoneNumber='" + StorePhoneNumber + '\'' +
                ", OwnerID='" + OwnerID + '\'' +
                ", Introduce='" + Introduce + '\'' +
                ", StorePW='" + StorePW + '\'' +
                ", Operatingtime='" + Operatingtime + '\'' +
                '}';
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(StoreID);
        dos.writeUTF(StoreName);
        dos.writeUTF(Adress);
        dos.writeUTF(StorePhoneNumber);
        dos.writeUTF(OwnerID);
        dos.writeUTF(Introduce);
        dos.writeUTF(StorePW);
        dos.writeUTF(Operatingtime);

        return buf.toByteArray();
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getStoreName() {
        return StoreName;
    }

    public void setStoreName(String storeName) {
        StoreName = storeName;
    }

    public String getAdress() {
        return Adress;
    }

    public void setAdress(String adress) {
        Adress = adress;
    }

    public String getStorePhoneNumber() {
        return StorePhoneNumber;
    }

    public void setStorePhoneNumber(String storePhoneNumber) {
        StorePhoneNumber = storePhoneNumber;
    }

    public String getOwnerID() {
        return OwnerID;
    }

    public void setOwnerID(String ownerID) {
        OwnerID = ownerID;
    }

    public String getIntroduce() {
        return Introduce;
    }

    public void setIntroduce(String introduce) {
        Introduce = introduce;
    }

    public String getStorePW() {
        return StorePW;
    }

    public void setStorePW(String storePW) {
        StorePW = storePW;
    }

    public String getOperatingtime() {
        return Operatingtime;
    }

    public void setOperatingtime(String operatingtime) {
        Operatingtime = operatingtime;
    }

}
