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
    private DateTimePicker dateTimePicker;

    // Final Result of user selection
    private Date selectedStartDate;
    private Date selectedEndDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        initValues();
        resetValues();
    }

    private void initValues() {
        timeZone = TimeZone.getTimeZone("UTC");
        simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz", Locale.US);
        simpleDateFormat.setTimeZone(timeZone);
        dateTimePicker = new DateTimePicker();
    }

    private void resetValues() {
        startDate = Calendar.getInstance(timeZone);
        endDate = Calendar.getInstance(timeZone);
        mTvStartDateTime.setText("");
        mTvEndDateTime.setText("");
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.b_start_date_time)
    public void ctaSelectStartDateTime(View view) {
        resetValues();
        dateTimePicker.getDateTime(this, this, startDate, DateTimePicker.Type.START_DATE_TIME);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.b_end_date_time)
    public void ctaSelectEndDateTime(View view) {
        dateTimePicker.getDateTime(this, this, endDate, DateTimePicker.Type.END_DATE_TIME);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.b_validate_dates)
    public void ctaValidateDates(View view) {
        if (isValidDate(selectedStartDate, selectedEndDate)) {
            Toast.makeText(getApplicationContext(), "Valid Date Range!", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Invalid Date Range Selection!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDateTimeSet(Calendar calendar, DateTimePicker.Type type) {
        if (type.equals(DateTimePicker.Type.START_DATE_TIME)) {
            startDate = calendar;
            endDate = calendar;
            selectedStartDate = startDate.getTime();
            mTvStartDateTime.setText(simpleDateFormat.format(selectedStartDate) + " " + timeZone(selectedStartDate));
        } else if (type.equals(DateTimePicker.Type.END_DATE_TIME)) {
            endDate = calendar;
            selectedEndDate = endDate.getTime();
            mTvEndDateTime.setText(simpleDateFormat.format(selectedEndDate) + " " + timeZone(selectedEndDate));
        }
    }

    private boolean isValidDate(Date selectedStartDate, Date selectedEndDate) {
        return selectedStartDate != null && selectedEndDate != null && selectedEndDate.after(selectedStartDate);
    }

    private String timeZone(Date date) {
        long millis = timeZone.getRawOffset() + (timeZone.inDaylightTime(date) ? timeZone.getDSTSavings() : 0);

        return String.format("%s%s:%s", millis < 0 ? "-" : "+",
                String.format("%02d", Math.abs(TimeUnit.MILLISECONDS.toHours(millis))),
                String.format("%02d", Math.abs(TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis))))
        );
    }
}