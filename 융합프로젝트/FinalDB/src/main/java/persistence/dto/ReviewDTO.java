package persistence.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import protocol.MySerializableClass;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString

public class ReviewDTO implements MySerializableClass
{
    private String ClientID;
    private String StoreID;
    private String MenuName;
    private String Review;
    private LocalDateTime deliveryTime;
    private int Grade;
    private String ownerAnswer;

    public ReviewDTO()
    {

    }
    public static ReviewDTO readReview(DataInputStream dis) throws IOException
    {
        String ClientID = dis.readUTF();
        String storeID = dis.readUTF();
        String MenuName = dis.readUTF();
        String Review = dis.readUTF();
        LocalDateTime deliveryTime = LocalDateTime.parse(dis.readUTF());
        int Grade = dis.readInt();
        String ownerAnswer = dis.readUTF();

        return new ReviewDTO(ClientID, storeID, MenuName, Review, deliveryTime, Grade,ownerAnswer);
    }
    public ReviewDTO(String clientID, String storeID, String menuName, String review, LocalDateTime deliveryTime, int grade, String ownerAnswer) {
        ClientID = clientID;
        StoreID = storeID;
        MenuName = menuName;
        Review = review;
        this.deliveryTime = deliveryTime;
        Grade = grade;
        this.ownerAnswer = ownerAnswer;
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

        return buf.toByteArray();
    }
}
