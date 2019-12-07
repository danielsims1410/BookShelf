package com.example.bookshelf;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import androidx.recyclerview.widget.RecyclerView;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {
    private Context context;
    private ArrayList<Book> books;
    private OnItemListener onItemListener;

    public BookAdapter(Context context, ArrayList<Book> books, OnItemListener onItemListener) {
        this.context = context;
        this.books = books;
        this.onItemListener = onItemListener;
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_card, parent, false);
        return new BookHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        Book book = books.get(position);
        holder.setDetails(book, position);
    }

    @Override
    public int getItemCount() { return books.size(); }

    public class BookHolder extends RecyclerView.ViewHolder implements  View.OnClickListener {
        TextView txtTitle, txtPageCount, txtDateRead;
        ImageView imgReview;
        OnItemListener onItemListener;

        public BookHolder(View itemView, OnItemListener onItemListener) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtBookTitle);
            txtPageCount = itemView.findViewById(R.id.txtBookPageCount);
            txtDateRead = itemView.findViewById(R.id.txtBookDateRead);
            imgReview = itemView.findViewById(R.id.imgStars);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        public void setDetails(Book book, int position) {
            Bitmap reviewImage;
            int[] starImages = { R.drawable.review_0, R.drawable.review_1, R.drawable.review_2,
                                R.drawable.review_3, R.drawable.review_4, R.drawable.review_5 };
            int selectedStarImage;
            txtTitle.setText(book.getBookTitle());
            txtPageCount.setText("Page Count: " + book.getPageCount());
            txtDateRead.setText("Date Read: " + book.getDateRead());
            selectedStarImage = starImages[book.getReviewScore()];
            reviewImage = BitmapFactory.decodeResource(context.getResources(), selectedStarImage);
            imgReview.setImageBitmap(reviewImage);
            }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            Book book = books.get(position);
            Toast toast = Toast.makeText(context, book.getBookTitle() + " Pressed!", Toast.LENGTH_LONG);
            toast.show();
            onItemListener.onItemClick(getAdapterPosition());
            }
        }

        public interface OnItemListener {
            void onItemClick(int position);
        }
}
