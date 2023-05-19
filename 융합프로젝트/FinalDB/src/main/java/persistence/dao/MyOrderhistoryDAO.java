package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MyOrderhistoryDAO {
    private final SqlSessionFactory sqlSessionFactory;
    Scanner sc = new Scanner(System.in);
    private SqlSession session;

    public MyOrderhistoryDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
public void receiptComplete(String ClientID, String StoreID, String OrderState){
    SqlSession session = sqlSessionFactory.openSession();//true를 하면 자동 저장가능. commit rollback
    Map<Object,String>map=new HashMap<>();
    map.put("ClientID",ClientID);
    map.put("StoreID",StoreID);
    map.put("OrderState",OrderState);
    try {
        session.update("mapper.OrderhistoryMapper.receiptComplete",map);
        session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
    } finally {
        session.rollback(); //!!
        session.close();
    }
}

public void orderCancel(String ClientID, String StoreID,LocalDateTime deliveryDate){
    SqlSession session = sqlSessionFactory.openSession();//true를 하면 자동 저장가능. commit rollback
    Map<Object,Object>map=new HashMap<>();

    map.put("ClientID",ClientID);
    map.put("StoreID",StoreID);
    map.put("DeliveryDate",deliveryDate);
    try {
        session.update("mapper.OrderhistoryMapper.orderCancel",map);
        session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
    } finally {
        session.rollback(); //!!
        session.close();
    }

}
public void orderReject(String ClientID, String StoreID){
    SqlSession session = sqlSessionFactory.openSession();//true를 하면 자동 저장가능. commit rollback
    Map<Object,Object>map=new HashMap<>();

    map.put("ClientID",ClientID);
    map.put("StoreID",StoreID);
    try {
        session.update("mapper.OrderhistoryMapper.orderReject",map);
        session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
    } finally {
        session.rollback(); //!!
        session.close();
    }
}

    public List<OrderhistoryDTO> selectAll() {
        List<OrderhistoryDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            dtos = session.selectList("mapper.OrderhistoryMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }
    public List<OrderhistoryDTO>storeOrderCheck(String StoreID){
        List<OrderhistoryDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            dtos = session.selectList("mapper.OrderhistoryMapper.storeOrderCheck",StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }
    public List<MenuDTO> findMenuName(String StoreID) {
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollbac
     List<MenuDTO>menus=new ArrayList<>();

        try {
            menus = session.selectList("mapper.MenuMapper.findMenus", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }

        return menus;
    }
    public List<MenuoptionDTO>options(String StoreID){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollbac
        List<MenuoptionDTO>menus=new ArrayList<>();

        try {
            menus = session.selectList("mapper.MenuoptionMapper.storeOptions", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }

        return menus;
    }
    public List<MenuoptionDTO>oneStoreMenus(String StoreID){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollbac
        List<MenuoptionDTO>menus=new ArrayList<>();

        try {
            menus = session.selectList("mapper.MenuMapper.findMenus", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }

        return menus;
    }
   public List<OrderhistoryDTO>findStorehistory(String StoreID){
       List<OrderhistoryDTO> list = new ArrayList<>();

       SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
       try {
           list = session.selectList("mapper.OrderhistoryMapper.findStoreOrderhistory",StoreID);
           session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
       } finally {
           session.rollback(); //!!
           session.close();
       }
       return list;
   }
    public List<OrderhistoryDTO>storeTotalMoneyCheck(String StoreID, String MenuName){
        List<OrderhistoryDTO> dtos = new ArrayList<>();
        Map<Object,String>list=new HashMap<>();
        list.put("StoreID",StoreID);
        list.put("MenuName",MenuName);
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            dtos = session.selectList("mapper.OrderhistoryMapper.storeOrderCheck",list);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }
    public List<OrderhistoryDTO> findOrders() {
        List<OrderhistoryDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            dtos = session.selectList("mapper.OrderhistoryMapper.writeReview");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }

    public void newOrder(String ClientID, String StoreName, String MenuName, String options, LocalDateTime time) {
        SqlSession session = sqlSessionFactory.openSession();
        OrderhistoryDTO orderhistoryDTO = new OrderhistoryDTO();

        String[] option = options.split("/");
        int totalPrice = 0;
        List<MenuDTO> menuDTOS = new ArrayList<>();
        List<StoreDTO> storeDTOS = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();

        storeDTOS = session.selectList("mapper.StoreMapper.findStoreID", StoreName);
        String StoreID = storeDTOS.get(0).getStoreID();
        map.put("StoreID", StoreID);
        map.put("MenuName", MenuName);

        menuDTOS = session.selectList("mapper.MenuMapper.findPrice", map);

        if (menuDTOS.get(0).getRemainingSale() == 0)
            System.out.println("재료소진으로 주문이 취소되었습니다");
        else {

            menuDTOS = session.selectList("mapper.MenuMapper.findPrice", map);
            totalPrice += menuDTOS.get(0).getPrice();

            if(!options.equals("no")) {
                for (int i = 0; i < option.length; i++) {
                    Map<String, Object> param = new HashMap<>();
                    param.put("StoreID", StoreID);
                    param.put("MenuName", MenuName);
                    param.put("Options", option[i]);
                    List<MenuoptionDTO> menuoptionDTOS = new ArrayList<>();
                    menuoptionDTOS = session.selectList("mapper.MenuoptionMapper.findMenu", param);
                    totalPrice += menuoptionDTOS.get(0).getPrice();
                }
            }
            if (menuDTOS.get(0).getDiscount() > 0) {
                totalPrice = totalPrice - menuDTOS.get(0).getDiscount();
            }
            orderhistoryDTO.setClientID(ClientID);
            orderhistoryDTO.setDeliveryDate(time); //현재 시간 넣기
            orderhistoryDTO.setStoreID(StoreID);
            orderhistoryDTO.setMenu(MenuName);
            orderhistoryDTO.setTotalPrice(totalPrice);
            orderhistoryDTO.setOrderState("접수대기");
            if(options.equals("no"))
                orderhistoryDTO.setOptions("");
            else
                orderhistoryDTO.setOptions(options);
            try {
                session.insert("mapper.OrderhistoryMapper.newOrder", orderhistoryDTO);
                Map<String, Object> map1 = new HashMap<>();
                map1.put("StoreID", StoreID);
                map1.put("MenuName", MenuName);

                session.update("mapper.MenuMapper.remiainingdown", map1);
                session.update("mapper.MenuMapper.updateOrderQuentity", map1);
                session.commit();
            } finally {
                session.rollback();
                session.close();
            }
        }
}


    public List<Map<String, Object>> checkOrder() {
        List<OrderhistoryDTO> dtos = new ArrayList<>();
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> mapList = new HashMap<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            dtos = session.selectList("mapper.OrderhistoryMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
            LocalDateTime localDateTime = dtos.get(0).getDeliveryDate();
            int totalPrice = 0;
            for (int i = 0; i < dtos.size(); i++) {
                if (localDateTime != dtos.get(i).getDeliveryDate()) {
                    mapList.put("StoreID", dtos.get(i - 1).getStoreID());
                    mapList.put("MenuName", dtos.get(i - 1).getMenu());
                    mapList.put("Options", dtos.get(i - 1).getOptions());
                    mapList.put("totalPrice", Integer.toString(totalPrice));
                    mapList.put("OrderState", dtos.get(i - 1).getOrderState());
                    list.add(mapList);
                } else {
                    totalPrice += dtos.get(i).getTotalPrice();

                }

            }

            if (totalPrice != 0)
                mapList.put("StoreID", dtos.get(dtos.size() - 1).getStoreID());
            mapList.put("MenuName", dtos.get(dtos.size() - 1).getMenu());
            mapList.put("Options", dtos.get(dtos.size() - 1).getOptions());
            mapList.put("totalPrice", Integer.toString(totalPrice));
            mapList.put("StoreStates", dtos.get(dtos.size() - 1).getOrderState());
            list.add(mapList);
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public void orderCancel(String ClientID, LocalDateTime DeliveryDate, String StoreName) {
        Map<String, Object> map2 = new HashMap<>();
        List<OrderhistoryDTO> orderhistoryDTOS = null;
        List<StoreDTO> storeDTOS = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession();
        try {
            storeDTOS = session.selectList("mapper.StoreMapper.findStoreID", StoreName);
            String StoreID = storeDTOS.get(0).getStoreID();
            map2.put("ClientID", ClientID);
            map2.put("DeliveryDate", DeliveryDate);
            map2.put("StoreID", StoreID);
            orderhistoryDTOS = session.selectList("mapper.OrderhistoryMapper.isDeliveryStart", map2);
            for (int i = 0; i < orderhistoryDTOS.size(); i++) {
                if (orderhistoryDTOS.get(i).getOrderState().equals("배달중")) {
                    System.out.println("이미 배달중인 주문은 취소가 불가능합니다.");
                    break;
                } else {
                    map2.put("changeOrderState", "주문취소");
                    map2.put("OrderState", "접수대기");
                    session.update("mapper.OrderhistoryMapper.changeOrderState", map2);
                }
            }
            session.commit();
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }

    //    public void orderReception(String ClientID, LocalDateTime DeliveryDate, String StoreID) {
//        Map<String, Object> map = new HashMap<>();
//
//        SqlSession session = sqlSessionFactory.openSession();
//
//        String delivery = "배달중";
//        map.put("ClientID", ClientID);
//        map.put("DeliveryDate", DeliveryDate);
//        map.put("StoreID", StoreID);
//        map.put("changeOrderState", delivery);
//        map.put("OrderState", "접수대기");
//        try {
//            session.update("mapper.OrderhistoryMapper.changeOrderState", map);
//            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
//        } finally {
//            session.rollback(); //!!
//            session.close();
//        }
//    }
    public void orderReception(String ClientID, LocalDateTime DeliveryDate, String StoreID) {
        Map<String, Object> map = new HashMap<>();

        SqlSession session = sqlSessionFactory.openSession();

        String delivery = "접수완료";
        map.put("ClientID", ClientID);
        map.put("DeliveryDate", DeliveryDate);
        map.put("StoreID", StoreID);
        map.put("changeOrderState", delivery);
        map.put("OrderState", "접수대기");
        try {
            session.update("mapper.OrderhistoryMapper.changeOrderState", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }

    public void deliveryFinish(String ClientID, String StoreID, LocalDateTime DeliveryDate) {
        Map<String, Object> map = new HashMap<>();

        SqlSession session = sqlSessionFactory.openSession();

        String delivery = "배달완료";

        map.put("ClientID", ClientID);
        map.put("OrderState", delivery);
        map.put("beforeOrderState", "배달중");
        map.put("StoreID", StoreID);
        map.put("DeliveryDate", DeliveryDate);
        try {
            session.update("mapper.OrderhistoryMapper.deliveryFinish", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }

    public List<OrderhistoryDTO> customerCheckOrder(String ClientID) {
        List<OrderhistoryDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.OrderhistoryMapper.customerOrderCheck", ClientID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!

        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public List<OrderhistoryDTO> findOrderWait(String StoreID) {
        List<OrderhistoryDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.OrderhistoryMapper.selectOrderWait", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!

        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public List<OrderhistoryDTO> findOrderDelibery(String StoreID) {
        List<OrderhistoryDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.OrderhistoryMapper.selectOrderDelivery", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!

        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public List<OrderhistoryDTO> findCancelAndFinishClient(String clientID) {
        List<OrderhistoryDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.OrderhistoryMapper.selectCancelAndFinish", clientID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!

        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public List<OrderhistoryDTO> findClientWaiting(String ClientID) {
        List<OrderhistoryDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.OrderhistoryMapper.selectClientWaiting", ClientID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!

        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public List<OrderhistoryDTO> findClientDelivery(String ClientID) {
        List<OrderhistoryDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.OrderhistoryMapper.selectClientDelivery", ClientID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!

        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public List<OrderhistoryDTO> findOrderDone(String StoreID)
    {
        List<OrderhistoryDTO>list=new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.OrderhistoryMapper.selectOrderDone", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!

        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public void changeWaitingToDone(String ClientID, String StoreID, LocalDateTime DeliveryDate)
    {
        Map<String, Object> map = new HashMap<>();

        SqlSession session = sqlSessionFactory.openSession();

        String delivery = "접수완료";

        map.put("ClientID", ClientID);
        map.put("OrderState", delivery);
        map.put("beforeOrderState", "접수대기");
        map.put("StoreID", StoreID);
        map.put("DeliveryDate", DeliveryDate);
        try {
            session.update("mapper.OrderhistoryMapper.ReceptionDone", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }

    public void changeDonetoDelivery(String ClientID, String StoreID) {
        Map<String, Object> map = new HashMap<>();

        SqlSession session = sqlSessionFactory.openSession();

        String delivery = "배달중";

        map.put("ClientID", ClientID);
        map.put("changeOrderState", delivery);
        map.put("OrderState", "접수완료");
        map.put("StoreID", StoreID);
        try {
            session.update("mapper.OrderhistoryMapper.changeOrderState", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }
    }


