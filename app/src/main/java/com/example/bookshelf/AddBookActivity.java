package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class AddBookActivity extends AppCompatActivity {
    private static final String TAG = "Add Book Activity";
    private final int MAX_REVIEW_SCORE = 5;
    private int authorID;
    private ArrayList<EditText> editTextArrayList;
    private EditText etTitle, etDateRead, etReviewScore, etPageCount;
    private Button btnChooseDateRead;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        if (getIntent().hasExtra("authorID")) {
            authorID = getIntent().getIntExtra("authorID", -1);
            Log.d(TAG, "Author ID Passed: " + authorID);
        }

        databaseHelper = new DatabaseHelper(this);
        editTextArrayList = new ArrayList<>();
        editTextArrayList.add(etTitle = findViewById(R.id.etEnterBookTitle));
        editTextArrayList.add(etDateRead = findViewById(R.id.etEnterBookDateRead));
        editTextArrayList.add(etReviewScore = findViewById(R.id.etEnterReviewScore));
        editTextArrayList.add(etPageCount = findViewById(R.id.etEnterPageCount));
        btnChooseDateRead = findViewById(R.id.btnChooseDateRead);
    }

    public void onClickBtnChooseDateRead(View v) {
        DialogFragment newFragment = new DatePickerHelper("add");
        newFragment.show(getSupportFragmentManager(), "datePicker");
        etDateRead.setVisibility(View.VISIBLE);
        btnChooseDateRead.setText("Change Date");
    }

    public void onInsertBookClick(View v) {
        if (allBookFieldsValid()) {
            databaseHelper.addBook(
                    etTitle.getText().toString(),
                    authorID,
                    etDateRead.getText().toString(),
                    Integer.parseInt(etReviewScore.getText().toString()),
                    Integer.parseInt(etPageCount.getText().toString()));
            Toast toast = Toast.makeText(this, "Book Added!", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean allBookFieldsValid() {
        boolean allValid = true;
        for(EditText editText : editTextArrayList) {
            if(TextUtils.isEmpty(editText.getText())) {
                if(editText == etDateRead) btnChooseDateRead.setError("Date Required!");
                else editText.setError("Field Required!");
                allValid = false;
            }
            else if (editText == etReviewScore && Integer.parseInt(etReviewScore.getText().toString()) > MAX_REVIEW_SCORE) {
                editText.setError("Must be 5 or Under!");
                allValid = false;
            }
        }
        return allValid;
    }
}
