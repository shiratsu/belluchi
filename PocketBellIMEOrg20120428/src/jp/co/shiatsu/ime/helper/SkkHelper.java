package jp.co.shiatsu.ime.helper;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

public class SkkHelper extends SQLiteOpenHelper{

	
	//The Android
    private static String DB_PATH = "/data/data/jp.co.shiatsu.ime/databases/"; 
	
	// 
    static final String DB_NAME_ASSET="skk.db";
    
    private static String DB_NAME = "skk";
    
    //
    static final String TABLE = "dictionary";
    static final String TAG = "SkkHelper";
    
    // 
    static final int DB_VERSION=1;
    
    private final Context mContext; 
    private SQLiteDatabase mDataBase;
	
	public SkkHelper(Context context) {
		// 
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		this.mContext = context;
	}

	/** 
     * asset 
     *  
     **/  
    public void createEmptyDataBase() throws IOException{  
        boolean dbExist = checkDataBaseExists();  
  
        if(dbExist){  
            // 
        }else{  
            //  
            this.getReadableDatabase();  
            try {  
                // asset   
                copyDataBaseFromAsset();
            } catch (IOException e) {
            	throw new Error(e.toString());
            	
            } 
            
        }  
    }
	
    
	//
	private void copyDataBaseFromAsset() throws IOException {
		// TODO Auto-generated method stub
		
		//try{
			
			// asset 
	        InputStream mInput = mContext.getAssets().open(DB_NAME_ASSET);  
	        
	        // 
	        String outFileName = DB_PATH + DB_NAME;  
	        
	        OutputStream mOutput = new FileOutputStream(outFileName); 
	        
	        //
	        byte[] buffer = new byte[1024];
	        int size;  
	        while ((size = mInput.read(buffer)) > 0){
	            mOutput.write(buffer, 0, size);  
	        }  
	        
	        //Close the streams  
	        mOutput.flush();  
	        mOutput.close();  
	        mInput.close(); 
		/*
		}catch(Exception e){
			Log.e("TAG",e.getMessage());
			e.printStackTrace(); 
		}*/
	}

	//
	private boolean checkDataBaseExists() {
		SQLiteDatabase checkDb = null;  
		   
        String dbPath = DB_PATH + DB_NAME;  
        try{  
            checkDb = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY);  
        }catch(SQLiteException e){  
            //
        	
            System.out.println(e.toString());
        }  
   
        if(checkDb != null){  
            checkDb.close();  
        }  
        return checkDb != null ? true : false;
	}

	public SQLiteDatabase openDataBase() throws SQLException{  
        //Open the database  
        String myPath = DB_PATH + DB_NAME;  
        mDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);  
        return mDataBase;  
    } 
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		Log.w(TAG,
                "Version mismatch :" + oldVersion + 
                " to " + newVersion );
		
	}
	/** 
     * 
     * @param is 
     * @return 
     * @throws IOException 
     */  
    private String readFile(InputStream is) throws IOException{  
        BufferedReader br = null;  
        try {  
            br = new BufferedReader(new InputStreamReader(is,"UTF8"));  
  
            StringBuilder sb = new StringBuilder();      
            String str;        
            while((str = br.readLine()) != null){        
                sb.append(str +"\n");       
            }      
            return sb.toString();  
        } finally {  
            if (br != null) br.close();  
        }  
    }

}
