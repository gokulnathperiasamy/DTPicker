package com.kpgn.dtpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DateTimePicker.OnDateTimeSetListener {

    @BindView(R.id.tv_start_date_time)
    TextView mTvStartDateTime;

    @BindView(R.id.tv_end_date_time)
    TextView mTvEndDateTime;

    private TimeZone timeZone;
    private Calendar startDate;
    private Calendar endDate;
    private SimpleDateFormat simpleDateFormat;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        timeZone = TimeZone.getTimeZone("UTC");
        startDate = Calendar.getInstance(timeZone);
        endDate = Calendar.getInstance(timeZone);
        simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.b_start_date_time)
    public void ctaSelectStartDateTime(View view) {
        DateTimePicker.getDateTime(this, this, startDate, DateTimePicker.Type.START_DATE_TIME);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.b_end_date_time)
    public void ctaSelectEndDateTime(View view) {
        DateTimePicker.getDateTime(this, this, startDate, DateTimePicker.Type.END_DATE_TIME);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.b_validate_dates)
    public void ctaValidateDates(View view) {
        if (isValidDate(startDate, endDate)) {
            Toast.makeText(getApplicationContext(), "Valid Date Range!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Date Range Selection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateTimeSet(Calendar calendar, DateTimePicker.Type type) {
        if (type.equals(DateTimePicker.Type.START_DATE_TIME)) {
            startDate = calendar;
            mTvStartDateTime.setText(simpleDateFormat.format(startDate.getTime()) + " " + timeZone(startDate.getTime()));
        } else if (type.equals(DateTimePicker.Type.END_DATE_TIME)) {
            endDate = calendar;
            mTvEndDateTime.setText(simpleDateFormat.format(endDate.getTime()) + " " + timeZone(endDate.getTime()));
        }
    }

    private boolean isValidDate(Calendar startDate, Calendar endDate) {
        return endDate.getTime().after(startDate.getTime());
    }

    private String timeZone(Date date) {
        long millis = timeZone.getRawOffset() + (timeZone.inDaylightTime(date) ? timeZone.getDSTSavings() : 0);

        return String.format("%s%s:%s",
                millis < 0 ? "-" : "+",
                String.format("%02d", Math.abs(TimeUnit.MILLISECONDS.toHours(millis))),
                String.format("%02d", Math.abs(TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))))
        );
    }
}