package jp.co.shiatsu.ime;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class PocketBell extends Activity  implements OnClickListener,OnFocusChangeListener{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);
        EditText text = (EditText) this.findViewById(R.id.text);
        text.setOnFocusChangeListener(this);
        Button topButton = (Button) this.findViewById(R.id.top);
        topButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
			case R.id.top:
				Intent new_intent = new Intent(android.provider.Settings.ACTION_INPUT_METHOD_SETTINGS);
				startActivity(new_intent);
				break;
	
			default:
				break;
		}
	}

	@Override
    public void onFocusChange(View v, boolean hasFocus) {
        // TODO Auto-generated method stub
        if(hasFocus == false){
            InputMethodManager imm =
                                     (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(v.getWindowToken(),0);
        }
    }
}