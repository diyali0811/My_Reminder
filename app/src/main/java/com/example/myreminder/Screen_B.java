package com.example.myreminder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class Screen_B extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    Calendar now = Calendar.getInstance();
    private int NotificationId=1;
    TimePickerDialog tpd;
    DatePickerDialog dpd;
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
now.set(Calendar.YEAR,year);
now.set(Calendar.MONTH,month);
now.set(Calendar.DAY_OF_MONTH,dayOfMonth);



    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
now.set(Calendar.HOUR_OF_DAY,hourOfDay);
now.set(Calendar.MINUTE,minute);



    }

    public class MainActivity extends AppCompatActivity {
        DatabaseHelper mydb;
        EditText title,details;
        TimePicker time;
        DatePicker date;
        Button save;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            mydb = new DatabaseHelper(this);
            title = (EditText) findViewById(R.id.title);
            details=(EditText) findViewById(R.id.details);
            time=(TimePicker)findViewById(R.id.time);
            date=(DatePicker)findViewById(R.id.date);
            save = (Button) findViewById(R.id.save);



            AddData();


        }

        public void AddData() {
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean isInserted = mydb.insertData(title.getText().toString(), details.getText().toString());
                    if (isInserted == true) {
                        Toast.makeText(Screen_B.this, "inserted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Screen_B.this, "not inserted", Toast.LENGTH_SHORT).show();
                    }
                    Intent intent=new Intent(Screen_B.this,AlarmReceiver.class);
                    intent.putExtra("NotificationId",NotificationId);
                    intent.putExtra("To Do:",title.getText().toString());
                    PendingIntent alarmintent=PendingIntent.getBroadcast(Screen_B.this,0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                    AlarmManager alarm=(AlarmManager) getSystemService(ALARM_SERVICE);
                    switch(v.getId()){
                        case R.id.save:
                            int hour=time.getCurrentHour();
                            int minute=time.getCurrentMinute();
                            int day=date.getDayOfMonth();
                            int month=date.getMonth();
                            int year=date.getYear();
                            Calendar startTime=Calendar.getInstance();
                            startTime.set(Calendar.HOUR_OF_DAY,hour);
                            startTime.set(Calendar.MINUTE,minute);
                            startTime.set(Calendar.SECOND,0);
                            startTime.set(Calendar.DAY_OF_MONTH,day);
                            startTime.set(Calendar.MONTH,month);
                            startTime.set(Calendar.YEAR,year);
                            long alarmStartTime=startTime.getTimeInMillis();
                            alarm.set(AlarmManager.RTC_WAKEUP,alarmStartTime,alarmintent);


                    }
                }
            });
        }}}
