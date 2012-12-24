package jp.co.shiatsu.ime.socialime;


import java.util.List;

import org.json.JSONArray;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import android.os.AsyncTask;
import android.text.TextUtils;



public class PostEmoji extends AsyncTask<Object, Integer, String> {

	
	
	public PostEmoji() {
		
		
        
	}
	
	@Override
    protected void onPreExecute() {
       
    }
	
	@Override
	protected String doInBackground(Object... params) {
		// TODO Auto-generated method stu
		List<String> emojiSpList = (List<String>) params[0];
		
		
		SharedPreferences sp = (SharedPreferences) params[1];
		Editor e = sp.edit();
		e.putString("emojiSp", TextUtils.join(",", emojiSpList));
		e.commit();
		return null;
	}
	
	

	// 
	protected void onStopExecute() {
		
	}

	// 
	@Override
	protected void onPostExecute(String updateTime) {
		
	}
}
