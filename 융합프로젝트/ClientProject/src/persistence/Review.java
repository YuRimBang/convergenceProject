package persistence;

import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public class Review implements MySerializableClass
{
    private String ClientID;
    private String StoreID;
    private String MenuName;
    private String Review;
    private LocalDateTime deliveryTime;
    private int Grade;
    private String ownerAnswer;

    public Review(String clientID, String storeID, String menuName, String review, LocalDateTime deliveryTime, int grade, String ownerAnswer) {
        ClientID = clientID;
        StoreID = storeID;
        MenuName = menuName;
        Review = review;
        this.deliveryTime = deliveryTime;
        Grade = grade;
        this.ownerAnswer = ownerAnswer;
    }

    @Override
    public String toString() {
        return "Review{" +
                "ClientID='" + ClientID + '\'' +
                ", StoreID='" + StoreID + '\'' +
                ", MenuName='" + MenuName + '\'' +
                ", Review='" + Review + '\'' +
                ", deliveryTime=" + deliveryTime +
                ", Grade=" + Grade +
                ", ownerAnswer=" + ownerAnswer+
                '}';
    }

    public static Review readReview(DataInputStream dis) throws IOException
    {
        String ClientID = dis.readUTF();
        String storeID = dis.readUTF();
        String MenuName = dis.readUTF();
        String Review = dis.readUTF();
        LocalDateTime deliveryTime = LocalDateTime.parse(dis.readUTF());
        int Grade = dis.readInt();
        String ownerAnswer = dis.readUTF();

        return new Review(ClientID, storeID, MenuName, Review, deliveryTime, Grade,ownerAnswer);
    }

    public byte[] getBytes() throws IOException
    {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(buf);

        dos.writeUTF(ClientID);
        dos.writeUTF(StoreID);
        dos.writeUTF(MenuName);
        dos.writeUTF(Review);
        dos.writeUTF(String.valueOf(deliveryTime));
        dos.writeInt(Grade);
        dos.writeUTF(ownerAnswer);

        return buf.toByteArray();
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public String getStoreID() {
        return StoreID;
    }

    public void setStoreID(String storeID) {
        StoreID = storeID;
    }

    public String getMenuName() {
        return MenuName;
    }

    public void setMenuName(String menuName) {
        MenuName = menuName;
    }

    public void setReview(String review) {
        Review = review;
    }

    public LocalDateTime getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(LocalDateTime deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    public String getOwnerAnswer() {
        return ownerAnswer;
    }

    public void setOwnerAnswer(String ownerAnswer) {
        this.ownerAnswer = ownerAnswer;
    }
}
