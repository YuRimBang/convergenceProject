package protocol;

import persistence.*;

import javax.print.DocFlavor;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static protocol.Header.*;

public class ResponseReceiver
{
    public boolean receiveResult(DataInputStream inputStream)throws IOException
    {
        Header header = Header.readHeader(inputStream);
        switch (header.code)
        {
            case CODE_RESULT_SUCCESS:
                System.out.println("성공!");
                break;
            case CODE_RESULT_FAIL:
                System.out.println("실패");
                return false;
            case CODE_RESULT_HOLD:
                System.out.println("보류");
                break;
        }
        return true;
    }
    public void receiveCustInfo(DataInputStream inputStream) throws IOException {

        Header header = Header.readHeader(inputStream);
        byte[] body = new byte[header.bodySize];
        inputStream.read(body);
        DataInputStream bodyReader = new DataInputStream(new ByteArrayInputStream(body));

        Customer customer = Customer.readCustomer(bodyReader);
        System.out.println(customer);
    }

    public void receiveCustListInfo(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);
        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Customer customer = Customer.readCustomer(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(customer);
            }
        }
        else
            System.out.println("고객이 없습니다.");
    }
    public void receiveOwnerListInfo(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);
        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Owner owner = Owner.readOwner(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(owner);
            }
        }
        else
            System.out.println("점주가 없습니다.");

    }
    public boolean receiveStoreListInfo(DataInputStream inputStream) throws IOException {

        Header header = Header.readHeader(inputStream);
        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Store store = Store.readStore(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(store);
            }
            return true;
        }
        else
            return false;

    }
    public void receiveStoreInfo(DataInputStream inputStream) throws IOException {

        Header header = Header.readHeader(inputStream);
        if(header.bodySize ==0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else
        {

            Store store = Store.readStore(inputStream);
            System.out.println(store);
        }
    }
    public void receiveStoreSalesInfo(DataInputStream inputStream) throws IOException {

        Header header = Header.readHeader(inputStream);
        int size1 = inputStream.readInt();
        List<Store> storeList = new ArrayList<>();
        List<Integer> storeSalseList = new ArrayList<>();
        for(int i = 0; i < size1; i++)
        {
            Store store = Store.readStore(inputStream);
            storeList.add(store);
        }
        int size2 = inputStream.readInt();
        for(int i = 0 ; i < size2; i++)
        {
            storeSalseList.add(inputStream.readInt());
        }
        for(int i = 0; i<size1; i++)
        {
            System.out.println(storeList.get(i) + "매출 : " + storeSalseList.get(i));
        }
    }
    public void receiveOrderList(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size>0)
        {
            for(int i = 0; i<size; i++)
            {
                Orderhistory orderhistory = Orderhistory.readOrderhistory(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(orderhistory);
            }
        }
        else
            System.out.println("주문이 없습니다.");
    }
    public boolean receiveMenuOptionList(DataInputStream inputStream) throws IOException {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Menuoption review = Menuoption.readMenuoption(inputStream);
                System.out.println(review);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    public void receiveReviewList(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Review review = Review.readReview(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(review);
            }
        }
        else
            System.out.println("리뷰가 없습니다.");

    }
    public boolean receiveCanCanCelList(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Orderhistory orderhistory = Orderhistory.readOrderhistory(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(orderhistory);
            }
            return true;
        }
        else {
            System.out.println("주문이 없습니다.");
            return false;
        }
    }
    public void receiveCanCanCelOrderhistoryList(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Orderhistory orderhistory = Orderhistory.readOrderhistory(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(orderhistory);
            }
        }
        else
            System.out.println("주문이 없습니다.");
    }
    public boolean receiveCanAcceptOrderList(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size >0)
        {
            for(int i = 0; i<size; i++)
            {
                Orderhistory orderhistory = Orderhistory.readOrderhistory(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(orderhistory);
            }
            return true;
        }
        else
            return false;

    }
    public void receiveStateInfo(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);
        int totalMoney = inputStream.readInt();
        int size = inputStream.readInt();
        if(totalMoney > 0 && size > 0)
        {
            System.out.println("총매출 "+ totalMoney);
            for(int i = 0; i<size; i++)
            {
                Menu menu = Menu.readMenu(inputStream);
                System.out.println(menu.getMenuName() +" 총 팔린량 "+ menu.getOrderQuantity());
            }
        }
        else
            System.out.println("매출이 없습니다.");
    }
    public boolean receiveMenuListInfo(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Menu menu = Menu.readMenu(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(menu);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean receiveCanStartOrderList(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Orderhistory orderhistory = Orderhistory.readOrderhistory(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(orderhistory);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean receiveCanEndOrderList(DataInputStream inputStream) throws IOException
    {
        Header header = Header.readHeader(inputStream);

        int size = inputStream.readInt();
        if(size > 0)
        {
            for(int i = 0; i<size; i++)
            {
                Orderhistory orderhistory = Orderhistory.readOrderhistory(inputStream);
                System.out.print((i+1) + ". ");
                System.out.println(orderhistory);
            }
            return true;
        }
        else
        {
            return false;
        }
    }
}
