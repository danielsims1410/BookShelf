package com.example.bookshelf;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AddAuthorActivity extends AppCompatActivity {

    private EditText etName, etAge, etGenre, etKnownFor;
    private TextView txtImageMessage;
    private Button btnAddImage;
    private ImageView imgProfile;
    private byte[] imageByte;
    private Bitmap imageBitmap;
    private ImageHelper imageHelper;
    private DatabaseHelper databaseHelper;
    private boolean imageAdded = false;
    private ArrayList<EditText> EditTextArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author);
        imageHelper = new ImageHelper();
        databaseHelper = new DatabaseHelper(this);
        EditTextArrayList = new ArrayList<>();
        EditTextArrayList.add(etName = findViewById(R.id.etEnterName));
        EditTextArrayList.add(etAge = findViewById(R.id.etEnterAge));
        EditTextArrayList.add(etGenre = findViewById(R.id.etEnterGenre));
        EditTextArrayList.add(etKnownFor = findViewById(R.id.etEnterKnownFor));
        txtImageMessage = findViewById(R.id.txtImageMessage);
        btnAddImage = findViewById(R.id.btnAddImage);
        imgProfile = findViewById(R.id.imgProfile);
    }

    public void onInsertAuthorClick(View v) {
        if (allFieldsEntered()) {
            databaseHelper.addAuthor(
                    etName.getText().toString(),
                    etGenre.getText().toString(),
                    etKnownFor.getText().toString(),
                    Integer.parseInt(etAge.getText().toString()),
                    imageByte);
            Toast toast = Toast.makeText(this, "Author Added!", Toast.LENGTH_LONG);
            toast.show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast toast = Toast.makeText(this, "Missing Field(s)!", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    public void onAddImageClick(View v) {
        startActivityForResult(new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI), 1);
    }

    private boolean allFieldsEntered() {
        boolean allEntered = true;
        for(EditText editText : EditTextArrayList) {
            if(TextUtils.isEmpty(editText.getText())) {
                editText.setError("Field Required!");
                allEntered = false;
            }
        }

        if (!imageAdded) {
            allEntered = false;
            txtImageMessage.setText("Profile Image Required!");
            txtImageMessage.setVisibility(View.VISIBLE);
        }
        return allEntered;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageByte = imageHelper.getBytes(imageBitmap);
                imageAdded = true;
                btnAddImage.setText("Change Image");
                btnAddImage.setActivated(false);
                imgProfile.setImageBitmap(imageBitmap);
                imgProfile.setVisibility(View.VISIBLE);
                txtImageMessage.setVisibility(View.INVISIBLE);
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
