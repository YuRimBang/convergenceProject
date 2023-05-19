package view;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyOrderhistoryDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.OwnerDTO;
import persistence.dto.ReviewDTO;
import persistence.dto.StoreDTO;
import service.OrderhistoryService;
import service.StoreService;

import java.util.ArrayList;
import java.util.List;

public class StoreView
{
    MyStoreDAO myStoreDAO=new MyStoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    StoreService storeService=new StoreService(myStoreDAO);
MyOrderhistoryDAO myOrderhistoryDAO=new MyOrderhistoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());
OrderhistoryService orderhistoryService=new OrderhistoryService(myOrderhistoryDAO);
OrderhistoryView orderhistoryView=new OrderhistoryView();

    public void oneStoreView(StoreDTO storeDTO)
    {
        List<ReviewDTO>reviews=storeService.oneStoreReview(storeDTO.getStoreID());
        System.out.println(storeDTO.getStoreName()+" "+storeDTO.getStorePhoneNumber()+" "+storeDTO.getAdress()+" "+storeDTO.getIntroduce());
        int gradeSum=0;
        for(int i=0;i<reviews.size();i++){
            gradeSum+=reviews.get(i).getGrade();
        }
        System.out.println("평균별점: "+gradeSum/ reviews.size());
        for(int i=0;i<reviews.size();i++){
            System.out.println(reviews.get(i).getReview()+" "+reviews.get(i).getGrade());
        }

    }

    public void storesView(List<StoreDTO>stores)
    {
       for(int i=0;i<stores.size();i++) {
           System.out.println(stores.get(i).getStoreName() + " "+ stores.get(i).getStorePhoneNumber()+" "+stores.get(i).getAdress()+" "+stores.get(i).getOperatingtime()+" "+stores.get(i).getIntroduce());
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
   /* public void allStoreTotalMoney(){
        List<StoreDTO>storeDTOS=storeService.allStores();
        for(int i=0;i<storeDTOS.size();i++){
            System.out.println(storeDTOS.get(i).getStoreName());
            orderhistoryView.allStoreTotalMoney(storeDTOS.get(i).getStoreID());
        }
    }*/

    public List<Integer> allStoreTotalMoney(){
        List<StoreDTO>storeDTOS=storeService.allStores();
        List<Integer> totalMoneyList = new ArrayList<>();

        for(int i=0;i<storeDTOS.size();i++){
            //System.out.println(storeDTOS.get(i).getStoreName());
            //orderhistoryView.allStoreTotalMoney(storeDTOS.get(i).getStoreID());
            totalMoneyList.add(i, orderhistoryService.totalMoney(storeDTOS.get(i).getStoreID()));
        }

        return totalMoneyList;
    }

}