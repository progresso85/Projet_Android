package com.example.flashcard.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public final class Question implements Parcelable {

    private String title;
    private String category;
    private String image;
    private List<String> answers;
    private String right_answer;
    private int difficulty;

    public Question(String title, String category, String image, List<String> answers, String right_answer, int difficulty) {
        this.title = title;
        this.category = category;
        this.image = image;
        this.answers = answers;
        this.right_answer = right_answer;
        this.difficulty = difficulty;
    }

    protected Question(Parcel in) {
        title = in.readString();
        category = in.readString();
        image = in.readString();
        answers = in.createStringArrayList();
        right_answer = in.readString();
        difficulty = in.readInt();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImageUrl() {
        return image;
    }

    public void setImageUrl(String imageUrl) {
        this.image = imageUrl;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(category);
        parcel.writeString(image);
        parcel.writeStringList(answers);
        parcel.writeString(right_answer);
        parcel.writeInt(difficulty);
    }

    @Override
    public String toString() {
        return "Question{" +
                "title='" + title + '\'' +
                ", category='" + category + '\'' +
                ", image='" + image + '\'' +
                ", answers=" + answers +
                ", right_answer='" + right_answer + '\'' +
                ", difficulty='" + difficulty + '\'' +
                '}';
    }
}
