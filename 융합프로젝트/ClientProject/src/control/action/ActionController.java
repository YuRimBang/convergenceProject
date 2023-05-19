package control.action;

import control.target.CustomerController;
import control.target.ManagerController;
import control.target.OwnerController;
import protocol.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
import java.io.*;
import java.util.Scanner;

public class ActionController {
        private RequestOwnerSender requestOwnerSender = new RequestOwnerSender();
        private RequestCustomerSender requestCustomerSender = new RequestCustomerSender();
        private ResponseReceiver responseReceiver = new ResponseReceiver();

        public boolean signUpMenu(DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
                System.out.println("회원가입 유형을 선택하십시오.");
                System.out.println("1. 고객 회원가입");
                System.out.println("2. 점주 회원가입");
                Scanner scanner = new Scanner(System.in);
                int option = scanner.nextInt();
                return signUp(option, inputStream, outputStream);
        }

        public boolean signUp(int option, DataInputStream inputStream, DataOutputStream outputStream) throws IOException {
                System.out.println("옵션 : " + option);
                if (option == 1) {
                        requestCustomerSender.requestSignUp(outputStream);
                        return responseReceiver.receiveResult(inputStream);
                } else if (option == 2) {
                        requestOwnerSender.requestSignUp(outputStream);
                        return responseReceiver.receiveResult(inputStream);
                } else {
                        System.out.println("잘못된 옵션입니다.");
                        return false;
                }
        }

        public void login(DataOutputStream outputStream) throws IOException {
                Scanner scanner = new Scanner(System.in);
                System.out.print("ID를 입력하세요 : ");
                String id = scanner.next();
                System.out.print("PW를 입력하세요 : ");
                String pw = scanner.next();
                ByteArrayOutputStream buf = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(buf);
                dos.writeUTF(id);
                dos.writeUTF(pw);
                byte[] body = buf.toByteArray();
                Header header = new Header(
                        Header.TYPE_REQUEST,
                        Header.CODE_REQ_LOGIN,
                        body.length
                );
                outputStream.write(header.getBytes());
                outputStream.write(body);
        }

        CustomerController customerController = new CustomerController();
        ManagerController managerController = new ManagerController();
        OwnerController ownerController = new OwnerController();

        public boolean decideClientType(DataInputStream bodyReader, DataOutputStream outputStream) throws IOException {
                Header header = Header.readHeader(bodyReader);
                byte[] body = new byte[header.bodySize];
                bodyReader.read(body);
                DataInputStream type = new DataInputStream(new ByteArrayInputStream(body));
                String clientType = type.readUTF();
                String clientID = type.readUTF();
                switch (clientType) {
                        case "관리자":
                                Scanner sc = new Scanner(System.in);
                                System.out.println("실행할 옵션을 선택하세요.");
                                System.out.println("1. 고객정보조회");
                                System.out.println("2. 점주정보조회");
                                System.out.println("3. 가게목록조회");
                                System.out.println("4. 가게매출조회");
                                System.out.println("5. 가게승인");
                                System.out.print("6. 메뉴승인\n=>");
                                int option = sc.nextInt();
                                managerController.handleRead(option, bodyReader, outputStream);
                                break;
                        case "점주":
                                Scanner sc1 = new Scanner(System.in);
                                System.out.println("실행할 옵션을 선택하세요.");
                                System.out.println("1. 가게등록신청");
                                System.out.println("2. 메뉴등록신청");
                                System.out.println("3. 메뉴수정");
                                System.out.println("4. 할인정책");
                                System.out.println("5. 가게운영시간설정");
                                System.out.println("6. 답글등록");
                                System.out.println("7. 주문정보조회");
                                System.out.println("8. 가게정보조회");
                                System.out.println("9. 통계정보조회");
                                System.out.println("10. 주문 접수/거절");
                                System.out.println("11. 배달완료신청");
                                System.out.println("12. 메뉴옵션 신청");
                                System.out.print("13. 배달신청\n=>");

                                int option1 = sc1.nextInt();
                                ownerController.handleRead(option1,clientID, bodyReader, outputStream);
                                break;
                        case "고객":
                                Scanner sc2 = new Scanner(System.in);
                                System.out.println("실행할 옵션을 선택하세요.");
                                System.out.println("1. 개인정보수정");
                                System.out.println("2. 음식주문");
                                System.out.println("3. 주문취소");
                                System.out.println("4. 리뷰별점등록");
                                System.out.println("5. 음식점리스트정보조회");
                                System.out.println("6. 계정정보조회");
                                System.out.println("7. 주문내역조회");
                                System.out.print("8. 음식점단일조회\n=>");
                                int option2 = sc2.nextInt();
                                customerController.handleRead(option2, clientID, bodyReader, outputStream);
                                break;
                }
                return true;
        }

}



