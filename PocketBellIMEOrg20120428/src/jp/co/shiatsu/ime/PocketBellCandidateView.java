package jp.co.shiatsu.ime;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import jp.co.shiatsu.ime.service.PocketBellIME;

import android.content.res.Configuration;
import android.content.res.Resources;

import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class PocketBellCandidateView implements GestureDetector.OnGestureListener{

	
	
	private static final int OUT_OF_BOUNDS = -1;
	private PocketBellIME pbi;
	private List<String> candidates;
	private List<Integer> emojiList = new ArrayList<Integer>();
	
	private Paint paint;
	private int touchX = OUT_OF_BOUNDS;
	private int selectedIndex;
	private int backgroundColor;
	private int foregroundColor;
	private Map<String,Integer> emojiMap = new HashMap<String, Integer>();
	
	
	private int mTotalWidth;
	
	private Rect mBgPadding;
	
	private boolean mScrolled;
    private int mTargetScrollX;
    
    private int[] mWordWidth;
    private int[] mWordX;
    private Drawable mSelectionHighlight;

    private static final int SCROLL_PIXELS = 20;
    private boolean emojiFlag;
    
    private ViewGroup mViewBody;
    private ScrollView mViewBodyScroll;
    private ViewGroup mViewCandidateBase;
    /** Layout for the candidates list on normal view */
    private LinearLayout mViewCandidateList1st;
    /** Layout for the candidates list on full view */
    private AbsoluteLayout mViewCandidateList2nd;
    
    /** Width of {@code mReadMoreButton} */
    private int mReadMoreButtonWidth = 0;
    /** Color of the candidates */
    private int mTextColor = 0;
    private ImageView mReadMoreButton;
    private boolean mIsFullView = false;
    /** Gesture detector */
    private GestureDetector mGestureDetector;
    /** The view of the scaling up candidate */
    private View mViewScaleUp;
    
    /** Size of candidates view (normal) */
    public static final int VIEW_TYPE_NORMAL = 0;
    /** Size of candidates view (full) */
    public static final int VIEW_TYPE_FULL   = 1;
    /** Size of candidates view (close/non-display) */
    public static final int VIEW_TYPE_CLOSE  = 2;
    
    private String mWord;
    
    /** View type (VIEW_TYPE_NORMAL or VIEW_TYPE_FULL or VIEW_TYPE_CLOSE) */
    public int mViewType;
    
    /** Height of a line */
    public static final int LINE_HEIGHT = 34;
    /** Number of lines to display (Portrait) */
    public static final int LINE_NUM_PORTRAIT       = 2;
    /** Number of lines to display (Landscape) */
    public static final int LINE_NUM_LANDSCAPE      = 1;
    
    /** Portrait display({@code true}) or landscape({@code false}) */
    private boolean mPortrait;
    
    /** Minimum width of a candidate (density support) */
    private int mCandidateMinimumWidth;
    /** Maximum width of a candidate (density support) */
    private int mCandidateMinimumHeight;
    
    private int mViewHeight;
    
    /** {@code true} if the candidate delete state is selected */
	private boolean mIsScaleUp = false;
	
	/** Template object for each candidate and normal/full view change button */
    private TextView mViewCandidateTemplate;
    
    /** {@code true} if there are more candidates to display. */
    private boolean mCanReadMore = false;
    
    /** Limitation of displaying candidates */
    private int mDisplayLimit;
    
    /** List of candidates */
    private ArrayList<String> mWnnWordArray;
    
    private final DisplayMetrics mMetrics = new DisplayMetrics();
    
    /** Whether hide the view if there is no candidates */
    private boolean mAutoHideMode;
    
    /** Number of candidates displaying */
    private int mWordCount = 0;
    
    /** The offset when the candidates is flowed out the candidate window */
    private int mDisplayEndOffset = 0;
    
    /** Number of candidates in full view */
    private int mFullViewWordCount;
    /** Number of candidates in the current line (in full view) */
    private int mFullViewOccupyCount;
    
    /** Id of the top line view (in full view) */
    private int mFullViewPrevLineTopId;
    
    /** Whether all candidates is displayed */
    private boolean mCreateCandidateDone;
    
    /** Number of lines in normal view */
    private int mNormalViewWordCountOfLine;
    
    /** Character width of the candidate area */
    private int mLineLength = 0;
    /** Number of lines displayed */
    private int mLineCount = 1;
    
    //各文字の大きさ
    private int strWidth = 15;
    private int strHeght = 15;
    
    //
    private int totalWidth = 0;
    private int totalHeight = 0;
    
    /** Width of the view */
    private int mViewWidth;
    
    /** Align the candidate left if the width of the string exceeds this threshold */
    private static final int CANDIDATE_LEFT_ALIGN_THRESHOLD = 120;
    
    /** Maximum lines */
    private static final int DISPLAY_LINE_MAX_COUNT = 1000;
    /** Width of the view */
    private static final int CANDIDATE_MINIMUM_WIDTH = 48;
    /** Height of the view */
    private static final int CANDIDATE_MINIMUM_HEIGHT = 35;
    
    /** View of the previous candidate (in full view) */
    private TextView mFullViewPrevView;
    
    /** Maximum number of displaying candidates par one line (full view mode) */
    private static final int FULL_VIEW_DIV = 4;
    
    /** Layout of the previous candidate (in full view) */
    private ViewGroup.LayoutParams mFullViewPrevParams;
    
    /** Event listener for touching a candidate */
    private OnTouchListener mCandidateOnTouch = new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                boolean ret = false;
                return ret;
            }
        };
    
    private Resources res;
    public Map<String, Integer> getEmojiMap() {
		return emojiMap;
	}

	public void setEmojiMap(Map<String, Integer> emojiMap) {
		this.emojiMap = emojiMap;
	}
	public List<Integer> getEmojiList() {
		return emojiList;
	}

	public void setEmojiList(List<Integer> emojiList) {
		this.emojiList = emojiList;
	}
    
	public void setEmojiFlag(boolean emojiFlag) {
		this.emojiFlag = emojiFlag;
	}
	/**
	 * 文字の余白
	 */
	private static final int X_GAP = 10;

	public void setPbi(PocketBellIME pbi2) {
		this.pbi = pbi2;
	}

	/**
     * Constructor
     */
    public PocketBellCandidateView() {
        this(-1);
    }

    /**
     * Constructor
     *
     * @param displayLimit      The limit of display
     */
    public PocketBellCandidateView(int displayLimit) {
        this.mDisplayLimit = displayLimit;
        this.mWnnWordArray = new ArrayList<String>();
        this.mAutoHideMode = true;
        mMetrics.setToDefaults();
    }

	/**
	 * 初期化
	 * @param parent
	 * @param width
	 * @param height
	 * @return
	 */
	public View initView(PocketBellIME parent, int width, int height) {
		this.pbi = parent;
		mViewWidth = width;
        mViewHeight = height;
//		mViewHeight = 503;
        mCandidateMinimumWidth = (int)(CANDIDATE_MINIMUM_WIDTH * mMetrics.density);
        mCandidateMinimumHeight = (int)(CANDIDATE_MINIMUM_HEIGHT * mMetrics.density);
        mPortrait = 
            (parent.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE);
		res = parent.getResources();
		LayoutInflater inflater = parent.getLayoutInflater();
        mViewBody = (ViewGroup)inflater.inflate(R.layout.candidates, null);
        mViewBodyScroll = (ScrollView)mViewBody.findViewById(R.id.candview_scroll);
        mViewBodyScroll.setOnTouchListener(mCandidateOnTouch);

        mViewCandidateBase = (ViewGroup)mViewBody.findViewById(R.id.candview_base);
        
        createNormalCandidateView();
        mViewCandidateList2nd = (AbsoluteLayout)mViewBody.findViewById(R.id.candidates_2nd_view);

        mReadMoreButtonWidth = res.getDrawable(R.drawable.cand_up).getMinimumWidth();

        mTextColor = res.getColor(R.color.candidate_text);
        
        mReadMoreButton = (ImageView)mViewBody.findViewById(R.id.read_more_text);
        mReadMoreButton.setPadding(0, 10, 20, 0);
        mReadMoreButton.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mIsFullView) {
                            mReadMoreButton.setImageResource(R.drawable.cand_down);
                        } else {
                            mReadMoreButton.setImageResource(R.drawable.cand_up);
                        }
                	    break;
                    case MotionEvent.ACTION_UP:
                        if (mIsFullView) {
                            mReadMoreButton.setImageResource(R.drawable.cand_down_press);
                        } else {
                            mReadMoreButton.setImageResource(R.drawable.cand_up_press);
                        }
                        break;
                    default:
                        break;
                    }
                    return false;
                }
            });
        mReadMoreButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (!v.isShown()) {
                        return;
                    }

                    if (mIsFullView) {
                        mIsFullView = false;
                        pbi.onEvent(new PocketBellEvent(PocketBellEvent.LIST_CANDIDATES_NORMAL));
                        
                    } else {
                        mIsFullView = true;
                        pbi.onEvent(new PocketBellEvent(PocketBellEvent.LIST_CANDIDATES_FULL));
                    }
                }
            });
        setViewType(VIEW_TYPE_CLOSE);

        mGestureDetector = new GestureDetector(this);

        View scaleUp = (View)inflater.inflate(R.layout.candidate_scale_up, null);
        mViewScaleUp = scaleUp;
        /* select button */
        Button b = (Button)scaleUp.findViewById(R.id.candidate_select);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectCandidate(mWord);
            }
        });
        
        /* cancel button */
        b = (Button)scaleUp.findViewById(R.id.candidate_cancel);
        b.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    setViewLayout(VIEW_TYPE_NORMAL);
                    
                }
            });

        
		return mViewBody;
		
	}

	/**
	 * setViewレイアウト
	 * @param viewTypeNormal
	 */
	protected boolean setViewLayout(int type) {
		// TODO Auto-generated method stub
		mViewType = type;
        setViewScaleUp(false, null);

        switch (type) {
	        case VIEW_TYPE_CLOSE:
	            mViewCandidateBase.setMinimumHeight(-1);
	            return false;
	
	        case VIEW_TYPE_NORMAL:
	            mViewBodyScroll.scrollTo(0, 0);
	            mViewCandidateList1st.setVisibility(View.VISIBLE);
	            mViewCandidateList2nd.setVisibility(View.GONE);
	            mViewCandidateBase.setMinimumHeight(-1);
	            int line = (mPortrait) ? LINE_NUM_PORTRAIT : LINE_NUM_LANDSCAPE;
//	            Log.d("line","*******************************line********************"+line);
//	            Log.d("line","*******************************line********************"+getCandidateMinimumHeight());
	            mViewCandidateList1st.setMinimumHeight(getCandidateMinimumHeight() * line);
	            return false;
	
	        case VIEW_TYPE_FULL:
	        default:
//	        	mViewCandidateList1st.setVisibility(View.GONE);
	            mViewCandidateList2nd.setVisibility(View.VISIBLE);
	            mViewCandidateBase.setMinimumHeight(mViewHeight);
	            return true;
        }
	}

	private void setViewScaleUp(boolean up, String word) {
		// TODO Auto-generated method stub
		if (up == mIsScaleUp || (mViewScaleUp == null)) {
            return;
        }

        if (up) {
            setViewLayout(VIEW_TYPE_NORMAL);
            mViewCandidateList1st.setVisibility(View.GONE);
            mViewCandidateBase.setMinimumHeight(-1);
            mViewCandidateBase.addView(mViewScaleUp);
            TextView text = (TextView)mViewScaleUp.findViewById(R.id.candidate_scale_up_text);
//            Log.d("text","text"+text);
            text.setText(word);
            if (!mPortrait) {
                Resources r = mViewBodyScroll.getContext().getResources();
                text.setTextSize(r.getDimensionPixelSize(R.dimen.candidate_delete_word_size_landscape));
            }

            mIsScaleUp = true;
            setReadMore();
        } else {
            mIsScaleUp = false;
            mViewCandidateBase.removeView(mViewScaleUp);
        }
	}

	/**
	 * さらに読み込む
	 */
	/**
     * Display {@code mReadMoreText} if there are more candidates.
     */
    private void setReadMore() {
        if (mIsScaleUp) {
            mReadMoreButton.setVisibility(View.GONE);
            mViewCandidateTemplate.setVisibility(View.GONE);
            return;
        }

        if (mIsFullView) {
            mReadMoreButton.setVisibility(View.VISIBLE);
            mReadMoreButton.setImageResource(R.drawable.cand_down);
        } else {
//        	Log.d("ReadMore","readmore");
            if (mCanReadMore) {
//            	Log.d("ReadMore","readmore");
                mReadMoreButton.setVisibility(View.VISIBLE);
                mReadMoreButton.setImageResource(R.drawable.cand_up);
            } else {
                mReadMoreButton.setVisibility(View.GONE);
                mViewCandidateTemplate.setVisibility(View.GONE);
            }
        }
    }


	/**
	 * 使用する言葉を選択
	 * @param mWord2
	 */
	protected void selectCandidate(String mWord2) {
		// TODO Auto-generated method stub
		setViewLayout(VIEW_TYPE_NORMAL);
		pbi.onEvent(new PocketBellEvent(PocketBellEvent.SELECT_CANDIDATE, mWord2));
	}

	/**
	 * 表示タイプを設定
	 * @param viewTypeClose
	 */
	public void setViewType(int type) {
		// TODO Auto-generated method stub
		boolean readMore = setViewLayout(type);
		if (readMore) {
            displayCandidates(false,-1);
        }else{
        	if (type == VIEW_TYPE_NORMAL) {
                mIsFullView = false;
                if (mDisplayEndOffset > 0) {
                    int maxLine = getMaxLine();
                    displayCandidates(false, maxLine);
                } else {
                    setReadMore();
                }
            } else {
                if (mViewBody.isShown()) {
                    pbi.setCandidatesViewShown(false);
                }
            }
        }
	}
	
	/**
     * Clear the list of the normal candidate view.
     */
    private void clearNormalViewCandidate() {
        LinearLayout candidateList = mViewCandidateList1st;
        int lineNum = candidateList.getChildCount();
        for (int i = 0; i < lineNum; i++) {

            LinearLayout lineView = (LinearLayout)candidateList.getChildAt(i);
            int size = lineView.getChildCount();
            for (int j = 0; j < size; j++) {
                View v = lineView.getChildAt(j);
                v.setVisibility(View.GONE);
            }
        }
    }
	
	/** @see CandidatesViewManager#clearCandidates */
    public void clearCandidates() {
        clearNormalViewCandidate();

        ViewGroup layout = mViewCandidateList2nd;
        int size = layout.getChildCount();
        for (int i = 0; i < size; i++) {
            View v = layout.getChildAt(i);
            v.setVisibility(View.GONE);
        }
    
        mLineCount = 1;
        mWordCount = 0;
        mWnnWordArray.clear();

        mLineLength = 0;

        mIsFullView = false;
        setViewLayout(VIEW_TYPE_NORMAL);
        if (mAutoHideMode) {
            setViewLayout(VIEW_TYPE_CLOSE);
        }

        if (mAutoHideMode && mViewBody.isShown()) {
        	pbi.setCandidatesViewShown(false);
        }
        mCanReadMore = false;
        setReadMore();
    }

	/** @see CandidatesViewManager#displayCandidates */
    public void displayCandidatesNormal() {
    	mCanReadMore = false;
        mDisplayEndOffset = 0;
        mIsFullView = false;
        mFullViewWordCount = 0;
        mFullViewOccupyCount = 0;
        mFullViewPrevLineTopId = 0;
        mCreateCandidateDone = false;
        mNormalViewWordCountOfLine = 0;
        
        clearCandidates();
        setViewLayout(VIEW_TYPE_NORMAL);
    }
	
	synchronized private void displayCandidates(boolean dispFirst,int maxLine) {
		// TODO Auto-generated method stub
		boolean isBreak = false;
		
//		Log.d("size","************************size:"+candidates.size());
//		for (int i = 0; i < candidates.size(); i++) {
		int displayLimit = mDisplayLimit;
		int i=0;
		int maxSize = candidates.size();
		while ((displayLimit == -1 || mWordCount < displayLimit)) {	
			if(maxSize == i){
				break;
			}
			String word = candidates.get(i);
			
			setCandidate(false, word);
//			Log.d("maxLine","maxLine:"+maxLine);
//			Log.d("dispFirst","dispFirst:"+dispFirst);
//			Log.d("mLineCount","mLineCount:"+mLineCount);
			if (dispFirst && (maxLine < mLineCount)) {
//				Log.d("mCanReadMore","$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$+++++++++++++++++++++++++++++++++++++++++++++++++++++++$$$$$$");
                mCanReadMore = true;
                isBreak = true;
                break;
            }
			i++;
		}
//		Log.d("mCanReadMore","mCanReadMore:"+mCanReadMore);
		
		if (!isBreak && !mCreateCandidateDone) {
            /* align left if necessary */
            createNextLine();
            mCreateCandidateDone = true;
        }
		
		if (mWordCount < 1) { /* no candidates */
            if (mAutoHideMode) {
                pbi.setCandidatesViewShown(false);
                return;
            } else {
                mCanReadMore = false;
                mIsFullView = false;
                setViewLayout(VIEW_TYPE_NORMAL);
            }
        }
		setReadMore();
		
		if (!(mViewBody.isShown())) {
            pbi.setCandidatesViewShown(true);
        }
		
		
		
		
	}
	
	/**
     * Retrieve the width of string to draw.
     * 
     * @param text          The string
     * @param start         The start position (specified by the number of character)
     * @param end           The end position (specified by the number of character)
     * @return          The width of string to draw
     */ 
    public int measureText(CharSequence text, int start, int end) {
        if (end - start < 3) {
            return getCandidateMinimumWidth();
        }

        TextPaint paint = mViewCandidateTemplate.getPaint();
        return (int)paint.measureText(text, start, end);
    }
	
	 /**
     * Add a candidate into the list.
     * @param isCategory  {@code true}:caption of category, {@code false}:normal word
     * @param word        A candidate word
     */
    private void setCandidate(boolean isCategory, String word) {
    	int textLength = measureText(word, 0, word.length());
    	TextView template = mViewCandidateTemplate;
        textLength += template.getPaddingLeft() + template.getPaddingRight();
        int maxWidth = mViewWidth;
        TextView textView;
        
        if (mIsFullView || getMaxLine() < mLineCount) {
//        	Log.d("mLineCount","aaaaaaaaaaaaaaaaaaaaaaaaaaa:");
            /* Full view */
            int indentWidth = mViewWidth / FULL_VIEW_DIV;
            int occupyCount = Math.min((textLength + indentWidth) / indentWidth, FULL_VIEW_DIV);
            if (isCategory) {
                occupyCount = FULL_VIEW_DIV;
            }

            if (FULL_VIEW_DIV < (mFullViewOccupyCount + occupyCount)) {
                if (FULL_VIEW_DIV != mFullViewOccupyCount) {
                    mFullViewPrevParams.width += (FULL_VIEW_DIV - mFullViewOccupyCount) * indentWidth;
                    mViewCandidateList2nd.updateViewLayout(mFullViewPrevView, mFullViewPrevParams);
                }
                mFullViewOccupyCount = 0;
                mFullViewPrevLineTopId = mFullViewPrevView.getId();
                mLineCount++;
            }

            ViewGroup layout = mViewCandidateList2nd;

            int width = indentWidth * occupyCount;
            int height = getCandidateMinimumHeight();


            ViewGroup.LayoutParams params = buildLayoutParams(mViewCandidateList2nd, width, height);

            textView = (TextView) layout.getChildAt(mFullViewWordCount);
            if (textView == null) {
                textView = createCandidateView();
                textView.setLayoutParams(params);

                mViewCandidateList2nd.addView(textView);
            } else {
                mViewCandidateList2nd.updateViewLayout(textView, params);
            }

            mFullViewOccupyCount += occupyCount;
            mFullViewWordCount++;
            mFullViewPrevView = textView;
            mFullViewPrevParams = params;

        } else {
            textLength = Math.max(textLength, getCandidateMinimumWidth());

            /* Normal view */
            int nextEnd = mLineLength + textLength;
            if (mLineCount == 1) {
                maxWidth -= getCandidateMinimumWidth();
            }

            if ((maxWidth < nextEnd) && (mWordCount != 0)) {
//            	Log.d("createNextLine", "createNextLinecreateNextLinecreateNextLinecreateNextLinecreateNextLinecreateNextLine:");
                createNextLine();
//                Log.d("getMaxLine", "getmaxline:"+getMaxLine());
//                Log.d("mLineCount", "mLineCount:"+mLineCount);
                if (getMaxLine() < mLineCount) {
//                	Log.d("createNextLine", "createNextLinecreateNextLinecreateNextLinecreateNextLinecreateNextLinecreateNextLine:");
                    mLineLength = 0;
                    /* Call this method again to add the candidate in the full view */
                    setCandidate(isCategory, word);
                    return;
                }
                
                mLineLength = textLength;
            } else {
                mLineLength = nextEnd;
            }
//            Log.d("mLineCount", "mLineCount:"+mLineCount);
//            Log.d("mNormalViewWordCountOfLine", "mNormalViewWordCountOfLine:"+mNormalViewWordCountOfLine);
            LinearLayout lineView = (LinearLayout) mViewCandidateList1st.getChildAt(mLineCount - 1);
//            Log.d("lineView", "lineView:"+lineView);
            textView = (TextView) lineView.getChildAt(mNormalViewWordCountOfLine);
//            Log.d("textView", "textView:"+textView);
            
            
            if (isCategory) {
                if (mLineCount == 1) {
                    mViewCandidateTemplate.setBackgroundDrawable(null);
                }
                mLineLength += CANDIDATE_LEFT_ALIGN_THRESHOLD;
            }

            mNormalViewWordCountOfLine++;
        }
        
//        Log.d("word", "word:"+word);
        textView.setText(word);
        textView.setTextColor(mTextColor);
        textView.setId(mWordCount);
        textView.setVisibility(View.VISIBLE);
        textView.setPressed(false);
//        Log.d("isCategory", ">>>>>>>>>>>>>>>>>isCategory"+isCategory+"<<<<<<<<<<<<<<<<<<<<");
        if (isCategory) {
            textView.setOnClickListener(null);
            textView.setOnLongClickListener(null);
            textView.setBackgroundDrawable(null);
        } else {
            textView.setOnClickListener(mCandidateOnClick);
            textView.setOnLongClickListener(mCandidateOnLongClick);
            textView.setBackgroundResource(R.drawable.cand_back);
        }
        
        textView.setOnTouchListener(mCandidateOnTouch);

        if (maxWidth < textLength) {
            textView.setEllipsize(TextUtils.TruncateAt.END);
        } else {
            textView.setEllipsize(null);
        }

        ImageSpan span = null;
        if (word.equals(" ")) {
            span = new ImageSpan(pbi, R.drawable.word_half_space,
                                 DynamicDrawableSpan.ALIGN_BASELINE);
        } else if (word.equals("\u3000" /* full-width space */)) {
            span = new ImageSpan(pbi, R.drawable.word_full_space,
                                 DynamicDrawableSpan.ALIGN_BASELINE);
        }

        if (span != null) {
            SpannableString spannable = new SpannableString("   ");
            spannable.setSpan(span, 1, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE); 
            textView.setText(spannable);
        }

        mWnnWordArray.add(mWordCount, word);
        mWordCount++;
    	
    }
    
    /** Event listener for long-clicking a candidate */
    private OnLongClickListener mCandidateOnLongClick = new OnLongClickListener() {
            public boolean onLongClick(View v) {
                
            
                return true;
            }
        };

    
    /**
     * Create a layout for the next line.
     */
    private void createNextLine() {
//    	Log.d("nextLine","nextnextnextnextnextnextnextnextnextnextnxtnextnextnextnextnextnextnextnext");
        int lineCount = mLineCount;
        Log.d("nextLine","lineCount:"+lineCount);
        if (mIsFullView || getMaxLine() < lineCount) {
            /* Full view */
            mFullViewOccupyCount = 0;
            mFullViewPrevLineTopId = mFullViewPrevView.getId();
        } else {
            /* Normal view */
            LinearLayout lineView = (LinearLayout) mViewCandidateList1st.getChildAt(lineCount - 1);
            float weight = 0;
            
            if (mLineLength < CANDIDATE_LEFT_ALIGN_THRESHOLD) {
                if (lineCount == 1) {
                    mViewCandidateTemplate.setVisibility(View.GONE);
                }
            } else {
                weight = 1.0f;
            }

            LinearLayout.LayoutParams params
                = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                ViewGroup.LayoutParams.WRAP_CONTENT,
                                                weight);
            
            int child = lineView.getChildCount();
            Log.d("child","child:"+child);
            for (int i = 0; i < child; i++) {
                View view = lineView.getChildAt(i);

                if (view != mViewCandidateTemplate) {
                    view.setLayoutParams(params);
                }
            }

            mLineLength = 0;
            mNormalViewWordCountOfLine = 0;
        }
        mLineCount++;
    }

	/**
     * Create the normal candidate view
     */
    private void createNormalCandidateView() {
        mViewCandidateList1st = (LinearLayout)mViewBody.findViewById(R.id.candidates_1st_view);
        mViewCandidateList1st.setOnTouchListener(mCandidateOnTouch);
        mViewCandidateList1st.setOnClickListener(mCandidateOnClick);

        int line = getMaxLine();
        int width = mViewWidth;
//        Log.d("line","*******************************************************");
//        Log.d("line","line:"+line);
//        Log.d("line","*******************************************************");
        for (int i = 0; i < line; i++) {
            LinearLayout lineView = new LinearLayout(mViewBodyScroll.getContext());
            lineView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = 
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                              ViewGroup.LayoutParams.WRAP_CONTENT);
            lineView.setLayoutParams(layoutParams);
            
            
            
            for (int j = 0; j < (width / getCandidateMinimumWidth()); j++) {
            	
                TextView tv = createCandidateView();
                Log.d("tv","tv:"+tv);
                lineView.addView(tv);
            }

            if (i == 0) {
                TextView tv = createCandidateView();
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                             ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutParams.weight = 0;
                layoutParams.gravity = Gravity.RIGHT;
                tv.setLayoutParams(layoutParams);

                lineView.addView(tv);
                mViewCandidateTemplate = tv;
            }
            mViewCandidateList1st.addView(lineView);
        }
    }


	public void setCandidates(List<String> selection) {
		this.candidates = selection;
		
		mCanReadMore = false;
        mDisplayEndOffset = 0;
        mIsFullView = false;
        mFullViewWordCount = 0;
        mFullViewOccupyCount = 0;
        mFullViewPrevLineTopId = 0;
        mCreateCandidateDone = false;
        mNormalViewWordCountOfLine = 0;

        clearCandidates();
        setViewLayout(VIEW_TYPE_NORMAL);
        
        mViewCandidateTemplate.setVisibility(View.VISIBLE);
        mViewCandidateTemplate.setBackgroundResource(R.drawable.cand_back);
        Log.d("getMaxLine","getMaxLine:"+getMaxLine());
		
		displayCandidates(true,getMaxLine());
	}



	/**
	 * ジェスチャーのimplementsメソッドたち
	 */
	
	
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
     * @return the minimum width of a candidate view.
     */
    private int getCandidateMinimumWidth() {
        return mCandidateMinimumWidth;
    }

    /**
     * @return the minimum height of a candidate view.
     */
    private int getCandidateMinimumHeight() {
        return mCandidateMinimumHeight;
    }

    /**
     * Create a view for a candidate.
     * @return the view
     */
    private TextView createCandidateView() {
        TextView text = new TextView(mViewBodyScroll.getContext());
        text.setTextSize(20);
        text.setBackgroundResource(R.drawable.cand_back);
        text.setGravity(Gravity.CENTER);
        text.setSingleLine();
        text.setPadding(4, 4, 4, 4);
        text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                           ViewGroup.LayoutParams.WRAP_CONTENT,
                                                           1.0f));
        text.setMinHeight(getCandidateMinimumHeight());
        text.setMinimumWidth(getCandidateMinimumWidth());
        return text;
    }
    
    /** @see CandidatesViewManager#getMaxLine */
    private int getMaxLine() {
    	
        int maxLine = (mPortrait) ? LINE_NUM_PORTRAIT : LINE_NUM_LANDSCAPE;
        return maxLine;
    }
    
    /**
     * Create AbsoluteLayout.LayoutParams
     * @param layout AbsoluteLayout
     * @param width
     * @param height
     * @return ViewGroup.LayoutParams
     */
    private ViewGroup.LayoutParams buildLayoutParams(AbsoluteLayout layout, int width, int height) {

        int indentWidth = mViewWidth / FULL_VIEW_DIV;
        int x         = indentWidth * mFullViewOccupyCount;
        int nomalLine = (mPortrait) ? LINE_NUM_PORTRAIT : LINE_NUM_LANDSCAPE;
        int y         = getCandidateMinimumHeight() * (mLineCount - nomalLine - 1);
        ViewGroup.LayoutParams params
              = new AbsoluteLayout.LayoutParams(width, height, x, y);

        return params;
    }
    
    
    
    
    /** Event listener for clicking a candidate */
    /**
     * 選択した時
     */
    private OnClickListener mCandidateOnClick = new OnClickListener() {
            public void onClick(View v) {
            	
            	Log.d("mCandidateOnClick","(((((((((((((((((((((((mCandidateOnClick:))))))))))))))))))))))))))");
                if (!v.isShown()) {
                    return;
                }
                
                if (v instanceof TextView) {
                	Log.d("mCandidateOnClick","(((((((((((((((((((((((v:))))))))))))))))))))))))))");
                    TextView text = (TextView)v;
                    int wordcount = text.getId();
                    String word = null;
                    word = mWnnWordArray.get(wordcount);
                    selectCandidate(word);
                }
            }
        };
        
        

}
