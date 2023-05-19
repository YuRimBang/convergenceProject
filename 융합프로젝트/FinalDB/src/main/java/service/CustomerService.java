package service;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyCustomerDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.CustomerDTO;

import java.util.*;

public class CustomerService {
    Scanner sc = new Scanner(System.in);
    MyCustomerDAO mycustomerDAO = new MyCustomerDAO(MyBatisConnectionFactory.getSqlSessionFactory());

    public CustomerService(MyCustomerDAO mycustomerDAO) {
        this.mycustomerDAO = mycustomerDAO;
    }


    public List<CustomerDTO> showAll() {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS = mycustomerDAO.selectAll();
        return customerDTOS;
    }
/*    public void signUp() {
        System.out.println("고객 이름을 입력해주세요");
        String name = sc.nextLine();
        System.out.println("고객 나이를 입력해주세요");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("핸드폰 번호를 입력해주세요");
        String phoneNumber = sc.nextLine();
        System.out.println("주소를 입력해주세요");
        String adress = sc.nextLine();
        System.out.println("고객 아이디를 입력해주세요");
        String clientID = sc.nextLine();
        System.out.println("비밀번호를 입력해주세요");
        String ClientPW = sc.nextLine();
        Map<Object, Object> list = new HashMap<>();
        list.put("ClientAge", age);
        list.put("ClientID", clientID);
        list.put("ClientName", name);
        list.put("PhoneNumber", phoneNumber);
        list.put("Adress", adress);
        list.put("ClientPW", ClientPW);
        mycustomerDAO.signUp(list);
    }*/

    public void signUp(String name, int age, String phoneNumber, String adress, String clientID, String ClientPW) {
        Map<Object, Object> list = new HashMap<>();
        list.put("ClientAge", age);
        list.put("ClientID", clientID);
        list.put("ClientName", name);
        list.put("PhoneNumber", phoneNumber);
        list.put("Adress", adress);
        list.put("ClientPW", ClientPW);

        mycustomerDAO.signUp(list);
    }

    public boolean checkPW(String CustomerID, String password) {
        List<CustomerDTO> customerDTOS = mycustomerDAO.customerInformation(CustomerID);
        if (password.equals(customerDTOS.get(0).getClientPW())) {
            System.out.println("비밀번호 일치함");
            return true;
        } else {
            System.out.println("비밀번호가 일치하지 않습니다");
            return false;
        }
    }

    /*public void changeInformation(String CustomerID, String CustomerPW) {
        boolean isContinue = checkPW(CustomerID, CustomerPW);

        if (isContinue == true) {
            String answer;
            System.out.println("비밀번호를 수정하시겠습니까?(Y/N)");
            answer = sc.next();
            if (answer.equals(("Y"))) ;
            {
                System.out.println("수정할 비밀번호를 입력해주세요");
                String pw = sc.nextLine();
                mycustomerDAO.changePW(pw);
            }
            System.out.println("전화번호를 수정하시겠습니까?(Y/N)");
            answer = sc.nextLine();
            if (answer.equals("Y")) {
                System.out.println("수정할 전화번호를 입력해주세요");
                String phoneNumber = sc.nextLine();
                mycustomerDAO.changePhoneNumber(phoneNumber);
            }
            System.out.println("주소를 수정하시겠습니까?(Y/N)");
            answer = sc.nextLine();
            if (answer.equals("Y")) {
                System.out.println("수정할 주소를 입력해주세요");
                String adress = sc.nextLine();
                mycustomerDAO.changeAdress(adress);
            }
        }
    }*/

    public boolean changeInformation(String CustomerID, String CustomerPW,String newPW, String phoneNumber, String address) {
        boolean isContinue = checkPW(CustomerID, CustomerPW);
        List<CustomerDTO> customerDTOS = mycustomerDAO.customerInformation(CustomerID);
        CustomerDTO customerDTO = customerDTOS.get(0);

        String beforePhoneNumber = customerDTO.getPhoneNumber();
        String beforeAddress = customerDTO.getAdress();



        if (isContinue == true) {
            if(newPW.equals("0"))
            {
                mycustomerDAO.changePW(CustomerID, CustomerPW);
            }
            else
            {
                mycustomerDAO.changePW(CustomerID, newPW);
            }

            if(phoneNumber.equals("0"))
            {
                mycustomerDAO.changePhoneNumber(CustomerID, beforePhoneNumber);
            }
            else
            {
                mycustomerDAO.changePhoneNumber(CustomerID, phoneNumber);
            }
            if(address.equals("0"))
            {
                mycustomerDAO.changeAdress(CustomerID, beforeAddress);
            }
            else
            {
                mycustomerDAO.changeAdress(CustomerID, address);
            }
        }

        return isContinue;
    }

    public List<CustomerDTO> customerInformation(String customerID) {
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerDTOS = mycustomerDAO.customerInformation(customerID);
        return customerDTOS;
    }

    public List<CustomerDTO> customerLogin(String id, String pw)
    {
        List<CustomerDTO> list = new ArrayList<>();
        list = mycustomerDAO.checkCustomerLogin(id, pw);

        return list;
    }

//    public void showInformation()
//    {
//
//    }
}
