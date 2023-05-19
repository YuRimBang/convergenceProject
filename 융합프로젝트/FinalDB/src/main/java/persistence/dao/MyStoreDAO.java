package persistence.dao;

import jdk.swing.interop.SwingInterOpUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.*;

import java.util.*;

public class MyStoreDAO {
    private final SqlSessionFactory sqlSessionFactory;
    Scanner sc=new Scanner(System.in);
    public MyStoreDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<StoreDTO> selectAll(){
        List<StoreDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.StoreMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }


    public List<StoreDTO> storeCheck(String StoreName){
        List<StoreDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        try{
            list =  session.selectList("mapper.StoreMapper.findStoreID", StoreName);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }
    public List<ReviewDTO>oneStoreReview(String storeID){
        List<ReviewDTO>list=new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        try{
            list =  session.selectList("mapper.ReviewMapper.oneStoreReview", storeID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public List<StoreDTO>AllSotre(){
        List<StoreDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        try{
            list =  session.selectList("mapper.StoreMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }

    public void changeOperatingTime(Map<Object,String>list){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        try{
            session.update("mapper.StoreMapper.changeOperatingTime",list);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
    }

    public void storeRegistration(String StoreName, String StoreID, String StorePW, String Adress, String PhoneNumber, String OwnerID, String Introduce,String operatingTime){
        SqlSession session = sqlSessionFactory.openSession();
        StoreDTO storeDTO=new StoreDTO();

        storeDTO.setStoreName(StoreName);
        storeDTO.setStoreID(StoreID);
        storeDTO.setAdress(Adress);
        storeDTO.setStorePhoneNumber(PhoneNumber);
        storeDTO.setOwnerID(OwnerID);
        storeDTO.setIntroduce(Introduce);
        storeDTO.setStorePW(StorePW);
        storeDTO.setOperatingtime(operatingTime);
        try{
            session.insert("mapper.StoreMapper.storeRegistration",storeDTO);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
    }
 public List<StoreDTO> findStoreName(String storeID)
    {
        SqlSession session = sqlSessionFactory.openSession();
        List<StoreDTO> storeDTOS = new ArrayList<>();

        try{
            storeDTOS = session.selectList("mapper.StoreMapper.findStoreName",storeID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return storeDTOS;
    }
    public List<StoreDTO> selectOne(String StoreID){
        List<StoreDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.StoreMapper.selectOne", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }


}
