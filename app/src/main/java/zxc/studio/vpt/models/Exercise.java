package zxc.studio.vpt.models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.Date;

import zxc.studio.vpt.DateConverter;


public class Exercise implements Parcelable{

    private String exercise_athlete_id;
    private String exercise_coach_id;
    private String exercise_classification;
    private Boolean exercise_completedBoolean;
    private Date exercise_completedDate;
    private int exercise_difficulty;
    private float exercise_distance;
    private float exercise_duration;
    private String exercise_id;
    private String exercise_name;
    private String exercise_note;
    private int exercise_reps;
    private int exercise_rpe;
    private int exercise_sequence;
    private String exercise_uom;
    private float exercise_weight;
    private String exercise_workout_id;
    private String exercise_programme_id;

    public Exercise(){}

    public Exercise(String athlete_id,String coach_id,String classification,Boolean completeBoolean,Date completedDate,int difficulty,float distance,float duration,String id,String name,String note,int reps,int rpe,int sequence,String uom,float weight,String workout_id,String programme_id){
        exercise_athlete_id = athlete_id;
        exercise_coach_id = coach_id;
        exercise_classification = classification;
        exercise_completedBoolean = completeBoolean;
        exercise_completedDate = completedDate;
        exercise_difficulty = difficulty;
        exercise_distance = distance;
        exercise_duration = duration;
        exercise_id = id;
        exercise_name = name;
        exercise_note = note;
        exercise_reps = reps;
        exercise_rpe = rpe;
        exercise_sequence = sequence;
        exercise_uom = uom;
        exercise_weight = weight;
        exercise_workout_id = workout_id;
        exercise_programme_id = programme_id;
    }

    protected Exercise(Parcel in) {
        exercise_athlete_id = in.readString();
        exercise_coach_id = in.readString();
        exercise_classification = in.readString();
        byte tmpExercise_completedBoolean = in.readByte();
        exercise_completedBoolean = tmpExercise_completedBoolean == 0 ? null : tmpExercise_completedBoolean == 1;
        exercise_difficulty = in.readInt();
        exercise_distance = in.readFloat();
        exercise_duration = in.readFloat();
        exercise_id = in.readString();
        exercise_name = in.readString();
        exercise_note = in.readString();
        exercise_reps = in.readInt();
        exercise_rpe = in.readInt();
        exercise_sequence = in.readInt();
        exercise_uom = in.readString();
        exercise_weight = in.readFloat();
        exercise_workout_id = in.readString();
        exercise_programme_id = in.readString();
    }

    public static final Creator<Exercise> CREATOR = new Creator<Exercise>() {
        @Override
        public Exercise createFromParcel(Parcel in) {
            return new Exercise(in);
        }

        @Override
        public Exercise[] newArray(int size) {
            return new Exercise[size];
        }
    };

    public String getExercise_athlete_id() {
        return exercise_athlete_id;
    }
    public void setExercise_athlete_id(String exercise_athlete_id) {
        this.exercise_athlete_id = exercise_athlete_id;
    }
    public String getExercise_coach_id() {
        return exercise_coach_id;
    }
    public void setExercise_coach_id(String exercise_coach_id) {
        this.exercise_coach_id = exercise_coach_id;
    }
    public String getExercise_classification() {
        return exercise_classification;
    }
    public void setExercise_classification(String exercise_classification) {
        this.exercise_classification = exercise_classification;
    }
    public Boolean getExercise_completedBoolean() {
        return exercise_completedBoolean;
    }
    public void setExercise_completedBoolean(Boolean exercise_completedBoolean) {
        this.exercise_completedBoolean = exercise_completedBoolean;
    }
    public Date getExercise_completedDate() {
        return exercise_completedDate;
    }
    public void setExercise_completedDate(Date exercise_completedDate) {
        this.exercise_completedDate = exercise_completedDate;
    }
    public int getExercise_difficulty() {
        return exercise_difficulty;
    }
    public void setExercise_difficulty(int exercise_difficulty) {
        this.exercise_difficulty = exercise_difficulty;
    }
    public float getExercise_distance() {
        return exercise_distance;
    }
    public void setExercise_distance(float exercise_distance) {
        this.exercise_distance = exercise_distance;
    }
    public float getExercise_duration() {
        return exercise_duration;
    }
    public void setExercise_duration(float exercise_duration) {
        this.exercise_duration = exercise_duration;
    }
    public String getExercise_id() {
        return exercise_id;
    }
    public void setExercise_id(String exercise_id) {
        this.exercise_id = exercise_id;
    }
    public String getExercise_name() {
        return exercise_name;
    }
    public void setExercise_name(String exercise_name) {
        this.exercise_name = exercise_name;
    }
    public String getExercise_note() {
        return exercise_note;
    }
    public void setExercise_note(String exercise_note) {
        this.exercise_note = exercise_note;
    }
    public int getExercise_reps() {
        return exercise_reps;
    }
    public void setExercise_reps(int exercise_reps) {
        this.exercise_reps = exercise_reps;
    }
    public int getExercise_rpe() {
        return exercise_rpe;
    }
    public void setExercise_rpe(int exercise_rpe) {
        this.exercise_rpe = exercise_rpe;
    }
    public int getExercise_sequence() {
        return exercise_sequence;
    }
    public void setExercise_sequence(int exercise_sequence) {
        this.exercise_sequence = exercise_sequence;
    }
    public String getExercise_uom() {
        return exercise_uom;
    }
    public void setExercise_uom(String exercise_uom) {
        this.exercise_uom = exercise_uom;
    }
    public float getExercise_weight() {
        return exercise_weight;
    }
    public void setExercise_weight(float exercise_weight) {
        this.exercise_weight = exercise_weight;
    }
    public String getExercise_workout_id() {
        return exercise_workout_id;
    }
    public void setExercise_workout_id(String exercise_workout_id) {
        this.exercise_workout_id = exercise_workout_id;
    }
    public String getExercise_programme_id() {
        return exercise_programme_id;
    }
    public void setExercise_programme_id(String exercise_programme_id) {
        this.exercise_programme_id = exercise_programme_id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(exercise_athlete_id);
        parcel.writeString(exercise_coach_id);
        parcel.writeString(exercise_classification);
        parcel.writeByte((byte) (exercise_completedBoolean == null ? 0 : exercise_completedBoolean ? 1 : 2));
        parcel.writeInt(exercise_difficulty);
        parcel.writeFloat(exercise_distance);
        parcel.writeFloat(exercise_duration);
        parcel.writeString(exercise_id);
        parcel.writeString(exercise_name);
        parcel.writeString(exercise_note);
        parcel.writeInt(exercise_reps);
        parcel.writeInt(exercise_rpe);
        parcel.writeInt(exercise_sequence);
        parcel.writeString(exercise_uom);
        parcel.writeFloat(exercise_weight);
        parcel.writeString(exercise_workout_id);
        parcel.writeString(exercise_programme_id);
    }
}
