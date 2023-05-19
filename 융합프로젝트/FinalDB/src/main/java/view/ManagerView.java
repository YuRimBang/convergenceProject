package view;

import persistence.dto.CustomerDTO;
import persistence.dto.OwnerDTO;
import persistence.dto.StoreDTO;

import java.util.*;

public class ManagerView
{
    public void printCustomerAll(List<CustomerDTO> dtos)
    {
        System.out.println("");
        for(CustomerDTO dto:dtos)
        {
            System.out.println(dto.toString());
        }
    }

    public void printOwnerAll(List<OwnerDTO> dtos)
    {
        System.out.println("");
        for(OwnerDTO dto:dtos)
        {
            System.out.println(dto.toString());
        }
    }

    public void printStoreAll(List<StoreDTO> dtos)
    {
        System.out.println("");
        for(StoreDTO dto:dtos)
        {
            System.out.println(dto.toString());
        }
    }

    public void printStoreSale(List<StoreDTO> dtos)
    {
        System.out.println("");
        for(StoreDTO dto:dtos)
        {
            System.out.println(dto);
        }
    }
}
