package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.MyBatisConnectionFactory;
import persistence.dto.MenuoptionDTO;
import service.MenuService;

import java.util.*;

public class MyMenuoptionDAO {
    private final SqlSessionFactory sqlSessionFactory;

    public MyMenuoptionDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertMenuoption(String StoreID, String MenuName, String Options, int price) {
        SqlSession session = sqlSessionFactory.openSession();
        Map<Object,Object>optionInformation=new HashMap<>();
        optionInformation.put("StoreID",StoreID);
        optionInformation.put("MenuName",MenuName);
        optionInformation.put("Options",Options);
        optionInformation.put("price",price);
        try {
            session.insert("mapper.MenuoptionMapper.insertMenuoption", optionInformation);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }

    public List<MenuoptionDTO> selectAll() {
        List<MenuoptionDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
        try {
            list = session.selectList("mapper.MenuoptionMapper.selectAll");
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }

        return list;
    }

}
