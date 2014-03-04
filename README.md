Code Source is still developing.. Help and ideas are welcome.

What's KBCalendar ?
================

KBCalendar is an android horizontal calendar who uses [twoway-view](https://github.com/lucasr/twoway-view) make by [Lucas Rocha](https://github.com/lucasr).
KBCalendar wants to show a list of days with a selected effect on the center day.

![PreviewImage](http://i.picasion.com/pic76/f3ea3b7eabe6a49ad213587f1a08e40a.gif) 

[Video available here](https://drive.google.com/file/d/0Bz_yKoJtTIWmVGJOSThMU1lJam8/edit?usp=sharing)


How do I use it?
================

### Add TwoWayView 

1. Import TwoWayView as a library to your project.

2. Add a `TwoWayView` to your layout.

**Example**:

    <org.lucasr.twowayview.TwoWayView
        android:id="@+id/list"
        style="@style/TwoWayView"
        android:layout_width="fill_parent"
        android:layout_height="100dp"
        android:drawSelectorOnTop="false"
        tools:context=".MainActivity" />


### Add KBCalendar

1. Import KBCalendar.java and KBCalendarAdapter.java to your .app repository.

2. Import list_item.xml to your layout repository.

3. In your activity add the following code :

        KBCalendar mKBCalendar = new KBCalendar(this);
        mKBCalendar.loadKBCalendar();

**That's it !**

   
More possibilities ?
================

### Add events

1. Implements KBCalendar.IAgendaDateSelect

2. Add the following code : 

        @Override
        public void onDateSelect(Date date) {
            Log.i("KBCalendar", "Date : " + DateFormat.format("dd/MM", date).toString());
        }
        
        @Override
        public void onListScroll(Date date) {
            Log.i("KBCalendar", "Date : " + DateFormat.format("dd/MM", date).toString());
        }

### Center agenda on date

 - goToday()
 - centerToDate(Date dateCenter)
 - getCurrentDate()

### Others

 - setDaySize(int daySize)
 - setDayNumberSize(int dayNumberSize)
 - setFormatDay(String formatDay)
 - setFormatDayNumber(String formatDayNumber)
 - setHexColorDay(String hexColorDay)
 - setHexColorDayNumber(String hexColorDayNumber)
 - setHexBackgroundColor(String hexBackgroundColor)
 - setDateStartCalendar(Date dateStartCalendar) 
 - setDateEndCalendar(Date dateEndCalendar)
 - setNumberOfRowOnScreen(int numberOfRowOnScreen)
 
(Note : if you call these methods, pay attention to make it before loadKBCalendar())
 



License
=======

    Copyright (C) 2013 2014 Kevin Bernage (http://kevinbernage.netii.net)

    KBCalendar's code is based on bits and pieces of Android's TwoWayView.


    Copyright (C) 2012 The Android Open Source Project

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.




