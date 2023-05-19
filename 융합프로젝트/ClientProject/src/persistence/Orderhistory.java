package persistence;
import protocol.MySerializableClass;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class Orderhistory implements MySerializableClass
{
    private String ClientID;
    private String Menu;
    private String StoreID;
    private String OrderState;
    private LocalDateTime DeliveryDate;
    private String options;
    private int totalPrice;

    public Orderhistory(String clientID, String menu, String storeID, String orderState, LocalDateTime deliveryDate, String options, int totalPrice)
    {
        ClientID = clientID;
        Menu = menu;
        StoreID = storeID;
        OrderState = orderState;
        DeliveryDate = deliveryDate;
        this.options = options;
        this.totalPrice = totalPrice;
    }
    public static Orderhistory readOrderhistory(DataInputStream dis) throws IOException
    {
        String ClientID = dis.readUTF();
        String Menu = dis.readUTF();
        String StoreID = dis.readUTF();
        String OrderState = dis.readUTF();
        LocalDateTime DeliveryDate = LocalDateTime.parse(dis.readUTF());
        String options = dis.readUTF();
        int totalPrice= dis.readInt();

        return new Orderhistory(ClientID, Menu, StoreID,OrderState,DeliveryDate,options,totalPrice);
    }
    @Override
    public String toString() {
        return "Orderhistory{" +
                "ClientID='" + ClientID + '\'' +
                ", Menu='" + Menu + '\'' +
                ", StoreID='" + StoreID + '\'' +
                ", OrderState='" + OrderState + '\'' +
                ", DeliveryDate=" + DeliveryDate +
                ", options='" + options + '\'' +
                ", totalPrice=" + totalPrice +
                '}';
    }
    @Override
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

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getMenu() {
        return Menu;
    }

    public void setMenu(String menu) {
        Menu = menu;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getOrderState() {
        return OrderState;
    }

    public void setOrderState(String orderState) {
        OrderState = orderState;
    }

    public LocalDateTime getDeliveryDate() {
        return DeliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        DeliveryDate = deliveryDate;
    }

    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }
}
