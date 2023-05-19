package control.action;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Scanner;

public class Controller {
    public static final int REGIST = 1;
    public static final int LOGIN = 2;
    public static final int QUIT = 3;

    public boolean handleCommand(int command, Scanner s, DataInputStream bodyReader, DataOutputStream outputStream) throws IOException {
        ActionController actionController = new ActionController();
            switch (command) {
                case REGIST:
                    return actionController.signUpMenu(bodyReader, outputStream);
                case LOGIN:
                    actionController.login(outputStream);
                    return actionController.decideClientType(bodyReader,outputStream);
                case QUIT:
                    return false;
            }
            return false;
        }
}

