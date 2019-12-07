package com.example.bookshelf;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {
    private String bookTitle, dateRead;
    private int bookID, authorID, reviewScore, pageCount;

    public Book(String title, int authorID, String dateRead, int review, int pageCount) {
        this.bookTitle = title;
        this.authorID = authorID;
        this.dateRead = dateRead;
        this.reviewScore = review;
        this.pageCount = pageCount;
    }

    public Book() {}

    protected Book(Parcel in) {
        bookID = in.readInt();
        bookTitle = in.readString();
        authorID = in.readInt();
        dateRead = in.readString();
        reviewScore = in.readInt();
        pageCount = in.readInt();
    }

    //Necessary for Parcelable
    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) { return new Book(in); }

        @Override
        public Book[] newArray(int size) { return new Book[size]; }
    };

    public String getBookTitle() { return bookTitle; }
    public String getDateRead() { return dateRead; }
    public int getBookID() { return bookID; }
    public int getAuthorID() { return authorID; }
    public int getReviewScore() { return reviewScore; }
    public int getPageCount() { return pageCount; }

    public void setBookTitle(String title) { this.bookTitle = title; }
    public void setDateRead(String dateread) { this.dateRead = dateread; }
    public void setBookID(int bookid) {this.bookID = bookid; }
    public void setAuthorID(int authorid) { this.authorID = authorid; }
    public void setReviewScore(int reviewScore) { this.reviewScore = reviewScore; }
    public void setPageCount(int pagecount) {this.pageCount = pagecount; }

    @Override
    public int describeContents() { return 0; }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookID);
        dest.writeString(bookTitle);
        dest.writeInt(authorID);
        dest.writeString(dateRead);
        dest.writeInt(reviewScore);
        dest.writeInt(pageCount);
    }
}
