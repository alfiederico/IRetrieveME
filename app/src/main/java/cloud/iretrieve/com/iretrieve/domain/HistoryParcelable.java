/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cloud.iretrieve.com.iretrieve.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 *
 * @author Alfie
 */


public class HistoryParcelable implements Parcelable {


    private int id;

    private int typeId;


    private int userId;

    private String type;


    private String subject;


    private String description;


    private String date;


    private String location;


    private int settleId;


    private String place;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int user_id) {
        this.userId = user_id;
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
        return typeId;
    }

    /**
     * @param typeId the typeId to set
     */
    public void setTypeId(int typeId) {
        this.typeId = typeId;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);

        dest.writeInt(typeId);


        dest.writeInt(userId);

        dest.writeString(type);


        dest.writeString(subject);


        dest.writeString(description);


        dest.writeString(date);


        dest.writeString(location);


        dest.writeInt(settleId);


        dest.writeString(place);
    }

    public static final Creator<HistoryParcelable> CREATOR = new Creator<HistoryParcelable>() {

        public HistoryParcelable createFromParcel(Parcel source) {

            HistoryParcelable mArt = new HistoryParcelable();

            mArt.setId(source.readInt());

            mArt.setTypeId(source.readInt());


            mArt.setUserId(source.readInt());

            mArt.setType(source.readString());


            mArt.setSubject(source.readString());


            mArt.setDescription(source.readString());


            mArt.setDate(source.readString());


            mArt.setLocation(source.readString());


            mArt.setSettleId(source.readInt());


            mArt.setPlace(source.readString());

            return mArt;

        }

        public HistoryParcelable[] newArray(int size) {

            return new HistoryParcelable[size];

        }

    };
}
