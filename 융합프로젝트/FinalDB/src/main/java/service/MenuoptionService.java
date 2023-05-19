package service;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyMenuDAO;
import persistence.dao.MyMenuoptionDAO;
import persistence.dto.MenuoptionDTO;
import persistence.dto.StoreDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuoptionService {
    Scanner sc=new Scanner(System.in);
    MyMenuoptionDAO myMenuoptionDAO=new MyMenuoptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
   MyMenuDAO myMenuDAO=new MyMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
   MenuService menuService=new MenuService(myMenuDAO);
    public MenuoptionService(MyMenuoptionDAO myMenuoptionDAO){
        this.myMenuoptionDAO=myMenuoptionDAO;
    }
    public void insertMenuOption(){
        System.out.print("메뉴 옵션을 등록하고자 하는 가게의 아이디를 입력해주세요: ");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요: ");
        String StorePW=sc.nextLine();
        List<StoreDTO> storeDTOS= menuService.login(StoreID,StorePW);
        if(storeDTOS.size()==0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");
        else{
            System.out.println("옵션을 추가하고자 하는 메뉴이름을 입력해주세요");
            String MenuName=sc.nextLine();
            System.out.print("옵션을 입력하세요. : ");
            String Options = sc.nextLine();
            System.out.println("");
            System.out.println("가격을 입력하시오 ");
            int price=sc.nextInt();
            myMenuoptionDAO.insertMenuoption(StoreID,MenuName,Options,price);
        }
    }

    public boolean insertMenuOption(String StoreID, String StorePW, String MenuName, String Options, int price){
        List<StoreDTO> storeDTOS= menuService.login(StoreID,StorePW);
        if(storeDTOS.size()==0)
            return false;
        else{
            myMenuoptionDAO.insertMenuoption(StoreID,MenuName,Options,price);
            return true;
        }
    }

    public List<MenuoptionDTO> sendMenuOptionList(String StoreID, String MenuName)
    {
        List <MenuoptionDTO> allMenuOptionList = myMenuoptionDAO.selectAll();
        List<MenuoptionDTO> menuOptionLIst = new ArrayList<>();

        for(int i=0; i < allMenuOptionList.size(); i++)
        {
            if (allMenuOptionList.get(i).getStoreID().equals(StoreID) && allMenuOptionList.get(i).getMenuName().equals(MenuName))
            {
                menuOptionLIst.add(allMenuOptionList.get(i));
            }
        }

        return menuOptionLIst;
    }
}
