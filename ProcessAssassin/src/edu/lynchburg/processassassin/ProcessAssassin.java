package edu.lynchburg.processassassin;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProcessAssassin extends Activity implements OnClickListener {
	
	private static EditText pidEntry;
	private static Button killProcess;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_assassin);
		
		pidEntry = (EditText)findViewById(R.id.pidEntry);
		killProcess = (Button)findViewById(R.id.killP);
		
		killProcess.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.process_assassin, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		//Kill the process id indicated by pidEntry
		int pid = Integer.valueOf(pidEntry.getText().toString());		
		android.os.Process.killProcess(pid);		
		Toast.makeText(getApplicationContext(), pid+" has been sent a SIG9", Toast.LENGTH_SHORT).show();
		
	}

}
