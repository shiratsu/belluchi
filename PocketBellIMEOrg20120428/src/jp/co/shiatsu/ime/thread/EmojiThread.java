package jp.co.shiatsu.ime.thread;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import jp.co.shiatsu.ime.conf.CodeDefine;
import jp.co.shiatsu.ime.util.HandleHttpRequest;

public class EmojiThread implements Runnable {
	
	private HandleHttpRequest hhr;
	private Handler mHandler;
	private String requestStr;
	private List<byte[]> emojiList;
	private List<String> selection;
	
	
	public EmojiThread(Handler handler,List<byte[]> emojiList2) {
		
		// TODO Auto-generated constructor stub
		// WebAPIへのアクセスが終わったことを知らせるためのハンドラ
        this.mHandler = handler;
        
        this.emojiList = emojiList2;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Message msg = new Message();
		try {
			selection = new ArrayList<String>();
			Log.d("EmojiThread","emojiSize:"+emojiList.size());
			
			for(int i=0;i<emojiList.size();i++){
				selection.add(new String(emojiList.get(i), 0, 4));
				Log.d("EmojiThread","emoji:"+emojiList.get(i));
			}
			
			msg.obj = selection;
			msg.what = CodeDefine.MESSAGE_WHAT_EMOJI_END;
			
			
		} catch (Exception e) {
			Log.d("Exception", "except(" + e.toString() + ") called");
			// TODO: handle exception
			msg.what = CodeDefine.MESSAGE_WHAT_EMOJI_ERROR;
		}
		mHandler.sendMessage(msg);
		
	}
}
