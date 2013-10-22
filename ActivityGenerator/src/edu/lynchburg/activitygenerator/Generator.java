package edu.lynchburg.activitygenerator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Debug.MemoryInfo;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Generator extends Activity implements OnClickListener {
	public static String FAT_DATA = "fd";
	public static String PID = "p";
	List<Integer> processes;
	Button createB, createF, killP;
	TextView status, procList;
	EditText pidEntry;
	ActivityManager.MemoryInfo memInfo;
	ActivityManager am;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_generator);
		
		createB = (Button)findViewById(R.id.createB);
		createB.setOnClickListener(this);
		createF = (Button)findViewById(R.id.createF);
		createF.setOnClickListener(this);
		killP = (Button)findViewById(R.id.killP);
		killP.setOnClickListener(this);
		
		status = (TextView)findViewById(R.id.gen_status);
		procList = (TextView)findViewById(R.id.processList);
		pidEntry = (EditText)findViewById(R.id.pidEntry);
		processes = new ArrayList<Integer>();
		//displayInfo
		
		//display generator PID
		am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
		memInfo = new ActivityManager.MemoryInfo();
		updateStatus();
		
	}
	
	@Override
	protected void onNewIntent(Intent i)
	{
		super.onNewIntent(i);	
		Toast.makeText(getApplicationContext(), "Generator got a new Intent!", Toast.LENGTH_SHORT).show();
		
		setIntent(i);
		Intent update = getIntent();
		if(update.hasExtra(PID))
		{			
			processes.add(update.getIntExtra(PID, -1));
		}
		
		updateList();
		updateStatus();
	}
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		Toast.makeText(getApplicationContext(), "Generator is dead!", Toast.LENGTH_SHORT).show();
	}
	
	@Override
	protected void onRestart()
	{
		super.onRestart();
		Toast.makeText(getApplicationContext(), "Generator is restarting!", Toast.LENGTH_SHORT).show();
		updateStatus();
	}
	@Override
	protected void onResume()
	{
		super.onResume();
		Toast.makeText(getApplicationContext(), "Generator is resuming!", Toast.LENGTH_SHORT).show();
		updateList();
		updateStatus();
	}
	
	private void updateList()
	{
		Iterator<Integer> iter = processes.iterator();
		String current = "";
		while(iter.hasNext())
			current += "PID: "+iter.next()+"\n";
		procList.setText(current);		
	}
	
	private void updateStatus()
	{
		am.getMemoryInfo(memInfo);
		//consult the pids sent back from child activities
		int[] pids;
		//MemoryInfo[] processMemInfo = am.getProcessMemoryInfo(pids);
		
		status.setText("Generator PID: "+android.os.Process.myPid()+
						"\nAvailable Mem: "+memInfo.availMem);
	}
	
	private void killProcess()
	{
		int pidToKill =  Integer.valueOf(pidEntry.getText().toString());
		Log.i("killProcess()","PID: "+pidToKill);
		android.os.Process.killProcess(pidToKill);
		
	}
	
	public void createNewFatActivity()
	{	
		//create the intent
		Intent i = new Intent(this, BlankActivity.class);
		//bundle in any data I want the activity to store
		//Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.medium_img);
		//i.putExtra(FAT_DATA, bMap);
		//start the activity
		startActivity(i);
		//the activity will have a button to return here.
	
	}
	
	public void createNewBlankActivity()
	{	
		//create the intent
		Intent i = new Intent(this, BlankActivity.class);
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		i.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
		//start the activity
		startActivity(i);
		//the activity will have a button to return here.	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.generator, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId())
		{
		case R.id.createB:
			createNewBlankActivity();
			break;
		case R.id.createF:
			createNewFatActivity();
			break;
		case R.id.killP:
			killProcess();
		default:
			break;
		}
	}

}
