package persistence;

import protocol.Header;
import protocol.MySerializableClass;
import java.io.*;
import java.util.List;

public class Menu implements MySerializableClass
{
    private String MenuName;
    private int OrderQuantity;
    private int Price;
    private String MenuDescription;
    private String StoreID;
    private int RemainingSale;
    private int Discount;
    private String Type;
    private List<Menuoption> option;

    static Menu menu = new Menu();
    public Menu(String menuName, int orderQuantity, int price, String menuDescription, String storeID, int remainingSale, int discount, String type, List<Menuoption> option)
    {
        MenuName = menuName;
        OrderQuantity = orderQuantity;
        Price = price;
        MenuDescription = menuDescription;
        StoreID = storeID;
        RemainingSale = remainingSale;
        Discount = discount;
        Type = type;
        this.option = option;
    }

    public Menu() {

    }

    public void receiveMenuOptionList(DataInputStream inputStream) throws IOException
    {
        int size = inputStream.readInt();

        for(int i = 0; i < size; i++)
        {
            Menuoption op = Menuoption.readMenuoption(inputStream);
            option.add(op);
        }
    }
    public static Menu readMenu(DataInputStream dis) throws IOException
    {
        String MenuName = dis.readUTF();
        int OrderQuantity = dis.readInt();
        int Price = dis.readInt();
        String MenuDescription = dis.readUTF();
        String StoreID = dis.readUTF();
        int RemainingSale = dis.readInt();
        int Discount = dis.readInt();
        String Type = dis.readUTF();
        menu.receiveMenuOptionList(dis);
        return new Menu(MenuName, OrderQuantity, Price,MenuDescription,StoreID,RemainingSale,Discount,Type,menu.option);
    }
    @Override
    public byte[] getBytes() throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(MenuName);
        dos.writeInt(OrderQuantity);
        dos.writeInt(Price);
        dos.writeUTF(MenuDescription);
        dos.writeUTF(StoreID);
        dos.writeInt(RemainingSale);
        dos.writeInt(Discount);
        dos.writeUTF(Type);

        dos.writeInt(option.size());
        for(MySerializableClass object : option)
            dos.write(object.getBytes());

        return buf.toByteArray();
    }
    @Override
    public String toString() {
        return "Menu{" +
                "MenuName='" + MenuName + '\'' +
                ", OrderQuantity=" + OrderQuantity +
                ", Price=" + Price +
                ", MenuDescription='" + MenuDescription + '\'' +
                ", StoreID='" + StoreID + '\'' +
                ", RemainingSale=" + RemainingSale +
                ", Discount=" + Discount +
                ", Type='" + Type + '\'' +
                ", option=" + option +
                '}';
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public int getOrderQuantity() {
        return OrderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        OrderQuantity = orderQuantity;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public String getMenuDescription() {
        return MenuDescription;
    }

    public void setMenuDescription(String menuDescription) {
        MenuDescription = menuDescription;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public int getRemainingSale() {
        return RemainingSale;
    }

    public void setRemainingSale(int remainingSale) {
        RemainingSale = remainingSale;
    }

    public int getDiscount() {
        return Discount;
    }

    public void setDiscount(int discount) {
        Discount = discount;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public List<Menuoption> getOption() {
        return option;
    }

    public void setOption(List<Menuoption> option) {
        this.option = option;
    }
}
