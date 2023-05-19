package control.target;

import protocol.RequestManagerSender;
import protocol.ResponseReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class ManagerController
{
    RequestManagerSender requestManagerSender= new RequestManagerSender();

    public void handleRead(int optionNum, DataInputStream bodyReader, DataOutputStream outputStream) throws IOException
    {
            ResponseReceiver responseReceiver = new ResponseReceiver();
            switch (optionNum) {
                case 1://고객목록조회O
                    requestManagerSender.requestCustInfo(outputStream);
                    responseReceiver.receiveCustListInfo(bodyReader);
                    break;
                case 2://점주목록조회O
                    requestManagerSender.requestOwnerInfo(outputStream);
                    responseReceiver.receiveOwnerListInfo(bodyReader);
                    break;
                case 3://가게목록조회O
                    requestManagerSender.requestStoreInfo(outputStream);
                    responseReceiver.receiveStoreListInfo(bodyReader);
                    break;
                case 4://가게매출조회
                    requestManagerSender.requestStoreSalesInfo(outputStream);
                    responseReceiver.receiveStoreSalesInfo(bodyReader);
                    break;
                case 5://가게추가승인요청O
                    requestManagerSender.requestStoreAccept(bodyReader,outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 6://메뉴추가승인요청0
                    requestManagerSender.requestMenuAccept(bodyReader,outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
            }
    }

}
