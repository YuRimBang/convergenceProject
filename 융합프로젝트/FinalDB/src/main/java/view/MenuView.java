package view;

import persistence.dao.MyMenuDAO;
import persistence.MyBatisConnectionFactory;
import persistence.dto.MenuDTO;
import persistence.dto.MenuoptionDTO;
import service.MenuService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuView {
    MyMenuDAO myMenuDAO = new MyMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());

//    public void allMenuViews(List<MenuDTO> allMenu) {
//        List<String> types = new ArrayList<>();
//        for (int i = 0; i < allMenu.size(); i++) {
//            boolean isInput = true;
//            for (int j = 0; j < types.size(); j++) {
//                if (allMenu.get(i).getType().equals(types.get(j)))
//                    isInput = false;
//            }
//            if (isInput == true)
//                types.add(allMenu.get(i).getType());
//        }
//        for(int i=0;i<types.size();i++)
//        {
//            List<MenuDTO>menus=myMenuDAO.typeToMenu(types.get(i));
//            System.out.println("["+types.get(i)+"]");
//            int count=1;
//            for(int j=0;j<menus.size();j++){
//                System.out.print(menus.get(j).getMenuName()+","+menus.get(j).getPrice()+",");
//                String[]options=menus.get(j).getCanOption().split(",");
//                for(int k=0;k< options.length;k++){
//                  int num=Integer.parseInt(options[k]);
//                    List<MenuoptionDTO>menuoptionDTOS=myMenuDAO.menuoptions(num);
//                    System.out.print(menuoptionDTOS.get(0).getOptions());
//                    if(k!=options.length-1)
//                        System.out.print(",");
//                }
//                System.out.println();
//                count++;
//            }
//
//        }


//    }


}
