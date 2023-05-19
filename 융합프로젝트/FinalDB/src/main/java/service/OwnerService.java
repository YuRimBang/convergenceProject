package service;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyOwnerDAO;
import persistence.dto.CustomerDTO;
import persistence.dto.OwnerDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OwnerService {
    private final MyOwnerDAO ownerDAO;
Scanner sc=new Scanner(System.in);
    MyOwnerDAO myOwnerDAO=new MyOwnerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    public OwnerService(MyOwnerDAO ownerDAO) { //의존성 주입
        this.ownerDAO = ownerDAO;
    }
    public List<OwnerDTO> findAll(){

    List<OwnerDTO> dtos1 =myOwnerDAO.selectAll();
    return dtos1;
    }
    public void ownerRegistration(){
        System.out.println("점주 이름을 입력해주세요");
        String OwnerName=sc.nextLine();
        System.out.println("점주 휴대폰 번호를 입력해주세오");
        String PhoneNumber=sc.nextLine();
        System.out.println("아이디를 입력해주세요");
        String OwnerID=sc.nextLine();
        System.out.println("비밀번호를 입력해주세요");
        String OwnerPW=sc.nextLine();
        myOwnerDAO.ownerRegistration(OwnerName,PhoneNumber,OwnerID,OwnerPW);
    }

    public void ownerRegistration(String name, String phoneNumber, String ownerID, String ownerPW)
    {
        myOwnerDAO.ownerRegistration(name, phoneNumber, ownerID, ownerPW);
    }

    public List<OwnerDTO> ownerLogin(String id, String pw)
    {
        List<OwnerDTO> list = new ArrayList<>();
        list = myOwnerDAO.checkOwnerLogin(id, pw);

        return list;
    }
}
