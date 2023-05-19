package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import protocol.Header;
import protocol.MySerializableClass;

import java.io.*;
import java.util.List;

@Getter
@Setter
@ToString
public class StoreDTO implements MySerializableClass
{
    private String StoreID;
    private String StoreName;
    private String Adress;
    private String StorePhoneNumber;
    private String OwnerID;
    private String Introduce;
    private String StorePW;
    private String Operatingtime;
    private List<OwnerDTO> owners;

    private static StoreDTO store;

    public StoreDTO()
    {

    }

    public StoreDTO(String storeID, String storeName, String adress, String storePhoneNumber, String ownerID, String introduce, String storePW, String operatingtime) {
        StoreID = storeID;
        StoreName = storeName;
        Adress = adress;
        StorePhoneNumber = storePhoneNumber;
        OwnerID = ownerID;
        Introduce = introduce;
        StorePW = storePW;
        Operatingtime = operatingtime;
    }
    public void receiveOwnerList(DataInputStream inputStream) throws IOException {
        Header header = Header.readHeader(inputStream);
        byte[] body = new byte[header.bodySize];
        inputStream.read(body);
        DataInputStream bodyReader = new DataInputStream((new ByteArrayInputStream(body)));

        int size = bodyReader.readInt();

        for(int i = 0; i < size; i++)
        {
            OwnerDTO owner = OwnerDTO.readOwner(bodyReader);
            owners.add(owner);
        }
    }
    public static StoreDTO readStore(DataInputStream dis) throws IOException
    {
        String StoreID = dis.readUTF();
        String StoreName = dis.readUTF();
        String Adress = dis.readUTF();
        String StorePhoneNumber = dis.readUTF();
        String OwnerID = dis.readUTF();
        String Introduce = dis.readUTF();
        String StorePW = dis.readUTF();
        String Operatingtime = dis.readUTF();
        //store.receiveOwnerList(dis);

        return new StoreDTO(StoreID, StoreName, Adress,StorePhoneNumber,OwnerID,Introduce,StorePW,Operatingtime);
    }
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
}
