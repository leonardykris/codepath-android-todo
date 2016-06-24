package com.test.leo.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import java.util.Calendar;

/**
 * Created by leo on 24/06/16.
 */
public class DatePickerFragment extends DialogFragment {
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // Use the current date as the default date in the picker
    long due;
    int year;
    int month;
    int day;

    Bundle of_joy = this.getArguments();
    final Calendar c = Calendar.getInstance();

    if (of_joy != null) {
      due = of_joy.getLong("due");
      if (due > 0) {
        c.setTimeInMillis(due);
      }
    }

    year = c.get(Calendar.YEAR);
    month = c.get(Calendar.MONTH);
    day = c.get(Calendar.DAY_OF_MONTH);

    // Create a new instance of DatePickerDialog and return it
    return new DatePickerDialog(getActivity(), (MainActivity)getActivity(), year, month, day);
  }
}
