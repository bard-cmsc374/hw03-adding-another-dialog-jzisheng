package com.example.jason.criminalintent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static android.support.v7.appcompat.R.id.time;

/**
 * Created by Jason on 10/15/2016.
 */

public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE =
            "com.example.jason.criminalintent.date";

    private static final String ARG_DATE = "date";
    private DatePicker mDatePicker;
    private TimePicker mTimePicker;

    public static DatePickerFragment newInstance(Date date){
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE,date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Date date = (Date) getArguments().getSerializable(ARG_DATE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int AM_PM = calendar.get(Calendar.AM_PM);

        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_date, null);

        final DatePicker mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_picker);
        mDatePicker.init(year,month,day,null);

        final TimePicker mTimePicker = (TimePicker) v.findViewById(R.id.timePicker);
        mTimePicker.setHour(hour);
        mTimePicker.setMinute(minute);
        mTimePicker.setIs24HourView(Boolean.TRUE);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                int year = mDatePicker.getYear();
                                int month = mDatePicker.getMonth();
                                int day = mDatePicker.getDayOfMonth();

                                int hour = mTimePicker.getHour();
                                int minute = mTimePicker.getMinute();

                                Date date = new GregorianCalendar(year,month,day,hour,minute).getTime();
                                sendResult(Activity.RESULT_OK,date);
                            }
                        })
                .create();

    }

    private void sendResult(int resultCode, Date date){
        if(getTargetFragment() == null){
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE,date);

        getTargetFragment()
                .onActivityResult(getTargetRequestCode(),resultCode,intent);
    }

}
