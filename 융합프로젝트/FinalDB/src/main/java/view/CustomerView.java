package view;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyCustomerDAO;
import persistence.dto.CustomerDTO;
import service.CustomerService;

import java.util.List;

public class CustomerView {
    MyCustomerDAO myCustomerDAO=new MyCustomerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    CustomerService  customerService=new CustomerService(myCustomerDAO);

    public void showCustomerInformation(List<CustomerDTO>list){
        System.out.println(list.get(0).getClientName()+" "+list.get(0).getClientAge()+" "+list.get(0).getPhoneNumber()+" "+list.get(0).getAdress()+" "+list.get(0).getClientID()+" "+list.get(0).getClientPW());
    }
    public void allCustomer(){
        List<CustomerDTO>customerDTOS=customerService.showAll();
      for(int i=0;i<customerDTOS.size();i++)
        System.out.println(customerDTOS.get(i).getClientName()+" "+customerDTOS.get(i).getClientAge()+" "+customerDTOS.get(i).getPhoneNumber()+" "+customerDTOS.get(i).getAdress()+" "+customerDTOS.get(i).getClientID()+" "+customerDTOS.get(i).getClientPW());
    }

    public void printCustomerAll(List<CustomerDTO> dtos)
    {
        System.out.println("");
        for(CustomerDTO dto:dtos)
        {
            System.out.println(dto.toString());
        }
    }
}
