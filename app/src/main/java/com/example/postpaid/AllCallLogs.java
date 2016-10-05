package com.example.postpaid;
import java.sql.Date;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.CallLog;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class AllCallLogs extends Activity {
	TextView textView = null;
	DatabaseHandler db = new DatabaseHandler(this);
	String call_type;
	ProgressDialog progress = null;
	StringBuffer sb = new StringBuffer();


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.all_call_logs);

		new MyAsyncTaskClass( ).execute("foo","baar","ss");
		textView = (TextView) findViewById(R.id.textview_call_logs);
		//String data = getIntent().getExtras().getString("AnyKeyName");
		Intent iin= getIntent();
		Bundle b = iin.getExtras();

		if(b!=null)
		{
			call_type =(String) b.get("Call Type");
			Log.d("Intent value",call_type);

		}
		//new MyAsyncTaskClass( ).execute("foo","baar","ss");

		//getDatabaseCallDetails();

		//getCallDetails();
	}

	 

	private void getDatabaseCallDetails() {

		int OutGoing=0,Incoming=0,Missed=0,count_calls=0;
		//StringBuffer sb = new StringBuffer();
		// Getting all logs value from database
		//List<Contact> contacts = db.getAllContacts(new Contact( startDate.getText().toString(),endDate.getText().toString()));    
		List<Contact> contacts = db.getAllContacts();  

		Log.d("data accessed", "11111111111111111111");


		for (Contact cn : contacts) {

			//if(cn.getType().contains("Outgoing")){
			if(cn.getType().contains(call_type)){

				sb.append("\nPhone Number:--- " + cn.getPhoneNumber() + " \nCall Type:--- "
						+ cn.getType() + " \nCall Date:--- " + cn.getDate()
						+ " \nCall duration in sec :--- " + cn.getDuration());
				sb.append("\n----------------------------------");

			}




			count_calls++;

		}
		//		sb.append("\n Call Log :"+String.valueOf(count_calls));
		//		sb.append("\nOutgoing :"+OutGoing/60);
		//		sb.append("\nIncoming :"+Incoming/60);
		//textView.setText(sb);


	}

	private class MyAsyncTaskClass extends AsyncTask<String, String, Integer> {

		@Override
		protected void onPreExecute() {

			Log.d("fsdfas", "11111111111111111111111");
			progress = new ProgressDialog(AllCallLogs.this);
			progress.setTitle("Loading");
			progress.setMessage("Wait while loading...");
			progress.show();
			// To dismiss the dialog
			Log.d("fsdfas", "11111111111111111111111----------------");


		}
		@Override
		protected Integer doInBackground(String... params) {
			// TODO Auto-generated method stub
			// do your thing
			Log.d("fsdfas", "22222222222222222222222222222");
			getDatabaseCallDetails();
			Log.d("fsdfas", "22222222222222222222222222222---------");
			return null;
		}
		@Override
		protected void onPostExecute(Integer result) {
			// put here everything that needs to be done after your async task finishes
			Log.d("fsdfas", "3333333333333333333333333333");
			progress.dismiss();
			textView.setText(sb);
			db.close();
			Log.d("fsdfas", "3333333333333333333333333333-----------");

		}

	}

}