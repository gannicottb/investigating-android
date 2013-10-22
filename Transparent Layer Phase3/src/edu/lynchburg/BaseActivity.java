package edu.lynchburg;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
/*
 * This is the base Activity that implements a layout with buttons. It will run a transparent Activity concurrently.
 * The transparent Activity will record motion events for its own purposes, then transmit the event to the base Activity.
 */
public class BaseActivity extends Activity implements OnTouchListener{
	LinearLayout baseLayer;	
	Button btn1;
	Button btn2;
	Button btn3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_base);
				
		baseLayer = (LinearLayout)findViewById(R.id.background);
		//transLayer = (TransparentLayout)findViewById(R.id.transLayer);		
		
		/*The activity is listening to the LinearLayout as well*/
		baseLayer.setOnTouchListener(this);			
		
		/*The buttons have to be instantiated as objects*/
		btn1 = (Button)findViewById(R.id.button1);
		btn2 = (Button)findViewById(R.id.button2);
		btn3 = (Button)findViewById(R.id.button3);
		
		/*The activity is listening to the buttons*/
		btn1.setOnTouchListener(this);
		btn2.setOnTouchListener(this);
		btn3.setOnTouchListener(this);
		
		/*Now, pass the MotionEvent from the Transparent Layer to our onTouch*/
		 //Get Intent from TransparentActivity
		Intent swap = getIntent();
		String action = swap.getAction();
		if(!swap.getAction().equalsIgnoreCase(Intent.ACTION_MAIN))			
		{
			//Extract the motion event
			MotionEvent me = swap.getParcelableExtra(TransparentActivity.SWAP_EVENT);
			//Build a dummy View
			View v = new View(this);
			//Set the dummy View's id to the id from the Intent
			v.setId(swap.getIntExtra(TransparentActivity.SWAP_VIEW, 0));
			//Call onTouch method
			onTouch(v, me);	
		}
	}
	
	/*This function is adapted from stackoverflow.com solution 
	(http://stackoverflow.com/questions/8685201/communication-between-custom-surfaceview-and-default-androids-views)*/	
	/*
	public void sendEventToBaseLayer(final MotionEvent me){		
		BaseActivity.this.runOnUiThread(new Runnable(){
			public void run(){
				//This Runnable communicates between my transparent activity and the base activity
				//baseLayer.dispatchTouchEvent(me);
				Toast.makeText(getBaseContext(), "BaseLayer: Roger that!", Toast.LENGTH_SHORT).show();
			}
		});
	}
	*/
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		CharSequence text = "poke";
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			switch(v.getId()){
			case R.id.button1:	
				text = "btn1";					
				break;
			case R.id.button2:
				text = "btn2";				
				break;
			case R.id.button3:
				text = "btn3";			
				break;			
			}
			CharSequence c = "baseLayer: "+ text;
			Toast.makeText(getBaseContext(), c, Toast.LENGTH_SHORT).show();		
		}		
		//Swap back to the TransparentActivity
		Intent swapBack = new Intent(this, TransparentActivity.class);		
		startActivity(swapBack);	
		return false;
	}

}
