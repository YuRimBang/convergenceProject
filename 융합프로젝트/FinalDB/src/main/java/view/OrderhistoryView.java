package view;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyOrderhistoryDAO;
import persistence.dto.MenuDTO;
import persistence.dto.OrderhistoryDTO;
import service.OrderhistoryService;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class OrderhistoryView
{ MyOrderhistoryDAO myOrderhistoryDAO=new MyOrderhistoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    OrderhistoryService orderhistoryService=new OrderhistoryService(myOrderhistoryDAO);
    Scanner sc=new Scanner(System.in);
    public void showOrderhistoryClient(List<OrderhistoryDTO> dtos)
    {
        System.out.println("[주문내역]");
        for(int i = 0; i < dtos.size(); i++)
        {
            System.out.println(dtos.get(i).getClientID() + "." + dtos.get(i).getMenu() + ", "+
                    dtos.get(i).getOptions() + ", " + dtos.get(i).getTotalPrice() + "," + dtos.get(i).getOrderState()
            + ", " + dtos.get(i).getDeliveryDate());
        }
    }


    public void ordersViews(List<Map<String, Object>>checkOrder){
        for(int i=0;i< checkOrder.size();i++) {
            Map<String,Object>map=checkOrder.get(i);
            for (Map.Entry<String, Object> entrySet : map.entrySet()) {
                System.out.print(entrySet.getValue());
                if(i!=0 && entrySet.getKey().equals("OrderState"))
                    System.out.println();
            }
        }
    }

    public void showAllOrderhistory(List<OrderhistoryDTO> dtos)
    {
        System.out.println("[주문 내역]"); //데베에 있는 모든 내역을 보여준다. 검사는 데베에서 하는 걸지도

        for(int i = 0; i < dtos.size(); i++)
        {
            System.out.println(dtos.get(i).getClientID() + "." + dtos.get(i).getMenu() + ", "+
                    dtos.get(i).getOptions() + ", " + dtos.get(i).getTotalPrice() + "," + dtos.get(i).getOrderState()
                    + ", " + dtos.get(i).getDeliveryDate());
        }
    }



public void storeTotalMoney(){
    System.out.println("통계를 조회할 가게 아이디를 입력해주세요");
    String storeID=sc.nextLine();
    System.out.println("총매출 "+ orderhistoryService.totalMoney(storeID));
    List<MenuDTO>menus=orderhistoryService.findMenuName(storeID);
    for(int i=0;i< menus.size();i++){
        System.out.println(menus.get(i).getMenuName()+" 총 팔린량 "+menus.get(i).getOrderQuantity());
    }
}

public int storeTotalMoney(String storeID)
{
    return orderhistoryService.totalMoney(storeID);
}

public List<MenuDTO> storeTotalSaleAmount(String storeID)
{
    List<MenuDTO>menus=orderhistoryService.findMenuName(storeID);

    return menus;
}

public void allStoreTotalMoney(String StoreID){
    System.out.println("총매출 "+ orderhistoryService.totalMoney(StoreID));
    List<MenuDTO>menus=orderhistoryService.findMenuName(StoreID);
    for(int i=0;i< menus.size();i++){
        System.out.println(menus.get(i).getMenuName()+" 총 팔린량 "+menus.get(i).getOrderQuantity());
    }
}
}
