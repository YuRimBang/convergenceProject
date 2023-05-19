package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.CustomerDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCustomerDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MyCustomerDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }
    public List<CustomerDTO> selectAll(){
        List<CustomerDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.CustomerMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;
    }
    public List<CustomerDTO>customerInformation(String ClientID){
        List<CustomerDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.CustomerMapper.customerInformation",ClientID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return dtos;

    }
    public void signUp(Map<Object,Object> list){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            session.selectList("mapper.CustomerMapper.signUp",list);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
    }
    public void changePW(String CustomerID, String pw){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        Map<String, String> map = new HashMap<>();
        map.put("ClientID", CustomerID);
        map.put("ClientPW", pw);
        try{
            session.update("mapper.CustomerMapper.changePW",map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
    }
    public void changePhoneNumber(String CustomerID, String phoneNumber){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        Map<String, String> map = new HashMap<>();
        map.put("ClientID", CustomerID);
        map.put("PhoneNumber", phoneNumber);
        try{
            session.update("mapper.CustomerMapper.changePhoneNumber",map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
    }
    public void changeAdress(String CustomerID, String adress){
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        Map<String, String> map = new HashMap<>();
        map.put("ClientID", CustomerID);
        map.put("Adress", adress);
        try{
            session.update("mapper.CustomerMapper.changeAdress", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
    }

    public List<CustomerDTO> checkCustomerLogin(String ClientID, String ClientPW)
    {
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<CustomerDTO> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("ClientID", ClientID);
        map.put("ClientPW", ClientPW);

        try{
            list = session.selectList("mapper.CustomerMapper.selectLogin",map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return list;
    }
}
