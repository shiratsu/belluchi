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
import jp.co.shiatsu.ime.helper.PredictHelper;
import jp.co.shiatsu.ime.helper.SkkHelper;
import jp.co.shiatsu.ime.util.HandleHttpRequest;



public class FeedPredict implements Runnable {
	
	private SQLiteDatabase mDb;
	private PredictHelper mPh;
	private Handler mHandler;
	private String requestStr;
	private List<String> lastDefault;
	
	public FeedPredict(Handler handler,String requestStr2,PredictHelper ph,List<String> defaultEndList) {
		
		// TODO Auto-generated constructor stub
		// WebAPIへのアクセスが終わったことを知らせるためのハンドラ
        this.mHandler = handler;
        this.mPh = ph;
        this.requestStr = requestStr2;
        this.lastDefault = defaultEndList;
        
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Message msg = new Message();
		try {
			
			
	      
	        try {
	    	 
	           mPh.createEmptyDataBase();  
	           mDb = mPh.openDataBase();
	        } catch (SQLiteException e) {
	            // ディスクフルなどでデータが書き込めない場合
	        	
	            mDb = mPh.getReadableDatabase();
	        } catch (IOException e) {
	        	throw new Error("Unable to create database");
	        }
	        
	        List<String> selection = new ArrayList<String>();
	        
	        //データ一覧を取得
	        // すべてのデータのカーソルを取得
	        Cursor c = fetchPredict(requestStr);
	        selection = setPredictList(c);
	        c.close();
			
			
			
//			for(int i=0;i<selection.size();i++){
//				Log.d("debug:", selection.get(i));
//			}
			selection.addAll(lastDefault);
			
			msg.obj = selection;
			msg.what = CodeDefine.MESSAGE_WHAT_SEARCH_PREDICT_END;
			
			
		} catch (Exception e) {
			Log.d("Exception", "except(" + e.toString() + ") called");
			// TODO: handle exception
			msg.what = CodeDefine.MESSAGE_WHAT_SEARCH_PREDICT_ERROR;
		}finally{
			if(mDb.isOpen()){
				mDb.close();
			}
	        mPh.close();
		}
		mHandler.sendMessage(msg);
		
	}

	
	private List<String> setPredictList(Cursor c) {
		List<String> selection = new ArrayList<String>();
		if(c.moveToFirst()){
			do{
				String value = c.getString(c.getColumnIndex("value"));				
				selection.add(value);				
			}while(c.moveToNext());
		}
		return selection;
	}

	private Cursor fetchPredict(String requestStr2) {
		String sqlstr = "select * from predict " +
					"where key = '"+requestStr2+"'";
		return mDb.rawQuery(sqlstr, null);
	}
}
