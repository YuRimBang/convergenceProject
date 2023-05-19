package control.target;

import protocol.Header;
import protocol.RequestCustomerSender;
import protocol.ResponseReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;


public class CustomerController
{
    RequestCustomerSender requestCustomerSender = new RequestCustomerSender();
    ResponseReceiver responseReceiver = new ResponseReceiver();

    public void handleRead(int option,String CustomerID,DataInputStream bodyReader, DataOutputStream outputStream) throws IOException
    {
        switch (option)
        {
            case 1://개인정보수정o
                requestCustomerSender.requestFixInfo(outputStream);
                responseReceiver.receiveResult(bodyReader);
                break;
            case 2://음식주문0
                requestCustomerSender.requestOrder(CustomerID,bodyReader,outputStream);
                responseReceiver.receiveResult(bodyReader);
                break;
            case 3://주문취소o
                requestCustomerSender.requestOrderCancel(CustomerID,bodyReader,outputStream);
                responseReceiver.receiveResult(bodyReader);
                break;
            case 4://리뷰별점등록o
                requestCustomerSender.requestWriteReview(CustomerID,bodyReader,outputStream);
                responseReceiver.receiveResult(bodyReader);
                break;
            case 5://음식점리스트정보o
                requestCustomerSender.requestStoreList(outputStream);
                responseReceiver.receiveStoreListInfo(bodyReader);
                break;
            case 6://계정정보o
                requestCustomerSender.requestCustInfo(CustomerID,outputStream);
                responseReceiver.receiveCustInfo(bodyReader);
                break;
            case 7://주문내역조회o
                requestCustomerSender.requestOrderhistoryList(CustomerID,outputStream);
                responseReceiver.receiveOrderList(bodyReader);
                break;
            case 8://음식점단일조회o
                requestCustomerSender.requestOneStore(outputStream);
                responseReceiver.receiveStoreInfo(bodyReader);
                break;
            case 9://자신의 리뷰별점조회
                requestCustomerSender.requestMyReview(CustomerID,outputStream);
                responseReceiver.receiveReviewList(bodyReader);
                break;
        }
        return;
    }
}
