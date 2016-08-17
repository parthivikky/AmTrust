package mobello.amtrust.com.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Parthi on 11-Aug-16.
 */
public class Choose implements Parcelable {

    private String name;
    private boolean isSelected;

    public Choose(String name, boolean isSelected) {
        this.name = name;
        this.isSelected = isSelected;
    }

    protected Choose(Parcel in) {
        name = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Choose> CREATOR = new Creator<Choose>() {
        @Override
        public Choose createFromParcel(Parcel in) {
            return new Choose(in);
        }

        @Override
        public Choose[] newArray(int size) {
            return new Choose[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
