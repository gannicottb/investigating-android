package edu.lynchburg.panicbutton;

import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {

	Button btn;
	ActivityManager aManager;
	int numTasks = 50;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btn = (Button)findViewById(R.id.button);
		btn.setOnClickListener(this);
		
		aManager = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		//Kill all tasks
		List<RunningTaskInfo> taskList = aManager.getRunningTasks(numTasks);
		for(int i = 0; i < taskList.size(); i++)
		{
			//Get the next task
			RunningTaskInfo t = taskList.get(i);
			Log.i("PanicButton.onClick()", "Next Task="+t.id);
			//If the task has activities in it
			if(t.numActivities > 0)
			{
				//Kill all processes associated with activities in the task
				for(int k = 0; k < t.numActivities; k++)
				{
					String pkgName = t.topActivity.getPackageName();
					Log.i("PanicButton.onClick(), numActivities in Task ="+t.numActivities,"Top Activity of "+t.id+" = "+pkgName);
					//Make sure we aren't trying to kill Home. I don't think I'm allowed to, but might as well be careful
					//if(!pkgName.equalsIgnoreCase("com.android.launcher"));
						aManager.killBackgroundProcesses(pkgName);
						
				}
			}
		
		}
		Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show();
		
	}

}
