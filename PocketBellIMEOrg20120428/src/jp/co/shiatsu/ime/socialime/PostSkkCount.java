package jp.co.shiatsu.ime.socialime;

import java.io.IOException;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.co.shiatsu.ime.conf.CodeDefine;
import jp.co.shiatsu.ime.helper.SkkWriteHelper;
import jp.co.shiatsu.ime.util.HandleHttpRequest;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;


public class PostSkkCount extends AsyncTask<Object, Integer, String> {

	
	private SQLiteDatabase mDb;
	
	// 
	public PostSkkCount() {
		
		
        
	}
	
	@Override
    protected void onPreExecute() {
       
    }
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stu
		SkkWriteHelper swh = (SkkWriteHelper) params[0];
		
		String val = (String) params[1];
		
		try {
	        
			
			
	        swh.createEmptyDataBase();
	        
	        
	        mDb = swh.openDataBase();

			updateDatabase(val);
			
			
		} catch (Exception e) {
			onStopExecute();
			e.printStackTrace();
			Log.d("debug:", e.toString());
       	 	
		}finally{
			if(mDb != null){
				if(mDb.isOpen()){
					mDb.close();
				}
			}
			if(swh != null){
				swh.close();
			}
		}
		
		long timeMillisEnd = System.currentTimeMillis(); 
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String updateTime = sdf1.format(new Date(timeMillisEnd));
		return updateTime;
	}
	
	
	/**
	 * 
	 * @param selection 
	 * @param requestStr2 
	 * @throws Exception 
	 */
	private void updateDatabase(String value) throws Exception {
		// TODO Auto-generated method stub
		String insertSql = "update dictionary set count = count + 1 where value = '"+value+"'";
		Log.d("SQL_CHECL","&&&&&&&&&&&&&"+insertSql+"&&&&&&&&&&&&&");
		mDb.execSQL(insertSql);
	}


	// 
	protected void onStopExecute() {
		
	}

	// 
	@Override
	protected void onPostExecute(String updateTime) {
		
	}
}
