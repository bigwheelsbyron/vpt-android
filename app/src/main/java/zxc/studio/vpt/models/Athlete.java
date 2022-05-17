package zxc.studio.vpt.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;

@Entity
public class Athlete implements Parcelable {

    private Integer EXPECTED_ID_LENGTH = 28;
    private String athleteUID;
    private String first_name;
    private String last_name;

    public Athlete(){}
    public Athlete(String Unique_id, String First_name, String Last_name){
        if (validID(Unique_id)){
            athleteUID=Unique_id;
        } else {
            athleteUID=null;
        }
        first_name=First_name;
        last_name=Last_name;
    }

    public String getAthleteUID() {
        return athleteUID;
    }
    public void setAthleteUID(String unique_Athlete_id) {
        if (validID(unique_Athlete_id)){
            athleteUID = unique_Athlete_id;
        } else {
            return;
        }
    }

    private boolean validID(String unique_athlete_id) {
        return unique_athlete_id.length() == EXPECTED_ID_LENGTH;
    }

    public String getFirst_name() {
        return first_name;
    }
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    public String getLast_name() {
        return last_name;
    }
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    protected Athlete(Parcel in) {
        athleteUID = in.readString();
    }

    public static final Creator<Athlete> CREATOR = new Creator<Athlete>() {
        @Override
        public Athlete createFromParcel(Parcel in) {
            return new Athlete(in);
        }

        @Override
        public Athlete[] newArray(int size) {
            return new Athlete[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(athleteUID);
    }
}
