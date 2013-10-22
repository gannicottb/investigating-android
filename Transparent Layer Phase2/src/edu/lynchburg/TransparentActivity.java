package edu.lynchburg;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TransparentActivity extends Activity implements OnTouchListener {
	LinearLayout transLayer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transparent);
		transLayer = (LinearLayout)findViewById(R.id.transLayer);
		transLayer.setOnTouchListener(this);
	}
	@Override
	public boolean onTouch(View v, MotionEvent me) {
		//Record the touch event
		Toast.makeText(getApplicationContext(), "TransparentLayer: poke", Toast.LENGTH_SHORT).show();
		//Pass the event on to the thing it's listening to
		//((BaseActivity)getApplicationContext()).sendEventToBaseLayer(me);   
		//sendEventToBaseLayer(me);   
		
		return false;
	}

}
