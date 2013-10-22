package edu.lynchburg;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;
/*
 * Since fragments are like little helper activities embedded in a host activity, they may be perfect for this.
 * This fragment will have no UI, it will sit on top of the host activity and record touches.
 * It may be that I need to set it as a DialogFragment
 */
public class TransparentFragment extends Fragment implements OnTouchListener {

	LinearLayout transLayer;
	OnTransparentFragmentTouchListener mListener;
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//transLayer = (LinearLayout)getActivity().findViewById(R.id.transLayer);
		//transLayer.setOnTouchListener(this);		
	}
	
	@Override
	public View onCreateView(LayoutInflater inf, ViewGroup container, Bundle savedInstanceState){
		//inflate the view
		View v = inf.inflate(R.layout.fragment_transparent, container, false);
		//initialize the layer
		transLayer = (LinearLayout) v.findViewById(R.id.transFrag);
		//set listener
		transLayer.setOnTouchListener(this);
		//return view
		return v;
	}
	
	@Override
	public void onAttach(Activity activity){
		super.onAttach(activity);
		try{
			mListener = (OnTransparentFragmentTouchListener)activity;
		} catch(ClassCastException e){
			throw new ClassCastException(activity.toString()+ "must implement OnTransparentFragmentTouch");
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		//Record the touch event
		Toast.makeText(getActivity(), "TransparentLayer: poke", Toast.LENGTH_SHORT).show();
		//Pass the event on to the thing it's listening to		
		mListener.onTransparentFragmentTouch(v, me);		
		return false;
	}
	
	public interface OnTransparentFragmentTouchListener{
		public void onTransparentFragmentTouch(View v, MotionEvent me);
	}
	
	public void addToActivity(int rootViewId, FragmentActivity baseActivity){
		//Obtain reference to FragmentManager of the baseActivity
		FragmentManager fManager = baseActivity.getSupportFragmentManager();
		//Begin a transaction
		FragmentTransaction fTransaction = fManager.beginTransaction();
		//Add this object to the root view
		fTransaction.add(rootViewId, this);
		//Finish the transaction
		fTransaction.commit();		
	}
	
	
}
