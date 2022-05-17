package zxc.studio.vpt.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import zxc.studio.vpt.DateConverter;

import java.util.Date;

public class Workout implements Parcelable {

    private String workout_athlete_id;
    private String workout_coach_id;
    private Date workout_completedDate;
    private Date workout_dateFor;
    private int workout_food;
    private int workout_mood;
    private float workout_sleep;
    private float workout_weight;
    private String workout_id;
    private String workout_programme_id;

    protected Workout(Parcel in) {
        workout_athlete_id = in.readString();
        workout_coach_id = in.readString();
        workout_food = in.readInt();
        workout_mood = in.readInt();
        workout_sleep = in.readFloat();
        workout_weight = in.readFloat();
        workout_id = in.readString();
        workout_programme_id = in.readString();
    }

    public Workout(){}
    public Workout(String athlete_id,String coach_id, Date completedDate, Date dateFor, int food, int mood, float sleep, float weight, String id, String programme_id){
        workout_athlete_id = athlete_id;
        workout_coach_id = coach_id;
        workout_completedDate = completedDate;
        workout_dateFor = dateFor;
        workout_food = food;
        workout_mood = mood;
        workout_sleep = sleep;
        workout_weight = weight;
        workout_id = id;
        workout_programme_id = programme_id;
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public String getWorkout_athlete_id() {
        return workout_athlete_id;
    }
    public void setWorkout_athlete_id(String workout_athlete_id) {
        this.workout_athlete_id = workout_athlete_id;
    }
    public String getWorkout_coach_id() {
        return workout_coach_id;
    }
    public void setWorkout_coach_id(String workout_coach_id) {
        this.workout_coach_id = workout_coach_id;
    }
    public Date getWorkout_completedDate() {
        return workout_completedDate;
    }
    public void setWorkout_completedDate(Date workout_completedDate) {
        this.workout_completedDate = workout_completedDate;
    }
    public Date getWorkout_dateFor() {
        return workout_dateFor;
    }
    public void setWorkout_dateFor(Date workout_dateFor) {
        this.workout_dateFor = workout_dateFor;
    }
    public int getWorkout_food() {
        return workout_food;
    }
    public void setWorkout_food(int workout_food) {
        this.workout_food = workout_food;
    }
    public int getWorkout_mood() {
        return workout_mood;
    }
    public void setWorkout_mood(int workout_mood) {
        this.workout_mood = workout_mood;
    }
    public float getWorkout_sleep() {
        return workout_sleep;
    }
    public void setWorkout_sleep(float workout_sleep) {
        this.workout_sleep = workout_sleep;
    }
    public float getWorkout_weight() {
        return workout_weight;
    }
    public void setWorkout_weight(float workout_weight) {
        this.workout_weight = workout_weight;
    }
    public String getWorkout_id() {
        return workout_id;
    }
    public void setWorkout_id(String workout_id) {
        this.workout_id = workout_id;
    }
    public String getWorkout_programme_id() {
        return workout_programme_id;
    }
    public void setWorkout_programme_id(String workout_programme_id) {
        this.workout_programme_id = workout_programme_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(workout_athlete_id);
        parcel.writeString(workout_coach_id);
        parcel.writeInt(workout_food);
        parcel.writeInt(workout_mood);
        parcel.writeFloat(workout_sleep);
        parcel.writeFloat(workout_weight);
        parcel.writeString(workout_id);
        parcel.writeString(workout_programme_id);
    }
}
