package network.server;

import control.action.ActionController;
import persistence.dto.MenuDTO;
import persistence.dto.StoreDTO;
import protocol.Header;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadServer
{
    public static List<StoreDTO> holdStoreList = new ArrayList<>();
    public static List<MenuDTO> holdMenuList = new ArrayList<>();
    public  static final int PORT = 4000;

    public static void main(String []args) throws IOException
    {

        ServerSocket ss = new ServerSocket(PORT);
        int connectCount = 0;
        System.out.println("ServerSocket created. \nWaiting for connection ...\n\n");
        //Socket socket = ss.accept();



        try
        {
            while(true)
            {
                Socket connectedClient  = ss.accept();
                InetAddress ia=connectedClient.getInetAddress(); // 접속에 사용된 prot
                int port = connectedClient.getLocalPort();//접속된 client IP
                String ip = ia.getHostAddress(); //접속
                System.out.println("Client connected!\n");

                ++connectCount;  //접속자수 카운트
                System.out.print("접속자 수: " + connectCount);
                System.out.print(" 접속-Local Port: "+ port);
                System.out.println(" Client IP: " + ip);

                Server handler = new Server(connectedClient);
                handler.start();
            }
        }catch (Exception e)
        {
            --connectCount;
            System.out.println(e);
        }finally {
            try
            {
                ss.close();
                --connectCount;
            }catch(IOException ignored) {
                --connectCount;
            }
            --connectCount;
        }
        --connectCount;
    }

    public static List<StoreDTO> sendStoreList()
    {
        List<StoreDTO> sendList = List.copyOf(holdStoreList);

        return sendList;
    }

    public static List<MenuDTO> sendMenuList()
    {
        List<MenuDTO> sendList = List.copyOf(holdMenuList);

        return sendList;
    }
}
