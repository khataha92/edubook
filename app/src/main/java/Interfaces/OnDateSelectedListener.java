package Interfaces;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

/**
 * Created by mac on 6/24/16.
 */
public abstract class OnDateSelectedListener implements DatePickerDialog.OnDateSetListener {

    String selectedDate = "";

    @Override
    public void onDateSet(DatePicker view1, int selectedYear, int selectedMonth, int selectedDay) {

        String year = String.valueOf(selectedYear);

        String month = String.valueOf(selectedMonth + 1);

        String day = String.valueOf(selectedDay);

        selectedDate = year+"-"+month+"-"+day;

        onDateSelected(selectedDate);

    }

    public abstract void onDateSelected(String selectedDate);
}
