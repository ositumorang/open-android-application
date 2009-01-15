package com.global.android.ContactBlackList;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ContactBlackList extends ListActivity//ListActivity 
{
	
    /** Called when the activity is first created. */
	//按钮声明部分
	private static final int MENU_ADD_PHONENO =Menu.FIRST;
	private static final int MENU_EXIT_PHONENO=Menu.FIRST+1;
	private static  Cursor cur;
	//private String mphone_number;
	private static  String user_name,mRow_id,phone_yes_no,mms_yes_no,mphone_number;
   //链接数据库声明部分
	private static final  String AUTHORITY="com.global.provider.ContactBlackList";
	private static final Uri CONTENT_URI =
		      Uri.parse("content://" + AUTHORITY + "/BlackListTable");
    private static final String[] PROJECTION = new String[] {
		                     "_id","user_name", "phone_number","number","mms" };	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.upDate();//调用upDate() show list in screen
            }
    //这部分主要打开数据库并且把数据库里的资料显示出来，
   protected void upDate()
    {   
	   
	  Intent intent=getIntent();//创建intent
       if(intent.getData()==null)
       	intent.setData(CONTENT_URI);
       //query name phone number and _id
           cur =getContentResolver().query(getIntent().getData(), 
           		PROJECTION, null, null, null);
			                 //show list from Database    
			            	 Cursor c = getContentResolver().query(CONTENT_URI, null, null, null, null);
			                 startManagingCursor(c);
			                 // Map Cursor columns to views defined in simple_list_item_2.xml
			                 ListAdapter adapter = new SimpleCursorAdapter(this,
			                         android.R.layout.simple_list_item_2, c, 
			                                 new String[] { "user_name","phone_number"}, 
			                                 new int[] { android.R.id.text1,android.R.id.text2});
			                 setListAdapter(adapter);
			               //判断黑名单中是否有内容,
			                 if(cur.getCount()==0)
					            {
				            	//如果没有数据，出现一个提示
				            	Toast.makeText(this, "No list", Toast.LENGTH_SHORT).show();  
					            }            
			           
    }
   protected void onResume() {
       super.onResume();
       //update list
       this.upDate();
   }
  
    protected void onListItemClick(ListView l, View v, int position, long id){
    	/*这部分主要是对数据的取与存。当任意一个选项被点击的时候将出现一个对话框，可以对这一选项进行
    	 * 编辑、删除、也提供删除所有的的黑名单的选项。当点击编辑选项，就从数据库把原始的数据调出来
    	 * 储存到SharedPreferences里，然后再EditBlackList.java中在取出并显示*/
    	super.onListItemClick(l, v, position, id);	
    	           cur.moveToPosition(position);
    	          /*用SharedPreferences存数据,把选择的name and phone number ... 给EditBlackList.java
			       *从数据库得到当前选项item的name and phone number... */
			         user_name=cur.getString(cur.getColumnIndexOrThrow("user_name"));
			         mRow_id=cur.getString(cur.getColumnIndexOrThrow("_id"));  
			         mphone_number=cur.getString(cur.getColumnIndexOrThrow("phone_number"));
			         phone_yes_no=cur.getString(cur.getColumnIndexOrThrow("number")); 
			         mms_yes_no=cur.getString(cur.getColumnIndexOrThrow("mms"));       
			        
			        //向SharedPreferences物件存name and, phone, number, _id,RadioButton数据  
			         Editor passwdfile_mRow_id =getSharedPreferences("DATA_MROW_ID",0).edit();
			         passwdfile_mRow_id.putString("DATA_MROW_ID", mRow_id);
			         passwdfile_mRow_id.commit();
			  		
			         Editor passwdfile_user_name =getSharedPreferences("DATA_USER_NAME",0).edit();
			  		passwdfile_user_name.putString("DATA_USER_NAME", user_name);
			  		passwdfile_user_name.commit();
			  		
			  		Editor passwdfile_phone_number =getSharedPreferences("DATA_PHONE_NUMBER",0).edit();
			  		passwdfile_phone_number.putString("DATA_PHONE_NUMBER", mphone_number);
			  		passwdfile_phone_number.commit();
			  		
			  		Editor passwdfile_phone_yes_no =getSharedPreferences("PHONE_YES_NO",0).edit();
			     	passwdfile_phone_yes_no.putString("PHONE_YES_NO", phone_yes_no);
			     	passwdfile_phone_yes_no.commit();
			     	
			     	Editor passwdfile_mms_yes_no =getSharedPreferences("MMS_YES_NO",0).edit();
			     	passwdfile_mms_yes_no.putString("MMS_YES_NO", mms_yes_no);
			     	passwdfile_mms_yes_no.commit();
				        
		//创建对话框,当点击黑名单任何选项，弹出一个删除和编辑的对话框，可以供user操作
		new AlertDialog.Builder(ContactBlackList.this)
                 .setTitle(R.string.select_dialog)
                 .setItems(R.array.select_dialog_items, new DialogInterface.OnClickListener() {
                     public void onClick(DialogInterface dialog, int which) {    
                         /* User clicked so do some stuff */      
                        if(which==0)
                          {       	
                        	//当选择确定的按钮，转向EditBlackList Activity.
                        	Intent intent_edit =new Intent(ContactBlackList.this,EditBlackList.class);
            				startActivity(intent_edit);
                          }
                      //删除一个选项
                        else if(which==1)
	                        {       
                        	        //提示是否删除
		                        	 new AlertDialog.Builder(ContactBlackList.this) 
		                            .setTitle(R.string.alert_dialog_warning) 
		                            .setMessage(R.string.this_number_will_delete) 
		                            //.setIcon(R.drawable.quit) 
		                            .setPositiveButton("ok", new DialogInterface.OnClickListener() { 
		                            
		                            	public void onClick(DialogInterface dialog, int whichButton) { 
		                            // setResult(RESULT_OK);//确定按钮事件 
		                            //当选择删除按钮事，删除当前的Item                      	
			                        	String cc[] = { "_id", mRow_id};
			                            getContentResolver().delete(getIntent().getData(), "BlackListTable", cc);
			                        	upDate();	
		                            } 
		                            }) 
		                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() { 
		                            public void onClick(DialogInterface dialog, int whichButton) { 
		                             //取消按钮事件 
		                            } 
		                            }) 
		                            .show();

                        	                        	
	                        }
                        //删除所有选项
                        else 
                        {
                        	new AlertDialog.Builder(ContactBlackList.this) 
                            .setTitle(R.string.alert_dialog_warning) 
                            .setMessage(R.string.all_number_will_delete) 
                            //.setIcon(R.drawable.quit) 
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() { 
                            
                            	public void onClick(DialogInterface dialog, int whichButton) { 
                            // setResult(RESULT_OK);//确定按钮事件 
                            //当选择删除按钮事，删除当前的Item                      	
	                        	
	                        	getContentResolver().delete(getIntent().getData(), "BlackListTable", null);
	                        	upDate();	
                            } 
                            }) 
                            .setNegativeButton("cancel", new DialogInterface.OnClickListener() { 
                            public void onClick(DialogInterface dialog, int whichButton) { 
                             //取消按钮事件 
                            } 
                            })    
                            .show();	
                        }
                        
                     }
                 })
                 .show();    
    }
    protected void onPause() {
        super.onPause();  
        cur.close();//close sursor 
    }
  //添加主按钮，add,edit,del,exit
    public boolean onCreateOptionsMenu(Menu menu)
    {
    	super.onCreateOptionsMenu(menu);
    	menu.add(0, MENU_ADD_PHONENO, 0, R.string.menu_add_phone_no).setIcon(R.drawable.ic_menu_add);
    	menu.add(0, MENU_EXIT_PHONENO, 1, R.string.menu_exit);
    	
    	return true;
    }
  //监听主按钮，add,edit,del,exit
    public boolean onOptionsItemSelected(MenuItem item)
    {
			switch(item.getItemId())
			{
			case MENU_ADD_PHONENO:
				/*监听添加按钮，
				 *这个intent的主要作用是转到另外一个activity（注意：这个activity的显示是一个浮动窗口view）
				 *这个浮动的窗口主要是添加黑名单的*/
				Intent intent =new Intent(ContactBlackList.this,AddBlackList.class);
				startActivity(intent);
				break;
			case MENU_EXIT_PHONENO:
				finish();
				break;	
			}
	    	
	    	return super.onOptionsItemSelected(item);   	
    }
  
}
