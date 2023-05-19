package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import protocol.Header;
import protocol.MySerializableClass;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString

public class MenuDTO implements MySerializableClass
{
    private String MenuName;
    private int OrderQuantity;
    private int Price;
    private String MenuDescription;
    private String StoreID;
    private int RemainingSale;
    private int Discount;
    private String Type;
    private List<MenuoptionDTO> option=new ArrayList<>();

    static MenuDTO menu = new MenuDTO();

    public MenuDTO()
    {

    }

    public MenuDTO(String menuName, int orderQuantity, int price, String menuDescription, String storeID, int remainingSale, int discount, String type, List<MenuoptionDTO> option)
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

    public void receiveMenuOptionList(DataInputStream inputStream) throws IOException
    {
        int size = inputStream.readInt();

        for(int i = 0; i < size; i++)
        {
            MenuoptionDTO op = MenuoptionDTO.readMenuoption(inputStream);
            option.add(op);
        }
    }
    public static MenuDTO readMenu(DataInputStream dis) throws IOException
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
        return new MenuDTO(MenuName, OrderQuantity, Price,MenuDescription,StoreID,RemainingSale,Discount,Type,menu.option);
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
        System.out.println("삐삐");
        dos.writeInt(option.size());

        System.out.println(option.size());

        for(MenuoptionDTO object : option)
            dos.write(object.getBytes());

        System.out.println("뽀뽀");
        return buf.toByteArray();
    }
}