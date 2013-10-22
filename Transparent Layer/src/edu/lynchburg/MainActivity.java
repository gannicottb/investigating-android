package edu.lynchburg;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity implements OnTouchListener {
	LinearLayout baseLayer;
	TransparentLayout transLayer;
	Button btn1;
	Button btn2;
	Button btn3;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		baseLayer = (LinearLayout)findViewById(R.id.background);
		transLayer = (TransparentLayout)findViewById(R.id.transLayer);		
		
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
		
		/*Therefore, when the buttons are pressed through the transparent layer, first the transLayer gets it, then the button*/
		
	}
	
	/*This function is adapted from stackoverflow.com solution 
	(http://stackoverflow.com/questions/8685201/communication-between-custom-surfaceview-and-default-androids-views)*/	
	
	public void sendEventToBaseLayer(final MotionEvent me){		
		MainActivity.this.runOnUiThread(new Runnable(){
			public void run(){
				//This Runnable communicates between my transparent layer and the base layer
				baseLayer.dispatchTouchEvent(me);
			}
		});
	}
	

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
			//Toast.makeText(getBaseContext(), c, Toast.LENGTH_SHORT).show();		
		}		
		return false;
	}

}
