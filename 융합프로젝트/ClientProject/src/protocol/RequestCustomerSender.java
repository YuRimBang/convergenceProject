package protocol;

import persistence.Customer;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class RequestCustomerSender {
    ResponseReceiver responseReceiver = new ResponseReceiver();

    public void requestSignUp(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("고객 이름을 입력해주세요");
        String name = sc.nextLine();
        System.out.println("고객 나이를 입력해주세요");
        int age = sc.nextInt();
        sc.nextLine();
        System.out.println("핸드폰 번호를 입력해주세요");
        String phoneNumber = sc.nextLine();
        System.out.println("주소를 입력해주세요");
        String adress = sc.nextLine();
        System.out.println("고객 아이디를 입력해주세요");
        String clientID = sc.nextLine();
        System.out.println("비밀번호를 입력해주세요");
        String ClientPW = sc.nextLine();
        Customer customer = new Customer(age, clientID, name, phoneNumber, adress, ClientPW);
        BodyMaker bodyMaker = new BodyMaker();
        bodyMaker.add(customer);
        byte[] body = bodyMaker.getBody();

        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_SIGNUP,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void requestFixInfo(DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        String answer;
        System.out.println("아이디를 입력해주세요");
        String id = sc.nextLine();
        System.out.println("비밀번호를 입력해주세요");
        String pw1 = sc.nextLine();
        System.out.println("비밀번호를 수정하시겠습니까?(Y/N)");
        answer = sc.next();
        String pw, phoneNumber, adress;
        if (answer.equals(("Y"))) {
            Scanner sc2 = new Scanner(System.in);
            System.out.println("수정할 비밀번호를 입력해주세요.(5자리 이상)");
            pw = sc2.nextLine();
        } else
            pw = "0";
        Scanner sc2 = new Scanner(System.in);
        System.out.println("전화번호를 수정하시겠습니까?(Y/N)");
        answer = sc2.nextLine();
        if (answer.equals("Y")) {
            Scanner sc3 = new Scanner(System.in);
            System.out.println("수정할 전화번호를 입력해주세요");
            phoneNumber = sc3.nextLine();
        } else
            phoneNumber = "0";
        Scanner sc3 = new Scanner(System.in);
        System.out.println("주소를 수정하시겠습니까?(Y/N)");
        answer = sc3.nextLine();
        if (answer.equals("Y")) {
            Scanner sc4 = new Scanner(System.in);
            System.out.println("수정할 주소를 입력해주세요");
            adress = sc4.nextLine();
        } else
            adress = "0";
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(id);
        dos.writeUTF(pw1);
        dos.writeUTF(pw);
        dos.writeUTF(phoneNumber);
        dos.writeUTF(adress);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_FIX_CUST_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void requestMenuOptionList(String id, String menuName, DataOutputStream outputStream) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(id);
        dos.writeUTF(menuName);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_MENUOPTIONLIST,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void requestMyReview(String id, DataOutputStream outputStream) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(id);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_MY_REVIEW,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public boolean requestOrder(String id, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
        Scanner sc = new Scanner(System.in);
        System.out.println("주문하고 싶은 가게 이름을 입력하시오");
        String StoreName = sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        System.out.println("Menu를 입력하시오");
        String Menu = sc.nextLine();
        requestMenuOptionList(id, Menu, outputStream);
        boolean isExist = responseReceiver.receiveMenuOptionList(inputStream);
        String options;

        if (isExist) {
            System.out.println("options를 입력하시오 구분하기위해 /를 넣어주세용 ㅎㅎ(없다면 no를 입력해주세요)");
            options = sc.nextLine();
        } else
            options = "no";

        dos.writeUTF(id);
        dos.writeUTF(StoreName);
        dos.writeUTF(Menu);
        dos.writeUTF(options);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_ORDER,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
        return true;
    }
    public void requestCanCancelList(String id,DataOutputStream outputStream) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(id);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_CANCANCEL_LIST,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

    public void requestOrderCancel(String id, DataInputStream inputStream, DataOutputStream outputStream) throws IOException
    {
        requestCanCancelList(id,outputStream);
        boolean run = responseReceiver.receiveCanCanCelList(inputStream);
        Scanner sc = new Scanner(System.in);
        int num;
        if(run)
        {
            System.out.println("주문을 취소할 주문의 번호를 입력해주세요");
            num = sc.nextInt();
        }
        else
           num = 0;

        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(id);
        dos.writeInt(num);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_SERVER_REQ_CANCEL_ORDER,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestCanCenlOrderhistoryList(String id, DataOutputStream outputStream) throws IOException {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeUTF(id);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_CANREVIEW_LIST,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestWriteReview(String id,DataInputStream inputStream,DataOutputStream outputStream) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        requestCanCenlOrderhistoryList(id,outputStream);
        responseReceiver.receiveCanCanCelOrderhistoryList(inputStream);
        System.out.println("리뷰를 쓰고싶은 가게의 이름, 메뉴와 일치하는 번호를 입력해주세요 더이상 쓰고싶지 않다면 -1을 입력해주세요");
        int num = sc.nextInt();
        System.out.println("별점을 입력해주세요");
        int star = sc.nextInt();
        sc.nextLine();
        System.out.println("리뷰를 입력해주세요");
        String review = sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(id);
        dos.writeInt(num);
        dos.writeInt(star);
        dos.writeUTF(review);

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REQ_REVIEW,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestStoreList(DataOutputStream outputStream) throws IOException
    {
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REFER_REQ_STORE_LIST,
                0
        );
        outputStream.write(header.getBytes());
    }
    public void requestCustInfo(String id,DataOutputStream outputStream) throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(id);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REFER_REQ_ACCOUNT_INFO,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestOrderhistoryList(String id,DataOutputStream outputStream) throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(id);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REFER_REQ_ORDER_LIST,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestOneStore(DataOutputStream outputStream) throws IOException
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("찾고자 하는 가게 이름을 입력하세요");
        String StoreName = sc.nextLine();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(StoreName);
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_CUSTOMER_REFER_REQ_STORE,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }

}
