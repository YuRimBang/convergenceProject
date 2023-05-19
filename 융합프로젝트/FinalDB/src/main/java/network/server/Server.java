package network.server;

import com.mysql.cj.MessageBuilder;
import com.mysql.cj.QueryResult;
import com.mysql.cj.Session;
import com.mysql.cj.TransactionEventHandler;
import com.mysql.cj.conf.PropertySet;
import com.mysql.cj.exceptions.ExceptionInterceptor;
import com.mysql.cj.protocol.*;
import com.sun.jdi.connect.spi.Connection;
import control.action.ActionController;
import control.type.RequestController;
import control.type.ResponseController;
import control.type.ResultController;
import persistence.dto.MenuDTO;
import persistence.dto.StoreDTO;
import protocol.Header;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Server extends Thread
{
    private Socket socket;

    public Server(Socket socket) {
        this.socket = socket;
    }

    @Override
     public void run() {
        try {
            boolean stop = false;

            while (true)
            {
                DataInputStream is = new DataInputStream(socket.getInputStream());
                DataOutputStream os = new DataOutputStream(socket.getOutputStream());

                Header header = Header.readHeader(is);
                byte[] body = new byte[header.bodySize];

                is.read(body);
                DataInputStream bodyReader = new DataInputStream(new ByteArrayInputStream(body));
                ActionController controller = new ActionController();

                controller.handleRequest(header, bodyReader, os);
            }

        } catch (Exception e)
        {
            System.out.println(e);
        }
    }



}
