package jp.co.shiatsu.ime.service;


import java.util.ArrayList;

import java.util.Arrays;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import jp.co.shiatsu.ime.PocketBellCandidateView;
import jp.co.shiatsu.ime.PocketBellEvent;
import jp.co.shiatsu.ime.R;
import jp.co.shiatsu.ime.conf.CodeDefine;
import jp.co.shiatsu.ime.helper.PredictHelper;
import jp.co.shiatsu.ime.helper.PredictWriteHelper;
import jp.co.shiatsu.ime.helper.SkkHelper;
import jp.co.shiatsu.ime.helper.SkkWriteHelper;
import jp.co.shiatsu.ime.socialime.FeedPredict;
import jp.co.shiatsu.ime.socialime.FeedSKK;
import jp.co.shiatsu.ime.socialime.FeedSocialIME;
import jp.co.shiatsu.ime.socialime.PostEmoji;
import jp.co.shiatsu.ime.socialime.PostKaomoji;
import jp.co.shiatsu.ime.socialime.PostKigo;
import jp.co.shiatsu.ime.socialime.PostPredictTask;
import jp.co.shiatsu.ime.socialime.PostSkkCount;
import jp.co.shiatsu.ime.socialime.PostSkkTask;
import jp.co.shiatsu.ime.thread.EmojiThread;
import jp.co.shiatsu.ime.value.PocketBellValue;
import jp.co.shiratsu.ime.keyboard.PocketBellKeyboard;
import jp.co.shiratsu.ime.keyboard.view.PocketBellKeyboardView;






import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;

import android.graphics.drawable.Drawable;
import android.inputmethodservice.InputMethodService;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;

import android.os.Handler;

import android.os.Message;
import android.preference.PreferenceManager;

import android.util.Log;

import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;

import android.widget.LinearLayout;

public class PocketBellIME extends InputMethodService 
	 {
	private KeyboardView keyboardView;
	private PocketBellKeyboard abKeyboard;
	private PocketBellKeyboard anotherKeyboard;
	private PocketBellKeyboard anotherKeyboard_english;
	
	private PocketBellKeyboard type4;
	private PocketBellKeyboard num_type3;
	private PocketBellKeyboard qwertyKeyboard;
	private PocketBellKeyboard qwertytype2;
	private PocketBellKeyboard qwertytype3;
	private StringBuilder composing = new StringBuilder();
	private PocketBellCandidateView candidatesView;
	private int imeOptions;
	private int mLastDisplayWidth;
	private LinearLayout mCandidateViewContainer;
	private SkkHelper sh;
	
	
	private HashMap<String,String> codeAry = PocketBellValue.codeAry;
	private HashMap<String,String> code2Ary = PocketBellValue.code2Ary;
	private HashMap<String,String> codeAnotherAry = PocketBellValue.codeAnotherAry;
	private HashMap<String,String> kanaAry = PocketBellValue.kanaAry;
	private HashMap<String,String> katakanaAry = PocketBellValue.katakanaAry;
	private List<String> defaultEndList = PocketBellValue.defaultEndList;
	private HashMap<String,String> upperMap = PocketBellValue.upperInfo;
	private HashMap<String,String> lowerMap = PocketBellValue.lowerInfo;
	private List<String> dakutenList = PocketBellValue.dakutenList;
	
	//private List<byte[]> emojiList = PocketBellValue.emojiList;
	private List<String> kaomojiList = PocketBellValue.kaomojiList;
	private List<String> kigouList = PocketBellValue.kigouList;
	private List<Integer> emojiImgList = new ArrayList<Integer>();
    private SQLiteDatabase mDb;
    private List<String> list;

	private PocketBellKeyboard currentKeyboard;
	private String prevCode;
	private String strCode;
	private String dakutenCode;
	private String str;
	private boolean portraitFlag;
	private boolean endFlag;
	private int hatenabikkuri = 0;
	private String hatenaBikkuriStr = null;
	private long mMetaState;
	
	private InputMethodManager mInputMethodManager;
	
	//キーボードの背景画像
	private Drawable keyboardBack;
	private Resources res;
	
	//qwertyのキーワード
	private String qChar;
	private String qPrevCode;
	
	private boolean mPredictionOn;
    private boolean mCompletionOn;
    private CompletionInfo[] mCompletions;
	
	//予測候補に使う変数を定義
	private String predictKey = null;
	private String predictVal = null;
    
    
	//qwertyの数字を扱う部分
	private int qwertyNum = 0;
	private boolean qwertyNumFlag = false;
	private int emojiFLag = 0;
	private String mWordSeparators;
	
	/** Size of candidates view (normal) */
    public static final int VIEW_TYPE_NORMAL = 0;
    /** Size of candidates view (full) */
    public static final int VIEW_TYPE_FULL   = 1;
    /** Size of candidates view (close/non-display) */
    public static final int VIEW_TYPE_CLOSE  = 2;
	
    /** Auto hide candidate view */
    protected boolean mAutoHideMode = true;
    
    /** IME's status for {@code mStatus} input/no candidates). */
    private static final int STATUS_INIT            = 0x0000;
    
    /** IME's status */
    protected int mStatus = STATUS_INIT;
    
    /** IME's status for {@code mStatus}(all candidates are displayed). */
    private static final int STATUS_CANDIDATE_FULL  = 0x0010;
    
    private SharedPreferences sp;
    private List<String> emojiSpList = new ArrayList<String>();
    private List<String> kaomojiSpList = new ArrayList<String>();
    private List<String> kigouSpList = new ArrayList<String>();
    private static final String emojiSpKey = "emojiSp";
    private static final String kaoSpKey = "kaomojiSp";
    private static final String kigouSpKey = "kigouSp";
    
    private boolean kigouFlag = false;
    
    /** Flag for checking if the previous down key event is consumed by OpenWnn  */
	private boolean mConsumeDownEvent;
	
	private boolean candidateViewShown;
    
    /**
     * Constructor
     */
    public PocketBellIME() {
		// TODO Auto-generated constructor stub
    	candidatesView = new PocketBellCandidateView(-1);
    	
    	

	} 

	private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
        	switch (msg.what) {
				case CodeDefine.MESSAGE_WHAT_SEARCHIME_END:
					list = (List) msg.obj;
//		        	Log.d("POSTSQKKKKKKK", "SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS");
		        	postSkk();
//		        	list.add(str);
//		        	if(katakanaAry.containsKey(str)){
//		        		String katakana = katakanaAry.get(str);
//		        		list.add(katakana);
//		        	}
		            if(list != null){
		            	setCandidatesViewShown(true);
		            	candidateViewShown=true;
		            	updateCandidate();
		            }
					break;
				case CodeDefine.MESSAGE_WHAT_SEARCH_PREDICT_END:
					list = (List) msg.obj;
//					Log.d("PREDICTTTTTTTTT", "PREDICTTTTTTTTT");
					if(list != null){
						setCandidatesViewShown(true);
						candidateViewShown=true;
		            	updateCandidate();
						
					}else{
//						Log.d("PREDICTTTTTTTTT", "mmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm");
						setEnd();
					}
					postPredict();
					break;
				default:
					break;
			}
        	
        }

		
    };
    
    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override public void onCreate() {
        super.onCreate();
        mInputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
        mWordSeparators = getResources().getString(R.string.word_separators);
        
        sp = getSharedPreferences("pocketbell",MODE_WORLD_READABLE|MODE_WORLD_WRITEABLE);
    	
    	String emoji = sp.getString("emojiSp", null);
    	String kaomoji = sp.getString("kaomojiSp", null);
    	String kigou = sp.getString("kigouSp", null);
    	if(emoji != null && !"".equals(emoji)){
    		emojiSpList =  new ArrayList(Arrays.asList(emoji.split(",")));
    	}
    	if(kaomoji != null && !"".equals(kaomoji)){
    		
    		kaomojiSpList =  new ArrayList(Arrays.asList(kaomoji.split(",")));
    	}
		if(kigou != null && !"".equals(kigou)){
			
			kigouSpList =  new ArrayList(Arrays.asList(kigou.split(",")));
		}
		kigouFlag = false;
    }
	
    /**
     * predictテーブルにデータを投入
     */
    protected void postPredict() {
		// TODO Auto-generated method stub
    	PredictWriteHelper ph = new PredictWriteHelper(this);
    	PostPredictTask task = new PostPredictTask();
		task.execute(ph,predictKey,predictVal);
		predictKey = predictVal;
	}

	/**
     * こんどはSocialIMEからデータを取ってきていれる
     */
    private void postSkk() {
		// TODO Auto-generated method stub
    	String start = composing.toString();
//		Log.d("PostSkk","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!"+start);
		SkkWriteHelper swh = new SkkWriteHelper(this);
		
		PostSkkTask task = new PostSkkTask();
		task.execute(swh,start);
	}
    


	@Override
	public void onInitializeInterface() {
		if (qwertyKeyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }
		
		qwertyKeyboard = new PocketBellKeyboard(this, R.xml.qwerty);
		qwertytype2 = new PocketBellKeyboard(this, R.xml.qwerty_another);
		qwertytype3 = new PocketBellKeyboard(this, R.xml.qwerty_type3);
		abKeyboard = new PocketBellKeyboard(this, R.xml.abkeyboard);
		anotherKeyboard = new PocketBellKeyboard(this, R.xml.anotherkeyboard_english);
		
		type4 = new PocketBellKeyboard(this, R.xml.type4);
		num_type3 = new PocketBellKeyboard(this, R.xml.num_type3);
		kigouFlag = false;
	}



	
	/**
	 * 絵文字を扱う
	 * @param checkCode 
	 */
	private void handleEmoji(String checkCode) {
		
		kigouFlag = true;
		// TODO Auto-generated method stub
		final int length = composing.length();
		if(length > 0){
//			getCurrentInputConnection().commitText(composing,
//				composing.length());
			getCurrentInputConnection().commitText(composing,
					1);
			composing.delete(0, composing.length());
		}
		hatenaBikkuriStr=null;
		// set byte code
//		Log.d("emojiFLag", "emojiFLag:"+emojiFLag);
		
		// CandidatesViewを表示
		//setCandidatesViewShown(false);
		candidatesView.setEmojiFlag(false);
		selection = new ArrayList<String>();
		switch (emojiFLag) {
			case 0:
				
				candidatesView.setEmojiFlag(true);
				emojiFLag = 1;
//				Log.d("emojiFLag2", "emojiFLag2:"+emojiFLag);
				setCandidatesViewShown(true);
				candidateViewShown=true;
				// 選択肢の更新
				//new Thread(new EmojiThread(mHandler,emojiList)).start();
				
				selection.addAll(emojiSpList);
				Collections.reverse(selection);
				selection.addAll(PocketBellValue.emojiStringList);
//	    		selection = PocketBellValue.emojiStringList;
	    		// CandidatesViewの選択肢を更新
	    		candidatesView.setCandidates(selection);
//	    		candidatesView.setEmojiList(emojiImgList);
				break;
	
			case 1:
				emojiFLag = 2;
//				Log.d("emojiFLag2", "emojiFLag2:"+emojiFLag);
//				selection = kaomojiList;
				selection.addAll(kaomojiSpList);
				Collections.reverse(selection);
				selection.addAll(kaomojiList);
				setCandidatesViewShown(true);
				candidateViewShown=true;
				// CandidatesViewの選択肢を更新
				candidatesView.setCandidates(selection);
//				candidatesView.setEmojiList(null);
				break;
			case 2:
				
				emojiFLag = 0;
//				Log.d("emojiFLag2", "emojiFLag2:"+emojiFLag);
//				selection = kigouList;
				selection.addAll(kigouSpList);
				Collections.reverse(selection);
				selection.addAll(kigouList);
				setCandidatesViewShown(true);
				candidateViewShown=true;
				// CandidatesViewの選択肢を更新
				candidatesView.setCandidates(selection);
//				candidatesView.setEmojiList(null);
				break;
		}
//		Log.d("emojiFLag3", "emojiFLag2:"+emojiFLag);
		
	}
	
	/**
	 * 横置きモードで数字を扱う
	 */
	protected void qwertyNumHandle() {
		// TODO Auto-generated method stub
		prevCode=null;
		str=null;
		
		switch (qwertyNum) {
			
			case 1:
				qwertyNumFlag=true;
				
				qwertyNum=2;
				break;
			case 2:
				
				qwertyNum=3;
				break;	
			case 3:
				
				qwertyNum=4;
				break;
			case 4:
				
				qwertyNum=5;
				break;		
			case 5:
				
				qwertyNum=6;
				break;
			case 6:
				
				qwertyNum=7;
				break;
			case 7:
				
				qwertyNum=8;
				break;
			case 8:
				
				qwertyNum=9;
				break;
			case 9:
				
				qwertyNum=0;
				break;
			case 0:
				
				qwertyNum=1;
				break;	
			default:
				break;
		}
		handleQuertyNum(qwertyNum);
		
	}

	/**
	 * 123456789の場合の処理
	 * @param qwertyNum2
	 */
	private void handleQuertyNum(int qwertyNum2) {
		// TODO Auto-generated method stub
		
		//フラグによって処理をわける
		if(qwertyNumFlag == true){
			
			final int length = composing.length();
	        if (length > 0) {
	        	composing.delete(length - 1, length);
	        }
			
		}
		composing.append(String.valueOf(qwertyNum2));
        getCurrentInputConnection().setComposingText(composing, 1);
		
	}

	/**
	 * カナに変換
	 * @param primaryCode
	 * @param keyCodes
	 */
	protected void handleCharacterqwerty(int primaryCode, int[] keyCodes) {
		// TODO Auto-generated method stub
		char checkCode = (char) primaryCode;
		Log.d("qwertytype2", "check:"+checkCode);
		Log.d("qPrevCode", "qPrevCode:"+qPrevCode);
		Log.d("primaryCode", "primaryCode"+primaryCode);
		
		//事前コードが入っていたら
		if(qPrevCode != null){
			
			final int prevlength = qPrevCode.length();
			
			//コードが事前コードと一致したら
			if(!qPrevCode.equals(String.valueOf(checkCode))){
				//連結
				qChar = qPrevCode+String.valueOf(checkCode);
				
				//照合してあった場合
				if(kanaAry.containsKey(qChar)){
					str = kanaAry.get(qChar);	
					if(str != null){
						
						final int length = composing.length();
						composing.delete(length - prevlength, length);
						
						appendToComposing(str);
					}
					qPrevCode = null;
					qChar = null;
				
				//照合してなかった場合	
				}else{
					qPrevCode = qPrevCode+String.valueOf(checkCode);
					//3文字に達していたら、qPrevCodeから削除
					int length = qPrevCode.length();
					if(length == 3){
						qPrevCode = null;
					}
					composing.append(checkCode);
					InputConnection ic = getCurrentInputConnection();
//					ic.setComposingText(composing, composing.length());
					ic.setComposingText(composing, 1);
				}
			//照合結果一致しなかったら	
			}else{
				final int length = composing.length();
				if(length >= prevlength){
					composing.delete(length - prevlength, length);
				}
				if(primaryCode == 110){
					String addStr = "ん"+String.valueOf(checkCode);
					str = String.valueOf(checkCode);
					
					appendToComposing(addStr);
				}else{
					String addStr = "っ"+String.valueOf(checkCode);
					str = String.valueOf(checkCode);
					
					appendToComposing(addStr);
				}
				
			}
			
			
		}else if(primaryCode == 46 || 
				 primaryCode == 44 || 
				 primaryCode == 49 || 
				 primaryCode == 50 ||
				 primaryCode == 51 || 
				 primaryCode == 52 || 
				 primaryCode == 53 || 
				 primaryCode == 54 || 
				 primaryCode == 55 || 
				 primaryCode == 56 || 
				 primaryCode == 57 || 
				 primaryCode == 48 ||
				 primaryCode == 33 ||
				 primaryCode == 63
				 ){
			composing.append((char) primaryCode);
	        getCurrentInputConnection().setComposingText(composing, 1);
	        qPrevCode = null;
			qChar = null;
		}else{
			qPrevCode = String.valueOf(checkCode);
			
			//とりあえず照合
			if(kanaAry.containsKey(qPrevCode)){
				str = kanaAry.get(qPrevCode);
				
				
				if(str != null){
					appendToComposing(str);
				}
				qPrevCode = null;
				qChar = null;
			}else{
				composing.append(checkCode);
				InputConnection ic = getCurrentInputConnection();
//				ic.setComposingText(composing, composing.length());
				ic.setComposingText(composing, 1);
			}
			
		}
		
		
	}

	/**
	 * びっくりまーくかはてな
	 */
	protected void handleBikkuriHatenaHankaku() {
		// TODO Auto-generated method stub
		final int length = composing.length();
		
		String tmpStr = null;
		switch (hatenabikkuri) {
			case 0:
				tmpStr = "?";
				hatenabikkuri = 1;
				break;
	
			case 1:
				tmpStr = "!";
				hatenabikkuri = 2;
				break;
		}
		if(hatenaBikkuriStr != null){
			if(length >= 1){
				composing.delete(length - 1, length);
			
			}else{
				appendToComposing(tmpStr);
				hatenaBikkuriStr=tmpStr;
			}
		}else{
			appendToComposing(tmpStr);
			hatenaBikkuriStr=tmpStr;
		}
		
		
	
	}
	
	/**
	 * びっくりまーくかはてな
	 */
	protected void handleBikkuriHatenaZenkaku() {
		// TODO Auto-generated method stub
		final int length = composing.length();
		String tmpStr = null;
		switch (hatenabikkuri) {
			case 0:
				tmpStr = "？";
				hatenabikkuri = 1;
				break;
	
			case 1:
				tmpStr = "！";
				hatenabikkuri = 2;
				break;
		}
		
		if(hatenaBikkuriStr != null){
			if(length >= 1){
				composing.delete(length - 1, length);
			
			}else{
				appendToComposing(tmpStr);
				hatenaBikkuriStr=tmpStr;
			}
		}else{
			appendToComposing(tmpStr);
			hatenaBikkuriStr=tmpStr;
		}
	}

	/**
	 * qwertyキーボードの扱い
	 * @param primaryCode
	 * @param keyCodes
	 */
	protected void handleCharacter(int primaryCode, int[] keyCodes) {
		// TODO Auto-generated method stub
		composing.append((char) primaryCode);
        getCurrentInputConnection().setComposingText(composing, 1);
        char tmp = (char) primaryCode;
        str = String.valueOf(tmp);
		
	}

	/**
	 * 大文字→小文字、小文字→大文字
	 */
	protected void handleUpper() {
		// TODO Auto-generated method stub
		final int length = composing.length();
Log.d("length","**********************str*******************************:"+str);			
Log.d("length","**********************length*******************************:"+length);		
		if("か".equals(str) || "き".equals(str)	|| "く".equals(str) || "け".equals(str) || "こ".equals(str)
		|| "さ".equals(str) || "し".equals(str)  || "す".equals(str) || "せ".equals(str) || "そ".equals(str)
		|| "た".equals(str) || "ち".equals(str)  || "つ".equals(str) || "て".equals(str) || "と".equals(str)
		|| "は".equals(str) || "ひ".equals(str)  || "ふ".equals(str) || "へ".equals(str) || "ほ".equals(str)
		|| "が".equals(str) || "ぎ".equals(str)  || "ぐ".equals(str) || "げ".equals(str) || "ご".equals(str)
		|| "ざ".equals(str) || "じ".equals(str)  || "ず".equals(str) || "ぜ".equals(str) || "ぞ".equals(str)
		|| "だ".equals(str) || "ぢ".equals(str)  || "づ".equals(str) || "で".equals(str) || "ど".equals(str)
		|| "ば".equals(str) || "び".equals(str)  || "ぶ".equals(str) || "べ".equals(str) || "ぼ".equals(str)
		|| "ぱ".equals(str) || "ぴ".equals(str)  || "ぷ".equals(str) || "ぺ".equals(str) || "ぽ".equals(str)
		){
			if (length > 1) {
	            composing.delete(length - 1, length);
	            handleDakuten();
	            
	            appendToComposing(str);
	        } else if (length > 0) {
	        	composing.setLength(0);
	        	handleDakuten();
	        	
	            appendToComposing(str);
	        }
			
		}else{
			if(!"ぎゃ".equals(str) && !"ぎゅ".equals(str) && !"ぎょ".equals(str)
			&& !"じゃ".equals(str) && !"じゅ".equals(str) && !"じょ".equals(str)		
			){
				if (length > 1) {
		            composing.delete(length - 1, length);
		            upperLower();
		            
		            appendToComposing(str);
		        } else if (length > 0) {
		        	composing.setLength(0);
		        	upperLower();
		            appendToComposing(str);
		        }
			}
			
		}
        
	}

	/**
	 * 濁点を扱う
	 */
	private void handleDakuten() {
		// TODO Auto-generated method stub
		if("か".equals(str) || "き".equals(str)	|| "く".equals(str) || "け".equals(str) || "こ".equals(str)){
			if("か".equals(str)){
				str = "が";
			}else if("き".equals(str)){
				str = "ぎ";
			}else if("く".equals(str)){
				str = "ぐ";
			}else if("け".equals(str)){
				str = "げ";
			}else if("こ".equals(str)){
				str = "ご";		
			}
		}else if("さ".equals(str) || "し".equals(str)  || "す".equals(str) || "せ".equals(str) || "そ".equals(str)){
			if("さ".equals(str)){
				str = "ざ";
			}else if("し".equals(str)){
				str = "じ";
			}else if("す".equals(str)){
				str = "ず";	
			}else if("せ".equals(str)){
				str = "ぜ";		
			}else if("そ".equals(str)){
				str = "ぞ";	
			}
		}else if("た".equals(str) || "ち".equals(str)  || "つ".equals(str) || "て".equals(str) || "と".equals(str)){
			if("た".equals(str)){
				str = "だ";
			}else if("ち".equals(str)){
				str = "ぢ";
			}else if("つ".equals(str)){
				str = "っ";
			}else if("て".equals(str)){
				str = "で";
			}else if("と".equals(str)){
				str = "ど";		
			}
		}else if("は".equals(str) || "ひ".equals(str)  || "ふ".equals(str) || "へ".equals(str) || "ほ".equals(str)){
			if("は".equals(str)){
				str = "ば";
			}else if("ひ".equals(str)){
				str = "び";	
			}else if("ふ".equals(str)){
				str = "ぶ";
			}else if("へ".equals(str)){
				str = "べ";
			}else if("ほ".equals(str)){
				str = "ぼ";	
			}
		}else if("が".equals(str) || "ぎ".equals(str)  || "ぐ".equals(str) || "げ".equals(str) || "ご".equals(str)){
			if("が".equals(str)){
				str = "か";	
			}else if("ぎ".equals(str)){
				str = "き";
			}else if("ぐ".equals(str)){
				str = "く";
			}else if("げ".equals(str)){
				str = "け";
			}else if("ご".equals(str)){
				str = "こ";	
			}
		}else if("ざ".equals(str) || "じ".equals(str)  || "ず".equals(str) || "ぜ".equals(str) || "ぞ".equals(str)){
			if("ざ".equals(str)){
				str = "さ";
			}else if("じ".equals(str)){
				str = "し";
			}else if("ず".equals(str)){
				str = "す";	
			}else if("ぜ".equals(str)){
				str = "せ";
			}else if("ぞ".equals(str)){
				str = "そ";		
			}
		}else if("だ".equals(str) || "ぢ".equals(str)  || "で".equals(str) || "ど".equals(str)){
			if("だ".equals(str)){
				str = "た";	
			}else if("ぢ".equals(str)){
				str = "ち";
			
			}else if("で".equals(str)){
				str = "て";
			}else if("ど".equals(str)){
				str = "と";		
			}
		}else if("ば".equals(str) || "び".equals(str)  || "ぶ".equals(str) || "べ".equals(str) || "ぼ".equals(str)){
			if("ば".equals(str)){
				str = "ぱ";
			}else if("び".equals(str)){
				str = "ぴ";
			}else if("ぶ".equals(str)){
				str = "ぷ";
			}else if("べ".equals(str)){
				str = "ぺ";
			}else if("ぼ".equals(str)){
				str = "ぽ";	
			}
		}else if("ぱ".equals(str) || "ぴ".equals(str)  || "ぷ".equals(str) || "ぺ".equals(str) || "ぽ".equals(str)){
			if("ぱ".equals(str)){
				str = "は";
			}else if("ぴ".equals(str)){
				str = "ひ";
			}else if("ぷ".equals(str)){
				str = "ふ";
			}else if("ぺ".equals(str)){
				str = "へ";
			}else if("ぽ".equals(str)){
				str = "ほ";	
			}
		}else if("づ".equals(str)){
			str = "つ";
		}else if("っ".equals(str)){
			str = "づ";	
		}
		
	}

	/**
	 * 大きく小さく
	 */
	private void upperLower() {
		// TODO Auto-generated method stub
		if(str != null){
			if(upperMap.containsKey(str)){
				str = upperMap.get(str);
			}else if(lowerMap.containsKey(str)){
				str = lowerMap.get(str);
			}else{
				//とりあえず、大きく出来るか確認
				String upperStr = str.toUpperCase();
	        	
				//同じなら小さくする
	        	if(str.equals(upperStr)){
	        		Log.d("str","ここだよね？？");
	        		str = upperStr.toLowerCase();
	            }else{
	            	str = upperStr;
	            }
			}
		}
		
	}

	private void handleBackspace() {
		Log.d("DEL:","del");
		final int length = composing.length();
        if (length > 1) {
            composing.delete(length - 1, length);
            getCurrentInputConnection().setComposingText(composing, 1);
            //appendToComposing(composing.toString());
        } else if (length > 0) {
        	composing.setLength(0);
            getCurrentInputConnection().commitText("", 0);
            hatenaBikkuriStr=null;
            //文字が０文字になるので、確定したのと同じ扱い
            qwertyNumFlag=false;
            emojiFLag = 0;
            //appendToComposing(composing.toString());
        } else {
            keyDownUp(KeyEvent.KEYCODE_DEL);
        }
        
        String searchStr = composing.toString();
        if(searchStr != null && !"".equals(searchStr)){
	        sh = new SkkHelper(this);
			// 選択肢の更新
	        endFlag = false;
			new Thread(new FeedSKK(mHandler,searchStr,sh)).start();
	        updateShiftKeyState(getCurrentInputEditorInfo());
        }
        //削除なのでフラグ系は一旦空、またはfalseにする
        qPrevCode=null;
        prevCode=null;
        qwertyNumFlag=false;
    }
	
	private void updateShiftKeyState(EditorInfo currentInputEditorInfo) {
		// TODO Auto-generated method stub
		
	}

	/**
     * Helper to send a key down / key up pair to the current editor.
     */
    private void keyDownUp(int keyEventCode) {
    	Log.d("KeyEvent","keyEventCode:"+keyEventCode);
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_DOWN, keyEventCode));
        
        getCurrentInputConnection().sendKeyEvent(
                new KeyEvent(KeyEvent.ACTION_UP, keyEventCode));
    	
        
    }
    
    /**
     * エンターボタンで確定する処理
     */
    private void kakutei(){
    	getCurrentInputConnection().commitText(composing,
				1);
		composing.delete(0, composing.length());
		
		// 選択肢を非表示
		setCandidatesViewShown(false);
		candidateViewShown=false;
		hatenaBikkuriStr=null;
		emojiFLag = 0;
		candidatesView.setEmojiFlag(false);
		str = null;
		prevCode = null;
		dakutenCode = null;
    }
	
	@Override
	public View onCreateInputView() {
//		Log.d("pocketbellime", "onCreateInputView()");
		kigouFlag = false;
		res = getResources();
		// ウィンドウマネージャのインスタンス取得
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		// ディスプレイのインスタンス生成
		Display disp = wm.getDefaultDisplay();
		int width = disp.getWidth();
		int height = disp.getHeight();
		
		Log.d("pocketbellime", "width:"+width);
		Log.d("pocketbellime", "height:"+height);
		kakutei();
		
		keyboardView = (PocketBellKeyboardView) getLayoutInflater().inflate(
				R.layout.keyboard, null);

		keyboardView.setOnKeyboardActionListener(listener);
		if(width < height){
			keyboardBack = res.getDrawable(R.drawable.backgr);
			keyboardView.setBackgroundDrawable(keyboardBack);
			keyboardView.setKeyboard(abKeyboard);
		}else{
//			Log.d("pocketbellime", "qwerty");
			keyboardBack = res.getDrawable(R.drawable.backmode1_3);
			keyboardView.setBackgroundDrawable(keyboardBack);
			keyboardView.setKeyboard(qwertyKeyboard);
		}
		
		return keyboardView;
	}

	@Override
	public void onStartInputView(EditorInfo info, boolean restarting) {
		kigouFlag = false;
		// ウィンドウマネージャのインスタンス取得
		WindowManager wm = (WindowManager)getSystemService(WINDOW_SERVICE);
		// ディスプレイのインスタンス生成
		Display disp = wm.getDefaultDisplay();
		int width = disp.getWidth();
		int height = disp.getHeight();
		prevCode = null;
		if(width < height){
			currentKeyboard = abKeyboard;
		}else{
			currentKeyboard = qwertyKeyboard;
		}
		
		prevCode = null;
		strCode = null;
		dakutenCode = null;
		str = null;
		portraitFlag = true;
		
		hatenabikkuri = 0;
		hatenaBikkuriStr = null;
		
		//qwertyのキーワード
		qChar = null;
		qPrevCode = null;
		
		
		//予測候補に使う変数を定義
		predictKey = null;
		predictVal = null;
	    
	    
		//qwertyの数字を扱う部分
		qwertyNum = 0;
		qwertyNumFlag = false;
		emojiFLag = 0;
		mWordSeparators = null;

        // 開始時に得た情報からキーボードの見た目を変更
		imeOptions = info.imeOptions;
		//updateCurrentKeyboardView();

		keyboardView.setKeyboard(currentKeyboard);
		
		final int length = composing.length();
//		Log.d("aaaaaaaaaaaaaaaaaaa","*************length:"+length+";;;;;;;;;;;;;;");
		if (length > 1) {
            composing.delete(length - 1, length);
            
        } else if (length > 0) {
        	composing.setLength(0);
        	
        }
		composing.delete(0, composing.length());
		//currentKeyboard.setShifted(true);
		//keyboardView.setSubtypeOnSpaceKey(subtype);
	}


	
	private List<String> selection = new ArrayList<String>();

	private void updateCandidate() {
		
		
		selection = new ArrayList<String>();
		selection = list;
		
		// CandidatesViewの選択肢を更新
		candidatesView.setCandidates(selection);
	}

	@Override
	public View onCreateCandidatesView() {

//		mCandidateViewContainer = (LinearLayout) getLayoutInflater()
//        .inflate(R.layout.candidates, null);
//		//mCandidateViewContainer.initViews();
//		candidatesView = (PocketBellCandidateView) mCandidateViewContainer.findViewById(R.id.candidatesListView);
//
//		candidatesView.setPbi(this);
//
//		//setCandidatesViewShown(true);
//		return mCandidateViewContainer;
		WindowManager wm = (WindowManager)getSystemService(Context.WINDOW_SERVICE);
		View view = candidatesView.initView(this,
                wm.getDefaultDisplay().getWidth(),
                wm.getDefaultDisplay().getHeight());
		candidatesView.setViewType(VIEW_TYPE_NORMAL);
		return view;
	}

	public void onCandidateSelect(String str) {
//		getCurrentInputConnection().commitText(str, str.length());
		getCurrentInputConnection().commitText(str, 1);
		composing.delete(0, composing.length());
		
		setCandidatesViewShown(false);
		candidateViewShown=false;
		hatenaBikkuriStr=null;
		candidatesView.setEmojiFlag(false);
		emojiFLag = 0;
		
	}

	private void appendToComposing(String str) {
		if(str != null){
			composing.append(str);
			InputConnection ic = getCurrentInputConnection();
//			ic.setComposingText(composing, composing.length());
			ic.setComposingText(composing, 1);
		}
		

		// 設定から候補表示を行うかどうかを取得
		SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
		boolean showSugestion = sp.getBoolean("show_suggestions", true);
		
		String searchStr = composing.toString();

		if (showSugestion) {
			// CandidatesViewを表示
			if(searchStr != null){
				
				sh = new SkkHelper(this);
				// 選択肢の更新
				endFlag = true;
				new Thread(new FeedSKK(mHandler,searchStr,sh)).start();
			}
			
		}
	}

	@Override
	public void onFinishInput() {
		super.onFinishInput();
		if(composing != null){
			int length = composing.length();
			if(length > 0){
				getCurrentInputConnection().commitText(composing,
						1);
				composing.delete(0, composing.length());
			}
			
		}
		
		selection = new ArrayList<String>();
		Log.d("finishfinish","finishfinish");
		setCandidatesViewShown(false);
		candidateViewShown=false;
		hatenaBikkuriStr=null;
		candidatesView.setEmojiFlag(false);
		hideWindow();
	}
	
	
	
	/**
     * Helper to determine if a given character code is alphabetic.
     */
    private boolean isAlphabet(int code) {
        if (Character.isLetter(code)) {
            return true;
        } else {
            return false;
        }
    }

    private KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {

		public void onKey(int primaryCode, int[] keyCodes) {
			kigouFlag = false;
			if(currentKeyboard == abKeyboard 
					|| currentKeyboard == anotherKeyboard 
					|| currentKeyboard == num_type3 
					|| currentKeyboard == type4){
						
//						Log.d("primaryCode", "primaryCode"+primaryCode);
						if (primaryCode == -100) {
//							
							kakutei();
						} else if (primaryCode == Keyboard.KEYCODE_DELETE) {
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							emojiFLag = 0;
							handleBackspace();
						} else if (primaryCode == Keyboard.KEYCODE_MODE_CHANGE) {
							// TODO Auto-generated method stub
							final int length = composing.length();
							if(length > 0){
//								getCurrentInputConnection().commitText(composing,
//									composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								composing.delete(0, composing.length());
								
							}
							// キーボードを変更
							if (currentKeyboard == abKeyboard) {
								currentKeyboard = anotherKeyboard;
								//currentKeyboard = type3;
							}else if(currentKeyboard == anotherKeyboard){
								currentKeyboard = num_type3;
							}else if(currentKeyboard == num_type3){
								currentKeyboard = type4;	
							} else {
								currentKeyboard = abKeyboard;
							}
							emojiFLag = 0;
							//Log.d("currentKeyboard:", "currentKeyboard"+currentKeyboard);
							//updateCurrentKeyboardView();
							keyboardView.setKeyboard(currentKeyboard);
							//Log.d("currentKeyboard:", "currentKeyboard"+currentKeyboard);
							currentKeyboard.setShifted(true);
							//Log.d("currentKeyboard:", "currentKeyboard"+currentKeyboard);
							candidatesView.setEmojiFlag(false);
							//Log.d("currentKeyboard:", "currentKeyboard"+currentKeyboard);
							str = null;
							prevCode = null;
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							Log.d("currentKeyboard:", "currentKeyboard"+currentKeyboard);

						} else if (primaryCode == PocketBellKeyboard.KEYCODE_OPTION) {
							str = null;
							prevCode = null;
							emojiFLag = 0;
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							// オプション画面の代わりにIME選択画面を表示
							InputMethodManager manager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
							manager.showInputMethodPicker();
						} else if (primaryCode == 92) {
							// TODO Auto-generated method stub
							final int length = composing.length();
							if(length > 0){
//								getCurrentInputConnection().commitText(composing,
//									composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								
								composing.delete(0, composing.length());
							}
							emojiFLag = 0;
							str = null;
							prevCode = null;
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							getCurrentInputConnection().sendKeyEvent(
					                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
						} else if (primaryCode == 93) {
							// TODO Auto-generated method stub
							final int length = composing.length();
							if(length > 0){
//								getCurrentInputConnection().commitText(composing,
//									composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								composing.delete(0, composing.length());
								
							}
							str = null;
							prevCode = null;
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							getCurrentInputConnection().sendKeyEvent(
					                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
						} else if (primaryCode == -111) {
							// TODO Auto-generated method stub
							final int length = composing.length();
							if(length > 0){
//								getCurrentInputConnection().commitText(composing,
//									composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								composing.delete(0, composing.length());
								
							}
							emojiFLag = 0;
							str = null;
							prevCode = null;
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							getCurrentInputConnection().sendKeyEvent(
					                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));	
						} else if (primaryCode == 400) {
							// TODO Auto-generated method stub
							final int length = composing.length();
							if(length > 0){
//								getCurrentInputConnection().commitText(composing,
//									composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								composing.delete(0, composing.length());
								
							}
							emojiFLag = 0;
							str = null;
							prevCode = null;
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							getCurrentInputConnection().sendKeyEvent(
					                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));		
								
						} else if (primaryCode == 99) {
							emojiFLag = 0;
							str = null;
							prevCode = null;
//							getCurrentInputConnection().commitText(composing,
//									composing.length());
							
							getCurrentInputConnection().commitText(composing,
									1);
							composing.delete(0, composing.length());
//							composing.delete(0, 1);
							sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
							setCandidatesViewShown(false);
							candidateViewShown=false;
							hatenaBikkuriStr=null;
							emojiFLag = 0;
							candidatesView.setEmojiFlag(false);
						} else if (primaryCode == 95) {
							/*
							getCurrentInputConnection().commitText(composing,
									composing.length());
							composing.delete(0, composing.length());
							sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
							*/
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							emojiFLag = 0;
							handleUpper();
						} else if (primaryCode == -215) {
							// TODO Auto-generated method stub
							final int length = composing.length();
							if(length > 0){
//								getCurrentInputConnection().commitText(composing,
//									composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								composing.delete(0, composing.length());
//								composing.delete(0, 1);
							}
							emojiFLag = 0;
							str = null;
							prevCode = null;
							// 選択肢を非表示
							setCandidatesViewShown(false);
							candidateViewShown=false;
							getCurrentInputConnection().sendKeyEvent(
					                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
						} else if (primaryCode == 150) {
							str = null;
							prevCode = null;
//							//絵文字
							endFlag = false;
							String checkCode = String.valueOf(primaryCode);
							if("150".equals(checkCode)){
								handleEmoji(checkCode);
							}
						} else if (primaryCode == 91) {
							
//							// 選択肢を非表示
//							setCandidatesViewShown(false);
//							getCurrentInputConnection().sendKeyEvent(
//					                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_CLEAR));
//							if(str != null){
//								appendToComposing(str);
//							}else{
//								appendToComposing(null);
//							}
							appendToComposing(null);
//							setCandidatesViewShown(true);
							str = null;
							prevCode = null;
							emojiFLag = 0;
						} else if (primaryCode == 94) {
							if(currentKeyboard == type4){
								Intent new_intent = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
								new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								startActivity(new_intent);
							}
						} else {
							
							if(currentKeyboard == abKeyboard){
								

								String checkCode = String.valueOf(primaryCode);
								
								handleCharacter(checkCode,primaryCode);
								

							}else if(currentKeyboard == anotherKeyboard){

								int length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
//									composing.delete(0, 1);
								}
								String checkCode = String.valueOf(primaryCode);
								str = codeAry.get(checkCode);
								Log.d("currentKeyboard:", "str:"+str);
								composing.append(str);
								InputConnection ic = getCurrentInputConnection();
								ic.setComposingText(composing, composing.length());
//								ic.setComposingText(composing, 1);
								
								
							}else if(currentKeyboard == num_type3){
								
								composing.append((char) primaryCode);
						        getCurrentInputConnection().setComposingText(composing, 1);
//						        getCurrentInputConnection().commitText(composing,
//										composing.length());
						        getCurrentInputConnection().commitText(composing,
						        		1);
								composing.delete(0, composing.length());
//								
							}else{
//								String checkCode = String.valueOf(primaryCode);
//								if("150".equals(checkCode)){
//									handleEmoji(checkCode);
//								}
							}
							
						}
					//qwertyのキーボード	
					}else{
						int length = 0;
						switch (primaryCode) {
							case Keyboard.KEYCODE_DELETE:
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								handleBackspace();
								break;
							
							case Keyboard.KEYCODE_MODE_CHANGE:
								// TODO Auto-generated method stub
								str=null;
								qPrevCode=null;
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								// キーボードを変更
								if (currentKeyboard == qwertyKeyboard) {
									keyboardBack = res.getDrawable(R.drawable.backmode1_3);
									keyboardView.setBackgroundDrawable(keyboardBack);
									keyboardView.setKeyboard(qwertytype2);
									currentKeyboard = qwertytype2;
								}else if(currentKeyboard == qwertytype2){
									keyboardBack = res.getDrawable(R.drawable.backmode2);
									keyboardView.setBackgroundDrawable(keyboardBack);
									keyboardView.setKeyboard(qwertytype3);
									currentKeyboard = qwertytype3;
								} else {
									keyboardBack = res.getDrawable(R.drawable.backmode1_3);
									keyboardView.setBackgroundDrawable(keyboardBack);
									keyboardView.setKeyboard(qwertyKeyboard);
									currentKeyboard = qwertyKeyboard;
								}
								
								//updateCurrentKeyboardView();
								keyboardView.setKeyboard(currentKeyboard);
								currentKeyboard.setShifted(true);
								break;	
								
							case 92:
								str=null;
								qPrevCode=null;
								// TODO Auto-generated method stub
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								getCurrentInputConnection().sendKeyEvent(
						                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_LEFT));
								break;
							case 93:
								str=null;
								qPrevCode=null;
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								getCurrentInputConnection().sendKeyEvent(
						                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_RIGHT));
								break;
							
							case -111:
								str=null;
								qPrevCode=null;
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								getCurrentInputConnection().sendKeyEvent(
						                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_UP));
								break;	
							case -112:
								str=null;
								qPrevCode=null;
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								getCurrentInputConnection().sendKeyEvent(
						                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DPAD_DOWN));
								break;
							
							//大文字→小文字、小文字→大文字
							case 95:
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								handleUpper();
								break;
							
							//改行
							case -99:
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
//								getCurrentInputConnection().commitText(composing,
//										composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								composing.delete(0, composing.length());
								sendDownUpKeyEvents(KeyEvent.KEYCODE_ENTER);
								hatenaBikkuriStr=null;
								qwertyNumFlag=false;
								qPrevCode=null;
								str=null;
								emojiFLag = 0;
								break;
							case -215:
								str=null;
								qPrevCode=null;
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								getCurrentInputConnection().sendKeyEvent(
						                new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_SPACE));
								break;
							
							//確定	
							case -100:
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
//								getCurrentInputConnection().commitText(composing,
//										composing.length());
								getCurrentInputConnection().commitText(composing,
										1);
								composing.delete(0, composing.length());
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								hatenaBikkuriStr=null;
								qwertyNumFlag=false;
								qPrevCode=null;
								emojiFLag = 0;
								str=null;
								break;

							case 201:
								str=null;
								qPrevCode=null;
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								
								emojiFLag = 0;
								//びっくりとはてな
								handleBikkuriHatenaHankaku();
								break;
							case -123456789:
								str=null;
								qPrevCode=null;
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								
								// 選択肢を非表示
								setCandidatesViewShown(false);
								candidateViewShown=false;
								emojiFLag = 0;
								//数字モード
								qwertyNumHandle();
								break;
							case -10:
								
								if(currentKeyboard == qwertytype3){
									Intent new_intent = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
									new_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
									startActivity(new_intent);
								}
								break;	
							case 150:
								str = null;
								qPrevCode=null;
								endFlag = false;
								String check = String.valueOf(primaryCode);
								handleEmoji(check);
								break;
							case 49:
							case 48:
							case 50:
							case 51:
							case 52:
							case 53:
							case 54:
							case 55:
							case 56:
							case 57:
								length = composing.length();
								if(length > 0){
//									getCurrentInputConnection().commitText(composing,
//										composing.length());
									getCurrentInputConnection().commitText(composing,
											1);
									composing.delete(0, composing.length());
								}
								if(currentKeyboard == qwertyKeyboard){
									//qwertyのキャラ設定
									handleCharacter(primaryCode, keyCodes);
								}else if(currentKeyboard == qwertytype2){
//									Log.d("qwertytype2","qwertytype2 mode");
									handleCharacterqwerty(primaryCode, keyCodes);
								}else if(currentKeyboard == qwertytype3){
									
									//qwertyのキャラ設定
									handleCharacter(primaryCode, keyCodes);
								}
								
								break;
							default:
								
								
								setCandidatesViewShown(false);
								candidateViewShown=false;
								String checkCode = String.valueOf(primaryCode);
								if(!"150".equals(checkCode)){
									if(currentKeyboard == qwertyKeyboard){
										//qwertyのキャラ設定
										handleCharacter(primaryCode, keyCodes);
									}else if(currentKeyboard == qwertytype2){
//										Log.d("qwertytype2","qwertytype2 mode");
										handleCharacterqwerty(primaryCode, keyCodes);
									}else if(currentKeyboard == qwertytype3){
										
										//qwertyのキャラ設定
										handleCharacter(primaryCode, keyCodes);
									}
//								}else{
//									handleEmoji(checkCode);
								}
								
								
								break;
						}
						
					}
		}

		

		



		public void onPress(int primaryCode) {
			Log.d("SampleIME", "onPress(" + primaryCode + ") called");
		}

		public void onRelease(int primaryCode) {
			Log.d("SampleIME", "onRelease(" + primaryCode + ") called");

		}

		public void onText(CharSequence text) {
			//Log.d("SampleIME", "text(" + text + ") called");
			InputConnection connection = getCurrentInputConnection();
			connection.commitText(text, 1);
			hatenaBikkuriStr=null;
			emojiFLag = 0;
		}

		public void swipeDown() {
			Log.d("SampleIME", "swipeDown()");

		}

		public void swipeLeft() {
			Log.d("SampleIME", "swipeLeft()");
			

		}

		public void swipeRight() {
			Log.d("SampleIME", "swipeRight()");
			
		}

		

		public void swipeUp() {
			Log.d("SampleIME", "swipeUp()");

		}
		
	};
    
	/**
	 * ベル打ちのコード設定その２
	 * @param checkCode
	 * @param primaryCode
	 */
	private void handleCharacterAnother(String checkCode, int primaryCode) {
		// TODO Auto-generated method stub
		//タイプ2
		String tmp = String.valueOf(primaryCode);
		//コードが一つでも入っていたら
		if(prevCode != null){
			int prevlength = prevCode.length();
			prevCode = prevCode+tmp;
			strCode = prevCode;
			
			
			str = codeAnotherAry.get(strCode);
			
			if(str != null){
				//文字を追加

				
				final int length = composing.length();
//				Log.d("length","length:"+length);
//				Log.d("prevlength","prevlength:"+prevlength);
//				Log.d("prevCode","prevCode:"+prevCode);
				composing.delete(length - prevlength, length);
				appendToComposing(str);
				prevCode = null;
			}else{
				prevCode=null;
				str = checkCode;
				composing.append(checkCode);
				InputConnection ic = getCurrentInputConnection();
				ic.setComposingText(composing, composing.length());
			}
		}else{
			if("150".equals(checkCode)){
				handleEmoji(checkCode);
			}else{
				str = checkCode;
				composing.append(checkCode);
				InputConnection ic = getCurrentInputConnection();
				ic.setComposingText(composing, composing.length());
				prevCode = String.valueOf(primaryCode);
			}
			
		}
	}
	
	/**
	 * ベル打ちのコード設定
	 * @param checkCode
	 */
	private void handleCharacter(String checkCode,int primaryCode) {
		// TODO Auto-generated method stub
		
		//この条件なら、このあと濁点月の文字が出てくることはありえない
		if(dakutenCode != null){
			if(dakutenCode.length() == 2){
				if(primaryCode != 0){
					dakutenCode = null;
				}
			}else if(dakutenCode.length() == 4){
				dakutenCode = null;
			}
		}
		
		
		String tmp = String.valueOf(primaryCode);
//		Log.d("dakutenCode","dakutenCode:"+dakutenCode);
//		Log.d("prevCode","prevCode:"+prevCode);
		if(dakutenCode != null){
			
			
			//濁点用の文字列
			dakutenCode = dakutenCode+tmp;
			if(prevCode != null){
				
				//これらは要らないので、削除
				
				strCode = null;
				
				str = codeAry.get(dakutenCode);
				if(str != null){
					//あとはstrを候補ビューにセットする
					setDakutenKouhoView(str,checkCode);
					prevCode=null;
				}else{
					prevCode = prevCode+tmp;
					str = codeAry.get(prevCode);
					if(str != null){
						final int length = composing.length();

//						
						if(length > 0){
							composing.delete(length - 1, length);
						}
						
						appendToComposing(str);
						prevCode = null;
					}else{
						str = checkCode;
						composing.append(checkCode);
						InputConnection ic = getCurrentInputConnection();
//						ic.setComposingText(composing, composing.length());
						ic.setComposingText(composing, 1);
					}
					
				}
				dakutenCode = null;
				
				
			}else{
				if("150".equals(checkCode)){
					handleEmoji(checkCode);
					dakutenCode = null;
				}else{
					str = checkCode;
					composing.append(checkCode);
					InputConnection ic = getCurrentInputConnection();
//					ic.setComposingText(composing, composing.length());
					ic.setComposingText(composing, 1);
					prevCode = String.valueOf(primaryCode);
				}
			}
		}else{
			//タイプ１
			//コードが一つでも入っていたら
			if(prevCode != null){
				int prevlength = prevCode.length();
				prevCode = prevCode+tmp;
				strCode = prevCode;
				
				
				str = codeAry.get(strCode);
				
				if(str != null){
					//文字を追加
					
					
					//濁点があるかもしれないやつは、prevcodeは維持
					if(dakutenList.contains(str)){
						dakutenCode = prevCode;
					}
					
					final int length = composing.length();
//					Log.d("length","length:"+length);
//					Log.d("prevlength","prevlength:"+prevlength);
//					Log.d("prevCode","prevCode:"+prevCode);
//					composing.delete(length - prevlength, 1);
					composing.delete(length - prevlength, length);
					appendToComposing(str);
					prevCode = null;
				}else{
					str = checkCode;
					composing.append(checkCode);
					InputConnection ic = getCurrentInputConnection();
//					ic.setComposingText(composing, composing.length());
					ic.setComposingText(composing, 1);
				}
			}else{
				if("150".equals(checkCode)){
					handleEmoji(checkCode);
				}else{
					str = checkCode;
					composing.append(checkCode);
					InputConnection ic = getCurrentInputConnection();
//					ic.setComposingText(composing, composing.length());
					ic.setComposingText(composing, 1);
					prevCode = String.valueOf(primaryCode);
				}
				
			}
		}
		
	}
	

	/**
	 * 候補ビューにセット
	 * @param str2
	 */
	private void setDakutenKouhoView(String str2,String checkCode) {
		// TODO Auto-generated method stub
		final int length = composing.length();
		composing.delete(length - 2, length);
		appendToComposing(str);

	}

	private void onKeyUpEvent(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
		
	}
	 @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // キー状態取得
//        Log.d("keydown","keydown");
//        if(candidateViewShown == true){
//        	if(keyCode == KeyEvent.KEYCODE_BACK){
//            	if(candidatesView.mViewType == candidatesView.VIEW_TYPE_FULL){
//            		mStatus |= STATUS_CANDIDATE_FULL;
//        			candidatesView.setViewType(VIEW_TYPE_FULL);
//            	}else if(candidatesView.mViewType == candidatesView.VIEW_TYPE_NORMAL){
//            		mStatus &= ~STATUS_CANDIDATE_FULL;
//        			candidatesView.setViewType(VIEW_TYPE_NORMAL);
//            	}
//            }	
//        }
        
        //処理未実行
//        return super.onKeyDown(keyCode, event);
        mConsumeDownEvent = onEvent(new PocketBellEvent(event));
		if (!mConsumeDownEvent) {
			return super.onKeyDown(keyCode, event);
		}
		return mConsumeDownEvent;
    }
	
	 /** @see android.inputmethodservice.InputMethodService#onKeyUp */
	@Override public boolean onKeyUp(int keyCode, KeyEvent event) {
		Log.d("onKeyUp","onKeyUp");
		boolean ret = mConsumeDownEvent;
        if (!ret) {
            ret = super.onKeyUp(keyCode, event);
        }else{
            onEvent(new PocketBellEvent(event));
        }
        return ret;
	}
    
	
	/** @see android.inputmethodservice.InputMethodService#setCandidatesViewShown */
    @Override public void setCandidatesViewShown(boolean shown) {
        super.setCandidatesViewShown(shown);
        if (shown) {
            showWindow(true);
//        } else {
//            if (mAutoHideMode) {
//                hideWindow();
//            }
        }
    }
    
    /** @see android.inputmethodservice.InputMethodService#hideWindow */
    @Override public void hideWindow() {
        super.hideWindow();
//        emojiFLag = 0;
//        selection = new ArrayList<String>();
//        setCandidatesViewShown(false);
//		candidateViewShown=false;
//		prevCode=null;
//		qPrevCode=null;
//		kigouFlag = false;
        hideStatusIcon();
    }

    /** @see jp.co.omronsoft.openwnn.OpenWnn#onEvent */
    synchronized public boolean onEvent(PocketBellEvent ev) {
    	
    	Log.d("testtestestst","evenctbbbbbbbbbb");
    	
    	KeyEvent keyEvent = ev.keyEvent;
        int keyCode = 0;
        if (keyEvent != null) {
            keyCode = keyEvent.getKeyCode();
        }
    	
    	if(ev.code == PocketBellEvent.LIST_CANDIDATES_FULL){
    		mStatus |= STATUS_CANDIDATE_FULL;
			candidatesView.setViewType(VIEW_TYPE_FULL);
    	}else if(ev.code == PocketBellEvent.LIST_CANDIDATES_NORMAL){
    		mStatus &= ~STATUS_CANDIDATE_FULL;
			candidatesView.setViewType(VIEW_TYPE_NORMAL);
    	}
    	boolean ret = false;
    	switch (ev.code) {
	    	case PocketBellEvent.INPUT_KEY:
				ret = processKeyEvent(keyEvent);
				break;
			case PocketBellEvent.SELECT_CANDIDATE:
				
//				Log.d("Evenct","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%SelectEvent%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//				getCurrentInputConnection().commitText(ev.word, ev.word.length());
				Log.d("emojiFLag::::::","test"+emojiFLag);
				if(kigouFlag == true){
					if(emojiFLag == 1){
						if(emojiSpList.contains(ev.word)){
							
						}else{
							Log.d("Evenct","%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%emojiSelect%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
							if(emojiSpList.size() == 10){
								emojiSpList.remove(0);
								emojiSpList.add(ev.word);
							}else{
								emojiSpList.add(ev.word);
							}
							PostEmoji pe = new PostEmoji();
							pe.execute(emojiSpList,sp);
							
						}
					}else if(emojiFLag == 2){
						if(kaomojiSpList.contains(ev.word)){
							
						}else{
							if(kaomojiSpList.size() == 10){
								kaomojiSpList.remove(0);
								kaomojiSpList.add(ev.word);
							}else{
								kaomojiSpList.add(ev.word);
							}
							PostKaomoji pe = new PostKaomoji();
							pe.execute(kaomojiSpList,sp);
							
						}
						
					}else if(emojiFLag == 0){
//						if(kigouSpList.contains(ev.word)){
//							
//						}else{
//							if(kigouSpList.size() == 10){
//								kigouSpList.remove(0);
//								kigouSpList.add(ev.word);
//							}else{
//								kigouSpList.add(ev.word);
//							}
//							PostKigo pe = new PostKigo();
//							pe.execute(kigouSpList,sp);
//							
//						}
					}
				}
				kigouFlag = false;
				
				getCurrentInputConnection().commitText(ev.word, 1);
				
				
				composing.delete(0, composing.length());
				setCandidatesViewShown(false);
				candidateViewShown=false;
				prevCode=null;
				qPrevCode=null;
				
				String val = ev.word;
				
				if(endFlag == true){
					endFlag = false;
					//setEnd();
					
					//TODO:ここはテストして重かったら消します
					//初期状態
					if(predictKey == null){
						//予測キーをセット
						predictKey = ev.word;
						setEnd();
					//キーがすでにある
					}else{
						predictVal = ev.word;
						PredictHelper ph = new PredictHelper(this);
						new Thread(new FeedPredict(mHandler,ev.word,ph,PocketBellValue.defaultEndList)).start();
						
						
					}
					
					
				}
				SkkWriteHelper swh = new SkkWriteHelper(this);
				
				PostSkkCount psc = new PostSkkCount();
				psc.execute(swh,val);
				
				
				break;
			
			default:
				break;
		}
//    	if(candidateViewShown == true){
//    		if(ev.keyEvent.KEYCODE_BACK){
//    			
//    		}
//    	}else{
//    		
//    	}
    	
    	
		return ret;
    	
    }
    
    /**
     * Handle a key event which is not right or left key when the
     * composing text is empty and some candidates are shown.
     *
     * @param ev        A key event
     * @return          {@code true} if this consumes the event; {@code false} if not.
     */
//    private boolean processKeyEventNoInputCandidateShown(KeyEvent ev) {
//        boolean ret = true;
//
//        switch (ev.getKeyCode()) {
//        case KeyEvent.KEYCODE_DEL:
//            ret = true;
//            break;
//        case KeyEvent.KEYCODE_ENTER:
//        case KeyEvent.KEYCODE_DPAD_UP:
//        case KeyEvent.KEYCODE_DPAD_DOWN:
//        case KeyEvent.KEYCODE_MENU:
//            ret = false;
//            break;
//            
//        case KeyEvent.KEYCODE_CALL:
//            return false;
//            
//        case KeyEvent.KEYCODE_DPAD_CENTER:
//            ret = true;
//            break;
//
//        case KeyEvent.KEYCODE_BACK:
//            if (mCandidatesViewManager.getViewType() == CandidatesViewManager.VIEW_TYPE_FULL) {
//                mStatus &= ~STATUS_CANDIDATE_FULL;
//                mCandidatesViewManager.setViewType(CandidatesViewManager.VIEW_TYPE_NORMAL);
//                return true;
//            } else {
//                ret = true;
//            }
//            break;
//        
//        default:
//            return true;
//        }
//
//        if (mConverter != null) {
//            /* initialize the converter */
//            mConverter.init();
//        }
//        updateViewStatusForPrediction(true, true);
//        return ret;
//    }

    private boolean processKeyEvent(KeyEvent keyEvent) {
		// TODO Auto-generated method stub
    	Log.d("keyEvent","keyEventkeyEventkeyEventkeyEvent");
    	int key = keyEvent.getKeyCode();
    	if(key == KeyEvent.KEYCODE_BACK){
    		if(candidateViewShown == true){
    			if(candidatesView.mViewType == candidatesView.VIEW_TYPE_FULL){
            		
            		mStatus &= ~STATUS_CANDIDATE_FULL;
        			candidatesView.setViewType(VIEW_TYPE_NORMAL);
            	}else if(candidatesView.mViewType == candidatesView.VIEW_TYPE_NORMAL){
            		composing.delete(0, composing.length());
    				setCandidatesViewShown(false);
    				candidateViewShown=false;
    				prevCode=null;
    				qPrevCode=null;
            	}	
	        }else{
//	        	hideWindow();
	        	return false;
	        	
	        }
    	}else{
    		return false;
    	}
		return true;
	}

	/**
     * 最後に一応候補を表示
     */
	private void setEnd() {
		// TODO Auto-generated method stub
		setCandidatesViewShown(true);
		candidateViewShown=true;
		selection = PocketBellValue.defaultEndList;
		// CandidatesViewの選択肢を更新
		candidatesView.setCandidates(selection);
		
	}
    
	@Override
	public void onDestroy(){
		super.onDestroy();
		Log.d("Destroy", "endendendendendendend");
		selection = null;
	}
	
	
}
