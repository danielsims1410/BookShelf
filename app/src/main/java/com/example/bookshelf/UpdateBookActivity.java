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
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;

public class UpdateBookActivity extends AppCompatActivity {
    private static final String TAG = "Update Book Activity";
    private static final int MAX_REVIEW_SCORE = 5;
    private DatabaseHelper databaseHelper;
    private Book book;
    private ArrayList<EditText> editTextArrayList;
    private EditText etUpdateTitle, etUpdateDate, etUpdateReviewScore, etUpdatePageCount;
    private Button btnChangeDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_book);

        if (getIntent().hasExtra("book")) {
            book = getIntent().getParcelableExtra("book");
            Log.d(TAG, "Book Passed: " + book.getBookTitle());
        }

        databaseHelper = new DatabaseHelper(this);
        TextView txtTitle = findViewById(R.id.txtUpdateBook);
        txtTitle.setText("Update " + book.getBookTitle());
        editTextArrayList = new ArrayList<>();
        editTextArrayList.add(this.etUpdateTitle = findViewById(R.id.etUpdateBookTitle));
        editTextArrayList.add(this.etUpdateDate = findViewById(R.id.etUpdateBookDateRead));
        editTextArrayList.add(this.etUpdateReviewScore = findViewById(R.id.etUpdateReviewScore));
        editTextArrayList.add(this.etUpdatePageCount = findViewById(R.id.etUpdatePageCount));
        btnChangeDate = findViewById(R.id.btnUpdateDateRead);
        setEditTexts();
    }

    public void setEditTexts() {
        etUpdateTitle.setText(book.getBookTitle());
        etUpdateDate.setText(book.getDateRead());
        etUpdateReviewScore.setText(String.valueOf(book.getReviewScore()));
        etUpdatePageCount.setText(String.valueOf(book.getPageCount()));
    }

    private boolean allUpdateFieldsValid() {
        boolean allValid = true;
        for(EditText editText : editTextArrayList) {
            if(TextUtils.isEmpty(editText.getText())) {
                if(editText == etUpdateDate) btnChangeDate.setError("Date Required!");
                else editText.setError("Field Required!");
                allValid = false;
            }
            else if (editText == etUpdateReviewScore && Integer.parseInt(etUpdateReviewScore.getText().toString()) > MAX_REVIEW_SCORE) {
                editText.setError("Must be 5 or Under!");
                allValid = false;
            }
        }
        return allValid;
    }

    public void onClickButtonHandlerUpdateBook(View v) {
        Intent intent = new Intent(this, MainActivity.class);
        switch(v.getId()) {
            case(R.id.btnUpdateBook):
                if(allUpdateFieldsValid()) {
                    databaseHelper.UpdateBook(book.getBookID(),
                            etUpdateTitle.getText().toString(),
                            book.getAuthorID(),
                            etUpdateDate.getText().toString(),
                            Integer.parseInt(etUpdateReviewScore.getText().toString()),
                            Integer.parseInt(etUpdatePageCount.getText().toString()));
                    Log.d(TAG, "Book ID Passed: " + book.getBookID());
                    Log.d(TAG, "Author ID Passed: " + book.getAuthorID());
                    Log.d(TAG, "Title Passed: " + book.getBookTitle());
                    startActivity(intent);
                    break;
                }

            case(R.id.btnDeleteBook):
                databaseHelper.deleteEntry(book.getBookID(), "book");
                startActivity(intent);
                break;

            case(R.id.btnUpdateDateRead):
                DialogFragment newFragment = new DatePickerHelper("update");
                newFragment.show(getSupportFragmentManager(), "datePicker");
                break;
        }
    }

}
