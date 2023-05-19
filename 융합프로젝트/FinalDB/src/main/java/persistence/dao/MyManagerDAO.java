package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.CustomerDTO;
import persistence.dto.ManagerDTO;
import persistence.dto.OwnerDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyManagerDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MyManagerDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public List<ManagerDTO> selectAll(){
        List<ManagerDTO> dtos = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try{
            dtos = session.selectList("mapper.ManagerMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
        return dtos;
    }

    public List<ManagerDTO> checkManagerLogin(String ManagerID, String ManagerPW)
    {
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        List<ManagerDTO> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("ManagerID", ManagerID);
        map.put("ManagerPW", ManagerPW);

        try{
            list = session.selectList("mapper.ManagerMapper.selectLogin",map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }

        return list;
    }

}
