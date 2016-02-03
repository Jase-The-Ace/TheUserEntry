package com.jessicardo.theuserentry.api.models;

import android.os.Parcel;
import android.os.Parcelable;

public class DriversLicense implements Parcelable {

    private String id;

    private String first_name;

    private String last_name;

    private String license_id;

    private String expiration;

    private String state;

    private String street;

    private String city;

    private String zipcode;

    // not sent to server
    private String dob;

    public DriversLicense() {
    }

    public void updateLicense(DriversLicense drivers_license) {
        if (drivers_license.id != null) {
            this.id = drivers_license.id;
        }
        this.first_name = drivers_license.first_name;
        this.last_name = drivers_license.last_name;
        this.license_id = drivers_license.license_id;
        this.expiration = drivers_license.expiration;
        this.state = drivers_license.state;
        this.street = drivers_license.street;
        this.city = drivers_license.city;
        this.zipcode = drivers_license.zipcode;
        this.dob = drivers_license.dob;
    }

    public boolean isEmpty() {
        return id == null
                && first_name == null
                && last_name == null
                && license_id == null
                && expiration == null
                && state == null
                && street == null
                && city == null
                && zipcode == null;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getLicenseId() {
        return license_id;
    }

    public String getExpiration() {
        return expiration;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setLicenseId(String license_id) {
        this.license_id = license_id;
    }

    public void setExpiration(String expiration) {
        this.expiration = expiration;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DriversLicense)) {
            return false;
        }

        DriversLicense that = (DriversLicense) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (first_name != null ? !first_name.equals(that.first_name) : that.first_name != null) {
            return false;
        }
        if (last_name != null ? !last_name.equals(that.last_name) : that.last_name != null) {
            return false;
        }
        if (license_id != null ? !license_id.equals(that.license_id) : that.license_id != null) {
            return false;
        }
        if (expiration != null ? !expiration.equals(that.expiration) : that.expiration != null) {
            return false;
        }
        if (state != null ? !state.equals(that.state) : that.state != null) {
            return false;
        }
        if (street != null ? !street.equals(that.street) : that.street != null) {
            return false;
        }
        if (city != null ? !city.equals(that.city) : that.city != null) {
            return false;
        }
        return !(zipcode != null ? !zipcode.equals(that.zipcode) : that.zipcode != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (first_name != null ? first_name.hashCode() : 0);
        result = 31 * result + (last_name != null ? last_name.hashCode() : 0);
        result = 31 * result + (license_id != null ? license_id.hashCode() : 0);
        result = 31 * result + (expiration != null ? expiration.hashCode() : 0);
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (zipcode != null ? zipcode.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DriversLicense{" +
                "id='" + id + '\'' +
                ", first_name='" + first_name + '\'' +
                ", last_name='" + last_name + '\'' +
                ", license_id='" + license_id + '\'' +
                ", expiration='" + expiration + '\'' +
                ", state='" + state + '\'' +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", dob='" + dob + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.first_name);
        dest.writeString(this.last_name);
        dest.writeString(this.license_id);
        dest.writeString(this.expiration);
        dest.writeString(this.state);
        dest.writeString(this.street);
        dest.writeString(this.city);
        dest.writeString(this.zipcode);
        dest.writeString(this.dob);
    }

    protected DriversLicense(Parcel in) {
        this.id = in.readString();
        this.first_name = in.readString();
        this.last_name = in.readString();
        this.license_id = in.readString();
        this.expiration = in.readString();
        this.state = in.readString();
        this.street = in.readString();
        this.city = in.readString();
        this.zipcode = in.readString();
        this.dob = in.readString();
    }

    public static final Creator<DriversLicense> CREATOR = new Creator<DriversLicense>() {
        public DriversLicense createFromParcel(Parcel source) {
            return new DriversLicense(source);
        }

        public DriversLicense[] newArray(int size) {
            return new DriversLicense[size];
        }
    };

}
