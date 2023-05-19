package view;

import persistence.dto.OwnerDTO;

import java.util.List;

public class OwnerView {

    public void printOwnerAll(List<OwnerDTO> dtos)
    {
        System.out.println("");
        for(OwnerDTO dto:dtos)
        {
            System.out.println(dto.toString());
        }
    }
}
