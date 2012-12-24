package jp.co.shiatsu.ime.socialime;

import java.io.IOException;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.co.shiatsu.ime.conf.CodeDefine;
import jp.co.shiatsu.ime.helper.PredictHelper;
import jp.co.shiatsu.ime.helper.PredictWriteHelper;
import jp.co.shiatsu.ime.helper.SkkWriteHelper;
import jp.co.shiatsu.ime.util.HandleHttpRequest;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.util.Log;


public class PostPredictTask extends AsyncTask<Object, Integer, String> {

	
	private SQLiteDatabase mDb;
	
	// 
	public PostPredictTask() {
		
		
        
	}
	
	@Override
    protected void onPreExecute() {
       
    }
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stu
		PredictWriteHelper ph = (PredictWriteHelper) params[0];
		
		String predictKey = (String) params[1];
		String predictVal = (String) params[2];
		
		try {
	        
			
			
			
			ph.createEmptyDataBase();
	        
	        
	        mDb = ph.openDataBase();
	        
			
			
			
			updateDatabase(predictKey,predictVal);
			
			
		} catch (Exception e) {
			onStopExecute();
			e.printStackTrace();
			Log.d("debug:", e.toString());
       	 	
		}finally{
			if(mDb.isOpen()){
				mDb.close();
			}
			ph.close();
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
	private void updateDatabase(String predictKey, String predictVal) throws Exception {
		// TODO Auto-generated method stub
		//件数を確認して、あったら、入れない
		String selectSql = "select count(1) as cnt from predict where key = '"+predictKey+"' and value = '"+predictVal+"'";
		Cursor c = mDb.rawQuery(selectSql, null);
		
		String val = null;
		if(c.moveToFirst()){
			do{
				val = c.getString(c.getColumnIndex("cnt"));				
								
			}while(c.moveToNext());
		}
		int cnt = Integer.valueOf(val);
		if(cnt == 0){
			String insertSql = "insert into predict values('"+predictKey+"','"+predictVal+"')";
			Log.d("SQL_CHECL","&&&&&&&&&&&&&"+insertSql+"&&&&&&&&&&&&&");
			mDb.execSQL(insertSql);
		}
		
	}

	
	// 
	protected void onStopExecute() {
		
	}

	// 
	@Override
	protected void onPostExecute(String updateTime) {
		
	}
}
