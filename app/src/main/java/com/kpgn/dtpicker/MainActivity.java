package com.kpgn.dtpicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DateTimePicker.OnDateTimeSetListener {

    @BindView(R.id.tv_start_date_time)
    TextView mTvStartDateTime;

    @BindView(R.id.tv_end_date_time)
    TextView mTvEndDateTime;

    private Calendar startDate;
    private Calendar endDate;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
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
            mTvStartDateTime.setText(startDate.getTime().toString());
        } else if (type.equals(DateTimePicker.Type.END_DATE_TIME)) {
            endDate = calendar;
            mTvEndDateTime.setText(endDate.getTime().toString());
        }
    }

    private boolean isValidDate(Calendar startDate, Calendar endDate) {
        return endDate.getTime().after(startDate.getTime());
    }
}