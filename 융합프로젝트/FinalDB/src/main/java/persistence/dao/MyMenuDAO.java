package persistence.dao;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import persistence.dto.MenuDTO;
import persistence.dto.MenuoptionDTO;
import persistence.dto.StoreDTO;

import java.util.*;
import java.util.Map;

public class MyMenuDAO {

    private final SqlSessionFactory sqlSessionFactory;

    public MyMenuDAO(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void insertMenu(String MenuName, int OrderQuantity, int price, String MenuDescription, String StoreID, int RemainingSale, int Discount, String Type) {

        SqlSession session = sqlSessionFactory.openSession();
        MenuDTO menuDTO=new MenuDTO();
        menuDTO.setMenuName(MenuName);
        menuDTO.setOrderQuantity(OrderQuantity);
        menuDTO.setPrice(price);
        menuDTO.setMenuDescription(MenuDescription);
        menuDTO.setStoreID(StoreID);
        menuDTO.setRemainingSale(RemainingSale);
        menuDTO.setDiscount(Discount);
        menuDTO.setType(Type);
        try {
            session.insert("mapper.MenuMapper.insertMenuAll", menuDTO);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
    }
public List<StoreDTO>login(String StoreID,String StorePW){
    Map<Object,String>storeInformation=new HashMap<>();
    List<StoreDTO>storeDTOS=new ArrayList<>();
    SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
storeInformation.put("StoreID",StoreID);
storeInformation.put("StorePW",StorePW);
    try {
        storeDTOS = session.selectList("mapper.StoreMapper.login", storeInformation);
        session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
    } finally {
        session.rollback(); //!!
        session.close();
    }
    return storeDTOS;
}
    public List<MenuDTO> allMenu(String StoreID) {
        List<MenuDTO> lists = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        try {
            lists = session.selectList("mapper.MenuMapper.AllMenu", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return lists;
    }
public List<MenuDTO>menus(String StoreID,String MenuName){
    List<MenuDTO> lists = new ArrayList<>();
    Map<String,Object>map=new HashMap<>();
    map.put("StoreID",StoreID);
    map.put("MenuName",MenuName);
    SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

    try {
        lists = session.selectList("mapper.MenuMapper.findSameMenu", map);
        session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
    } finally {
        session.rollback(); //!!
        session.close();
    }
    return lists;
}
public List<MenuDTO> typeToMenu(String type){
    List<MenuDTO> list = new ArrayList<>();
    SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
    try {
        list=session.selectList("mapper.MenuMapper.findTypeMenu", type);
        session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
    } finally {
        session.rollback(); //!!
        session.close();
    }
    return list;
}

    public List<MenuDTO> updataMenu(String StoreID, String beforeName,String NewMenuName, int Price) {
        List<MenuDTO> list = new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        Map<String, Object> map = new HashMap<>();
        map.put("StoreID", StoreID);
        map.put("NewMenuName", NewMenuName);
        map.put("Price", Price);
        map.put("beforeMenuName", beforeName);
        try {
            session.update("mapper.MenuMapper.updataMenu", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }
public List<MenuoptionDTO>menuoptions(int option){
    List<MenuoptionDTO> list = new ArrayList<>();
    SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback
    try {
        list=session.selectList("mapper.MenuoptionMapper.findOptionName", option);
        session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
    } finally {
        session.rollback(); //!!
        session.close();
    }
    return list;
}


    public String findStoreID(String storeID) {
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollbac
        String result;

        try {
            result = String.valueOf(session.update("mapper.MenuMapper.findStoreID", storeID));
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }

        return result;
    }

    public void updateDiscount(String storeID, String menuName, int discount)
    {
        Map<String, Object> map = new HashMap<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        map.put("StoreID", storeID);
        map.put("MenuName", menuName);
        map.put("Discount", discount);

        try{
            session.selectList("mapper.MenuMapper.updateDiscount", map);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        }finally {
            session.rollback(); //!!
            session.close();
        }
    }

    public List<MenuDTO>oneStoreMenus(String StoreID){
        List<MenuDTO>list=new ArrayList<>();
        SqlSession session = sqlSessionFactory.openSession(); //true를 하면 자동 저장가능. commit rollback

        try {
            list = session.selectList("mapper.MenuMapper.findMenus", StoreID);
            session.commit(); //오토커밋이 아니기때문에 해줘야한다. !!
        } finally {
            session.rollback(); //!!
            session.close();
        }
        return list;
    }
}
