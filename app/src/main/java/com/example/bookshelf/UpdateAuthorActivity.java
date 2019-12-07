package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class UpdateAuthorActivity extends AppCompatActivity implements BookAdapter.OnItemListener {

    private static final String TAG = "Update Author Activity";
    private DatabaseHelper databaseHelper;
    private ImageHelper imageHelper;
    private Author author;
    private EditText etUpdateName, etUpdateAge, etUpdateGenre, etUpdateKnownFor;
    private TextView txtBookListMessage;
    private Button btnChangeImage;
    private RecyclerView recyclerView;
    private BookAdapter bookAdapter;
    private ArrayList<Book> bookArrayList;
    private int authorID;
    private byte[] image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_author);

        if (getIntent().hasExtra("author")) {
            author = getIntent().getParcelableExtra("author");
            Log.d(TAG, "Author Passed: " + author.getID());
        }
        this.authorID = author.getID();
        this.image = author.getImage();
        recyclerView = findViewById(R.id.recyclerViewBooks);
        databaseHelper = new DatabaseHelper(this);
        bookArrayList = databaseHelper.getBooks(author.getID());
        bookAdapter = new BookAdapter(this, bookArrayList, this);
        recyclerView.setAdapter(bookAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        imageHelper = new ImageHelper();
        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdateAge = findViewById(R.id.etUpdateAge);
        etUpdateGenre = findViewById(R.id.etUpdateGenre);
        etUpdateKnownFor = findViewById(R.id.etUpdateKnownFor);
        txtBookListMessage = findViewById(R.id.txtAvailableBooks);
        btnChangeImage = findViewById(R.id.btnChangeImage);
        if(bookArrayList.size() > 0) txtBookListMessage.setText("Books by this Author:");
        setEditTexts();
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, UpdateBookActivity.class);
        intent.putExtra("book", bookArrayList.get(position));
        startActivity(intent);
    }

    private void setEditTexts() {
        etUpdateName.setText(author.getAuthorName());
        etUpdateAge.setText(author.getAuthorAge());
        etUpdateGenre.setText(author.getAuthorGenre());
        etUpdateKnownFor.setText(author.getAuthorNotableWorks());
    }

    public void btnPressHandler(View v) {
        switch(v.getId()) {
            case(R.id.btnUpdateAuthor):
                databaseHelper.updateAuthor(authorID,
                        etUpdateName.getText().toString(),
                        etUpdateGenre.getText().toString(),
                        etUpdateKnownFor.getText().toString(),
                        Integer.parseInt(etUpdateAge.getText().toString()),
                        this.image);
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
            case(R.id.btnChangeImage):
                startActivityForResult(new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
                break;
            case(R.id.btnAddBook):
                Intent intentAdd = new Intent(this, AddBookActivity.class);
                intentAdd.putExtra("authorID", authorID);
                startActivity(intentAdd);
                break;
            case(R.id.btnDeleteAuthor):
                databaseHelper.deleteEntry(authorID, "author");
                Intent intentDelete = new Intent(this, MainActivity.class);
                intentDelete.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intentDelete);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri newImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), newImage);
                this.image = imageHelper.getBytes(bitmap);
                btnChangeImage.setText("Change Image Again");
            }

            catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(this, "File not Found :(", Toast.LENGTH_LONG);
                toast.show();
            }
            catch (IOException e) {
                e.printStackTrace();
                Toast toast = Toast.makeText(this, "Error Loading Image :(", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
}
