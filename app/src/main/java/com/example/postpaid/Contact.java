package com.example.postpaid;
public class Contact {
     
    //private variables
    int _id;
    String _phone_number;
    String _type;
    String _duration;
    String _date;
    String _time;
    String _start_date;
    String _end_date;
     
    // Empty constructor
    public Contact(){
         
    }
    // constructor
    public Contact(int id, String _phone_number, String type, String duration, String date,String time){
        this._id = id;
        this._phone_number = _phone_number;
        this._type = type;
        this._duration = duration;
        this._date = date;
        this._time = time;
    }
     
    // constructor
    public Contact(String _phone_number, String type, String duration, String date,String time){
    	this._phone_number = _phone_number;
        this._type = type;
        this._duration = duration;
        this._date = date;
        this._time = time;
    }
    
 // constructor
    public Contact(String start_date,String end_date){
    	
        this._start_date = start_date;
        this._end_date = end_date;
    }
    
    // getting ID
    public int getID(){
        return this._id;
    }
     
    // setting id
    public void setID(int id){
        this._id = id;
    }
     
    // getting phone number
    public String getPhoneNumber(){
        return this._phone_number;
    }
     
    // setting phone number
    public void setPhoneNumber(String phone_number){
        this._phone_number = phone_number;
    }
    
    // getting name
    public String getType(){
        return this._type;
    }
     
    // setting name
    public void setType(String type){
        this._type = type;
    }
    
    // getting name
    public String getDuration(){
        return this._duration;
    }
     
    // setting name
    public void setDuration(String duration){
        this._duration = duration;
    }
    
    // getting name
    public String getDate(){
        return this._date;
    }
     
    // setting name
    public void setDate(String date){
        this._date = date;
    }
    
 // getting name
    public String getTime(){
        return this._time;
    }
     
    // setting name
    public void setTime(String time){
    	 this._time = time;
    }
    
 // getting name
    public String getStartDate(){
        return this._start_date;
    }
     
    // setting name
    public void setStartDate(String start_date){
    	 this._start_date = start_date;
         
    }
    
 // getting name
    public String getEndDate(){
        return this._end_date;
    }
     
    // setting name
    public void setEndDate(String end_date){
    	this._end_date = end_date;
    }
}