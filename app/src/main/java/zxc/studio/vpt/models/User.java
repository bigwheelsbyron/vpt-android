package zxc.studio.vpt.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {

    private String user_club;
    private String user_coach;
    private String user_email;
    private String user_firebase_id;
    private String user_firstName;
    private String user_id;
    private String user_lastName;
    private String user_sex;
    private Date user_signup_date;

    public User(){}

    public User(String club,String coach, String email, String firebase_id, String firstName, String id, String lastName, String sex, Date signup){
        user_club = club;
        user_coach = coach;
        user_email = email;
        user_firebase_id = firebase_id;
        user_firstName = firstName;
        user_id = id;
        user_lastName = lastName;
        user_sex = sex;
        user_signup_date = signup;
    }

    protected User(Parcel in) {
        user_club = in.readString();
        user_coach = in.readString();
        user_email = in.readString();
        user_firebase_id = in.readString();
        user_firstName = in.readString();
        user_id = in.readString();
        user_lastName = in.readString();
        user_sex = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(user_club);
        parcel.writeString(user_coach);
        parcel.writeString(user_email);
        parcel.writeString(user_firebase_id);
        parcel.writeString(user_firstName);
        parcel.writeString(user_id);
        parcel.writeString(user_lastName);
        parcel.writeString(user_sex);
//        parcel.writeString(user_signup_date);
    }

    public String getUser_club() {
        return user_club;
    }
    public void setUser_club(String user_club) {
        this.user_club = user_club;
    }
    public String getUser_coach() {
        return user_coach;
    }
    public void setUser_coach(String user_coach) {
        this.user_coach = user_coach;
    }
    public String getUser_email() {
        return user_email;
    }
    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }
    public String getUser_firebase_id() {
        return user_firebase_id;
    }
    public void setUser_firebase_id(String user_firebase_id) {
        this.user_firebase_id = user_firebase_id;
    }
    public String getUser_firstName() {
        return user_firstName;
    }
    public void setUser_firstName(String user_firstName) {
        this.user_firstName = user_firstName;
    }
    public String getUser_id() {
        return user_id;
    }
    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    public String getUser_lastName() {
        return user_lastName;
    }
    public void setUser_lastName(String user_lastName) {
        this.user_lastName = user_lastName;
    }
    public String getUser_sex() {
        return user_sex;
    }
    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }
    public Date getUser_signup_date() {
        return user_signup_date;
    }
    public void setUser_signup_date(Date user_signup_date) {
        this.user_signup_date = user_signup_date;
    }
}
