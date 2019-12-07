package com.example.bookshelf;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class AuthorAdapter extends RecyclerView.Adapter<AuthorAdapter.AuthorHolder> {
    private Context context;
    private ArrayList<Author> authors;
    private onItemListener onItemListener;

    public AuthorAdapter(Context context, ArrayList<Author> authors, onItemListener onItemListener) {
        this.context = context;
        this.authors = authors;
        this.onItemListener = onItemListener;
    }

    @Override
    public AuthorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.author_card, parent, false);
        return new AuthorHolder(view, onItemListener);
    }

    @Override
    public void onBindViewHolder(AuthorHolder holder, int position) {
        Author author = authors.get(position);
        holder.setDetails(author, position);
    }

    @Override
    public int getItemCount() {
        return authors.size();
    }

    public class AuthorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txtName, txtGenre, txtNotableWorks, txtAge;
        ImageView imgProfile;
        ImageHelper imageHelper = new ImageHelper();
        onItemListener onItemListener;

        public AuthorHolder(View itemView, onItemListener onItemListener) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtGenre = itemView.findViewById(R.id.txtGenre);
            txtNotableWorks = itemView.findViewById(R.id.txtNotableWork);
            txtAge = itemView.findViewById(R.id.txtAge);
            imgProfile = itemView.findViewById(R.id.imgProfile);
            this.onItemListener = onItemListener;
            itemView.setOnClickListener(this);
        }

        public void setDetails(Author author, int position) {
            txtName.setText(author.getAuthorName());
            txtGenre.setText("Genre: " + author.getAuthorGenre());
            txtNotableWorks.setText("Known for: " + author.getAuthorNotableWorks());
            txtAge.setText("Age: " + author.getAuthorAge());
            if(author.getImage() != null) imgProfile.setImageBitmap(imageHelper.getImage(author.getImage()));
        }

        @Override
        public void onClick(View view) {
            int position = getLayoutPosition();
            Author author = authors.get(position);
            Toast toast = Toast.makeText(context, author.getAuthorName() + " Pressed!", Toast.LENGTH_LONG);
            toast.show();
            onItemListener.onItemClick(getAdapterPosition());
        }

    }

    public interface onItemListener {
        void onItemClick(int position);
    }
}
