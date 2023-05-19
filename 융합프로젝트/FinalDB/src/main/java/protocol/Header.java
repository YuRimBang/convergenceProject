package protocol;
import java.io.*;

public class Header implements MySerializableClass
{
    public final static byte TYPE_REQUEST = 1;
    public final static byte TYPE_RESPONSE = 2;
    public final static byte TYPE_RESULT = 3;

    public final static byte CODE_REQ_LOGIN = 0;//로그인(접속)
    public final static byte CODE_SERVER_REQ_ADD_STORE = 1;//가게추가요청
    public final static byte CODE_SERVER_REQ_REGIST_MENU = 2;//메뉴등록요청
    public final static byte CODE_SERVER_REQ_PASSWORD = 3;//PW입력요청
    public final static byte CODE_SERVER_REQ_CANCEL_ORDER = 4;//주문취소요청
    public final static byte CODE_OWNER_REQ_ADD_STORE = 5;//가게등록신청
    public final static byte CODE_OWNER_REQ_REGIST_MENU = 6;//메뉴등록신청
    public final static byte CODE_OWNER_REQ_FIX_MENU = 7;//메뉴수정
    public final static byte CODE_OWNER_REQ_DISCNT_POLICY = 8;//할인정책
    public final static byte CODE_OWNER_REQ_STORE_TIME_SET = 9;//가게운영시간설정
    public final static byte CODE_OWNER_REQ_REPLY = 10;//답글등록요청
    public final static byte CODE_CUSTOMER_REQ_SIGNUP = 11;//고객 회원가입
    public final static byte CODE_CUSTOMER_REQ_FIX_CUST_INFO = 12;//개인정보수정
    public final static byte CODE_CUSTOMER_REQ_ORDER = 13;//음식주문
    public final static byte CODE_CUSTOMER_REQ_CANCEL_ORDER = 14;//주문취소
    public final static byte CODE_CUSTOMER_REQ_REVIEW = 15;//리뷰별점등록
    public final static byte CODE_MANAGER_REFER_REQ_CUST_INFO = 16;//고객정보조회
    public final static byte CODE_MANAGER_REFER_REQ_OWNER_INFO = 17;//점주정보조회
    public final static byte CODE_MANAGER_REFER_REQ_STORE_LIST = 18;//가게목록조회
    public final static byte CODE_MANAGER_REFER_REQ_STORE_SALES = 19;//가게매출조회
    public final static byte CODE_OWNER_REFER_REQ_ORDER_INFO = 20;//주문정보조회
    public final static byte CODE_OWNER_REFER_REQ_STORE_INFO = 21;//가게정보조회
    public final static byte CODE_OWNER_REFER_REQ_STATS_INFO = 22;//통계정보조회
    public final static byte CODE_CUSTOMER_REFER_REQ_ACCOUNT_INFO = 23;//계정정보
    public final static byte CODE_CUSTOMER_REFER_REQ_STORE_LIST = 24;//음식점리스트정보
    public final static byte CODE_CUSTOMER_REFER_REQ_ORDER_LIST = 25;//주문내역조회
    public final static byte CODE_CUSTOMER_REFER_REQ_STORE = 26; //음식점 단일 조회
    public final static byte CODE_CUSTOMER_REFER_REQ_MENU_LIST = 27; //메뉴조회
    public final static byte CODE_OWNER_REQ_SIGNUP = 28; //점주 회원가입
    public final static byte CODE_OWNER_REQ_REPLY_ANSWER = 29; //답글 등록 (번호, 답글)
    public final static byte CODE_MANAGER_REQ_STORE_ACCEPT = 30; //관리자 가게 승인
    public final static byte CODE_MANAGER_REQ_MENU_ACCEPT = 31; //관리자 메뉴 승인
    public final static byte CODE_OWNER_REQ_HOLD_ORDERLIST = 32; //점주가 주문 리스트 받음
    public final static byte CODE_CUSTOMER_REQ_CANCANCEL_LIST = 33; //취소 가능한 리스트 전송 요청
    public final static byte CODE_CUSTOMER_REQ_CANREVIEW_LIST = 34; //리뷰 가능한 리스트 전송 요청
    public final static byte CODE_OWNER_REQ_CANACCEPT_ORDER_LIST = 35; //주문 접수 가능한 주문 리스트
    public final static byte CODE_OWNER_REQ_ADD_MENUOPTION  = 36; // 메뉴옵션추가
    public final static byte CODE_OWNER_REQ_AND_DELIVERY = 37; //배달완료요청
    public final static byte CODE_CUSTOMER_REQ_MENUOPTIONLIST = 38; // 메뉴옵션 리스트
    public final static byte CODE_OWNER_REQ_CAN_DELIVERYLIST = 39; //배달완료가능 리스트
    public final static byte CODE_OWNER_REQ_START_DELIVERY = 40; // 배달시작 요청
    public final static byte CODE_OWNER_REQ_CAN_START_DELIVERYLIST = 41; //배달 시작 가능 리스트
    public final static byte CODE_CUSTOMER_REQ_MY_REVIEW = 42;


    //서희꺼
    public final static byte CODE_RES_LOGIN = 0;//로그인(응답)
    public final static byte CODE_SERVER_RES_ORDER_ANS_INFO = 1;//주문 거절/승인 정보
    public final static byte CODE_OWNER_RES_ORDER_ANS_INFO = 2;//주문 거절/접수 정보
    public final static byte CODE_CUST_RES_PASSWORD = 3;//비밀번호
    public final static byte CODE_CUST_RES_FIX_CUST_INFO = 4;//수정할 개인정보(PW포함)
    public final static byte CODE_SERVER_REFER_RES_CUST_ORDER_INFO = 5;//고객의 주문정보
    public final static byte CODE_SERVER_REFER_RES_CUST_INFO = 6;//고객정보
    public final static byte CODE_SERVER_REFER_RES_OWNER_INFO = 7;//점주정보
    public final static byte CODE_SERVER_REFER_RES_OWNER_STORE_LIST = 8;//가게목록정보
    public final static byte CODE_SERVER_REFER_RES_STORE_SALES = 9;//가게매출정보
    public final static byte CODE_SERVER_REFER_RES_STATS_INFO = 10;//통계정보
    public final static byte CODE_SERVER_REFER_RES_ACCOUNT_INFO = 11;//계정정보
    public final static byte CODE_SERVER_REFER_RES_STORE_LIST = 12;//음식점목록
    public final static byte CODE_SERVER_REFER_RES_CUST_ORDER_LIST = 13;//고객의 주문내역
    public final static byte CODE_SERVER_REFER_RES_STORE_INFO = 14;//가게정보
    public final static byte CODE_SERVER_REFER_RES_STORE = 15; //음식점 단일 조회 정보
    public final static byte CODE_SERVER_REFER_MENU_LIST = 16; //메뉴 조회
    public final static byte CODE_SERVER_RES_CANREVIEWLIST = 17; //답글 가능한 리뷰 리스트 전송
    public final static byte CODE_SERVER_RES_HOLD_STORE_LIST = 18; //서버가 매니저한테 가게 추가 보류 리스트 보내는거
    public final static byte CODE_SERVER_RES_HOLE_MENU_LIST = 19; //서버가 매니저한테 메뉴 추가 보류 리스트 보내는거
    public final static byte CODE_SERVER_RES_CANCANCEL_LIST = 20; //서버가 고객한테 취소 가능한 리스트 보내는거
    public final static byte CODE_SERVER_RES_CANREVIEW_LIST = 21; //서버가 고객한테 리뷰 가능한 리스트 보내는거
    public final static byte CODE_SERVER_RES_CANACCEPT_ORDER_LIST = 22; //주문 접수 가능한 주문 리스트
    public final static byte CODE_SERVER_RES_MENUOPTIONLIST = 23; //메뉴옵션 서버에서 리스트 보냄
    public final static byte CODE_CUSTOMER_RES_MY_REVIEW = 24;

    public final static byte CODE_RESULT_SUCCESS = 1;//성공
    public final static byte CODE_RESULT_FAIL = 2;//실패
    public final static byte CODE_RESULT_HOLD = 3;//등록신청 보류

    public final static int SIZE_HEADER = 6;

    public byte type;
    public byte code;
    public int bodySize;

    public Header(byte type, byte code, int bodySize)
    {
        this.type = type;
        this.code = code;
        this.bodySize = bodySize;
    }
    public static Header readHeader(DataInputStream dis) throws IOException
    {
        byte type = dis.readByte();
        byte code = dis.readByte();
        int bodySize = dis.readByte();
        return new Header(type,code, bodySize);
    }

    @Override
    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);
        dos.writeByte(type);
        dos.writeByte(code);
        dos.writeByte(bodySize);
        return buf.toByteArray();
    }
}