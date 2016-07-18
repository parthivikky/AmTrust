package mobello.amtrust.com.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

import mobello.amtrust.com.activity.DeviceCategoryActivity;

/**
 * Created by Parthi on 25-May-16.
 */
public class TechSupport {

    private ArrayList<Result> result = new ArrayList<Result>();

    /**
     *
     * @return
     * The result
     */
    public ArrayList<Result> getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(ArrayList<Result> result) {
        this.result = result;
    }

    public class Result {

        private String type_id;
        private String question;
        private ArrayList<Detail> details;

        public String getType_id() {
            return type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public ArrayList<Detail> getDetails() {
            return details;
        }

        public void setDetails(ArrayList<Detail> details) {
            this.details = details;
        }
    }

    public static class Detail implements Parcelable{

        private String photo;
        private String description;

        protected Detail(Parcel in) {
            photo = in.readString();
            description = in.readString();
        }

        public static final Creator<Detail> CREATOR = new Creator<Detail>() {
            @Override
            public Detail createFromParcel(Parcel in) {
                return new Detail(in);
            }

            @Override
            public Detail[] newArray(int size) {
                return new Detail[size];
            }
        };

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(photo);
            dest.writeString(description);
        }
    }
}
