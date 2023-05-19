package persistence;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Menuoption implements MySerializableClass
{
    private String StoreID;
    private String MenuName;
    private String Options;
    private int price;

    public Menuoption(String storeID, String menuName, String options, int price) {
        StoreID = storeID;
        MenuName = menuName;
        Options = options;
        this.price = price;
    }
    public static Menuoption readMenuoption(DataInputStream dis) throws IOException
    {
        String StoreID = dis.readUTF();
        String MenuName = dis.readUTF();
        String Options = dis.readUTF();
        int price = dis.readInt();

        return new Menuoption(StoreID, MenuName,Options,price);
    }
    @Override
    public String toString() {
        return "Menuoption{" +
                "StoreID='" + StoreID + '\'' +
                ", MenuName='" + MenuName + '\'' +
                ", Options='" + Options + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(StoreID);
        dos.writeUTF(MenuName);
        dos.writeUTF(Options);
        dos.writeInt(price);

        return buf.toByteArray();
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public String getOptions() {
        return Options;
    }

    public void setOptions(String options) {
        Options = options;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
