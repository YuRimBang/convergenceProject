package service;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import persistence.MyBatisConnectionFactory;
import persistence.dao.MyMenuDAO;
import persistence.dao.MyReviewDAO;
import persistence.dao.MyStoreDAO;
import persistence.dto.OrderhistoryDTO;
import persistence.dto.ReviewDTO;
import persistence.dto.StoreDTO;
import view.ReviewView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.apache.log4j.spi.Configurator.NULL;

public class ReviewService {
    private MyReviewDAO myReviewDAO = new MyReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    ReviewView reviewView = new ReviewView();
    Scanner sc = new Scanner(System.in);
    MyMenuDAO myMenuDAO = new MyMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    MenuService menuService = new MenuService(myMenuDAO);

    public ReviewService(MyReviewDAO myReviewDAO) {
        this.myReviewDAO = myReviewDAO;
    }

    public List<OrderhistoryDTO> canReviewList(String clientID)
    {
        //canReviewList 헤더 추가 요망
        List<OrderhistoryDTO> orderhistoryDTOS = myReviewDAO.isWriteReivew(clientID);
        List<ReviewDTO> allReviews = myReviewDAO.selectAll();
        List<OrderhistoryDTO> canReivew = new ArrayList<>();

        for (int i = 0; i < orderhistoryDTOS.size(); i++) {
            boolean isWrite = true;
            for (int j = 0; j < allReviews.size(); j++) {
                String parsedLocalDateTime1 = orderhistoryDTOS.get(i).getDeliveryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String parsedLocalDateTime2 = allReviews.get(j).getDeliveryTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (parsedLocalDateTime1.equals(parsedLocalDateTime2))
                    isWrite = false;

            }
            if (isWrite == true)
                canReivew.add(orderhistoryDTOS.get(i));
        }

        return canReivew;
    }

    public void writeReview(String clientID, int number, int star, String review)
    {
        List<OrderhistoryDTO> orderhistoryDTOS = myReviewDAO.isWriteReivew(clientID);
        List<ReviewDTO> allReviews = myReviewDAO.selectAll();
        List<OrderhistoryDTO> canReivew = new ArrayList<>();
        for (int i = 0; i < orderhistoryDTOS.size(); i++) {
            boolean isWrite = true;
            for (int j = 0; j < allReviews.size(); j++) {
                String parsedLocalDateTime1 = orderhistoryDTOS.get(i).getDeliveryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String parsedLocalDateTime2 = allReviews.get(j).getDeliveryTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (parsedLocalDateTime1.equals(parsedLocalDateTime2))
                    isWrite = false;

            }
            if (isWrite == true)
                canReivew.add(orderhistoryDTOS.get(i));
        }
        List<StoreDTO> storeDTOS = new ArrayList<>();
        MyStoreDAO myStoreDAO = new MyStoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
        for (int i = 0; i < canReivew.size(); i++) {
            storeDTOS = myStoreDAO.findStoreName(canReivew.get(i).getStoreID());
            //System.out.println(i + " " + storeDTOS.get(0).getStoreName() + " " + canReivew.get(i).getMenu() + " " + canReivew.get(i).getDeliveryDate());
        }
        myReviewDAO.writeReview(clientID, canReivew.get(number-1).getStoreID(), canReivew.get(number-1).getMenu(), review, star, canReivew.get(number-1).getDeliveryDate());
    }
    public void writeReview(String ClientID) {
        List<OrderhistoryDTO> orderhistoryDTOS = myReviewDAO.isWriteReivew(ClientID);
        List<ReviewDTO> allReviews = myReviewDAO.selectAll();
        List<OrderhistoryDTO> canReivew = new ArrayList<>();
        for (int i = 0; i < orderhistoryDTOS.size(); i++) {
            boolean isWrite = true;
            for (int j = 0; j < allReviews.size(); j++) {
                String parsedLocalDateTime1 = orderhistoryDTOS.get(i).getDeliveryDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                String parsedLocalDateTime2 = allReviews.get(j).getDeliveryTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                if (parsedLocalDateTime1.equals(parsedLocalDateTime2))
                    isWrite = false;

            }
            if (isWrite == true)
                canReivew.add(orderhistoryDTOS.get(i));
        }

        List<StoreDTO> storeDTOS = new ArrayList<>();
        MyStoreDAO myStoreDAO = new MyStoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());

        for (int i = 0; i < canReivew.size(); i++) {
            storeDTOS = myStoreDAO.findStoreName(canReivew.get(i).getStoreID());
            System.out.println(i + " " + storeDTOS.get(0).getStoreName() + " " + canReivew.get(i).getMenu() + " " + canReivew.get(i).getDeliveryDate());
        }
        System.out.println("리뷰를 쓰고싶은 가게의 이름, 메뉴와 일치하는 번호를 입력해주세요 더이상 쓰고싶지 않다면 -1을 입력해주세요");
        int num = sc.nextInt();
        System.out.println("별점을 입력해주세요");
        int stare = sc.nextInt();
        sc.nextLine();
        System.out.println("리뷰를 입력해주세요");
        String review = sc.nextLine();
        myReviewDAO.writeReview(ClientID, canReivew.get(num).getStoreID(), canReivew.get(num).getMenu(), review, stare, canReivew.get(num).getDeliveryDate());
    }

    public void showMyReviews(String ClientID) {
        int num = 0;

        String answer = "Y";
        List<ReviewDTO> allReview = myReviewDAO.allReview(ClientID);
        while (answer.equals("Y") && num <= allReview.size() - 1) {
            List<ReviewDTO> reviewList = myReviewDAO.showClientReview(ClientID, num);
            reviewView.printReveiw(reviewList);
            num += 2;
            if (num < allReview.size()) {
                System.out.println("다음 페이지를 보시겠습니까? (Y/N)");
                answer = sc.nextLine();

            }
        }
    }

    public List<ReviewDTO> sendMyReviews(String ClientID) {
        List<ReviewDTO> allReview = myReviewDAO.allReview(ClientID);

        return allReview;
    }

    public List<StoreDTO> storeName(String StoreName) {
        List<StoreDTO> storeName = myReviewDAO.storeName(StoreName);
        return storeName;
    }

    public List<ReviewDTO> storeReview() {
        System.out.println("리뷰를 보려는 가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String StorePW = sc.nextLine();
        List<StoreDTO> storeDTOS = menuService.login(StoreID, StorePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다.");

        List<ReviewDTO> storeReview = myReviewDAO.storeReview(StoreID);

        return storeReview;
    }

    public List<ReviewDTO> canOwnerReview(String storeID) {
        List<ReviewDTO> reviewDTOS = myReviewDAO.canOwnerReview(storeID);
        List<ReviewDTO> canWriteReviews = new ArrayList<>();
        for (int i = 0; i < reviewDTOS.size(); i++)
            if (reviewDTOS.get(i).getOwnerAnswer().equals(""))
                canWriteReviews.add(reviewDTOS.get((i)));
        return canWriteReviews;
    }

/*    public void writeOwnerReview() {
        System.out.println("답글을 쓸 가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비빌번호를 입력해주세요");
        String StorePW = sc.nextLine();
        List<StoreDTO> storeDTOS = menuService.login(StoreID, StorePW);
        if (storeDTOS.size() == 0)
            System.out.println("입력하신 정보와 일치하는 가게가 없습니다");
        else {
            List<ReviewDTO>canReviews=canOwnerReview(StoreID);
            if(canReviews.size()==0)
                System.out.println("답글을 쓸 수 있는 리뷰가 없습니다");
            else {
                System.out.println("답글을 쓸 수 있는 리뷰");
                for (int i = 0; i < canReviews.size(); i++)
                    System.out.println(i + " " + canReviews.get(i).toString());
                System.out.println("답글을 쓰고싶은 리뷰의 번호를 입력해주세요");
                int num = sc.nextInt();
                sc.nextLine();
                System.out.println("답글을 입력해주세요");
                String answer=sc.nextLine();
                Map<Object, Object> reviews = new HashMap<>();
                reviews.put("StoreID", StoreID);
                reviews.put("deliveryTime", canReviews.get(num).getDeliveryTime());
                reviews.put("ownerAnswer",answer);
                System.out.println(StoreID);
                System.out.println(canReviews.get(num).getDeliveryTime());
                System.out.println(answer);
                myReviewDAO.writeOwnerReview(reviews);



            }
        }*/

    public List<ReviewDTO> checkOwnerReview(String StoreID, String StorePW) {
        List<StoreDTO> storeDTOS = menuService.login(StoreID, StorePW);

        List<ReviewDTO> canReviews;

        if (storeDTOS.size() > 0) {
            canReviews = canOwnerReview(StoreID);
        } else {
            canReviews = new ArrayList<>();
        }

        return canReviews;
    }

    public boolean writeOwnerReview(String StoreID, String StorePW, int number, String answer) {
        List<ReviewDTO> canReviews = checkOwnerReview(StoreID, StorePW);

        if(canReviews.size() > 0)
        {
            Map<Object, Object> reviews = new HashMap<>();

            reviews.put("StoreID", StoreID);
            reviews.put("deliveryTime", canReviews.get(number).getDeliveryTime());
            reviews.put("ownerAnswer", answer);

            myReviewDAO.writeOwnerReview(reviews);

            return true;
        }
        else
            return false;

    }

}

