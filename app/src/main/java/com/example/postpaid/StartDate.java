package com.example.postpaid;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class StartDate extends Activity {

    private static final String DEBUG_TAG = "CalendarActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_date);

        try {
            Log.i(DEBUG_TAG, "Starting Calendar Test");

            // ListAllCalendarDetails();

            // will return the last found calendar with "Test" in the name
            int iTestCalendarID = ListSelectedCalendars();

            // change this when you know which calendar you want to use
            // If you create a new calendar, you may need to manually sync the
            // phone first
            if (iTestCalendarID != 0) {

                Uri newEvent2 = MakeNewCalendarEntry2(iTestCalendarID);
                int eventID2 = Integer.parseInt(newEvent2.getLastPathSegment());
                ListCalendarEntry(eventID2);

                Uri newEvent = MakeNewCalendarEntry(iTestCalendarID);
                int eventID = Integer.parseInt(newEvent.getLastPathSegment());
                ListCalendarEntry(eventID);
                
                // uncomment these to show updating and deleting entries

                //UpdateCalendarEntry(eventID);
                //ListCalendarEntrySummary(eventID);
                //DeleteCalendarEntry(eventID);
                
                //ListCalendarEntrySummary(eventID);
               // ListAllCalendarEntries(iTestCalendarID);
            } else {
                Log.i(DEBUG_TAG, "No 'Test' calendar found.");
            }

            Log.i(DEBUG_TAG, "Ending Calendar Test");


        } catch (Exception e) {
            Log.e(DEBUG_TAG, "General failure", e);
        }
    }

    private int ListSelectedCalendars() {
        int result = 0;
        String[] projection = new String[] { "_id", "name" };
        String selection = "selected=1";
        String path = "calendars";

        Cursor managedCursor = getCalendarManagedCursor(projection, selection,
                path);

        if (managedCursor != null && managedCursor.moveToFirst()) {

            Log.i(DEBUG_TAG, "Listing Selected Calendars Only");

            int nameColumn = managedCursor.getColumnIndex("name");
            int idColumn = managedCursor.getColumnIndex("_id");

            do {
                String calName = managedCursor.getString(nameColumn);
                String calId = managedCursor.getString(idColumn);
                Log.i(DEBUG_TAG, "Found Calendar '" + calName + "' (ID="
                        + calId + ")");
                if (calName != null && calName.contains("Test")) {
                    result = Integer.parseInt(calId);
                }
            } while (managedCursor.moveToNext());
        } else {
            Log.i(DEBUG_TAG, "No Calendars");
        }

        return result;

    }

    private void ListAllCalendarDetails() {
        Cursor managedCursor = getCalendarManagedCursor(null, null, "calendars");

        if (managedCursor != null && managedCursor.moveToFirst()) {

            Log.i(DEBUG_TAG, "Listing Calendars with Details");

            do {

                Log.i(DEBUG_TAG, "**START Calendar Description**");

                for (int i = 0; i < managedCursor.getColumnCount(); i++) {
                    Log.i(DEBUG_TAG, managedCursor.getColumnName(i) + "="
                            + managedCursor.getString(i));
                }
                Log.i(DEBUG_TAG, "**END Calendar Description**");
            } while (managedCursor.moveToNext());
        } else {
            Log.i(DEBUG_TAG, "No Calendars");
        }

    }

    private void ListAllCalendarEntries(int calID) {

        Cursor managedCursor = getCalendarManagedCursor(null, "calendar_id="
                + calID, "events");

        if (managedCursor != null && managedCursor.moveToFirst()) {

            Log.i(DEBUG_TAG, "Listing Calendar Event Details");

            do {

                Log.i(DEBUG_TAG, "**START Calendar Event Description**");

                for (int i = 0; i < managedCursor.getColumnCount(); i++) {
                    Log.i(DEBUG_TAG, managedCursor.getColumnName(i) + "="
                            + managedCursor.getString(i));
                }
                Log.i(DEBUG_TAG, "**END Calendar Event Description**");
            } while (managedCursor.moveToNext());
        } else {
            Log.i(DEBUG_TAG, "No Calendars");
        }

    }

    private void ListCalendarEntry(int eventId) {
        Cursor managedCursor = getCalendarManagedCursor(null, null, "events/" + eventId);
    
        if (managedCursor != null && managedCursor.moveToFirst()) {

            Log.i(DEBUG_TAG, "Listing Calendar Event Details");

            do {

                Log.i(DEBUG_TAG, "**START Calendar Event Description**");

                for (int i = 0; i < managedCursor.getColumnCount(); i++) {
                    Log.i(DEBUG_TAG, managedCursor.getColumnName(i) + "="
                            + managedCursor.getString(i));
                }
                Log.i(DEBUG_TAG, "**END Calendar Event Description**");
            } while (managedCursor.moveToNext());
        } else {
            Log.i(DEBUG_TAG, "No Calendar Entry");
        }

    }

    private void ListCalendarEntrySummary(int eventId) {
        String[] projection = new String[] { "_id", "title", "dtstart" };
        Cursor managedCursor = getCalendarManagedCursor(projection,
                null, "events/" + eventId);

        if (managedCursor != null && managedCursor.moveToFirst()) {

            Log.i(DEBUG_TAG, "Listing Calendar Event Details");

            do {

                Log.i(DEBUG_TAG, "**START Calendar Event Description**");

                for (int i = 0; i < managedCursor.getColumnCount(); i++) {
                    Log.i(DEBUG_TAG, managedCursor.getColumnName(i) + "="
                            + managedCursor.getString(i));
                }
                Log.i(DEBUG_TAG, "**END Calendar Event Description**");
            } while (managedCursor.moveToNext());
        } else {
            Log.i(DEBUG_TAG, "No Calendar Entry");
        }

    }

    private Uri MakeNewCalendarEntry(int calId) {
        ContentValues event = new ContentValues();

        event.put("calendar_id", calId);
        event.put("title", "Today's Event [TEST]");
        event.put("description", "2 Hour Presentation");
        event.put("eventLocation", "Online");

        long startTime = System.currentTimeMillis() + 1000 * 60 * 60;
        long endTime = System.currentTimeMillis() + 1000 * 60 * 60 * 2;

        event.put("dtstart", startTime);
        event.put("dtend", endTime);

        event.put("allDay", 0); // 0 for false, 1 for true
        event.put("eventStatus", 1);
        event.put("visibility", 0);
        event.put("transparency", 0);
        event.put("hasAlarm", 0); // 0 for false, 1 for true

        Uri eventsUri = Uri.parse(getCalendarUriBase()+"events");

        Uri insertedUri = getContentResolver().insert(eventsUri, event);
        return insertedUri;
    }

    private Uri MakeNewCalendarEntry2(int calId) {
        ContentValues event = new ContentValues();

        event.put("calendar_id", calId);
        event.put("title", "Birthday [TEST]");
        event.put("description", "All Day Event");
        event.put("eventLocation", "Worldwide");

        long startTime = System.currentTimeMillis() + 1000 * 60 * 60 * 24;

        event.put("dtstart", startTime);
        event.put("dtend", startTime);

        event.put("allDay", 1); // 0 for false, 1 for true
        event.put("eventStatus", 1);
        event.put("visibility", 0);
        event.put("transparency", 0);
        event.put("hasAlarm", 0); // 0 for false, 1 for true

        Uri eventsUri = Uri.parse(getCalendarUriBase()+"events");
        Uri insertedUri = getContentResolver().insert(eventsUri, event);
        return insertedUri;

    }

    private int UpdateCalendarEntry(int entryID) {
        int iNumRowsUpdated = 0;

        ContentValues event = new ContentValues();

        event.put("title", "Changed Event Title");
        event.put("hasAlarm", 1); // 0 for false, 1 for true

        Uri eventsUri = Uri.parse(getCalendarUriBase()+"events");
        Uri eventUri = ContentUris.withAppendedId(eventsUri, entryID);

        iNumRowsUpdated = getContentResolver().update(eventUri, event, null,
                null);

        Log.i(DEBUG_TAG, "Updated " + iNumRowsUpdated + " calendar entry.");

        return iNumRowsUpdated;
    }

    private int DeleteCalendarEntry(int entryID) {
        int iNumRowsDeleted = 0;

        Uri eventsUri = Uri.parse(getCalendarUriBase()+"events");
        Uri eventUri = ContentUris.withAppendedId(eventsUri, entryID);
        iNumRowsDeleted = getContentResolver().delete(eventUri, null, null);

        Log.i(DEBUG_TAG, "Deleted " + iNumRowsDeleted + " calendar entry.");

        return iNumRowsDeleted;
    }

    /**
     * @param projection
     * @param selection
     * @param path
     * @return
     */
    private Cursor getCalendarManagedCursor(String[] projection,
            String selection, String path) {
        Uri calendars = Uri.parse("content://calendar/" + path);

        Cursor managedCursor = null;
        try {
            managedCursor = managedQuery(calendars, projection, selection,
                    null, null);
        } catch (IllegalArgumentException e) {
            Log.w(DEBUG_TAG, "Failed to get provider at ["
                    + calendars.toString() + "]");
        }

        if (managedCursor == null) {
            // try again
            calendars = Uri.parse("content://com.android.calendar/" + path);
            try {
                managedCursor = managedQuery(calendars, projection, selection,
                        null, null);
            } catch (IllegalArgumentException e) {
                Log.w(DEBUG_TAG, "Failed to get provider at ["
                        + calendars.toString() + "]");
            }
        }
        return managedCursor;
    }

    /*
     * Determines if it's a pre 2.1 or a 2.2 calendar Uri, and returns the Uri
     */
    private String getCalendarUriBase() {
        String calendarUriBase = null;
        Uri calendars = Uri.parse("content://calendar/calendars");
        Cursor managedCursor = null;
        try {
            managedCursor = managedQuery(calendars, null, null, null, null);
        } catch (Exception e) {
            // eat
        }

        if (managedCursor != null) {
            calendarUriBase = "content://calendar/";
        } else {
            calendars = Uri.parse("content://com.android.calendar/calendars");
            try {
                managedCursor = managedQuery(calendars, null, null, null, null);
            } catch (Exception e) {
                // eat
            }

            if (managedCursor != null) {
                calendarUriBase = "content://com.android.calendar/";
            }

        }

        return calendarUriBase;
    }

}