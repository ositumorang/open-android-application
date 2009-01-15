package com.global.android.ContactBlackList;



import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public  class AddBlackList extends Activity implements  OnClickListener{
    /** Called when the activity is first created.
     * 这Class主要是添加黑名单View的显示，用户把添加到详细信息储存到数据库中 */
	private Button btn_ok,btn_cancel;
    private Bundle bundle_phone,bundle_mms;
	private EditText user_name,phone_number;
	private String yes="yes";
	private String no="no";
	//private Boolean c=false,c1=false;
	public static String   refuse_phone_number_yes=null,
	                       refuse_phone_number_no=null,
	                       refuse_mms_yes=null, 
	                       refuse_mms_no=null
	                       ,phone_yes="yes";
	private static final  String AUTHORITY="com.global.provider.ContactBlackList";
	private static final Uri CONTENT_URI =
		Uri.parse("content://" + AUTHORITY + "/BlackListTable");
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        update();
    }
        public void update()
      {
        Intent intent=getIntent();
        if(intent.getData()==null)
        intent.setData(CONTENT_URI);
        setContentView(R.layout.alert_dialog_text_entry);
        
         user_name=(EditText)findViewById(R.id.username_edit);
         phone_number=(EditText)findViewById(R.id.phone_no_edit);
         btn_ok=(Button)findViewById(R.id.ok_btn);
         btn_ok.setOnClickListener((OnClickListener) this);
         btn_cancel=(Button)findViewById(R.id.cancel_btn);
		 btn_cancel.setOnClickListener(this);
		 //radio button的操作
		 RadioGroup rg_phone_number=(RadioGroup)findViewById(R.id.refuse_phone_rg);
		 //对RadioGroup phone number 进行监听
		 rg_phone_number.setOnCheckedChangeListener(phoneNumberListener);
		 RadioButton rb_refuse_phone_yes=(RadioButton)findViewById(R.id.refuse_phone_rb_yes);
		 rb_refuse_phone_yes.setId(1001);
		 rb_refuse_phone_yes.setChecked(true);//设置初始为"yes" RadioButton
		 RadioButton rb_refuse_phone_no=(RadioButton)findViewById(R.id.refuse_phone_rb_no);
		 rb_refuse_phone_no.setId(1002);
		//对RadioGroup mms 进行监听
		 RadioGroup rg_mms=(RadioGroup)findViewById(R.id.refuse_mms_rg);
		 rg_mms.setOnCheckedChangeListener(mmsListener);
		 RadioButton rb_refuse_mms_yes=(RadioButton)findViewById(R.id.refuse_mms_yes);
		 rb_refuse_mms_yes.setId(1003);
		 rb_refuse_mms_yes.setChecked(true);//设置初始为"yes" RadioButton
		 RadioButton rb_refuse_mms_no=(RadioButton)findViewById(R.id.refuse_mms_no);
		 rb_refuse_mms_no.setId(1004);	   
    }  
		   OnCheckedChangeListener phoneNumberListener=new OnCheckedChangeListener()
		        {
				        	public  void onCheckedChanged(RadioGroup arg0, int arg1) 
				        	{		
				        		//从被点击的按钮中取出数据给bundle_phone
				        		bundle_phone =new Bundle();
				        		     if(arg0.getCheckedRadioButtonId()==1001)
						            	 { 
						            		bundle_phone.putString("PHONE_YES", yes);
						            		
						            	 }
						            	
				        		     else if (arg0.getCheckedRadioButtonId()==1002)
						            	 { 
						            		bundle_phone.putString("PHONE_YES", no);
						            	 }		     
				        	}
		        };
        
		   OnCheckedChangeListener mmsListener=new OnCheckedChangeListener()
		        {
			               
				        	public  void onCheckedChanged(RadioGroup arg0, int arg1) {
				        		
				        		//从被点击的按钮中取出数据bundle_mms
				        		bundle_mms =new Bundle();
				        		if(arg0.getCheckedRadioButtonId()==1003)
						            	 {	
				        			      bundle_mms.putString("MMS_YES", yes);				            		
						            	 }
						            	
				        		else if(arg0.getCheckedRadioButtonId()==1004)
						            	 {  
						            		bundle_mms.putString("MMS_YES", no);
						            	 }
				        		   
				        	    }
		        };    
		  	        
		    public void onClick(View v) {
		    	if (v == btn_ok){ 
		    		    
			    		String et1=user_name.getText().toString();
						String et2=phone_number.getText().toString();
						String et3=bundle_phone.getString("PHONE_YES");
						String et4=bundle_mms.getString("MMS_YES");
						ContentValues cv = new ContentValues();
						
	                        cv.put("user_name",et1 );  
	                        cv.put("phone_number", et2);
	                        cv.put("number",et3 );
						    cv.put("mms", et4);
						   /*用ContentProvider向数据库插入数据*/
						    getContentResolver().insert(getIntent().getData(), cv);
						    finish();   		
						}
		    	if(v.equals(btn_cancel)){ this.finish(); }
		    }  
}
         