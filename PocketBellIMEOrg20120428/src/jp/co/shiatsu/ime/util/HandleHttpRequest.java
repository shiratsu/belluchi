package jp.co.shiatsu.ime.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.util.Log;

/**
 * HTTPリクエストとXMLパースを扱うクラス
 * @author shhirats
 *
 */
public class HandleHttpRequest {
	
	private static final String TAG = null;
	public List<HashMap<String,String>> resultList = new ArrayList<HashMap<String,String>>();
	public String statusCode;
	public HashMap<String,String> xmlData;
	
	private URL url;
	private InputStream is;
	private URLConnection conn = null;
	private String selectionStr;
	public List<String> selection;
	
	
	public List<HashMap<String, String>> getResultList() {
		return resultList;
	}
	public void setResultList(List<HashMap<String, String>> resultList) {
		this.resultList = resultList;
	}
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	
	/**
	 * コンストラクタ
	 * @access public
	 * @param urlStr
	 * @throws IOException
	 */
	public HandleHttpRequest(){
		
	}
	
	/**
	 * GETメソッド
	 * @access public
	 * @param urlStr
	 * @param postParam
	 * @throws IOException 
	 */
	public void sendGet(String urlStr) throws IOException{
		conn = new URL(urlStr).openConnection();
		conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
		// User Agentの設定はAndroid1.6の場合のみ必要
		conn.setRequestProperty("User-Agent", "Android");
		
	    // HTTP POSTのための設定
	    ((HttpURLConnection)conn).setRequestMethod("GET");
	    conn.setDoOutput(true);
	    // HTTP接続開始
	    conn.connect();
	    
	    // 結果受け取り
        final int responceCode = ((HttpURLConnection) conn).getResponseCode();
        Log.d(TAG, "responceCode: " + responceCode);
	    if (responceCode != HttpURLConnection.HTTP_OK) {
            throw new RuntimeException("invalid responce code, " + responceCode);
        }
        
        is = conn.getInputStream(); 
		
	}
	
	
	/**
	 * inputStreamを変換する
	 * @param in
	 * @return
	 * @throws IOException
	 */
	public void inputStreemToString() throws IOException{
        
		Log.d(TAG, "kokohadoukana");
	    BufferedReader reader = 
	        new BufferedReader(new InputStreamReader(is, "UTF-8"/* 文字コード指定 */));
	    StringBuffer buf = new StringBuffer();
	    String str;
	    while ((str = reader.readLine()) != null) {
	            buf.append(str);
	            buf.append("\n");
	    }
	    selectionStr = buf.toString();
	    Log.d(TAG, selectionStr);
	    return;
	}
	public List<String> getSelection() {
		return selection;
	}
	public void setSelection(List<String> selection) {
		this.selection = selection;
	}
	
	/**
	 * とってきたストリングを分割して、候補を作成
	 */
	public void splistSelectionStr(){
		Log.d(TAG, selectionStr);
		String[] selectionAry = selectionStr.split("\t");
		Log.d(TAG, "length:"+selectionAry.length);
		for(int i=0;i<selectionAry.length;i++){
			Log.d(TAG, selectionAry[i]);
		}
		selection = Arrays.asList(selectionAry);
		return;
	}
	
}

