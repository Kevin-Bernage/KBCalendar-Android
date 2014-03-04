package com.kevinbernage.kbcalendar.app;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateFormat;
import android.util.Log;

import java.util.Date;

public class MainActivity extends ActionBarActivity implements KBCalendar.IKBAgendaEvent {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        KBCalendar mKBCalendar = new KBCalendar(this, this);
        mKBCalendar.loadKBCalendar();
    }

    @Override
    public void onDateSelect(Date date) {
        Log.i("KBCalendar", "Date : " + DateFormat.format("dd/MM", date).toString());

    }

    @Override
    public void onListScroll(Date date){
        Log.i("KBCalendar", "Date : " + DateFormat.format("dd/MM", date).toString());

    }

}
