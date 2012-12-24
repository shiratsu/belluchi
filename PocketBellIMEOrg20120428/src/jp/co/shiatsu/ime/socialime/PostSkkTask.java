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


public class PostSkkTask extends AsyncTask<Object, Integer, String> {

	
	private SQLiteDatabase mDb;
	
	// 
	public PostSkkTask() {
		
		
        
	}
	
	@Override
    protected void onPreExecute() {
       
    }
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stu
		SkkWriteHelper swh = (SkkWriteHelper) params[0];
		
		String requestStr = (String) params[1];
		HandleHttpRequest hhr = new HandleHttpRequest();
		try {
	        
			String requestUrl = CodeDefine.socialImeUrl+URLEncoder.encode(requestStr , "UTF-8")+"&charset=UTF-8";
			
			
	        swh.createEmptyDataBase();
	        
	        
	        mDb = swh.openDataBase();
	        
			
			hhr.sendGet(requestUrl);
			Log.d("debug:", "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA:"+requestUrl);
			hhr.inputStreemToString();
			hhr.splistSelectionStr();
			List<String> selection = new ArrayList<String>();
			selection = hhr.getSelection();
			
			updateDatabase(selection,requestStr);
			
			
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
	 */
	private void insertDatabase() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * 
	 * @param selection 
	 * @param requestStr2 
	 * @throws Exception 
	 */
	private void updateDatabase(List<String> selection, String requestStr2) throws Exception {
		// TODO Auto-generated method stub
		if(selection.size() > 0){
			
			
			for(String value : selection){
				
				Cursor c = fetchSkkCount(requestStr2,value);
				int count = feedCount(c);
				c.close();
				
				if(count == 0){
					String insertSql = "insert into dictionary(key,value,count) values('"+requestStr2+"','"+value+"',1)";
					Log.d("SQL_CHECL","&&&&&&&&&&&&&"+insertSql+"&&&&&&&&&&&&&");
					mDb.execSQL(insertSql);
				}
			}
		}else{
			throw new Exception("データベースの更新に失敗した");
		}
	}

	


	private int feedCount(Cursor c) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
		int count=0;
		String countString = null;
		if(c.moveToFirst()){
			do{
				countString = c.getString(c.getColumnIndex("count(*)"));
			}while(c.moveToNext());
		}	
		if(countString != null){
			count = Integer.valueOf(countString);
		}
		return count;
	}

	private Cursor fetchSkkCount(String requestStr2, String value) {
		// TODO Auto-generated method stub

		String sqlstr = "SELECT count(*) FROM dictionary where key = '"+requestStr2+"' and value = '"+value+"'";
		return mDb.rawQuery(sqlstr, null);
	}

	// 
	protected void onStopExecute() {
		
	}

	// 
	@Override
	protected void onPostExecute(String updateTime) {
		
	}
}
