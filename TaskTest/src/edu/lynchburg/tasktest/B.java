package edu.lynchburg.tasktest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

//This is the second activity (B). 

public class B extends Activity implements OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_b);
		
		Button btn = (Button)findViewById(R.id.btnB);
		btn.setOnClickListener(this);
				
	}

	@Override
	public void onClick(View v){
		//go to C
		Intent i = new Intent(this, C.class);
		//i.addFlags();
		startActivity(i);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
