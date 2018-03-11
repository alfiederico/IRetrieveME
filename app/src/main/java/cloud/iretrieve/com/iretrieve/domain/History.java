package cloud.iretrieve.com.iretrieve.domain;

import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alfie on 08/01/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class History {

    @JsonProperty("id")
    private int id;

    @JsonProperty("type_id")
    private int type_id;

    @JsonProperty("user_id")
    private int user_id;

    @JsonProperty("type")
    private String type;

    @JsonProperty("subject")
    private String subject;

    @JsonProperty("description")
    private String description;

    @JsonProperty("date")
    private String date;

    @JsonProperty("location")
    private String location;

    @JsonProperty("settleId")
    private int settleId;

    @JsonProperty("place")
    private String place;

    @JsonProperty("photo")
    private String photo;

    private Point point;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return user_id;
    }

    public void setUserId(int user_id) {
        this.user_id = user_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Point getPoint() {
        return null;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public boolean hasLocation() {
        return (this.getLocation() != null);
    }

    /**
     * @return the place
     */
    public String getPlace() {
        return place;
    }

    /**
     * @param place the place to set
     */
    public void setPlace(String place) {
        this.place = place;
    }

    /**
     * @return the typeId
     */
    public int getTypeId() {
        return type_id;
    }

    /**
     * @param type_id the typeId to set
     */
    public void setTypeId(int type_id) {
        this.type_id = type_id;
    }

    /**
     * @return the settleId
     */
    public int getSettleId() {
        return settleId;
    }

    /**
     * @param settleId the settleId to set
     */
    public void setSettleId(int settleId) {
        this.settleId = settleId;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}

