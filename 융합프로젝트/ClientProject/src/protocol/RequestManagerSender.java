package protocol;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestManagerSender
{
    ResponseReceiver responseReceiver = new ResponseReceiver();
    public void requestCustInfo(DataOutputStream outputStream) throws IOException {
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_MANAGER_REFER_REQ_CUST_INFO,
                0
        );
        outputStream.write(header.getBytes());
    }
    public void requestOwnerInfo(DataOutputStream outputStream) throws IOException {

        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_MANAGER_REFER_REQ_OWNER_INFO,
                0
        );
        outputStream.write(header.getBytes());
    }
    public void requestStoreInfo(DataOutputStream outputStream) throws IOException {

        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_MANAGER_REFER_REQ_STORE_LIST,
                0
        );
        outputStream.write(header.getBytes());
    }
    public void requestStoreSalesInfo(DataOutputStream outputStream) throws IOException {

        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_MANAGER_REFER_RE_STORE_SALES,
                0
        );
        outputStream.write(header.getBytes());
    }
    public void requestHoldStoreList(DataOutputStream outputStream) throws IOException {
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_SERVER_REQ_ADD_STORE,
                0
        );
        outputStream.write(header.getBytes());
    }
    public void requestHoldMenuList(DataOutputStream outputStream) throws IOException {
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_SERVER_REQ_REGIST_MENU,
                0
        );
        outputStream.write(header.getBytes());
    }
    public void requestStoreAccept(DataInputStream inputStream,DataOutputStream outputStream) throws IOException {
        requestHoldStoreList(outputStream);
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        boolean run = responseReceiver.receiveStoreListInfo(inputStream);
        List storeNumList = new ArrayList();
        if(run)
        {
            System.out.println("승인하고싶은 가게 번호를 입력하세요.(종료를 원한다면 0 입력)");
            Scanner sc = new Scanner(System.in);
            while (true) {
                int num = sc.nextInt();
                if (num == 0)
                    break;
                storeNumList.add(num);
            }

            dos.writeInt(storeNumList.size());
            if (storeNumList.size() > 0) {
                for (Object object : storeNumList)
                    dos.writeInt((Integer) object);
            }
        }
        else
        {
            dos.writeInt(storeNumList.size());
            System.out.println("가게가 없습니다.");
        }

        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_MANAGER_REQ_STORE_ACCEPT,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
    public void requestMenuAccept(DataInputStream inputStream,DataOutputStream outputStream) throws IOException {
        requestHoldMenuList(outputStream);
        List<Integer> storeNumList = new ArrayList();
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        boolean run = responseReceiver.receiveMenuListInfo(inputStream);
        if (run) {
            System.out.println("승인하고싶은 메뉴 번호를 입력하세요.(종료를 원한다면 0 입력)");
            Scanner sc = new Scanner(System.in);
            while (true) {
                int num = sc.nextInt();
                if (num == 0)
                    break;
                storeNumList.add(num);
            }
            dos.writeInt(storeNumList.size());
            for (int object : storeNumList)
            {
                dos.writeInt(object);
            }

        }
        else {
            dos.writeInt(storeNumList.size());
            System.out.println("메뉴가 없습니다.");
        }
        byte[] body = buf.toByteArray();
        Header header = new Header(
                Header.TYPE_REQUEST,
                Header.CODE_MANAGER_REQ_MENU_ACCEPT,
                body.length
        );
        outputStream.write(header.getBytes());
        outputStream.write(body);
    }
}

