package com.kevinbernage.kbcalendar.app;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;

import org.lucasr.twowayview.TwoWayView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Kevin on 24/02/2014.
 */
public class KBCalendar extends View {

    private TwoWayView mListView;
    private KBCalendarAdapter mCalendarAdapter;
    private ArrayList<Date> mListDays;

    //Start & End Dates
    private SimpleDateFormat dateFormat;
    private Date dateStartCalendar;
    private Date dateEndCalendar;

    private IAgendaDateSelect iAgendaDateSelect;

    //Number of Row Show on Screen
    private int numberOfRowOnScreen;
    //Position in arraylist of the center item
    public static int positionOfCenterItem;

    public KBCalendar(Context context, IAgendaDateSelect iAgendaDateSelect) {
        super(context);
        numberOfRowOnScreen = 5;
        dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dateStartCalendar = dateFormat.parse("26/06/2013");
            dateEndCalendar = dateFormat.parse("30/06/2014");

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        mListDays = new ArrayList<Date>();

        this.iAgendaDateSelect = iAgendaDateSelect;

    }



    /************************************************/
    /************************************************/
    /***************  PUBLIC METHODS ****************/
    /************************************************/
    /************************************************/

    /* Init KBCalendar View ; This method have to be call imperatively */
    public void loadKBCalendar(){
        View rootView = ((Activity)getContext()).getWindow().getDecorView().findViewById(android.R.id.content);

        mListView = (TwoWayView) rootView.findViewById(R.id.list);
        mListView.setHorizontalScrollBarEnabled(false);

        GregorianCalendar calendar = new java.util.GregorianCalendar();
        for (Date date = dateStartCalendar; !date.equals(dateEndCalendar);) {
            calendar.setTime(date);
            calendar.add(Calendar.DATE, 1);
            date = calendar.getTime();
            mListDays.add(date);
        }

        mCalendarAdapter = new KBCalendarAdapter(getContext(), mListDays, numberOfRowOnScreen);
        mListView.setAdapter(mCalendarAdapter);
        mListView.setOnScrollListener(new TwoWayView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(TwoWayView view, int scrollState) {
                if(scrollState == 0){
                    int position = getPositionOfCenterItem();
                    iAgendaDateSelect.onDateSelect(mListDays.get(position));
                    centerToPosition(position);
                }
            }

            @Override
            public void onScroll(TwoWayView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                int shiftCells = numberOfRowOnScreen / 2;
                positionOfCenterItem = firstVisibleItem + shiftCells;
                mCalendarAdapter.notifyDataSetChanged();
            }
        });
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View child, int position,
                                    long id) {
                iAgendaDateSelect.onDateSelect(mListDays.get(position));
                centerToPosition(position);
            }
        });
        new InitTask().execute();
    }


    /* Center the listView to today */
    public void goToday(){ centerToDate(new Date());}

    /* Center the listView to the date*/
    public void centerToDate(Date dateCenter){
        centerToPosition(positionOfDate(dateCenter));
    }

    /* Set the start date of the calendar */
    public void setDateStartCalendar(Date dateStartCalendar) {
        this.dateStartCalendar = dateStartCalendar;
    }
    /* Set the date end of the calendar */
    public void setDateEndCalendar(Date dateEndCalendar) {
        this.dateEndCalendar = dateEndCalendar;
    }
    /* Set the number of row that appears on screen */
    public void setNumberOfRowOnScreen(int numberOfRowOnScreen) {
        this.numberOfRowOnScreen = numberOfRowOnScreen;
    }





    /************************************************/
    /************************************************/
    /**************  PRIVATE METHODS ****************/
    /************************************************/
    /************************************************/


    /* Return position of date in listView ; Return -1 if date not exist */
    private int positionOfDate(Date date){
        int position = -1;
        for(int i=0;i<mListDays.size();i++){if(isDatesDaysEquals(date, mListDays.get(i))){ position = i; }}
        return position;
    }
    /* Return true if dates are equals */
    private boolean isDatesDaysEquals(Date date1, Date date2){
        if(dateFormat.format(date1).equals(dateFormat.format(date2))) return true; else return false;
    }
    /* Center KBAdapter to an item with its position */
    private void centerToPosition(int position){
        CenterToPositionTask centerTask = new CenterToPositionTask();
        centerTask.position = position;
        centerTask.execute();
    }
    /* Return position of selected date on center of screen*/
    private int getPositionOfCenterItem(){
        return mListView.getFirstVisiblePosition() + numberOfRowOnScreen / 2;
    }

    private class CenterToPositionTask extends AsyncTask<Void, Void, String[]> {
        int position;
        protected void onPreExecute() {

            if(position != -1){
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        int shiftCells = numberOfRowOnScreen / 2;
                        if(position - shiftCells > 0){
                            mListView.setSelection(position - shiftCells);
                        }else{
                            mListView.setSelection(position);
                        }
                    }
                });
            }
        }

        @Override
        protected String[] doInBackground(Void... params) {
            try {Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);

            //Refresh the adapter to draw background cells color
            int shiftCells = numberOfRowOnScreen / 2;
            positionOfCenterItem = position + shiftCells;
            mCalendarAdapter.notifyDataSetChanged();
            mListView.requestLayout();
        }
    }

    private class InitTask extends AsyncTask<Void, Void, String[]> {
        protected void onPreExecute() {
        }

        @Override
        protected String[] doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);

            goToday();
        }
    }


    public interface IAgendaDateSelect {

        public void onDateSelect(Date date);

    }
}
