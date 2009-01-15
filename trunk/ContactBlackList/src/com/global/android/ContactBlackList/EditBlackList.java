package com.global.android.ContactBlackList;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class EditBlackList extends Activity implements  OnClickListener{
    /** Called when the activity is first created. */
	private Button btn_ok,btn_cancel;
	private EditText user_name,phone_number;
	private String mRow_id;
	public String  phone_yes_no,mms_yes_no;
	private static final  String AUTHORITY="com.global.provider.ContactBlackList";
	private static final Uri CONTENT_URI =
		Uri.parse("content://" + AUTHORITY + "/BlackListTable");
    private Bundle bundle_phone,bundle_mms;
    private String et3,et4;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);	
        /*这部分主要对数据的取与存，首先从SharedPreferences中取出从ContactBlackList.java中
         * 传过来的数据，并把原始数据显示给用户查看并且修改，然后再储存到数据库中*/
        Intent intent=getIntent();
        if(intent.getData()==null)
        intent.setData(CONTENT_URI);
        setContentView(R.layout.alert_dialog_text_entry);
        //从contactBlackList得到name and phone number _id
        SharedPreferences passwdfile_mRow_id = getSharedPreferences( "DATA_MROW_ID", 0);
         mRow_id = passwdfile_mRow_id.getString("DATA_MROW_ID", null);
         //从contactBlackList得到name 
        SharedPreferences passwdfile_user_name = getSharedPreferences( "DATA_USER_NAME", 0);
    	String show_user_name = passwdfile_user_name.getString("DATA_USER_NAME", null);
        //从contactBlackList得到phone number 
    	SharedPreferences passwdfile_phone_number = getSharedPreferences( "DATA_PHONE_NUMBER", 0);
    	String show_phone_number = passwdfile_phone_number.getString("DATA_PHONE_NUMBER", null);
    	
    	SharedPreferences passwdfile_phone_yes_no = getSharedPreferences( "PHONE_YES_NO", 0);
         phone_yes_no = passwdfile_phone_yes_no.getString("PHONE_YES_NO", null);
      
        SharedPreferences passwdfile_mms_yes_no = getSharedPreferences( "MMS_YES_NO", 0);
         mms_yes_no = passwdfile_mms_yes_no.getString("MMS_YES_NO", null);
      
         user_name=(EditText)findViewById(R.id.username_edit); 
         user_name.setText(show_user_name);
        // show phone number from SharedPreferences
         phone_number=(EditText)findViewById(R.id.phone_no_edit);
         phone_number.setText(show_phone_number);   
      
         btn_ok=(Button)findViewById(R.id.ok_btn);
         btn_ok.setOnClickListener((OnClickListener) this);
         btn_cancel=(Button)findViewById(R.id.cancel_btn);
		 btn_cancel.setOnClickListener(this);
		 
		 RadioGroup rg_phone_number=(RadioGroup)findViewById(R.id.refuse_phone_rg);
		 rg_phone_number.setOnCheckedChangeListener(phoneNumberListener);
		 
		 RadioGroup rg_mms=(RadioGroup)findViewById(R.id.refuse_mms_rg);
		 rg_mms.setOnCheckedChangeListener(mmsListener);
		 
		 RadioButton rb_refuse_phone_yes=(RadioButton)findViewById(R.id.refuse_phone_rb_yes);
		 rb_refuse_phone_yes.setId(1001);
		 
		 RadioButton rb_refuse_phone_no=(RadioButton)findViewById(R.id.refuse_phone_rb_no);
		 rb_refuse_phone_no.setId(1002);
		 
		 RadioButton rb_refuse_mms_yes=(RadioButton)findViewById(R.id.refuse_mms_yes);
		 rb_refuse_mms_yes.setId(1003);
		
		 RadioButton rb_refuse_mms_no=(RadioButton)findViewById(R.id.refuse_mms_no);
		 rb_refuse_mms_no.setId(1004);
		 bundle_phone =new Bundle();
		 bundle_mms =new Bundle();
		//判断从数据库中取出的Radio的焦点的值，并设置其焦点
		 if (phone_yes_no.equalsIgnoreCase("yes"))
         {
        	 rb_refuse_phone_yes.setChecked(true);
        	 bundle_phone.putString("PHONE_YES", "yes");
         }
         else
         {
        	 rb_refuse_phone_no.setChecked(true); 
        	 bundle_phone.putString("PHONE_YES", "no");
         }
		
		 if (mms_yes_no.equalsIgnoreCase("yes"))
         {
        	 rb_refuse_mms_yes.setChecked(true); 
        	 bundle_mms.putString("MMS_YES", "yes");	
         }
         else
         {
        	 rb_refuse_mms_no.setChecked(true); 
        	 bundle_mms.putString("MMS_YES", "no");	
         }
    }
    OnCheckedChangeListener phoneNumberListener=new OnCheckedChangeListener()
    {
	        	public  void onCheckedChanged(RadioGroup arg0, int arg1) 
	        	{		
	        		//c=true;
	        		//从被点击的按钮中取出数据给bundle_phone
	        		
	        		     if(arg0.getCheckedRadioButtonId()==1001)
			            	 { 
			            		bundle_phone.putString("PHONE_YES", "yes");
			            		
			            	 }
			            	
	        		     else if (arg0.getCheckedRadioButtonId()==1002)
			            	 { 
			            		bundle_phone.putString("PHONE_YES", "no");
			            	 }		     
	        	}
    };

    OnCheckedChangeListener mmsListener=new OnCheckedChangeListener()
    {
               
	        	public  void onCheckedChanged(RadioGroup arg0, int arg1) {
	        		//c1=true;
	        		//从被点击的按钮中取出数据bundle_mms
	        		
	        		if(arg0.getCheckedRadioButtonId()==1003)
			            	 {	
	        			      bundle_mms.putString("MMS_YES", "yes");				            		
			            	 }
			            	
	        		else if(arg0.getCheckedRadioButtonId()==1004)
			            	 {  
			            		bundle_mms.putString("MMS_YES", "no");
			            	 }  
	        	    }
    };    
   
    public void onClick(View v) {
    	if (v == btn_ok){ 
            //更新数据
    		String et1=user_name.getText().toString();
			String et2=phone_number.getText().toString();		
			 et3=bundle_phone.getString("PHONE_YES");			
			 et4=bundle_mms.getString("MMS_YES");	
			ContentValues cv = new ContentValues();
	    	cv.put("user_name",et1 ); 
	    	cv.put("phone_number", et2);
	    	cv.put("number",et3 );
	    	cv.put("mms", et4);
	    	String cc[] = { "_id", mRow_id};
	    	getContentResolver().update(getIntent().getData(), cv, null, cc);
	    	finish();
    	}
    	if(v.equals(btn_cancel)){ this.finish(); }
    }
	}