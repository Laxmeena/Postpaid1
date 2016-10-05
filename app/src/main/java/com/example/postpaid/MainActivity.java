/*
 *  Copyright (C) Laxmeena Dev Innovation - All Rights Reserved
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  * Written by Prakash <laxmeena.dev.innovation@gmail.com>, October  2016
 *
 */

package com.example.postpaid;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.text.InputType;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class MainActivity extends ActionBarActivity {
	TextView textView = null;
	ListView list;
	//EditText startDate = null,endDate=null;
	AlertDialog levelDialog;
	DatabaseHandler db = new DatabaseHandler(this);
	// this context will use when we work with Alert Dialog
	final Context context = this;
	private int mYear, mMonth, mDay, mHour, mMinute;
	String[] parts;
	String call_date,call_time;
	ArrayList<String> itemname = new ArrayList<String>();
	ArrayList<String> imgid = new ArrayList<String>();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//textView = (TextView) findViewById(R.id.textview_call);

		getCallDetails();

		TextView button = (TextView) findViewById(R.id.Search_Logs);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub


				AlertDialog.Builder builder = new AlertDialog.Builder(context);
				//you should edit this to fit your needs
				builder.setTitle("Enter Date Cycle");

				final EditText one = new EditText(context);
				one.setHint("Start Date");//optional
				final EditText two = new EditText(context);
				two.setHint("End Date");//optional

				//in my example i use TYPE_CLASS_NUMBER for input only numbers
				//  one.setInputType(InputType.TYPE_CLASS_NUMBER);
				// two.setInputType(InputType.TYPE_CLASS_NUMBER);

				LinearLayout lay = new LinearLayout(context);
				lay.setOrientation(LinearLayout.VERTICAL);
				lay.addView(one);
				lay.addView(two);
				builder.setView(lay);
				one.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						calender(one);
					}
				});
				two.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						calender(two);
					}
				});

				// Set up the buttons
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						//get the two inputs
						// int i = Integer.parseInt(one.getText().toString());
						//  int j = Integer.parseInt(two.getText().toString());
						getDatabaseCallDetails(one.getText().toString(),two.getText().toString());

					}
				});

				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whichButton) {
						dialog.cancel();
					}
				});
				builder.show();

				//getDatabaseCallDetails();
			}

		});

		TextView button1 = (TextView) findViewById(R.id.all_logs);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				// Perform action on click


				// Strings to Show In Dialog with Radio Buttons
				final CharSequence[] items = {" Outgoing "," Incoming "," Missed "};

				// Creating and Building the Dialog 
				AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
				builder.setTitle("Select the Call Type");
				builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int item) {


						switch(item)
						{
						case 0:
							// Your code when first option seletced

							Intent intent = new Intent(MainActivity.this, AllCallLogs.class);
							intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent.putExtra("Call Type","Outgoing");
							startActivity(intent);

							break;
						case 1:
							// Your code when 2nd  option seletced

							Intent intent1 = new Intent(MainActivity.this, AllCallLogs.class);
							intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent1.putExtra("Call Type", "Incoming");
							startActivity(intent1);

							break;
						case 2:
							// Your code when 3rd option seletced

							Intent intent2 = new Intent(MainActivity.this, AllCallLogs.class);
							intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							intent2.putExtra("Call Type", "Missed");
							startActivity(intent2);

							break;
						}
						levelDialog.dismiss();    
					}
				});
				levelDialog = builder.create();
				levelDialog.show(); 
				// finish();
				//getDatabaseCallDetails();
			}

		});



	}

	public void getCallDetails() {
		int total_Minutes=0;


		String strOrder = android.provider.CallLog.Calls.DATE + " DESC";
		/* Query the CallLog Content Provider */
		Cursor managedCursor = managedQuery(CallLog.Calls.CONTENT_URI, null,null, null, strOrder);
		int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
		int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
		int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
		int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
		//sb.append("Call Log :");
		while (managedCursor.moveToNext()) {
			String phNum = managedCursor.getString(number);
			String callTypeCode = managedCursor.getString(type);
			String strcallDate = managedCursor.getString(date);
			Date callDate = new Date(Long.valueOf(strcallDate));
			SimpleDateFormat df2 = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
			String dateText = df2.format(callDate);
			parts = dateText.split(" ");
			call_date=parts[0];
			call_time=parts[1];
			String callDuration = managedCursor.getString(duration);
			String callType = null;
			int callcode = Integer.parseInt(callTypeCode);
			switch (callcode) {
			case CallLog.Calls.OUTGOING_TYPE:
				callType = "Outgoing";
				break;
			case CallLog.Calls.INCOMING_TYPE:
				callType = "Incoming";
				break;
			case CallLog.Calls.MISSED_TYPE:
				callType = "Missed";
				break;
			}
			total_Minutes += managedCursor.getInt(duration);;
			//		   sb.append("\nPhone Number:--- " + phNum + " \nCall Type:--- "
			//		     + callType + " \nCall Date:--- " + callDate
			//		     + " \nCall duration in sec :--- " + callDuration);
			//sb.append("\n----------------------------------");
			//Log.d("date", startDate.getText().toString());
			//db.addContact(new Contact( phNum, callType,callDuration,call_date,call_time)); 

			db.addContact(new Contact( phNum, callType,callDuration,call_date,call_time));

		}
		//managedCursor.close();


	}

	// All Database call logs cycle date wise	

	private void getDatabaseCallDetails(String S_Date,String E_Date) {

		int OutGoing=0,Incoming=0,count_calls=0;


		

		final StringBuffer sb = new StringBuffer();
		// Getting all logs value from database
		//List<Contact> contacts = db.getAllContactsByDate(new Contact( startDate.getText().toString(),endDate.getText().toString()));   
		List<Contact> contacts = db.getAllContactsByDate(new Contact( S_Date,E_Date));

		Log.d("data accessed", "11111111111111111111");

		for (Contact cn : contacts) {

			if(cn.getType().contains("Outgoing")){

				int Out_minute_call_wise;
				if( (Integer.parseInt(cn.getDuration())/60) < (Float.parseFloat(cn.getDuration())/60) ){
					OutGoing +=((Integer.parseInt(cn.getDuration())/60)+1);
					Out_minute_call_wise=((Integer.parseInt(cn.getDuration())/60)+1);
				}
				else{
					OutGoing +=(Integer.parseInt(cn.getDuration())/60);
					Out_minute_call_wise=(Integer.parseInt(cn.getDuration())/60);
				}


				sb.append(cn.getPhoneNumber()+"  "+cn.getDate()+" "+cn.getTime()+" "+Out_minute_call_wise+"\n");
				itemname.add(cn.getPhoneNumber()+"    "+Out_minute_call_wise );
				imgid.add( cn.getDate()+"   "+cn.getTime() );
				//sb.append("Id: "+cn.getID()+" ,Number: " + cn.getPhoneNumber() + " ,type: " + cn.getType()+ " ,duration: " + cn.getDuration()+ " ,date: " + cn.getDate());

			}


			// Writing Contacts to log
			if(cn.getType().contains("Incoming")){
				//Incoming +=Integer.valueOf(cn.getDuration());
				if( (Integer.parseInt(cn.getDuration())/60) < (Float.parseFloat(cn.getDuration())/60) ){
					Incoming +=((Integer.parseInt(cn.getDuration())/60)+1);

				}
				else{
					Incoming +=(Integer.parseInt(cn.getDuration())/60);

				}
			}
			count_calls++;

		}
		//		sb.append("\n Call Log :"+String.valueOf(count_calls));
		//		sb.append("\nOutgoing In Minutes :"+OutGoing/60);
		//		sb.append("\nIncoming In Minutes :"+Incoming/60);
		//textView.setText(sb);

		new AlertDialog.Builder(this)
		.setTitle("All Call In Minutes")
		.setMessage("\n Call Log :"+String.valueOf(count_calls)+"\nOutgoing In Minutes :"+OutGoing+"\nIncoming In Minutes :"+Incoming)
		.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// continue with delete
				//textView.setText(sb);


				CustomListAdapter adapter=new CustomListAdapter(MainActivity.this, itemname,imgid);
				list=(ListView)findViewById(R.id.list);
				list.setAdapter(adapter);

				list.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						//String Slecteditem= itemname[+position];
						//Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

					}
				});






			}
		})
		.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) { 
				// do nothing

			}
		})
		.setIcon(android.R.drawable.ic_dialog_alert)
		.show();

	}





	public void calender(final EditText any) {



		// Process to get Current Date
		final Calendar c = Calendar.getInstance();
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// Launch Date Picker Dialog
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {
			@Override
			public void onDateSet(DatePicker view, int year,
					int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				any.setText(year + "-"+ (monthOfYear + 1) + "-" + dayOfMonth);
				//if(monthOfYear!=10 || monthOfYear!=10)

			}
		}, mYear, mMonth, mDay);
		dpd.show();
	}




	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
