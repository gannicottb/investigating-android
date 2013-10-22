package edu.lynchburg.taskbomb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tv;
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//Display the count from the last activity
		//tv = (TextView)findViewById(R.id.status);
		int count = 0;
		Intent i = getIntent();
		if(i.hasExtra("count"))
		{
			count = i.getIntExtra("count", 0);
			count++;
		}		
		//tv.setText("Activity count = "+ count);
		//tv.invalidate();
		//Toast.makeText(getBaseContext(), "Activity Count= "+count, Toast.LENGTH_SHORT).show();
		Log.i("MainActivity", "Instance number: "+count);
		//Send the count to the next Activity
		Intent next = new Intent(this, MainActivity.class);
		next.putExtra("count", count);
		next.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(next);
	}



}
