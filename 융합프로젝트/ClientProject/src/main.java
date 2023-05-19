import control.action.Controller;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class main
{
    public static void main(String[] args) throws IOException {
        String ip = "192.168.192.1";
        Scanner s = new Scanner(System.in);
        boolean isContinue = true;

        Socket socket = new Socket(ip, 4000);
        System.out.println("connected!");
        DataInputStream is = new DataInputStream(socket.getInputStream());
        DataOutputStream os = new DataOutputStream(socket.getOutputStream());
        while(isContinue) {

            Controller controller = new Controller();
            int command;
            System.out.println("======================================");
            System.out.println("1. 회원가입");
            System.out.println("2. 로그인");
            System.out.println("3. 종료");
            System.out.println("======================================");
            System.out.print("메뉴를 선택하세요 : ");
            command = s.nextInt();

            isContinue = controller.handleCommand(command, s, is, os);
        }
    }
}
