package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.OrderhistoryDTO;
import persistence.dto.OwnerDTO;
import persistence.dto.ReviewDTO;
import persistence.dto.StoreDTO;

import java.time.LocalDateTime;
import java.util.*;

import static java.sql.JDBCType.NULL;

public class MyReviewDAO {
    private final SqlSessionFactory sqlSessionFactory;
    Scanner sc = new Scanner(System.in);

    public MyReviewDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<ReviewDTO> selectAll() {
        List<ReviewDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            dtos = session.selectList("mapper.ReviewMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }
    public List<ReviewDTO> canOwnerReview(String StoreID)
    {
        List<ReviewDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.ReviewMapper.canOwnerAnswer",StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }

    public void writeOwnerReview(Map<Object,Object>review){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            session.update("mapper.ReviewMapper.ownerAnswer",review);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }
    public List<ReviewDTO>allReview(String ClientID){
        List<ReviewDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            dtos = session.selectList("mapper.ReviewMapper.allReview",ClientID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }
    public List<OrderhistoryDTO> isWriteReivew(String ClientID){
        SqlSession session = sqlSessionFactory.openSession();
        List<OrderhistoryDTO> orderhistoryDTOS = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("ClientID", ClientID);
        map.put("OrderState", "배달완료");

        try {
            orderhistoryDTOS = session.selectList("mapper.OrderhistoryMapper.writeReview", map);


            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }

        return orderhistoryDTOS;
    }
    public void writeReview(String ClientID, String StoreID, String MenuName, String Reivew, int Grade,  LocalDateTime deliveryTime){
        SqlSession session = sqlSessionFactory.openSession();
        try {
            Map<String,Object>map1=new HashMap<>();
            map1.put("ClientID",ClientID);
            map1.put("StoreID",StoreID);
            map1.put("MenuName",MenuName);
            map1.put("Review",Reivew);
            map1.put("Grade",Grade);
            map1.put("deliveryTime",deliveryTime);
            map1.put("ownerAnswer","");
            session.insert("mapper.ReviewMapper.writeReview", map1);
            session.commit();
        } finally {
            session.rollback(); //!!
            session.close();
        }

    }


    public  List<ReviewDTO> showClientReview(String ClientID, int num)
    {
        List<ReviewDTO> reviewDTOS = new ArrayList<>();
        Map<Object, Object> review = new HashMap<>();
        review.put("ClientID", ClientID);
        review.put("page", num);
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            reviewDTOS = session.selectList("mapper.ReviewMapper.selectClientReview",review);
            session.commit();
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return reviewDTOS;
    }
    public List<StoreDTO>storeName(String StoreID){
        List<StoreDTO>storeName = new ArrayList<>();

        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            storeName = session.selectList("mapper.StoreMapper.findStoreName", StoreID);
            session.commit();
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return storeName;
    }
    public List<ReviewDTO>storeReview(String StoreID){
        List<ReviewDTO>reviewDTOS=new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            reviewDTOS = session.selectList("mapper.ReviewMapper.oneStoreReview", StoreID);
            session.commit();
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return reviewDTOS;
    }
}