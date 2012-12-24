package jp.co.shiatsu.ime.socialime;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import jp.co.shiatsu.ime.conf.CodeDefine;
import jp.co.shiatsu.ime.helper.SkkHelper;
import jp.co.shiatsu.ime.util.HandleHttpRequest;



public class FeedSKK implements Runnable {
	
	private SQLiteDatabase mDb;
	private SkkHelper sh;
	private Handler mHandler;
	private String requestStr;
	
	public FeedSKK(Handler handler,String requestStr2,SkkHelper sh2) {
		
		// TODO Auto-generated constructor stub
		// WebAPIへのアクセスが終わったことを知らせるためのハンドラ
        this.mHandler = handler;
        this.sh = sh2;
        this.requestStr = requestStr2;
        
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Message msg = new Message();
		try {
			
			
	      
	        try {
	    	 
	           sh.createEmptyDataBase();  
	           mDb = sh.openDataBase();
	        } catch (SQLiteException e) {
	            // ディスクフルなどでデータが書き込めない場合
	        	
	            mDb = sh.getReadableDatabase();
	        } catch (IOException e) {
	        	throw new Error("Unable to create database");
	        }
	        
	        List<String> selection = new ArrayList<String>();
	        
	        //データ一覧を取得
	        // すべてのデータのカーソルを取得
	        Cursor c = fetchAllSkk(requestStr);
	        selection = setSkkList(c);
	        c.close();
			
			
			
//			for(int i=0;i<selection.size();i++){
//				Log.d("debug:", selection.get(i));
//			}
			
			
			msg.obj = selection;
			msg.what = CodeDefine.MESSAGE_WHAT_SEARCHIME_END;
			
			
		} catch (Exception e) {
			Log.d("Exception", "except(" + e.toString() + ") called");
			// TODO: handle exception
			msg.what = CodeDefine.MESSAGE_WHAT_SEARCHIME_ERROR;
		}finally{
			if(mDb != null){
				if(mDb.isOpen()){
					mDb.close();
				}
			}
			if(sh != null){
				sh.close();
			}
	        
		}
		mHandler.sendMessage(msg);
		
	}

	
	private List<String> setSkkList(Cursor c) {
		List<String> selection = new ArrayList<String>();
		if(c.moveToFirst()){
			do{
				String value = c.getString(c.getColumnIndex("value"));				
				selection.add(value);				
			}while(c.moveToNext());
		}
		return selection;
	}

	private Cursor fetchAllSkk(String requestStr2) {
		String sqlstr = "select * from dictionary " +
					"where key = '"+requestStr2+"' order by count desc,id asc limit 500";
//					"where key like '"+requestStr2+"%' order by count desc,id asca limit 500";
		return mDb.rawQuery(sqlstr, null);
	}
}
