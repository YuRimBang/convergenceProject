package service;

import persistence.MyBatisConnectionFactory;
import persistence.dao.*;
import persistence.dto.*;

import java.util.*;

public class ManagerService 
{
    Scanner sc = new Scanner(System.in);
    
    MyManagerDAO myManagerDAO = new MyManagerDAO(MyBatisConnectionFactory.getSqlSessionFactory());

    public  ManagerService(MyManagerDAO myManagerDAO)
    {
        this.myManagerDAO = myManagerDAO;
    }

    public List<CustomerDTO> showCustomerInformation() //고객 정보 조회
    {
        MyCustomerDAO myCustomerDAO = new MyCustomerDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS = myCustomerDAO.selectAll();

        return customerDTOS;
    }

    public List<OwnerDTO> showOwnerInformation() //점주 정보 조회
    {
        MyOwnerDAO myOwnerDAO = new MyOwnerDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        List<OwnerDTO> ownerDTOS = new ArrayList<>();
        ownerDTOS = myOwnerDAO.selectAll();

        return ownerDTOS;
    }

    public List<StoreDTO> showStoreInformation()
    {
        MyStoreDAO myStoreDAO = new MyStoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        List<StoreDTO> storeDTOS = new ArrayList<>();
        storeDTOS = myStoreDAO.selectAll();

        return storeDTOS;
    }

    public List<ManagerDTO> managerLogin(String id, String pw)
    {
        List<ManagerDTO> list = new ArrayList<>();
        list = myManagerDAO.checkManagerLogin(id, pw);

        return list;
    }

//    public List<OrderhistoryDTO> showStoreSale()
//    {
//        MyOrderhistoryDAO myOrderhistoryDAO = new MyOrderhistoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());
//
//        List<OrderhistoryDTO> orderhistoryDTOS = new ArrayList<>();
//
//    }
}
