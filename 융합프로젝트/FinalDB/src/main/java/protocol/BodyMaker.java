package protocol;

import persistence.dto.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

public class BodyMaker {
    ByteArrayOutputStream buf;
    DataOutputStream dos;

    public BodyMaker()
    {
        buf = new ByteArrayOutputStream();
        dos = new DataOutputStream(buf);
    }

    public int getSize() {return buf.size();}

    public void add(MySerializableClass object) throws IOException
    {
        dos.write(object.getBytes());
    }

    public void addMenuList(List<MenuDTO> list) throws IOException
    {
        System.out.println(list.size());
        dos.writeInt(list.size());
        if(list.size() > 0)
        {
            System.out.println("...");
            for(MenuDTO object : list) dos.write(object.getBytes());
            System.out.println("................");
        }
    }
    public void addStoreList(List<StoreDTO> list) throws IOException
    {
        dos.writeInt(list.size());
        for(StoreDTO object : list) dos.write(object.getBytes());
    }

    public void addOrderhistoryList(List<OrderhistoryDTO> list) throws IOException
    {
        dos.writeInt(list.size());
        for(OrderhistoryDTO object : list) dos.write(object.getBytes());
    }

    public void addOwnerList(List<OwnerDTO> list) throws IOException
    {
        dos.writeInt(list.size());
        for(OwnerDTO object : list) dos.write(object.getBytes());
    }

    public void addManagerList(List<ManagerDTO> list) throws IOException
    {
        dos.writeInt(list.size());
        for(ManagerDTO object : list) dos.write(object.getBytes());
    }

    public void addMenuoptionList(List<MenuoptionDTO> list) throws IOException
    {
        dos.writeInt(list.size());
        for(MenuoptionDTO object : list) dos.write(object.getBytes());
    }

    public void addReviewList(List<ReviewDTO> list) throws IOException
    {
        dos.writeInt(list.size());
        for(ReviewDTO object : list) dos.write(object.getBytes());
    }

    public void addCustomerList(List<CustomerDTO> list) throws IOException
    {
        dos.writeInt(list.size());
        for(CustomerDTO object : list)
        {
            System.out.println(object.toString());
            dos.write(object.getBytes());
        }
        System.out.println(list.size());
    }

    public void addIntegerList(List<Integer> list) throws IOException
    {
        dos.writeInt(list.size());
        for(int object : list) dos.writeInt(object);
    }
    public void addIntBytes(int integer) throws IOException
    {
        dos.writeInt(integer);
    }

    public void addStringBytes(String str) throws IOException
    {
        dos.writeUTF(str);
    }

    public byte[] getBody()
    {
        return buf.toByteArray();
    }


}
