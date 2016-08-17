package mobello.amtrust.com.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Parthi on 01-Aug-16.
 */
public class ProfileInfo {
    public Boolean success;
    public Result result;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public class Result {

        public String status;
        public Result_ result;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public Result_ getResult() {
            return result;
        }

        public void setResult(Result_ result) {
            this.result = result;
        }
    }

    public static class Result_ implements Parcelable {

        public String firstname;
        public String lastname;
        public String imagename;
        public String email;
        public String mobile;
        public String channelTitle;
        public String country;
        public String countrycode;
        public String address;
        public String city;
        public String state;
        public String no_of_devices;
        public String no_of_policies;
        public String no_of_diagnosis;
        public String contactid;
        public String profile_picture;

        protected Result_(Parcel in) {
            firstname = in.readString();
            lastname = in.readString();
            imagename = in.readString();
            email = in.readString();
            mobile = in.readString();
            channelTitle = in.readString();
            country = in.readString();
            countrycode = in.readString();
            address = in.readString();
            city = in.readString();
            state = in.readString();
            no_of_devices = in.readString();
            no_of_policies = in.readString();
            no_of_diagnosis = in.readString();
            contactid = in.readString();
            profile_picture = in.readString();
        }

        public static final Creator<Result_> CREATOR = new Creator<Result_>() {
            @Override
            public Result_ createFromParcel(Parcel in) {
                return new Result_(in);
            }

            @Override
            public Result_[] newArray(int size) {
                return new Result_[size];
            }
        };

        public String getProfile_picture() {
            return profile_picture;
        }

        public void setProfile_picture(String profile_picture) {
            this.profile_picture = profile_picture;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getImagename() {
            return imagename;
        }

        public void setImagename(String imagename) {
            this.imagename = imagename;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getChannelTitle() {
            return channelTitle;
        }

        public void setChannelTitle(String channelTitle) {
            this.channelTitle = channelTitle;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public void setCountrycode(String countrycode) {
            this.countrycode = countrycode;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getNo_of_devices() {
            return no_of_devices;
        }

        public void setNo_of_devices(String no_of_devices) {
            this.no_of_devices = no_of_devices;
        }

        public String getNo_of_policies() {
            return no_of_policies;
        }

        public void setNo_of_policies(String no_of_policies) {
            this.no_of_policies = no_of_policies;
        }

        public String getNo_of_diagnosis() {
            return no_of_diagnosis;
        }

        public void setNo_of_diagnosis(String no_of_diagnosis) {
            this.no_of_diagnosis = no_of_diagnosis;
        }

        public String getContactid() {
            return contactid;
        }

        public void setContactid(String contactid) {
            this.contactid = contactid;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(firstname);
            parcel.writeString(lastname);
            parcel.writeString(imagename);
            parcel.writeString(email);
            parcel.writeString(mobile);
            parcel.writeString(channelTitle);
            parcel.writeString(country);
            parcel.writeString(countrycode);
            parcel.writeString(address);
            parcel.writeString(city);
            parcel.writeString(state);
            parcel.writeString(no_of_devices);
            parcel.writeString(no_of_policies);
            parcel.writeString(no_of_diagnosis);
            parcel.writeString(contactid);
            parcel.writeString(profile_picture);
        }
    }
}
