package com.example.flashcard.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Character implements Parcelable {

    private String name;
    private String image;
    private String anime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAnime() {
        return anime;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    protected Character(Parcel in) {
        name = in.readString();
        image = in.readString();
        anime = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(anime);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        @Override
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        @Override
        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

}
