package view;

import persistence.MyBatisConnectionFactory;
import persistence.dao.MyReviewDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.ReviewDTO;
import persistence.dto.StoreDTO;
import service.ReviewService;
import service.StoreService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReviewView
{
    MyReviewDAO myReviewDAO=new MyReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());

    public void printReveiw(List<ReviewDTO>reviews)
    {
        System.out.println("=========고객님의 작성된 리뷰===========");
        System.out.println("[   가게이름     |    고객 아이디    |    메뉴    |    별점     |    리뷰    ]");
        int count=1;
     int page=1;
        for(int i = 0; i < reviews.size(); i++) {
            List<StoreDTO> storeName = myReviewDAO.storeName(reviews.get(i).getStoreID());
            System.out.println(storeName.get(0).getStoreName() + " " + reviews.get(i).getClientID() + " " + reviews.get(i).getMenuName() + " " + reviews.get(i).getGrade() + " " + reviews.get(i).getReview());

        }
    }
    public void storeReviews(List<ReviewDTO>reviews){
        int gradeSum=0;
        for(int i=0;i<reviews.size();i++)
            gradeSum+=reviews.get(i).getGrade();
        System.out.println("평균 별점: "+gradeSum/reviews.size());
        for(int i=0;i<reviews.size();i++)
            System.out.println(reviews.get(i).getReview()+" "+reviews.get(i).getGrade());

    }

}
