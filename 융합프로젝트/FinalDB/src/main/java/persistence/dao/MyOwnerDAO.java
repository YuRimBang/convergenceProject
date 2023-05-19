package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.CustomerDTO;
import persistence.dto.OwnerDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOwnerDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MyOwnerDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public List<OwnerDTO> selectAll()
    {
        List<OwnerDTO> dtos = new ArrayList<>();
      SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
    try{
           dtos = session.selectList("mapper.OwnerMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
       }finally {
           session.rollback(); //!!
           session.close();
       }
      return dtos;
   }

   public void ownerRegistration(String OwnerName,String PhoneNumber, String OwnerID, String OwnerPW){
       SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
       Map<Object,String>ownerInformation=new HashMap<>();
       ownerInformation.put("OwnerName",OwnerName);
       ownerInformation.put("PhoneNumber",PhoneNumber);
       ownerInformation.put("OwnerID",OwnerID);
       ownerInformation.put("OwnerPW",OwnerPW);
       try{
           session.insert("mapper.OwnerMapper.ownerRegistration",ownerInformation);
           session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
       }finally {
           session.rollback(); //!!
           session.close();
       }
   }

    public List<OwnerDTO> checkOwnerLogin(String OwnerID, String OwnerPW)
    {
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<OwnerDTO> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("OwnerID", OwnerID);
        map.put("OwnerPW", OwnerPW);

        try{
            list = session.selectList("mapper.OwnerMapper.selectLogin",map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return list;
    }
}
