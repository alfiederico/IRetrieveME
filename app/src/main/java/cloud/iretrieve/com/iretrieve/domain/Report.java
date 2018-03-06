package cloud.iretrieve.com.iretrieve.domain;

import android.graphics.Point;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Alfie on 07/01/18.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class Report {
    @JsonProperty("id")
    private int id;

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

    @JsonProperty("date_created")
    private String date_created;

    @JsonProperty("last_updated")
    private String last_updated;

    @JsonProperty("isettle")
    private int isettle;

    @JsonProperty("usettle")
    private int usettle;

    @JsonProperty("place")
    private String place;

    private Point point;

    @JsonProperty("status")
    private String status;

    @JsonProperty("contact")
    private String contact;

    @JsonProperty("photo")
    private String photo;

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

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

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String datecreated) {
        this.date_created = datecreated;
    }

    public String getLast_updated() {
        return last_updated;
    }

    public void setLast_updated(String last_updated) {
        this.last_updated = last_updated;
    }

    public int getIsettle() {
        return isettle;
    }

    public void setIsettle(int isettle) {
        this.isettle = isettle;
    }

    public int getUsettle() {
        return usettle;
    }

    public void setUsettle(int usettle) {
        this.usettle = usettle;
    }

    public Point getPoint() {
        /**String _location = this.getLocation();
        if (this.getLocation() == null) {
            _location = "-1,-1";
        }
        return GeoConverters.StringToPointConverter.INSTANCE.convert(_location);**/
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
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
