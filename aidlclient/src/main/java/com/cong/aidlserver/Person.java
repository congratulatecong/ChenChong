package com.cong.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chenchong-ms on 2016/3/14.
 */
public class Person implements Parcelable {
    private String mName;
    private int mAge;

    public Person(String mName, int mAge) {
        this.mName = mName;
        this.mAge = mAge;
    }

    @Override
    public String toString() {
        return "Person{" +
                "Name='" + mName + '\'' +
                ", Age=" + mAge +
                '}';
    }

    public Person(Parcel source) {
        mName = source.readString();
        mAge = source.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mName);
        dest.writeInt(mAge);
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel source) {
            return new Person(source);
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };
}
