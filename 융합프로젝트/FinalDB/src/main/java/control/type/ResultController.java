package control.type;

import protocol.Header;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public class ResultController {
    public void handleRead(Header header, DataInputStream bodyReader, DataOutputStream outputStream) {
        switch (header.code) {
            case Header.CODE_RESULT_SUCCESS:
                resultSuccess(outputStream);
                break;

            case Header.CODE_RESULT_FAIL:
                resultFail(outputStream);
                break;

            case Header.CODE_RESULT_HOLD:
                resultHold(outputStream);
                break;
        }
    }

    public void resultSuccess(DataOutputStream outputStream) {

    }

    public void resultFail(DataOutputStream outputStream) {

    }

    public void resultHold(DataOutputStream outputStream) {

    }
}
