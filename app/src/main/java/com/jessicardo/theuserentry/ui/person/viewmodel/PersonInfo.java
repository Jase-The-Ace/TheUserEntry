package com.jessicardo.theuserentry.ui.person.viewmodel;

import android.os.Parcel;
import android.os.Parcelable;

public class PersonInfo implements Parcelable {

    private String mLine1;
    private String mLine2;
    private Long mId;

    public String getLine1() {
        return mLine1;
    }

    public void setLine1(String line1) {
        mLine1 = line1;
    }

    public String getLine2() {
        return mLine2;
    }

    public void setLine2(String line2) {
        mLine2 = line2;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonInfo)) {
            return false;
        }

        PersonInfo that = (PersonInfo) o;

        if (mLine1 != null ? !mLine1.equals(that.mLine1) : that.mLine1 != null) {
            return false;
        }
        if (mLine2 != null ? !mLine2.equals(that.mLine2) : that.mLine2 != null) {
            return false;
        }
        return !(mId != null ? !mId.equals(that.mId) : that.mId != null);

    }

    @Override
    public int hashCode() {
        int result = mLine1 != null ? mLine1.hashCode() : 0;
        result = 31 * result + (mLine2 != null ? mLine2.hashCode() : 0);
        result = 31 * result + (mId != null ? mId.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PersonInfo{" +
                "mLine1='" + mLine1 + '\'' +
                ", mLine2='" + mLine2 + '\'' +
                ", mId='" + mId + '\'' +
                '}';
    }

    public PersonInfo(String line1, String line2, Long id) {
        mLine1 = line1;
        mLine2 = line2;
        mId = id;
    }

    public PersonInfo() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLine1);
        dest.writeString(this.mLine2);
        dest.writeValue(this.mId);
    }

    protected PersonInfo(Parcel in) {
        this.mLine1 = in.readString();
        this.mLine2 = in.readString();
        this.mId = (Long) in.readValue(Long.class.getClassLoader());
    }

    public static final Creator<PersonInfo> CREATOR = new Creator<PersonInfo>() {
        public PersonInfo createFromParcel(Parcel source) {
            return new PersonInfo(source);
        }

        public PersonInfo[] newArray(int size) {
            return new PersonInfo[size];
        }
    };
}
