package com.dtpicker;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

public class DateTimePicker {

    public enum Type {
        START_DATE_TIME,
        END_DATE_TIME
    }

    public interface OnDateTimeSetListener {
        void onDateTimeSet(Calendar calendar, Type type);
    }

    private Context mContext;
    private OnDateTimeSetListener mOnDateTimeSetListener;
    private Calendar mSelectedDate;
    private Type mType;

    public void getDateTime(Context context, OnDateTimeSetListener onDateTimeSetListener, Calendar selectedDate, Type type) {
        mContext = context;
        mOnDateTimeSetListener = onDateTimeSetListener;
        mSelectedDate = selectedDate;
        mType = type;
        showDatePickerDialog(mSelectedDate);
    }

    private void showDatePickerDialog(Calendar date) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, dateSetListener, date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMinDate(date.getTimeInMillis() - 1000);
        datePickerDialog.show();
    }

    private void showTimePickerDialog(Calendar date) {
        TimePickerDialog timePickerDialog;
        if (DateUtils.isToday(date.getTime().getTime())) {
            timePickerDialog = new TimePickerDialog(mContext, timeSetListener, date.get(Calendar.HOUR_OF_DAY), date.get(Calendar.MINUTE), true);
        } else {
            timePickerDialog = new TimePickerDialog(mContext, timeSetListener, 0, 0, true);
        }

        timePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mSelectedDate.set(Calendar.YEAR, year);
            mSelectedDate.set(Calendar.MONTH, monthOfYear);
            mSelectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            showTimePickerDialog(mSelectedDate);
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int minute) {
            mSelectedDate.set(Calendar.HOUR_OF_DAY, hour);
            mSelectedDate.set(Calendar.MINUTE, minute);
            mOnDateTimeSetListener.onDateTimeSet(mSelectedDate, mType);
        }
    };
}
