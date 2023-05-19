package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class MenuoptionDTO implements MySerializableClass
{
    private String StoreID;
    private String MenuName;
    private String Options;
    private int price;

    public MenuoptionDTO()
    {

    }

    public MenuoptionDTO(String storeID, String menuName, String options, int price) {
        StoreID = storeID;
        MenuName = menuName;
        Options = options;
        this.price = price;
    }
    public static MenuoptionDTO readMenuoption(DataInputStream dis) throws IOException
    {
        String StoreID = dis.readUTF();
        String MenuName = dis.readUTF();
        String Options = dis.readUTF();
        int price = dis.readInt();

        return new MenuoptionDTO(StoreID, MenuName, Options,price);
    }

    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        System.out.println("a");
        dos.writeUTF(StoreID);
        System.out.println("b");
        dos.writeUTF(MenuName);
        System.out.println("c");
        dos.writeUTF(Options);
        System.out.println("d");
        dos.writeInt(price);
        System.out.println("e");

        return buf.toByteArray();
    }
}
