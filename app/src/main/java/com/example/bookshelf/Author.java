package com.example.bookshelf;

import android.os.Parcel;
import android.os.Parcelable;

public class Author implements Parcelable {
    private String authorName;
    private String authorGenre;
    private String authorNotableWorks;
    private String authorAge;
    private int authorID;
    private byte[] authorImage;

    public Author(String name, String genre, String notableWorks, String age, byte[] image) {
        authorName = name;
        authorGenre = genre;
        authorNotableWorks = notableWorks;
        authorAge = age;
        authorImage = image;
    }

    public Author() {

    }

    protected Author(Parcel in) {
        authorName = in.readString();
        authorGenre = in.readString();
        authorNotableWorks = in.readString();
        authorAge = in.readString();
        authorID = in.readInt();
        authorImage = new byte[in.readInt()];
        in.readByteArray(authorImage);
    }

    public static final Creator<Author> CREATOR = new Creator<Author>() {
        @Override
        public Author createFromParcel(Parcel in) {
            return new Author(in);
        }

        @Override
        public Author[] newArray(int size) {
            return new Author[size];
        }
    };

    public String getAuthorName() { return authorName; }

    public String getAuthorGenre() { return authorGenre; }

    public String getAuthorNotableWorks() { return authorNotableWorks; }

    public String getAuthorAge() { return authorAge; }

    public int getID() { return authorID; }

    public byte[] getImage() { return authorImage; }

    public void setAuthorName(String name) { authorName = name; }

    public void setAuthorGenre(String genre) { authorGenre = genre; }

    public void setAuthorNotableWorks(String notableWorks) { authorNotableWorks = notableWorks; }

    public void setAuthorAge(String age) { authorAge = age; }

    public void setID(int id) { authorID = id; }

    public void setImage(byte[] image) { authorImage = image; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(authorName);
        dest.writeString(authorGenre);
        dest.writeString(authorNotableWorks);
        dest.writeString(authorAge);
        dest.writeInt(authorID);
        dest.writeInt(authorImage.length);
        dest.writeByteArray(authorImage);
    }
}
