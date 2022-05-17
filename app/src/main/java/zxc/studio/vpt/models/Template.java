package zxc.studio.vpt.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Template implements Parcelable {
    private String coachID;
    private String templateName;
    private String template_ID;

    protected Template(Parcel in) {
        coachID = in.readString();
        templateName = in.readString();
        template_ID = in.readString();
    }
    public static final Creator<Template> CREATOR = new Creator<Template>() {
        @Override
        public Template createFromParcel(Parcel in) {
            return new Template(in);
        }

        @Override
        public Template[] newArray(int size) {
            return new Template[size];
        }
    };
    public String getCoachID() {
        return coachID;
    }
    public void setCoachID(String coachID) {
        this.coachID = coachID;
    }
    public String getTemplateName() {
        return templateName;
    }
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }
    public String getTemplate_ID() {
        return template_ID;
    }
    public void setTemplate_ID(String template_ID) {
        this.template_ID = template_ID;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public Template(){}
    public Template(String coachid,String templatename, String templateid){coachID=coachid;templateName=templatename;template_ID=templateid;}

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(coachID);
        parcel.writeString(templateName);
        parcel.writeString(template_ID);
    }
}
