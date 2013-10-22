package edu.lynchburg.activitygenerator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BlankActivity extends Activity implements OnClickListener {
	
	ImageView img;
	Button dismiss;	
	TextView status;
	int pid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new);
		
		pid = android.os.Process.myPid();	
		//am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		status = (TextView)findViewById(R.id.status);
		status.setText("PID: "+pid);
		img = (ImageView)findViewById(R.id.img);
		dismiss = (Button)findViewById(R.id.dismiss);	
		dismiss.setOnClickListener(this);
				
		Intent fromGen = getIntent();
		if(fromGen.hasExtra(Generator.FAT_DATA))
		{
			//get the data into a Bitmap and put it into the ImageView object.
			img.setImageBitmap((Bitmap)fromGen.getParcelableExtra(Generator.FAT_DATA));			
		}	
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//create intent
		Intent i = new Intent(this, Generator.class);
		//add the Pid to display in Generator	
		i.putExtra(Generator.PID, pid);
		//i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
		startActivity(i);
		//start generator (which is singletask, so you're really just going back to it without destroying this one)
		//At least in theory. It appears that this activity is destroyed when you go back down the stack.
	}
	
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(getApplicationContext(), pid+" is dead!", Toast.LENGTH_SHORT).show();
	}
	
	@Override 
	protected void onStop()
	{
		super.onStop();
		Toast.makeText(getApplicationContext(), pid+" is stopped!", Toast.LENGTH_SHORT).show();
	}

}
