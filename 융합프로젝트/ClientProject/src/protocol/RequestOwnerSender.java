package protocol;

import persistence.*;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestOwnerSender
{
    ResponseReceiver responseReceiver = new ResponseReceiver();

    public void requestSignUp(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("점주 이름을 입력해주세요");
        String OwnerName=sc.nextLine();
        System.out.println("점주 휴대폰 번호를 입력해주세오");
        String PhoneNumber=sc.nextLine();
        System.out.println("아이디를 입력해주세요");
        String OwnerID=sc.nextLine();
        System.out.println("비밀번호를 입력해주세요");
        String OwnerPW=sc.nextLine();
        Owner owner = new Owner(OwnerName,PhoneNumber,OwnerID,OwnerPW);
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.add(owner);
        byte[] body = bodyMaker.getBody();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_SIGNUP,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestAddStore(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게 아이디를 입력하세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력하세요");
        String StorePW = sc.nextLine();
        System.out.println("가게이름을 입력해주세요");
        String StoreName = sc.nextLine();
        System.out.println("가게 주소를 입력하세요");
        String StoreAdress = sc.nextLine();
        System.out.println("가게 전화번호를 입력하세요");
        String StorePhoneNumber = sc.nextLine();
        System.out.println("가게 주인 아이디를 입력하세요");
        String OwnerID = sc.nextLine();
        System.out.println("간단한 가게 소개를 입력해주세요");
        String Introduce = sc.nextLine();
        System.out.println("운영시간을 입력해주세요( HH:MM~HH:MM 형식 )");
        String operatingTime = sc.nextLine();
        Store store = new Store(StoreID,StoreName,StoreAdress,StorePhoneNumber,OwnerID,Introduce,StorePW,operatingTime);
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.add(store);
        byte[] body = bodyMaker.getBody();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_ADD_STORE,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestRegistMenu(String StoreID, DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("메뉴 이름을 입력하시오");
        String MenuName = sc.nextLine();
        System.out.println("메뉴에 대한 간단한 소개를 작성해 주세요");
        String MenuDescription = sc.nextLine();
        System.out.println("메뉴의 타입을 입력해주세요");
        String Type = sc.nextLine();
        System.out.println("가격을 입력해주세요");
        int price = sc.nextInt();
        System.out.println("남은 판매량을 입력해주세요");
        int RemainingSale = sc.nextInt();
        System.out.println("할인한다면 할인에 대한 정보를 입력해주세요 (단 할인을 원하지 않을시 0을 입력해 주세요)");
        int Discount = sc.nextInt();
        sc.nextLine();
        List<Menuoption> list = new ArrayList<>();
        Menu menu = new Menu(MenuName, 0, price, MenuDescription, StoreID, RemainingSale, Discount, Type, list);
        System.out.println(menu);
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.add(menu);
        byte[] body = bodyMaker.getBody();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_REGIST_MENU,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestFixMenu(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String StorePW = sc.nextLine();
        System.out.println("수정하고 싶은 메뉴의 이름을 입력해주세요 ");
        String beforeMenuName = sc.nextLine();
        System.out.println("새로운 메뉴 이름을 입력해주세요");
        String NewMenuName = sc.nextLine();
        System.out.println("수정할 가격을 입력해 주세요 없다면 -1을 입력해주세요");
        int price = sc.nextInt();
        Menuoption menuoption = new Menuoption(StoreID, beforeMenuName, NewMenuName, price);
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.addStringBytes(StoreID);
        bodyMaker.addStringBytes(StorePW);
        bodyMaker.add(menuoption);
        byte[] body = bodyMaker.getBody();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_FIX_MENU,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);

    }
    public void requestDiscntPolicy(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String StorePW = sc.nextLine();
        System.out.println("수정하고 싶은 메뉴의 이름을 입력해주세요 ");
        String menuName = sc.nextLine();
        System.out.println("이 메뉴를 얼마나 할인 하시겠습니까?(단위 : 원)");
        int discount = sc.nextInt();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(StoreID);
        dos.writeUTF(StorePW);
        dos.writeUTF(menuName);
        dos.writeInt(discount);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_DISCNT_POLICY,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);

    }//가게신청이랑 운영시간 설정할때
    public void requestSetStoreTime(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String StorePW = sc.nextLine();
        System.out.println("번경할 운영시간을 입력해주세요( HH:MM~HH:MM 형식)");
        String operatingTime = sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(StoreID);
        dos.writeUTF(StorePW);
        dos.writeUTF(operatingTime);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_STORE_TIME_SET,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);


    }
    public void requestOwnerCheck(DataOutputStream outputStream)throws IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String StorePW = sc.nextLine();

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(StoreID);
        dos.writeUTF(StorePW);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_REPLY,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestReply(DataInputStream inputStream,DataOutputStream outputStream) throws IOException
    {
        requestOwnerCheck(outputStream);
        Scanner sc = new Scanner(System.in);
        System.out.println("가게의 아이디를 입력해주세요");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String StorePW = sc.nextLine();

        //사이즈가 0이면  쓸수있는 답글이 없거나 아이디 비번이 맞지않음
        responseReceiver.receiveReviewList(inputStream);
        Header header = Header.readHeader(inputStream);
        System.out.println(header.bodySize);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        if(header.bodySize > 0)
        {
            System.out.print("답글을 작성할 리뷰번호를 입력하세요 : ");
            int reviewNum = sc.nextInt();
            System.out.print("답글내용을 입력하세요 : ");
            String replyInfo = sc.nextLine();
            dos.writeUTF(StoreID);
            dos.writeUTF(StorePW);
            dos.writeInt(reviewNum);
            dos.writeUTF(replyInfo);
            byte[] body = buf.toByteArray();
            Header header2 = new Header(
                    Header.TYPE_REQUEST,
                    Header.CODE_OWNER_REQ_REPLY_ANSWER,
                    body.length
            );
            outputStream.write(header2.getBytes());
            outputStream.write(body);
        }
        else
        {
            System.out.println("입력정보가 틀리거나 쓸수있는 답글이 없습니다.");
            Header header2 = new Header(
                    Header.TYPE_REQUEST,
                    Header.CODE_OWNER_REQ_REPLY_ANSWER,
                    0
            );
            outputStream.write(header2.getBytes());
        }
    }
    public void requestOrderInfo(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(storeID);
        dos.writeUTF(storePW);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REFER_REQ_ORDER_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestStartDeliveryList(String storeID,String storePW,DataOutputStream outputStream) throws IOException {

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(storeID);
        dos.writeUTF(storePW);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_CAN_START_DELIVERYLIST,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestStartDelivery(DataInputStream inputStream,DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        requestStartDeliveryList(storeID, storePW, outputStream);
        String clientID;
        System.out.println("========현재 접수완료인 주문=======");
        if (responseReceiver.receiveCanStartOrderList(inputStream))
        {
            System.out.println("배달을 시작한 고객의 아이디를 입력해주세요.(아니면 no를 입력해주세요)");
            clientID = sc.next();

        } else {
            System.out.println("접수완료인 주문이 없습니다.");
            clientID = "0";
        }

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(storeID);
        dos.writeUTF(storePW);
        dos.writeUTF(clientID);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_START_DELIVERY,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestEndOrderList(String storeID, String storePW, DataOutputStream outputStream) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(storeID);
        dos.writeUTF(storePW);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_CAN_DELIVERYLIST,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestEndDelivery(DataInputStream inputStream,DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();

        requestEndOrderList(storeID, storePW, outputStream);
        String clientID;
        System.out.println("========현재 배달 중인 주문=======");

        if (responseReceiver.receiveCanEndOrderList(inputStream))
        {
            System.out.println("배달을 완료할 고객의 아이디를 입력해주세요.(그만 하시려면 no를 입력해주세요)");
            clientID = sc.next();

        } else {
            System.out.println("배달 중인 주문이 없습니다");
            clientID = "0";
        }

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(storeID);
        dos.writeUTF(storePW);
        dos.writeUTF(clientID);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_AND_DELIVERY,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestStoreInfo(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("조회하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW = sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(storeID);
        dos.writeUTF(storePW);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REFER_REQ_STORE_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestStatesInfo(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("통계를 조회할 가게 아이디를 입력해주세요");
        String storeID=sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(storeID);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REFER_REQ_STATS_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestCanAcceptOrderList(String id, String pw,DataOutputStream outputStream) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(id);
        dos.writeUTF(pw);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_CANACCEPT_ORDER_LIST,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void orderAcceptInfo(DataInputStream inputStream,DataOutputStream outputStream) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("주문을 접수하려는 가게 아이디를 입력해주세요 ");
        String storeID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요");
        String storePW=sc.nextLine();
        requestCanAcceptOrderList(storeID,storePW,outputStream);
        int option;
        String customerID;
        boolean run = responseReceiver.receiveCanAcceptOrderList(inputStream);
        if(run)
        {
            System.out.println("실행할 옵션을 입력하세요.");
            System.out.println("1. 접수 승인");
            System.out.println("2. 접수 거절");
            option = sc.nextInt();
            sc.nextLine();
            System.out.println("승인 또는 거절할 고객 아이디를 입력하세요.");
            customerID = sc.nextLine();
        }
        else
        {
            System.out.println("접수대기중인 주문이 없습니다.");
            option = 0;
            customerID = "0";
        }

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(storeID);
        dos.writeUTF(storePW);
        dos.writeInt(option);
        dos.writeUTF(customerID);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_RESPONSE,
                Header.CODE_OWNER_RES_ORDER_ANS_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestRegistOption(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.print("메뉴 옵션을 등록하고자 하는 가게의 아이디를 입력해주세요: ");
        String StoreID = sc.nextLine();
        System.out.println("가게 비밀번호를 입력해주세요: ");
        String StorePW=sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        System.out.println("옵션을 추가하고자 하는 메뉴이름을 입력해주세요");
        String MenuName=sc.nextLine();
        System.out.print("옵션을 입력하세요. : ");
        String Options = sc.nextLine();
        System.out.println("가격을 입력하시오 ");
        int price=sc.nextInt();

        dos.writeUTF(StoreID);
        dos.writeUTF(StorePW);
        dos.writeUTF(MenuName);
        dos.writeUTF(Options);
        dos.writeInt(price);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_OWNER_REQ_ADD_MENUOPTION,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
}
