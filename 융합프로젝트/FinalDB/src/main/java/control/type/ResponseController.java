package control.type;

import com.mysql.cj.x.protobuf.MysqlxCrud;
import persistence.MyBatisConnectionFactory;
import persistence.dao.MyOrderhistoryDAO;
import persistence.dto.MenuDTO;
import persistence.dto.OrderhistoryDTO;
import persistence.dto.StoreDTO;
import protocol.Header;
import service.OrderhistoryService;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ResponseController
{
    MyOrderhistoryDAO myOrderhistoryDAO = new MyOrderhistoryDAO(MyBatisConnectionFactory.getSqlSessionFactory());
    OrderhistoryService orderhistoryService = new OrderhistoryService(myOrderhistoryDAO);




    public void handleRead(Header header, DataInputStream bodyReader, DataOutputStream outputStream) throws IOException
    {
        switch (header.code)
        {
            case Header.CODE_OWNER_RES_ORDER_ANS_INFO:
                String orderAnsStoreID = bodyReader.readUTF();
                String orderAnsStorePW = bodyReader.readUTF();
                int orderAnsFlag = bodyReader.readInt();
                String orderAnsCustomerID = bodyReader.readUTF();
                ownerResOrderAnsInfo(orderAnsStoreID, orderAnsStorePW, orderAnsFlag, orderAnsCustomerID, outputStream);
        }
    }

    public void ownerResOrderAnsInfo(String storeID, String storePW, int flag, String customerID, DataOutputStream outputStream) throws IOException
    {
        Header resHeader;

        if(flag != 0 && !(customerID.equals("0")))
        {
            boolean orderAnsFlag = orderhistoryService.orderReceptionDone(storeID,  storePW,  flag,  customerID);

            if(orderAnsFlag)
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

}
