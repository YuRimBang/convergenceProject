package service;

import org.apache.ibatis.session.SqlSession;
import persistence.MyBatisConnectionFactory;
import persistence.dao.MyMenuDAO;
import persistence.dao.MyOrderhistoryDAO;
import persistence.dto.MenuDTO;
import persistence.dto.StoreDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuService {
    Scanner sc = new Scanner(System.in);
    private MyMenuDAO myMenuDAO = new MyMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());

    public MenuService(MyMenuDAO myMenuDAO) {
        this.myMenuDAO = myMenuDAO;
    }

    public List<StoreDTO> login(String StoreID, String StorePW) {
        List<StoreDTO> storeDTOS = myMenuDAO.login(StoreID, StorePW);
        return storeDTOS;
    }

    public void insertMenu() {
        System.out.println("메뉴를 등록하려는 가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String StorePW = sc.nextLine();
        List<StoreDTO> storeDTOS = login(StoreID, StorePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            System.out.println("메뉴 이름을 입력하시오");
            String MenuName = sc.nextLine();
            System.out.println("메뉴에 대한 간단한 소개를 작성해 주세요");
            String MenuDescription = sc.nextLine();
            System.out.println("메뉴의 타입을 입력해주세요");
            String Type = sc.nextLine();
            System.out.println("가격을 입력해주세요");
            int price = sc.nextInt();
            System.out.println("남은 판매량을 입력해주세요 입력해주세요");
            int RemainingSale = sc.nextInt();
            System.out.println("할인한다면 할인에 대한 정보를 입력해주세요 (단 할인을 원하지 않을시 0을 입력해 주세요)");
            int Discount = sc.nextInt();
            sc.nextLine();
            myMenuDAO.insertMenu(MenuName, 0, price, MenuDescription, StoreID, RemainingSale, Discount, Type);
        }

    }

    public void insertMenu(String StoreID, String MenuName, String MenuDescription,String Type, int price,  int RemainingSale, int Discount)
    {
        myMenuDAO.insertMenu(MenuName, 0, price, MenuDescription, StoreID, RemainingSale, Discount, Type);
    }

    public List<MenuDTO> AllMenu() {
        System.out.println("메뉴를 조회하고자 하는 가게 아이디를 입력하시오");
        String StoreID = sc.nextLine();
        List<MenuDTO> lists = myMenuDAO.allMenu(StoreID);
        return lists;
    }

    public void menuModify() {

        System.out.println("주문을 접수하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        List<StoreDTO> storeDTOS = login(storeID, storePW);

        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            System.out.println("수정하고 싶은 메뉴의 이름을 입력해주세요 ");
            String beforeMenuName = sc.nextLine();
            System.out.println("새로운 메뉴 이름을 입력해주세요");
            String NewMenuName = sc.nextLine();
            System.out.println("수정할 가격을 입력해 주세요 없다면 -1을 입력해주세요");
            int price = sc.nextInt();
            myMenuDAO.updataMenu(storeID, beforeMenuName, NewMenuName, price);
        }
    }

    public boolean menuModify(String storeID, String storePW, String menuName, String newMenuName, int price) {
        List<StoreDTO> storeDTOS = login(storeID, storePW);

        if (storeDTOS.size() == 0)
            return false;
        else {
            myMenuDAO.updataMenu(storeID, menuName, newMenuName, price);
            return true;
        }
    }

    public void discountModify() {
        System.out.println("주문을 접수하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        List<StoreDTO> storeDTOS = login(storeID, storePW);

        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else {
            System.out.println("수정하고 싶은 메뉴의 이름을 입력해주세요 ");
            String menuName = sc.nextLine();
            System.out.println("이 메뉴를 얼마나 할인 하시겠습니까?(단위 : 원)");
            int discount = sc.nextInt();
            myMenuDAO.updateDiscount(storeID, menuName, discount);
        }
    }

    public List<MenuDTO> oneStoreMenus(String StoreID){
        List<MenuDTO>lists=myMenuDAO.oneStoreMenus(StoreID);
        return lists;
    }

    public boolean discountModify(String storeID, String storePW, String menuName, int discount) {
        List<StoreDTO> storeDTOS = login(storeID, storePW);

        if (storeDTOS.size() == 0)
            return false;
        else {
            myMenuDAO.updateDiscount(storeID, menuName, discount);
            return true;
        }
    }
}
