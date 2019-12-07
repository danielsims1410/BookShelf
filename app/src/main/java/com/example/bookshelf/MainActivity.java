package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AuthorAdapter.onItemListener {

    private RecyclerView recyclerView;
    private AuthorAdapter authorAdapter;
    private ArrayList<Author> authorArrayList;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        databaseHelper = new DatabaseHelper(this);
        authorArrayList = databaseHelper.getAuthors();
        authorAdapter = new AuthorAdapter(this, authorArrayList, this);
        recyclerView.setAdapter(authorAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.addOnItemTouchListener(new RecyclerView.SimpleOnItemTouchListener());
    }

    public void onAddAuthorClick(View v) {
        Intent intent = new Intent(this, AddAuthorActivity.class);
        startActivity(intent);
    }

    @Override
    public void onItemClick(int position) {
        Intent intent = new Intent(this, UpdateAuthorActivity.class);
        intent.putExtra("author", authorArrayList.get(position));
        startActivity(intent);
    }
}
