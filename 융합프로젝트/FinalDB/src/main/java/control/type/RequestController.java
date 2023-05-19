package control.type;

import lombok.Data;
import network.server.Server;
import network.server.ThreadServer;
import persistence.MyBatisConnectionFactory;
import persistence.dao.*;
import persistence.dto.*;
import protocol.BodyMaker;
import protocol.Header;
import protocol.MySerializableClass;
import service.*;
import view.OrderhistoryView;
import view.StoreView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequestController
{
    MyCustomerDAO myCustomerDAO = new MyCustomerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    CustomerService customerService=new CustomerService(myCustomerDAO);

    MyStoreDAO myStoreDAO = new MyStoreDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    StoreService storeService = new StoreService(myStoreDAO);

    MyReviewDAO myReviewDAO = new MyReviewDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    ReviewService reviewService = new ReviewService(myReviewDAO);

    MyOwnerDAO myOwnerDAO = new MyOwnerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    OwnerService ownerService = new OwnerService(myOwnerDAO);

    MyManagerDAO myManagerDAO = new MyManagerDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    ManagerService managerService = new ManagerService(myManagerDAO);

    MyOrderhistoryDAO myOrderhistoryDAO = new MyOrderhistoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    OrderhistoryService orderhistoryService = new OrderhistoryService(myOrderhistoryDAO);

    MyMenuDAO myMenuDAO = new MyMenuDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    MenuService menuService = new MenuService(myMenuDAO);

    MyMenuoptionDAO myMenuoptionDAO = new MyMenuoptionDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    MenuoptionService menuoptionService = new MenuoptionService(myMenuoptionDAO);

    public void handleRead(Header header, DataInputStream bodyReader, DataOutputStream outputStream) throws IOException
    {
        switch (header.code)
        {
            case Header.CODE_REQ_LOGIN :
                String longinID = bodyReader.readUTF();
                String longinPW = bodyReader.readUTF();

                login(longinID, longinPW, outputStream);

                break; //로그인

            case Header.CODE_SERVER_REQ_ADD_STORE :
                addStoreToManager(outputStream);
                break; // 가게 추가 요청

            case Header.CODE_SERVER_REQ_REGIST_MENU :
                registerMenuToManager(outputStream);
                break; // 메뉴 등록 요청

            case Header.CODE_SERVER_REQ_PASSWORD :
                requestPassword(outputStream);
                break; // pw 입력 요청

            case Header.CODE_SERVER_REQ_CANCEL_ORDER :
                String orderCancelCustomerID = bodyReader.readUTF();
                int cancelOrderNumber = bodyReader.readInt();

                cancelOrderToOwner(orderCancelCustomerID,cancelOrderNumber, outputStream);

                break; // 주문 취소 요청

            case Header.CODE_OWNER_REQ_ADD_STORE :
                StoreDTO store = StoreDTO.readStore(bodyReader);
                System.out.println(store.toString());

                addStore(store, outputStream);
                break; // 가게 등록 신청

            case Header.CODE_OWNER_REQ_REGIST_MENU :
                MenuDTO menu = MenuDTO.readMenu(bodyReader);
                System.out.println(menu.toString());

                registerMenu(menu, outputStream);
                break; // 메뉴 등록 신청

            case Header.CODE_OWNER_REQ_FIX_MENU :
                //String storeID, String storePW, String menuName, String newMenuName, int pri
                String fixMenuStoreID = bodyReader.readUTF();
                String fixMenuStorePW = bodyReader.readUTF();
                String fixMenuName = bodyReader.readUTF();
                String fixNewMenuName = bodyReader.readUTF();
                int fixMenuPrice = bodyReader.readInt();

                fixMenu(fixMenuStoreID,fixMenuStorePW,fixMenuName,fixNewMenuName,fixMenuPrice,outputStream);
                break; // 메뉴 수정

            case Header.CODE_OWNER_REQ_DISCNT_POLICY :
                String discountStoreID = bodyReader.readUTF();
                String discountStorePW = bodyReader.readUTF();
                String discountMenuName = bodyReader.readUTF();
                int discountAmount = bodyReader.readInt();

                discountPolicy(discountStoreID, discountStorePW, discountMenuName, discountAmount, outputStream);
                break; // 할인정책

            case Header.CODE_OWNER_REQ_STORE_TIME_SET :
                //String storeID, String storePW, String operatingTime,
                String timeSetStoreID = bodyReader.readUTF();
                String timeSetStorePW = bodyReader.readUTF();
                String timeSetOperatingTime = bodyReader.readUTF();

                timeSet(timeSetStoreID, timeSetStorePW, timeSetOperatingTime, outputStream);
                break; // 가게 운영시간 설정

            case Header.CODE_OWNER_REQ_REPLY :
                String replyStoreID = bodyReader.readUTF();
                String replyStorePW = bodyReader.readUTF();

                reqReply(replyStoreID, replyStorePW, outputStream);
                break; // 답글 등록 요청

            case Header.CODE_CUSTOMER_REQ_SIGNUP :
                CustomerDTO customer = CustomerDTO.readCustomerDTO(bodyReader);
                signUp(customer,outputStream);

                break; // 회원가입

            case Header.CODE_CUSTOMER_REQ_FIX_CUST_INFO :
                String fixInfoCustomerID = bodyReader.readUTF();
                String fixInfoCustomerPW = bodyReader.readUTF();
                String fixInfoNewPW = bodyReader.readUTF();;
                String fixInfoPhoneNum = bodyReader.readUTF();
                String fixInfoAddress = bodyReader.readUTF();

                fixCustomerInfo(fixInfoCustomerID, fixInfoCustomerPW, fixInfoNewPW, fixInfoPhoneNum, fixInfoAddress, outputStream);
                break; // 개인정보 수정

            case Header.CODE_CUSTOMER_REQ_ORDER :
                //id, 가게이름, 메뉴, 옵션
                String orderCustomerID = bodyReader.readUTF();
                String orderStoreName = bodyReader.readUTF();
                String orderMenu = bodyReader.readUTF();
                String orderOption = bodyReader.readUTF();

                reqOrder(orderCustomerID, orderStoreName, orderMenu, orderOption, outputStream);
                break; // 음식주문

            case Header.CODE_CUSTOMER_REQ_CANCEL_ORDER :
                String cancelOrderCustomerID = bodyReader.readUTF();
                int cancelNumber = bodyReader.readInt();

                cancelOrder(cancelOrderCustomerID, cancelNumber, outputStream);
                break; // 주문 취소

            case Header.CODE_CUSTOMER_REQ_REVIEW :
                String customerReqReviewID = bodyReader.readUTF();
                int customerReviewNumber = bodyReader.readInt();
                int customerReviewStar = bodyReader.readInt();
                String customerReview = bodyReader.readUTF();

                requestReview(customerReqReviewID,customerReviewNumber ,customerReviewStar, customerReview, outputStream);
                break; // 리뷰별점 등록

            case Header.CODE_MANAGER_REFER_REQ_CUST_INFO :
                referCustomerInfo(outputStream);
                break; // 고객정보 조회

            case Header.CODE_MANAGER_REFER_REQ_OWNER_INFO:
                referOwnerInfo(outputStream);
                break; // 점주 정보 조회

            case Header.CODE_MANAGER_REFER_REQ_STORE_LIST :
                referStoreListToManager(outputStream);
                break; // 가게 목록 조회

            case Header.CODE_MANAGER_REFER_REQ_STORE_SALES :
                referStoreSales(outputStream);
                break; //가게 매출 조회

            case Header.CODE_OWNER_REFER_REQ_ORDER_INFO :  // 주문정보 조회
                //이거 가게 별로 혹은 점주 별로 볼 수 있도록 수정 희망 요함.
                referOrderInfoList(outputStream);
                break;

            case Header.CODE_OWNER_REFER_REQ_STORE_INFO :
                String storeInfoID = bodyReader.readUTF();
                String storeInfoPW = bodyReader.readUTF();

                referStoreInfo(storeInfoID, storeInfoPW, outputStream);
                break; // 가게 정보 조회

            case Header.CODE_OWNER_REFER_REQ_STATS_INFO :
                String statsInfoStoreID = bodyReader.readUTF();
                referStatsInfo(statsInfoStoreID, outputStream);
                break; // 통계 정보 조회

            case Header.CODE_CUSTOMER_REFER_REQ_ACCOUNT_INFO :
                String customerAccountID = bodyReader.readUTF();
                customerAccountInfo(customerAccountID, outputStream);
                break; // 계정 정보

            case Header.CODE_CUSTOMER_REFER_REQ_STORE_LIST : // 음식점 리스트 정보
                referStoreList(outputStream);
                break;

            case Header.CODE_CUSTOMER_REFER_REQ_ORDER_LIST : // 주문 내역 조회
                String customerID = bodyReader.readUTF();

                referOrderList(customerID, outputStream);
                break;

            case Header.CODE_CUSTOMER_REFER_REQ_STORE : // 음식점 단일 조회
                String storeName = bodyReader.readUTF();

                referStore(storeName, outputStream);
                break;

            case Header.CODE_CUSTOMER_REFER_REQ_MENU_LIST :
                String menuListStoreID = bodyReader.readUTF();

                customerReferMenu(menuListStoreID, outputStream);
                break;

            case Header.CODE_OWNER_REQ_SIGNUP :
                String ownerSignUpName = bodyReader.readUTF();
                String ownerSignUpPhone = bodyReader.readUTF();
                String ownerSignUpID = bodyReader.readUTF();
                String ownerSignUpPW = bodyReader.readUTF();

                ownerSignUP(ownerSignUpName, ownerSignUpPhone, ownerSignUpID, ownerSignUpPW, outputStream);
                break;

            case Header.CODE_OWNER_REQ_REPLY_ANSWER:
                String replyAnsStoreID = bodyReader.readUTF();
                String replyAnsStorePW = bodyReader.readUTF();
                int replyAnsNumber = bodyReader.readInt();
                String replyAnswer = bodyReader.readUTF();

                if(header.bodySize == 0)
                {
                    Header resHeader = new Header(
                            Header.TYPE_RESULT,
                            Header.CODE_RESULT_FAIL,
                            0
                    );
                    outputStream.write(resHeader.getBytes());
                }
                else
                {
                    replyAnswer(replyAnsStoreID, replyAnsStorePW, replyAnsNumber, replyAnswer, outputStream);
                }
                break;

            case Header.CODE_MANAGER_REQ_STORE_ACCEPT:
                int storeSize = bodyReader.readInt();

                List<StoreDTO> tmpStoreList = ThreadServer.holdStoreList;
                for(int i = 0; i < storeSize; i++)
                {
                    int num = bodyReader.readInt() - 1;
                    if(ThreadServer.holdStoreList.contains(tmpStoreList.get(num)))
                    {
                        storeService.storeRegistration(tmpStoreList.get(num).getStoreName(), tmpStoreList.get(num).getStoreID(), tmpStoreList.get(num).getStorePW(), tmpStoreList.get(num).getAdress(), tmpStoreList.get(num).getStorePhoneNumber(), tmpStoreList.get(num).getOwnerID(), tmpStoreList.get(num).getIntroduce(), tmpStoreList.get(num).getOperatingtime());
                        ThreadServer.holdStoreList.remove(tmpStoreList.get(num));
                    }
                }

                Header resStoreHeader = new Header(
                        Header.TYPE_RESULT,
                        Header.CODE_RESULT_SUCCESS,
                        0
                );

                outputStream.write(resStoreHeader.getBytes());
                break;

            case Header.CODE_MANAGER_REQ_MENU_ACCEPT:
                int menuSize = bodyReader.readInt();
                List<MenuDTO> tmpMenuList = List.copyOf(ThreadServer.holdMenuList);
                for(int i = 0; i < menuSize; i++)
                {
                    int num = bodyReader.readInt() - 1;
                    System.out.println(num);
                    if(ThreadServer.holdMenuList.contains(tmpMenuList.get(num)))
                    {
                        menuService.insertMenu(
                                tmpMenuList.get(num).getStoreID(),
                                tmpMenuList.get(num).getMenuName(),
                                tmpMenuList.get(num).getMenuDescription(),
                                tmpMenuList.get(num).getType(),
                                tmpMenuList.get(num).getPrice(),
                                tmpMenuList.get(num).getRemainingSale(),
                                tmpMenuList.get(num).getDiscount()
                        );
                        ThreadServer.holdMenuList.remove(tmpMenuList.get(num));
                    }
                }

                Header resMenuHeader = new Header(
                        Header.TYPE_RESULT,
                        Header.CODE_RESULT_SUCCESS,
                        0
                );

                outputStream.write(resMenuHeader.getBytes());
                break;


            case Header.CODE_CUSTOMER_REQ_CANCANCEL_LIST:
                String canCancel = bodyReader.readUTF();
                sendCanCancelOrderList(canCancel, outputStream);
                break;

            case Header.CODE_CUSTOMER_REQ_CANREVIEW_LIST:
                String canReviewCustomerID = bodyReader.readUTF();
                sendCanReviewList(canReviewCustomerID, outputStream);
                break;

            case Header.CODE_OWNER_REQ_CANACCEPT_ORDER_LIST:
                String orderListStoreID = bodyReader.readUTF();
                String orderListStorePW = bodyReader.readUTF();
                sendCanAcceptOrderList(orderListStoreID,  orderListStorePW, outputStream);
                break;

            case Header.CODE_OWNER_REQ_ADD_MENUOPTION :
                String addMOID = bodyReader.readUTF();
                String addMOPW = bodyReader.readUTF();
                String menuName = bodyReader.readUTF();
                String menuOption = bodyReader.readUTF();
                int menuOptionPrice = bodyReader.readInt();

                boolean menuoptionFlag = menuoptionService.insertMenuOption(addMOID, addMOPW, menuName, menuOption, menuOptionPrice);
                Header menuoptionHeader;

                if(menuoptionFlag)
                {
                    menuoptionHeader = new Header(
                            Header.TYPE_RESULT,
                            Header.CODE_RESULT_SUCCESS,
                            0
                    );
                }
                else
                {
                    menuoptionHeader = new Header(
                            Header.TYPE_RESULT,
                            Header.CODE_RESULT_FAIL,
                            0
                    );
                }
                outputStream.write(menuoptionHeader.getBytes());
                break;

            case Header.CODE_OWNER_REQ_AND_DELIVERY :
                String deliveryStoreID = bodyReader.readUTF();
                String deliveryStorePW = bodyReader.readUTF();
                String deliveryClientID = bodyReader.readUTF();
                boolean flag = true;

                if(deliveryClientID.equals("0"))
                {
                    flag = false;
                }
                else
                {
                    flag = orderhistoryService.changeOrderFinish(deliveryStoreID, deliveryStorePW, deliveryClientID);
                }

                Header deliveryFinishHeader;
                if(flag)
                {
                    deliveryFinishHeader = new Header(
                            Header.TYPE_RESULT,
                            Header.CODE_RESULT_SUCCESS,
                            0
                    );
                }
                else {
                    deliveryFinishHeader = new Header(
                            Header.TYPE_RESULT,
                            Header.CODE_RESULT_FAIL,
                            0
                    );
                }
                outputStream.write(deliveryFinishHeader.getBytes());

                break;

            case Header.CODE_CUSTOMER_REQ_MENUOPTIONLIST:
                String menuOptionListID = bodyReader.readUTF();
                String menuOptionMenuName = bodyReader.readUTF();

                List<MenuoptionDTO> menuoptionDTOS = menuoptionService.sendMenuOptionList(menuOptionListID, menuOptionMenuName);

                BodyMaker bodyMaker = new BodyMaker();
                bodyMaker.addMenuoptionList(menuoptionDTOS);
                byte[] menuOptionListBody = bodyMaker.getBody();

                Header menuOptionListHeader = new Header(
                        Header.TYPE_RESPONSE,
                        Header.CODE_SERVER_RES_MENUOPTIONLIST,
                        menuOptionListBody.length
                );

                outputStream.write(menuOptionListHeader.getBytes());
                outputStream.write(menuOptionListBody);

                break;

            case Header.CODE_OWNER_REQ_CAN_DELIVERYLIST :
                String canDeliveryID = bodyReader.readUTF();
                String canDeliveryPW = bodyReader.readUTF();

                List<OrderhistoryDTO> canDeliveryList = orderhistoryService.changeOrderFinishList(canDeliveryID, canDeliveryPW);

                BodyMaker bodyMaker2 = new BodyMaker();
                bodyMaker2.addOrderhistoryList(canDeliveryList);
                byte[] canDeliveryListBody = bodyMaker2.getBody();

                Header canDeliveryListHeader = new Header(
                        Header.TYPE_RESPONSE,
                        Header.CODE_SERVER_RES_MENUOPTIONLIST,
                        canDeliveryListBody.length
                );

                outputStream.write(canDeliveryListHeader.getBytes());
                outputStream.write(canDeliveryListBody);
                break;

            case Header.CODE_OWNER_REQ_START_DELIVERY :
                String startDelStoreID = bodyReader.readUTF();
                String startDelStorePW = bodyReader.readUTF();
                String startDelClientID = bodyReader.readUTF();

                boolean storeDelFlag = true;

                if(startDelClientID.equals("0"))
                {
                    storeDelFlag = false;
                }
                else
                {
                    storeDelFlag = orderhistoryService.orderRecption(startDelStoreID, startDelStorePW, startDelClientID);
                }

                Header startDelHeader;
                if(storeDelFlag)
                {
                    startDelHeader = new Header(
                            Header.TYPE_RESULT,
                            Header.CODE_RESULT_SUCCESS,
                            0
                    );
                }
                else {
                    startDelHeader = new Header(
                            Header.TYPE_RESULT,
                            Header.CODE_RESULT_FAIL,
                            0
                    );
                }
                outputStream.write(startDelHeader.getBytes());
                break;

            case Header.CODE_OWNER_REQ_CAN_START_DELIVERYLIST:
                String canStartID = bodyReader.readUTF();
                String canStartPW = bodyReader.readUTF();

                List<OrderhistoryDTO> cansStartList = orderhistoryService.orderRecption(canStartID, canStartPW);

                BodyMaker bodyMaker1 = new BodyMaker();
                bodyMaker1.addOrderhistoryList(cansStartList);
                byte[] cansStartListBody = bodyMaker1.getBody();

                Header cansStartListHeader = new Header(
                        Header.TYPE_RESPONSE,
                        Header.CODE_SERVER_RES_MENUOPTIONLIST,
                        cansStartListBody.length
                );

                outputStream.write(cansStartListHeader.getBytes());
                outputStream.write(cansStartListBody);

                break;

            case Header.CODE_CUSTOMER_REQ_MY_REVIEW :
                String clientID = bodyReader.readUTF();

                List<ReviewDTO> reviewDTOS = reviewService.sendMyReviews(clientID );

                BodyMaker bodyMaker11 = new BodyMaker();
                bodyMaker11.addReviewList(reviewDTOS);
                byte[] reviewListBody = bodyMaker11.getBody();

                Header reviewListHeader = new Header(
                        Header.TYPE_RESPONSE,
                        Header.CODE_CUSTOMER_RES_MY_REVIEW,
                        reviewListBody.length
                );

                outputStream.write(reviewListHeader.getBytes());
                outputStream.write(reviewListBody);
                break;
        }
    }

    //0 로그인
    public void login(String id, String pw, DataOutputStream outputStream) throws IOException
    {
        List<CustomerDTO> customer = new ArrayList<>();
        List<ManagerDTO> manager = new ArrayList<>();
        List<OwnerDTO> owner = new ArrayList<>();

        customer = customerService.customerLogin(id, pw);
        manager = managerService.managerLogin(id, pw);
        owner = ownerService.ownerLogin(id, pw);

        String clientType;
        String clientID;

        if(customer.size() > 0)
        {
            System.out.println(customer.get(0).getClientName());
            clientType = "고객";
            clientID = customer.get(0).getClientID();
        }
        else if(manager.size() > 0)
        {
            System.out.println(manager.get(0).getManagerName());
            clientType = "관리자";
            clientID = manager.get(0).getManagerID();
        }
        else if(owner.size() > 0)
        {
            System.out.println(owner.get(0).getOwnerName());
            clientType = "점주";
            clientID = owner.get(0).getOwnerID();
        }
        else
        {
            System.out.println("로그인 실패");
            clientType = "NULL";
            clientID = "NULL";
        }

        System.out.println("Client Type 확인 : " + clientType);
        System.out.println("Client ID 확인 : " + clientID);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addStringBytes(clientType);
        bodyMaker.addStringBytes(clientID);

        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_RES_LOGIN,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //1 가게 추가 요청
    public void addStoreToManager(DataOutputStream outputStream) throws IOException
    {
        List<StoreDTO> sendList = List.copyOf(ThreadServer.sendStoreList());

        System.out.println(sendList.get(0).toString());
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addStoreList(sendList);

        byte [] resBody = bodyMaker.getBody();
        System.out.println(resBody.length);

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_RES_HOLD_STORE_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //2 메뉴 등록 요청
    public void registerMenuToManager(DataOutputStream outputStream) throws IOException
    {
        List<MenuDTO> sendList = ThreadServer.sendMenuList();

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addMenuList(sendList);

        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_RES_HOLE_MENU_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //3 pw 입력 요청
    public void requestPassword(DataOutputStream outputStream) throws IOException
    {
        Header resHeader = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_SERVER_REQ_PASSWORD,
                0
        );

        outputStream.write(resHeader.getBytes());
   }

    //4 주문 취소 요청
    public void cancelOrderToOwner(String clientID, int number, DataOutputStream outputStream) throws IOException
    {
        Header resHeader;

        if(number != 0) {
            orderhistoryService.orderCancel(clientID, number - 1);//주문 취소

            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_SUCCESS,
                    0
            );
        }
        else
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_FAIL,
                    0
            );
        }

        outputStream.write(resHeader.getBytes());
    }

    //5 가게 등록 신청
    public void addStore(StoreDTO storeDTO, DataOutputStream outputStream) throws IOException
    {
        ThreadServer.holdStoreList.add(storeDTO);

        Header resHeader = new Header(
                Header.TYPE_RESULT,
                Header.CODE_RESULT_HOLD,
                0
        );

        outputStream.write(resHeader.getBytes());
        //storeService.storeRegistration(storeDTO.getStoreName(), storeDTO.getStoreID(), storeDTO.getStorePW(), storeDTO.getAdress(), storeDTO.getStorePhoneNumber(), storeDTO.getOwnerID(), storeDTO.getIntroduce(), storeDTO.getOperatingtime());
    }

    //6 메뉴 등록 신청
    public void registerMenu(MenuDTO menuDTO, DataOutputStream outputStream) throws IOException
    {
        ThreadServer.holdMenuList.add(menuDTO);

        Header resHeader = new Header(
                Header.TYPE_RESULT,
                Header.CODE_RESULT_HOLD,
                0
        );

        outputStream.write(resHeader.getBytes());
    }

    //7 메뉴 수정
    public void fixMenu(String storeID, String storePW, String menuName, String newMenuName, int price, DataOutputStream outputStream) throws IOException
    {
        boolean flag = menuService.menuModify(storeID, storePW, menuName, newMenuName, price);//메뉴수정(메뉴이름 ,가격 )
        Header resHeader;

        if(flag)
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_SUCCESS,
                    0
            );
        }
        else
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_FAIL,
                    0
            );
        }

        outputStream.write(resHeader.getBytes());
    }

    //8 할인 정책
    public void discountPolicy (String storeID, String storePW, String menuName, int discount, DataOutputStream outputStream) throws IOException
    {
        boolean flag = menuService.discountModify(storeID, storePW, menuName, discount);//할인정책 수정 (기본 설정은 메뉴 등록에서 한다)
        Header resHeader;

        if(flag)
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_SUCCESS,
                    0
            );
        }
        else
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_FAIL,
                    0
            );
        }

        outputStream.write(resHeader.getBytes());
    }

    //9 가게 운영시간 설정
    public void timeSet(String storeID, String storePW, String operatingTime, DataOutputStream outputStream) throws IOException
    {
        boolean flag = storeService.changeOperatingTime(storeID, storePW, operatingTime);
        Header resHeader;

        if(flag)
        {
             resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_SUCCESS,
                    0
            );
        }
        else
        {
             resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_FAIL,
                    0
            );
        }

        outputStream.write(resHeader.getBytes());
    }

    //10 답글 등록 요청
    public void reqReply(String storeID, String storePW, DataOutputStream outputStream) throws IOException
    {
        List<ReviewDTO> reviewList = reviewService.checkOwnerReview(storeID, storePW);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addReviewList( reviewList);

        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_RES_CANREVIEWLIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //11 회원 가입
    public void signUp(CustomerDTO customer, DataOutputStream outputStream) throws IOException, EOFException
    {
        customerService.signUp(customer.getClientName(), customer.getClientAge(), customer.getPhoneNumber(), customer.getAdress(), customer.getClientID(), customer.getClientPW());
        Header resHeader = new Header(
                Header.TYPE_RESULT,
                Header.CODE_RESULT_SUCCESS,
                0
        );

        outputStream.write(resHeader.getBytes());
    }

    //12 개인정보 수정
    public void fixCustomerInfo(String CustomerID, String CustomerPW,String newPW, String phoneNumber, String address, DataOutputStream outputStream) throws IOException
    {
        // 비번, 전번, 주소 순서
        boolean flag = customerService.changeInformation(CustomerID, CustomerPW, newPW, phoneNumber, address);//개인정보 수정
        Header resHeader;

        if(flag)
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_SUCCESS,
                    0
            );
        }
        else
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_FAIL,
                    0
            );
        }

        outputStream.write(resHeader.getBytes());
    }

    //13 음식 주문
    public void reqOrder(String id, String storeName, String menu, String option, DataOutputStream outputStream) throws IOException
    {
        boolean flag = orderhistoryService.createOrders(id,storeName,menu,option);

        Header resHeader;

        if(flag)
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_SUCCESS,
                    0
            );
        }
        else
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_FAIL,
                    0
            );
        }
        outputStream.write(resHeader.getBytes());
    }

    //14 주문 취소
    public void cancelOrder(String clientID, int cancelNumber, DataOutputStream outputStream) throws IOException
    {
        orderhistoryService.orderCancel(clientID, cancelNumber);

        Header resHeader = new Header(
                Header.TYPE_RESULT,
                Header.CODE_RESULT_SUCCESS,
                0
        );

        outputStream.write(resHeader.getBytes());
    }

    //15 리뷰 별점 등록
    public void requestReview(String clientID, int number, int star, String review, DataOutputStream outputStream) throws IOException
    {
        reviewService.writeReview(clientID,number,star,review); //리뷰와 별점 등록 (배달 완료시에만), 리뷰 작성

        Header resHeader = new Header(
                Header.TYPE_RESULT,
                Header.CODE_RESULT_SUCCESS,
                0
        );

        outputStream.write(resHeader.getBytes());
    }

    //16 고객 정보 조회
    public void referCustomerInfo(DataOutputStream outputStream) throws IOException
    {
        List<CustomerDTO> customerList = customerService.showAll();//관리자시점 고객조회

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addCustomerList(customerList);

        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_CUST_INFO,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //17 점주 정보 조회
    public void referOwnerInfo(DataOutputStream outputStream) throws IOException
    {
        List<OwnerDTO> ownerList = ownerService.findAll(); //관리자 시점 점주 조회

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addOwnerList(ownerList);

        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_OWNER_INFO,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //18 가게 목록 조회
    public void referStoreListToManager(DataOutputStream outputStream) throws IOException
    {
        List<StoreDTO> storeList = storeService.showStore(); //가게 정보 조회(관리자)

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addStoreList(storeList);

        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_STORE_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //19 가게 매출 조회
    public void referStoreSales(DataOutputStream outputStream) throws IOException
    {
        StoreView storeView = new StoreView();

        List<StoreDTO> allStoreList = storeService.allStores();
        List<Integer> allStoreTotalPriceList = storeView.allStoreTotalMoney();

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addStoreList(allStoreList);
        bodyMaker.addIntegerList(allStoreTotalPriceList);

        byte [] resBody = bodyMaker.getBody();
        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_STORE_SALES,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //20 주문정보 조회
    public void referOrderInfoList(DataOutputStream outputStream) throws IOException {

        List<OrderhistoryDTO> orderHistoryList = orderhistoryService.selectAll(); //주문 조회(전체)

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addOrderhistoryList(orderHistoryList);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_CUST_ORDER_INFO,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    // 21 가게정보 조회
    public void referStoreInfo(String storeID, String storePW, DataOutputStream outputStream)throws IOException
    {
        List<StoreDTO> store =  storeService.showOneStore( storeID,  storePW);//가게정보 조회 //뭐하는 건지 나중에 보자구
        StoreDTO storeDTO;
        if(store.size() > 0)  storeDTO = store.get(0);
        else storeDTO = null;

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.add(storeDTO);

        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_STORE_INFO,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    // 22 통계정보 조회
    public void referStatsInfo(String storeID, DataOutputStream outputStream) throws IOException
    {
        System.out.println("1");
        OrderhistoryView orderhistoryView = new OrderhistoryView();
        System.out.println("2");
        int totalMoney = orderhistoryView.storeTotalMoney(storeID);
        System.out.println("3");
        List<MenuDTO> menu = orderhistoryView.storeTotalSaleAmount(storeID);
        System.out.println("4");
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addIntBytes(totalMoney);
        System.out.println("5");
        bodyMaker.addMenuList(menu);
        System.out.println("6");
        byte [] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_STATS_INFO,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    // 23 고객 개인정보 조회(계정정보)
    public void customerAccountInfo(String customerID, DataOutputStream outputStream) throws IOException
    {
        CustomerDTO info = customerService.customerInformation(customerID).get(0);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.add(info);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_ACCOUNT_INFO,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //24 : 음식점 리스트 조회
    public void referStoreList(DataOutputStream outputStream) throws  IOException
    {
        List<StoreDTO> storeList = storeService.allStores(); //주문 조회(전체)

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addStoreList(storeList);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_STORE_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //25 : 주문 내역 조회
    public void referOrderList(String id, DataOutputStream outputStream) throws IOException
    {
        List <OrderhistoryDTO> orderhistoryDTOList = orderhistoryService.showOrderhistoryService(id); //주문 내역 조회, 주문 이력 조회(고객 관점)리뷰 보기

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addOrderhistoryList(orderhistoryDTOList);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_CUST_ORDER_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //26 : 음식점 단일 조회
    public void referStore(String storeName, DataOutputStream outputStream) throws IOException
    {
        StoreDTO findStore = storeService.findStore(storeName);//한 음식점 조회

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.add(findStore);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_RES_STORE,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //27 메뉴 조회
    public void customerReferMenu(String storeID, DataOutputStream outputStream) throws IOException
    {
        List<MenuDTO> menuList = menuService.oneStoreMenus(storeID);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addMenuList(menuList);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_REFER_MENU_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //28 점주 회원 가입
    public void ownerSignUP(String name, String phoneNumber, String ownerID, String ownerPw, DataOutputStream outputStream) throws IOException
    {
        ownerService.ownerRegistration(name, phoneNumber, ownerID, ownerPw);


        Header resHeader = new Header(
                Header.TYPE_RESULT,
                Header.CODE_RESULT_SUCCESS,
                0
        );

        outputStream.write(resHeader.getBytes());
    }

    //29. 답글
    public void replyAnswer(String storeID, String storePW, int number, String answer, DataOutputStream outputStream) throws IOException
    {
        boolean flag = reviewService.writeOwnerReview(storeID, storePW, number, answer);
        Header resHeader;

        if(flag)
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_SUCCESS,
                    0
            );
        }
        else
        {
            resHeader = new Header(
                    Header.TYPE_RESULT,
                    Header.CODE_RESULT_FAIL,
                    0
            );
        }

        outputStream.write(resHeader.getBytes());
    }

    //33 취소 가능한 주문 리스트 보내기
    public void sendCanCancelOrderList(String customerID, DataOutputStream outputStream) throws IOException
    {
        List<OrderhistoryDTO> canCancel =  myOrderhistoryDAO.findClientWaiting(customerID);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addOrderhistoryList(canCancel);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_RES_CANCANCEL_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //34 리뷰 작성 가능한 리스트 보내기
    public void sendCanReviewList(String customerID, DataOutputStream outputStream) throws IOException
    {
        List<OrderhistoryDTO> canReview = reviewService.canReviewList(customerID);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addOrderhistoryList(canReview);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader =new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_RES_CANREVIEW_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }

    //35 주문 접수 가능한 리스트(점주입장)
    public void sendCanAcceptOrderList(String storeID, String storePW, DataOutputStream outputStream) throws IOException
    {
        List<OrderhistoryDTO> canAcceptList = orderhistoryService.orderReceptionDone(storeID,  storePW);

        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addOrderhistoryList(canAcceptList);

        byte[] resBody = bodyMaker.getBody();

        Header resHeader = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_SERVER_RES_CANACCEPT_ORDER_LIST,
                resBody.length
        );

        outputStream.write(resHeader.getBytes());
        outputStream.write(resBody);
    }
}
