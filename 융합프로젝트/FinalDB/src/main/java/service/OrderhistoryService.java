package service;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyMenuDAO;
import persistence.dao.MyOrderhistoryDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.MenuDTO;
import persistence.dto.MenuoptionDTO;
import persistence.dto.OrderhistoryDTO;
import persistence.dto.StoreDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class OrderhistoryService {

    private MyOrderhistoryDAO myOrderhistoryDAO = new MyOrderhistoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    Scanner sc = new Scanner(System.in);
    MyMenuDAO myMenuDAO = new MyMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    MenuService menuService = new MenuService(myMenuDAO);

    MyStoreDAO myStoreDAO = new MyStoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    StoreService storeService = new StoreService(myStoreDAO);

    public OrderhistoryService(MyOrderhistoryDAO myOrderhistoryDAO) {
        this.myOrderhistoryDAO = myOrderhistoryDAO;
    }

    public List<OrderhistoryDTO> showOrderhistoryService() {
        List<OrderhistoryDTO> orderhistoryDTOList = new ArrayList<>();
        System.out.println("고객님의 아이디를 입력해주세요 ");
        String clientID = sc.next();
        orderhistoryDTOList = myOrderhistoryDAO.customerCheckOrder(clientID);

        return orderhistoryDTOList;
    }

    public List<OrderhistoryDTO> showOrderhistoryService(String clientID) {
        List<OrderhistoryDTO> orderhistoryDTOList = new ArrayList<>();
        orderhistoryDTOList = myOrderhistoryDAO.customerCheckOrder(clientID);

        return orderhistoryDTOList;
    }

    public void createOrders(String ClientID) {
        System.out.println("주문하고 싶은 가게 이름을 입력하시오");
        String StoreName = sc.nextLine();
        LocalDateTime deliveryTime = LocalDateTime.now();
        String strDeliveryTime = deliveryTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
        String nowHour;
        if (strDeliveryTime.charAt(11) == '0')
            nowHour = String.valueOf(strDeliveryTime.charAt(12));
        else
            nowHour = String.valueOf(strDeliveryTime.charAt(11)) + String.valueOf(strDeliveryTime.charAt(12));
        String nowMinute;
        if (strDeliveryTime.charAt(14) == '0')
            nowMinute = String.valueOf(strDeliveryTime.charAt(15));
        else
            nowMinute = String.valueOf(strDeliveryTime.charAt(14)) + String.valueOf(strDeliveryTime.charAt(15));
        String operatingTime = storeService.findStore(StoreName).getOperatingtime();
        String startHour;
        String startMinute;
        String finishHour;
        String finishMinute;
        if (operatingTime.charAt(0) == '0')
            startHour = String.valueOf(operatingTime.charAt(1));
        else
            startHour = String.valueOf(operatingTime.charAt(0)) + String.valueOf(operatingTime.charAt(1));
        if (operatingTime.charAt(3) == '0')
            startMinute = String.valueOf(operatingTime.charAt(4));
        else
            startMinute = String.valueOf(3) + String.valueOf(operatingTime.charAt(4));
        if (operatingTime.charAt(6) == '0')
            finishHour = String.valueOf(operatingTime.charAt(7));
        else
            finishHour = String.valueOf(operatingTime.charAt(6)) + String.valueOf(operatingTime.charAt(7));
        if (operatingTime.charAt(9) == '0')
            finishMinute = String.valueOf(operatingTime.charAt(10));
        else
            finishMinute = String.valueOf(operatingTime.charAt(9)) + String.valueOf(operatingTime.charAt(10));
        boolean isInput = false;

        if (Integer.parseInt(nowHour) == Integer.parseInt(startHour)) {
            if (Integer.parseInt(nowMinute) > Integer.parseInt(startMinute))
                isInput = true;
        } else if (Integer.parseInt(nowHour) == Integer.parseInt(finishHour)) {
            if (Integer.parseInt(nowMinute) < Integer.parseInt(finishMinute)) {
                isInput = true;
            }
        } else {
            if (Integer.parseInt(nowHour) > Integer.parseInt(startHour) && Integer.parseInt(nowHour) < Integer.parseInt(finishHour))
                isInput = true;
        }

        if (isInput == true) {
            System.out.println("Menu를 입력하시오 ");
            String Menu = sc.nextLine();
            System.out.println("options를 입력하시오 구분하기위해 /를 넣어주세용 ㅎㅎ(옵션을 추가하고 싶지 않다면 no를 입력해주세요");
            String options = sc.nextLine();

            myOrderhistoryDAO.newOrder(ClientID, StoreName, Menu, options, deliveryTime);
            while (true) {
                System.out.println("Menu를 입력하시오 (더이상 메뉴를 고르고 싶지 않다면 no를 입력해주세요");
                Menu = sc.nextLine();
                if (Menu.equals("no"))
                    break;
                else {

                    System.out.println("options를 입력하시오 구분하기위해 /를 넣어주세용 ㅎㅎ(옵션을 추가하고 싶지 않다면 no를 입력해주세요");
                    options = sc.nextLine();
                    myOrderhistoryDAO.newOrder(ClientID, StoreName, Menu, options, deliveryTime);
                }
            }
        } else {
            System.out.println("영업시간이 지나 주문을 할 수 없습니다.");
        }

    }

    public boolean createOrders(String ClientID, String storeName, String menu, String option) {
        LocalDateTime deliveryTime = LocalDateTime.now();
        String strDeliveryTime = deliveryTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SSS"));
        String nowHour;
        if (strDeliveryTime.charAt(11) == '0')
            nowHour = String.valueOf(strDeliveryTime.charAt(12));
        else
            nowHour = String.valueOf(strDeliveryTime.charAt(11)) + String.valueOf(strDeliveryTime.charAt(12));
        String nowMinute;
        if (strDeliveryTime.charAt(14) == '0')
            nowMinute = String.valueOf(strDeliveryTime.charAt(15));
        else
            nowMinute = String.valueOf(strDeliveryTime.charAt(14)) + String.valueOf(strDeliveryTime.charAt(15));
        String operatingTime = storeService.findStore(storeName).getOperatingtime();
        String startHour;
        String startMinute;
        String finishHour;
        String finishMinute;
        if (operatingTime.charAt(0) == '0')
            startHour = String.valueOf(operatingTime.charAt(1));
        else
            startHour = String.valueOf(operatingTime.charAt(0)) + String.valueOf(operatingTime.charAt(1));
        if (operatingTime.charAt(3) == '0')
            startMinute = String.valueOf(operatingTime.charAt(4));
        else
            startMinute = String.valueOf(3) + String.valueOf(operatingTime.charAt(4));
        if (operatingTime.charAt(6) == '0')
            finishHour = String.valueOf(operatingTime.charAt(7));
        else
            finishHour = String.valueOf(operatingTime.charAt(6)) + String.valueOf(operatingTime.charAt(7));
        if (operatingTime.charAt(9) == '0')
            finishMinute = String.valueOf(operatingTime.charAt(10));
        else
            finishMinute = String.valueOf(operatingTime.charAt(9)) + String.valueOf(operatingTime.charAt(10));
        boolean isInput = false;

        if (Integer.parseInt(nowHour) == Integer.parseInt(startHour)) {
            if (Integer.parseInt(nowMinute) > Integer.parseInt(startMinute))
                isInput = true;
        } else if (Integer.parseInt(nowHour) == Integer.parseInt(finishHour)) {
            if (Integer.parseInt(nowMinute) < Integer.parseInt(finishMinute)) {
                isInput = true;
            }
        } else {
            if (Integer.parseInt(nowHour) > Integer.parseInt(startHour) && Integer.parseInt(nowHour) < Integer.parseInt(finishHour))
                isInput = true;
        }

        if (isInput)
            myOrderhistoryDAO.newOrder(ClientID, storeName, menu, option, LocalDateTime.now());

        return isInput;
    }

    public int totalMoney(String StoreID) {
        List<OrderhistoryDTO> orderhistoryDTOS = myOrderhistoryDAO.storeOrderCheck(StoreID);
        int sum = 0;
        for (int i = 0; i < orderhistoryDTOS.size(); i++)
            sum += orderhistoryDTOS.get(i).getTotalPrice();
        return sum;
    }

    public List<MenuDTO> findMenuName(String StoreID) {
        List<MenuDTO> menus = myOrderhistoryDAO.findMenuName(StoreID);
        return menus;
    }

    public List<Map<String, Object>> checkOrder() {
        List<Map<String, Object>> orders = myOrderhistoryDAO.checkOrder();
        return orders;
    }

    public List<OrderhistoryDTO> selectAll() {
        List<OrderhistoryDTO> orders = myOrderhistoryDAO.selectAll();
        return orders;
    }

    public void orderRecption() {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        System.out.println("주문을 접수하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            String clientID = "";
            List<OrderhistoryDTO> list = new ArrayList<>();

            while (!(clientID.equals("no"))) {
                orders = myOrderhistoryDAO.findOrderDone(storeID);
                if (orders.size() > 0) {
                    System.out.println("========현재 접수완료인 주문=======");
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println(orders.get(i).getClientID() + " " + orders.get(i).getMenu() + " " + orders.get(i).getOptions() +
                                " " + orders.get(i).getTotalPrice() + " " + orders.get(i).getDeliveryDate() + " " + orders.get(i).getOrderState());
                    }

                    System.out.println("배달시작한  고객의 아이디를 입력해주세요.(아니면 no를 입력해주세요)");
                    clientID = sc.next();

                    if (!(clientID.equals("no"))) {
                        list = myOrderhistoryDAO.findOrderDone(clientID);
                        System.out.println(list.size());
                        for (int j = 0; j < list.size(); j++) {
                            myOrderhistoryDAO.changeDonetoDelivery(clientID, list.get(j).getStoreID());
                        }
                    }
                } else {
                    System.out.println("접수완료인 주문이 없습니다");
                    break;
                }
            }
        }
    }

    public boolean orderRecption(String storeID, String storePW, String clientID) {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            return false;
        else {
            System.out.println(storeID+ " " + clientID);
            List<OrderhistoryDTO> list = new ArrayList<>();

            orders = myOrderhistoryDAO.findOrderDone(storeID);

            if (orders.size() > 0) {

                list = myOrderhistoryDAO.findOrderDone(storeID);
                System.out.println(list.size());

                for (int j = 0; j < list.size(); j++) {
                    myOrderhistoryDAO.changeDonetoDelivery(clientID, list.get(j).getStoreID());
                }
                return true;
            } else {
                return false;
            }
        }
    }


    public List<OrderhistoryDTO> orderRecption(String storeID, String storePW) {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);

        List<OrderhistoryDTO> list = new ArrayList<>();
        orders = myOrderhistoryDAO.findOrderDone(storeID);

        return orders;
    }

    public void orderCancel(String ClientID) {

        List<OrderhistoryDTO> canCancel = myOrderhistoryDAO.findClientWaiting(ClientID);
        for (int i = 0; i < canCancel.size(); i++) {
            System.out.println(i + " " + canCancel.get(i).toString());
        }
        System.out.println("주문을 취소할 주문의 번호를 입력해주세요");
        int num = sc.nextInt();
        sc.nextLine();
        myOrderhistoryDAO.orderCancel(ClientID, canCancel.get(num).getStoreID(), canCancel.get(num).getDeliveryDate());
    }

    public void orderCancel(String ClientID, int number) {
        List<OrderhistoryDTO> canCancel = myOrderhistoryDAO.findClientWaiting(ClientID);
        myOrderhistoryDAO.orderCancel(ClientID, canCancel.get(number).getStoreID(), canCancel.get(number).getDeliveryDate());
    }

    public void changeOrderFinish() {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        System.out.println("주문을 접수하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            String clientID = "";
            List<OrderhistoryDTO> list = new ArrayList<>();

            while (!(clientID.equals("no"))) {
                orders = myOrderhistoryDAO.findOrderDelibery(storeID);
                if (orders.size() > 0) {
                    System.out.println("========현재 배달 중인 주문=======");
                    for (int i = 0; i < orders.size(); i++) {
                        System.out.println(orders.get(i).getClientID() + " " + orders.get(i).getMenu() + " " + orders.get(i).getOptions() +
                                " " + orders.get(i).getTotalPrice() + " " + orders.get(i).getDeliveryDate() + " " + orders.get(i).getOrderState());
                    }

                    System.out.println("접수를 허락할 고객의 아이디를 입력해주세요.(접수를 그만 하시려면 no를 입력해주세요)");
                    clientID = sc.next();

                    if (!(clientID.equals("no"))) {
                        list = myOrderhistoryDAO.findClientDelivery(clientID);
                        for (int j = 0; j < list.size(); j++) {
                            myOrderhistoryDAO.deliveryFinish(clientID, list.get(j).getStoreID(), list.get(j).getDeliveryDate());
                        }
                    }
                } else {
                    System.out.println("배달 중인 주문이 없습니다");
                    break;
                }
            }
        }
    }

    public boolean changeOrderFinish(String storeID, String storePW, String clientID) {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            return false;
        else {
            List<OrderhistoryDTO> list = new ArrayList<>();

            orders = myOrderhistoryDAO.findOrderDelibery(storeID);
            if (orders.size() > 0) {
                list = myOrderhistoryDAO.findClientDelivery(clientID);
                for (int j = 0; j < list.size(); j++) {
                    myOrderhistoryDAO.deliveryFinish(clientID, list.get(j).getStoreID(), list.get(j).getDeliveryDate());
                }

                return true;
            } else {
                return false;
            }
        }
    }

    public List<OrderhistoryDTO> changeOrderFinishList(String storeID, String storePW) {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);

        return orders = myOrderhistoryDAO.findOrderDelibery(storeID);
    }

    public List<OrderhistoryDTO> orderReceptionDone(String storeID, String storePW) {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);

        if (storeDTOS.size() > 0) {
            orders = myOrderhistoryDAO.findOrderWait(storeID);
        }

        return orders;
    }

    public boolean orderReceptionDone(String storeID, String storePW, int flag, String customerID) {
        boolean orderFlag = true;

        List<OrderhistoryDTO> orders = new ArrayList<>();
        orders = orderReceptionDone(storeID, storePW);

        if (orders.size() > 0) {
            if (flag == 1) //승인
            {
                myOrderhistoryDAO.receiptComplete(customerID, storeID, "접수완료");
                orders = myOrderhistoryDAO.findOrderWait(storeID);
            } else if (flag == 2) //거절
            {
                myOrderhistoryDAO.orderReject(customerID, storeID);
                orders = myOrderhistoryDAO.findOrderWait(storeID);
            } else {
                orderFlag = false;
            }
        } else {
            orderFlag = false;
        }
        return orderFlag;
    }

    public void orderReceptionDone() {
        List<OrderhistoryDTO> orders = new ArrayList<>();
        System.out.println("주문을 접수하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            orders = myOrderhistoryDAO.findOrderWait(storeID);
            String answer = "";

            if (orders.size() > 0) {
                System.out.println("========현재 접수대기 중인 주문=======");
                for (int i = 0; i < orders.size(); i++) {
                    System.out.println(orders.get(i).getClientID() + " " + orders.get(i).getMenu() + " " + orders.get(i).getOptions() +
                            " " + orders.get(i).getTotalPrice() + " " + orders.get(i).getDeliveryDate() + " " + orders.get(i).getOrderState());

                }
                System.out.println("접수를 받을 고객의 아이디를 입력해주세요");
                answer = sc.nextLine();
                while ((!answer.equals("no")) && orders.size() > 0) {
                    myOrderhistoryDAO.receiptComplete(answer, storeID, "접수완료");
                    orders = myOrderhistoryDAO.findOrderWait(storeID);

                    if (orders.size() > 0) {
                        for (int i = 0; i < orders.size(); i++) {
                            System.out.println(orders.get(i).getClientID() + " " + orders.get(i).getMenu() + " " + orders.get(i).getOptions() +
                                    " " + orders.get(i).getTotalPrice() + " " + orders.get(i).getDeliveryDate() + " " + orders.get(i).getOrderState());
                        }
                        System.out.println("접수를 받을 고객의 아이디를 입력해주세요");
                        answer = sc.nextLine();
                    } else {
                        System.out.println("접수대기인 주문이 없습니다");
                    }

                }

                System.out.println("========현재 접수대기 중인 주문=======");
                for (int i = 0; i < orders.size(); i++) {
                    System.out.println(orders.get(i).getClientID() + " " + orders.get(i).getMenu() + " " + orders.get(i).getOptions() +
                            " " + orders.get(i).getTotalPrice() + " " + orders.get(i).getDeliveryDate() + " " + orders.get(i).getOrderState());
                }
                System.out.println("접수를 거절할 고객의 아이디를 입력해주세요");
                answer = sc.nextLine();

                orders = myOrderhistoryDAO.findOrderWait(storeID);
                while (!answer.equals("no") && orders.size() > 0) {
                    myOrderhistoryDAO.orderReject(answer, storeID);
                    orders = myOrderhistoryDAO.findOrderWait(storeID);

                    if (orders.size() > 0) {
                        System.out.println("접수를 거절할 고객의 아이디를 입력해주세요");
                        for (int i = 0; i < orders.size(); i++) {
                            System.out.println(orders.get(i).getClientID() + " " + orders.get(i).getMenu() + " " + orders.get(i).getOptions() +
                                    " " + orders.get(i).getTotalPrice() + " " + orders.get(i).getDeliveryDate() + " " + orders.get(i).getOrderState());

                        }
                        answer = sc.nextLine();
                    } else {
                        System.out.println("접수대기인 주문이 없습니다");
                    }

                }
            } else {
                System.out.println("접수대기인 주문이 없습니다");
            }
        }
    }


    public List<OrderhistoryDTO> orderViewClient(String clientID) {
        List<OrderhistoryDTO> list = new ArrayList<>();

        list = myOrderhistoryDAO.findCancelAndFinishClient(clientID);

        return list;
    }


}
