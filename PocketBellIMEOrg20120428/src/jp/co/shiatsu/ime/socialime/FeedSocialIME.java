package jp.co.shiatsu.ime.socialime;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import jp.co.shiatsu.ime.conf.CodeDefine;
import jp.co.shiatsu.ime.util.HandleHttpRequest;

public class FeedSocialIME implements Runnable {
	
	private HandleHttpRequest hhr;
	private Handler mHandler;
	private String requestStr;
	
	public FeedSocialIME(Handler handler,String requestStr2) {
		
		// TODO Auto-generated constructor stub
		// WebAPIへのアクセスが終わったことを知らせるためのハンドラ
        this.mHandler = handler;
        
        this.requestStr = requestStr2;
        
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Message msg = new Message();
		try {
			hhr = new HandleHttpRequest();
			String requestUrl = CodeDefine.socialImeUrl+URLEncoder.encode(requestStr , "UTF-8");
			hhr.sendGet(requestUrl);
			
			hhr.inputStreemToString();
			hhr.splistSelectionStr();
			List<String> selection = new ArrayList<String>();
			selection = hhr.getSelection();
			
			for(int i=0;i<selection.size();i++){
				Log.d("debug:", selection.get(i));
			}
			
			msg.obj = selection;
			msg.what = CodeDefine.MESSAGE_WHAT_SEARCHIME_END;
			
			
		} catch (Exception e) {
			Log.d("Exception", "except(" + e.toString() + ") called");
			// TODO: handle exception
			msg.what = CodeDefine.MESSAGE_WHAT_SEARCHIME_ERROR;
		}
		mHandler.sendMessage(msg);
		
	}
}
