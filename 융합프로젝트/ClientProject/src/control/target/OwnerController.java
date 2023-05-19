package control.target;

import protocol.RequestOwnerSender;
import protocol.ResponseReceiver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class OwnerController
{
    RequestOwnerSender requestOwnerSender = new RequestOwnerSender();
    ResponseReceiver responseReceiver = new ResponseReceiver();
    public void handleRead(int option,String Clientid,DataInputStream bodyReader, DataOutputStream outputStream) throws IOException
    {

            switch (option)
            {
                case 1://가게등록신청 0
                    requestOwnerSender.requestAddStore(outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 2://메뉴등록신청0
                    requestOwnerSender.requestRegistMenu(Clientid,outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 3://메뉴수정0
                    requestOwnerSender.requestFixMenu(outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 4://할인정책0
                    requestOwnerSender.requestDiscntPolicy(outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 5://가게운영시간설정0
                    requestOwnerSender.requestSetStoreTime(outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 6://답글등록
                    requestOwnerSender.requestReply(bodyReader,outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 7://주문정보조회
                    requestOwnerSender.requestOrderInfo(outputStream);
                    responseReceiver.receiveOrderList(bodyReader);
                    break;
                case 8://가게정보조회0
                    requestOwnerSender.requestStoreInfo(outputStream);
                    responseReceiver.receiveStoreInfo(bodyReader);
                    break;
                case 9://통계정보조회x
                    requestOwnerSender.requestStatesInfo(outputStream);
                    responseReceiver.receiveStateInfo(bodyReader);
                    break;
                //======================================================
                case 10://주문 거절/접수 정보(주문접수 (승인->접수완료,거절))o
                    requestOwnerSender.orderAcceptInfo(bodyReader,outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;

                case 11://배달중->배달완료o
                    requestOwnerSender.requestEndDelivery(bodyReader,outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;

                case 12://메뉴옵션등록신청0
                    requestOwnerSender.requestRegistOption(outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;
                case 13 ://접수완료->배달중o
                    requestOwnerSender.requestStartDelivery(bodyReader,outputStream);
                    responseReceiver.receiveResult(bodyReader);
                    break;

            }
    }
}
