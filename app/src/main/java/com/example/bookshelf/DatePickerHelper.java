package com.example.bookshelf;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;

import androidx.fragment.app.DialogFragment;

public class DatePickerHelper extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private String action;

    public DatePickerHelper(String action) {
        this.action = action;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        final Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);
        return new DatePickerDialog(getActivity(), this, year, month , day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        month++; //Month index starts at 0
        String date = day + "/" + month + "/" + year;
        EditText editText = null;
        switch(action) {
            case ("add"):
                editText = getActivity().findViewById(R.id.etEnterBookDateRead);
                break;
            case("update"):
                editText = getActivity().findViewById(R.id.etUpdateBookDateRead);
                break;
        }

        editText.setText(date);
    }
}
