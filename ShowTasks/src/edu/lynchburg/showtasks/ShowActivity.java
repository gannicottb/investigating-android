package edu.lynchburg.showtasks;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.widget.TextView;

public class ShowActivity extends Activity {

	ActivityManager aManager;
	TextView tv;
	int numTasks = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show);
		
		tv = (TextView)findViewById(R.id.output);
		aManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		
		
		showTasks(numTasks);		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		showTasks(numTasks);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.show, menu);
		return true;
	}
	
	public void showTasks(int numTasks){
		List<RunningTaskInfo> taskList = aManager.getRunningTasks(numTasks);
		
		String output = "Currently running tasks, most recent on top. Total: "+taskList.size()+"\n\n";
		for(int i=0; i < taskList.size(); i++)
		{
			RunningTaskInfo rti = taskList.get(i);
			output += "Task id = "+rti.id+".\n Base Activity name = "+rti.baseActivity.toShortString()+"\n\n";			
		}
		
		tv.setText(output);
	}

}
