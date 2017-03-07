package model;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

/**
 * Created by gohv on 02.03.17.
 */

public class Ad extends RealmObject implements Parcelable {
    private String productName;
    private String productPrice;
    private String productLocation;
    private String productDescription;
    private String productPhoto;
    private String contactNumber;
    private String category;
    private String subCategory;

    public Ad() {
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }



    protected Ad(Parcel in) {
        productName = in.readString();
        productPrice = in.readString();
        productLocation = in.readString();
        productDescription = in.readString();
        productPhoto = in.readString();
        contactNumber = in.readString();
        category = in.readString();
        subCategory = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productPrice);
        dest.writeString(productLocation);
        dest.writeString(productDescription);
        dest.writeString(productPhoto);
        dest.writeString(contactNumber);
        dest.writeString(category);
        dest.writeString(subCategory);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Ad> CREATOR = new Parcelable.Creator<Ad>() {
        @Override
        public Ad createFromParcel(Parcel in) {
            return new Ad(in);
        }

        @Override
        public Ad[] newArray(int size) {
            return new Ad[size];
        }
    };

    @Override
    public String toString() {
        return "Ad{" +
                "productName='" + productName + '\'' +
                ", productPrice='" + productPrice + '\'' +
                ", productLocation='" + productLocation + '\'' +
                ", productDescription='" + productDescription + '\'' +
                ", productPhoto='" + productPhoto + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", category='" + category + '\'' +
                ", subCategory='" + subCategory + '\'' +
                '}';
    }
}