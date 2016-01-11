package com.raystone.ray.snappytransition;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.content.pm.ProviderInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Ray on 1/10/2016.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "extra_date";
    public DatePicker mDatePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date,null);
        mDatePicker = (DatePicker)view.findViewById(R.id.date_picker);
        return new AlertDialog.Builder(getActivity()).setView(view).setTitle("Choose a date").setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year, month,day).getTime();
                sendResult(Activity.RESULT_OK,date);
            }
        }).create();
    }

    private void sendResult(int resultCode, Date date)
    {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date);
        getTargetFragment().onActivityResult(getTargetRequestCode(),resultCode,intent);
    }
}
