package edu.lynchburg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;
/*
 *This Activity swaps with the other activity when it gets a touchEvent
 */
public class TransparentActivity extends Activity implements OnTouchListener {
	public final static String SWAP_VIEW = "edu.lynchburg.TransparentActivity.View";
	public final static String SWAP_EVENT = "edu.lynchburg.TransparentActivity.MotionEvent";
	LinearLayout transLayer;
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transparent);
		transLayer = (LinearLayout)findViewById(R.id.transLayer);
		transLayer.setOnTouchListener(this);		
	}
	
	
	
	@Override
	public boolean onTouch(View v, MotionEvent me) {
		//Record the touch event
		Toast.makeText(getBaseContext(), "TransparentLayer: poke", Toast.LENGTH_SHORT).show();
		//Pass the event on to the thing it's listening to
		Intent swap = new Intent(this, BaseActivity.class);
		//put the view's Id in the swap intent
		swap.putExtra(SWAP_VIEW, v.getId());
		//put the motion event in the swap intent
		swap.putExtra(SWAP_EVENT, me);
		swap.setAction(getPackageName());
		startActivity(swap);	
		//if this returns true, the event will stop here.
		return false;
	}
	
	public interface OnTransparentFragmentTouchListener{
		public void onTransparentFragmentTouch(View v, MotionEvent me);
	}
	
	
}
