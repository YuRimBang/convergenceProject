package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import persistence.dao.MyOrderhistoryDAO;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class OrderhistoryDTO implements MySerializableClass
{
    private String ClientID;
    private String Menu;
    private String StoreID;
    private String OrderState;
    private LocalDateTime DeliveryDate;
    private String options;
    private int totalPrice;

    public OrderhistoryDTO()
    {

    }

    public OrderhistoryDTO(String ClientID, String Menu, String StoreID,String OrderState,LocalDateTime DeliveryDate,String options,int totalPrice)
    {
        this.ClientID = ClientID;
        this.Menu = Menu;
        this.StoreID = StoreID;
        this.OrderState = OrderState;
        this.DeliveryDate =DeliveryDate;
        this.options = options;
        this.totalPrice = totalPrice;
    }

    public static OrderhistoryDTO readOrderhistory(DataInputStream dis) throws IOException
    {
        String ClientID = dis.readUTF();
        String Menu = dis.readUTF();
        String StoreID = dis.readUTF();
        String OrderState = dis.readUTF();
        LocalDateTime DeliveryDate = LocalDateTime.parse(dis.readUTF());
        String options = dis.readUTF();
        int totalPrice= dis.readInt();

        return new OrderhistoryDTO(ClientID, Menu, StoreID,OrderState,DeliveryDate,options,totalPrice);
    }
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(ClientID);
        dos.writeUTF(Menu);
        dos.writeUTF(StoreID);
        dos.writeUTF(OrderState);
        dos.writeUTF(String.valueOf(DeliveryDate));
        dos.writeUTF(options);
        dos.writeInt(totalPrice);

        return buf.toByteArray();
    }
}
