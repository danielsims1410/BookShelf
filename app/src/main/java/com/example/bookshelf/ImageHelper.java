package com.example.bookshelf;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

public class ImageHelper
{

    public byte[] getBytes(Bitmap bitmap) //Bitmap->Byte Array Conversion
    {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, stream);
        return stream.toByteArray();
    }

    public Bitmap getImage(byte[] image)  //Byte Array->Bitmap Conversion
    {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

}
