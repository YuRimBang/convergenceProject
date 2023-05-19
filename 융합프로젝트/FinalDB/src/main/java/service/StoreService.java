package service;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyMenuDAO;
import persistence.dao.MyOrderhistoryDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.OrderhistoryDTO;
import persistence.dto.ReviewDTO;
import persistence.dto.StoreDTO;

import java.util.*;

public class StoreService
{
    Scanner sc = new Scanner(System.in);
    MyStoreDAO myStoreDAO = new MyStoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    MyMenuDAO myMenuDAO = new MyMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    MenuService menuService = new MenuService(myMenuDAO);

    public StoreService(MyStoreDAO myStoreDAO) {
        this.myStoreDAO = myStoreDAO;
    }

    //매장등록:등록과 동시에 조회가능해야함
/*    public void storeRegistration() {
        List<StoreDTO> storeDTOS = new ArrayList<>();
        StoreDTO storeDTO = new StoreDTO();
        System.out.println("가게 아이디를 입력하세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력하세요");
        String StorePW = sc.nextLine();
        System.out.println("가게이름을 입력해주세요");
        String StoreName = sc.nextLine();
        System.out.println("가게 주소를 입력하세요");
        String StoreAdress = sc.nextLine();
        System.out.println("가게 전화번호를 입력하세요");
        String StorePhoneNumber = sc.nextLine();
        System.out.println("가게 주인 아이디를 입력하세요");
        String OwnerID = sc.nextLine();
        System.out.println("간단한 가게 소개를 입력해주세요");
        String Introduce = sc.nextLine();
        System.out.println("운영시간을 입력해주세요");
        String operatingTime = sc.nextLine();
        myStoreDAO.storeRegistration(StoreName, StoreID, StorePW, StoreAdress, StorePhoneNumber, OwnerID, Introduce, operatingTime);
    }*/

    public void storeRegistration(String StoreName, String StoreID, String StorePW, String StoreAdress, String StorePhoneNumber, String OwnerID, String Introduce, String operatingTime) {
        myStoreDAO.storeRegistration(StoreName, StoreID, StorePW, StoreAdress, StorePhoneNumber, OwnerID, Introduce, operatingTime);
    }

    public void changeOperatingTime() {
        Map<Object, String> list = new HashMap<>();
        System.out.println("운영시간을 변경할 가게 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀면호를 입력해주세요");
        String StorePW = sc.nextLine();
        List<StoreDTO> storeDTOS = menuService.login(StoreID, StorePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            list.put("StoreID", StoreID);
            System.out.println("번경할 운영시간을 입력해주세요");
            String operatingTime = sc.nextLine();
            list.put("Operatingtime", operatingTime);
            myStoreDAO.changeOperatingTime(list);
        }
    }

    public boolean changeOperatingTime(String storeID, String storePW, String operatingTime) {
        Map<Object, String> list = new HashMap<>();

        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            return false;
        else {
            list.put("StoreID", storeID);
            list.put("Operatingtime", operatingTime);
            myStoreDAO.changeOperatingTime(list);
            return true;
        }
    }

    //전체 매장 조회
    public List<StoreDTO> showStore() {
        List<StoreDTO> storeDTOS = new ArrayList<>();
        storeDTOS = myStoreDAO.AllSotre();
        return storeDTOS;
    }

    public List<StoreDTO> showOneStroe() {
        List<StoreDTO> storeDTOS = new ArrayList<>();
        System.out.println("조회하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            storeDTOS = myStoreDAO.selectOne(storeID);
        }

        return storeDTOS;
    }

    public List<StoreDTO> showOneStore(String storeID, String storePW)
    {
        List<StoreDTO> storeDTOS = new ArrayList<>();

        storeDTOS = menuService.login(storeID, storePW);
        storeDTOS = myStoreDAO.selectOne(storeID);

        return storeDTOS;
    }

    public StoreDTO findStore() {
        System.out.println("찾고자 하는 가게 이름을 입력하세요");
        String StoreName = sc.nextLine();
        List<StoreDTO> storeDTOS = myStoreDAO.storeCheck(StoreName);
        return storeDTOS.get(0);
    }

    public StoreDTO findStore(String storeName)
    {
        List<StoreDTO> storeDTOS = myStoreDAO.storeCheck(storeName);
        return storeDTOS.get(0);
    }

    public List<ReviewDTO> oneStoreReview(String storeID) {
        List<ReviewDTO> reviews = myStoreDAO.oneStoreReview(storeID);
        return reviews;
    }

    public List<OrderhistoryDTO> showStoreOrder()
    {
        MyOrderhistoryDAO myOrderhistoryDAO = new MyOrderhistoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        List<OrderhistoryDTO> storeOrders = new ArrayList<>();


        System.out.println("가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        List<StoreDTO> storeDTOS = menuService.login(storeID, storePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            storeOrders = myOrderhistoryDAO.findStorehistory(storeID);
        }

        return storeOrders;
    }
    public List<StoreDTO>allStores(){
        List<StoreDTO>storeDTOS=myStoreDAO.selectAll();
        return storeDTOS;
    }

}

