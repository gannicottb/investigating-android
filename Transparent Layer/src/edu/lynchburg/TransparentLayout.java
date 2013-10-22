package edu.lynchburg;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TransparentLayout extends LinearLayout implements OnTouchListener {
	
	Grid theGrid;
	
	public TransparentLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setFocusable(true);
		setFocusableInTouchMode(true);
		
		//Initialize Grid with window information	
		WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();			
		theGrid = new Grid(display.getHeight(), display.getWidth());
		
		//set touch listener
		setOnTouchListener(this);		
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev){
		return true; //consume all touch events
	}
	@Override
	public void onDraw(Canvas canvas){
		//Put a square of the appropriate color in each square of the grid
		for(int row = 0; row < theGrid.NUMROWS; row++){
			for(int col = 0; col < theGrid.NUMCOLS; col++ ){
				if(theGrid.cells[row][col]>0){
					//Draw a rectangle with the right color in the right place							
					canvas.drawRect(theGrid.getCellToColor(row, col), theGrid.getHeat(row, col));
				}
			}
		}		
	}

	@Override
	public boolean onTouch(View v, MotionEvent me) {
		//Record the touch event
		//Toast.makeText(getContext(), "TransparentLayer: poke", Toast.LENGTH_SHORT).show();
		//Pass the event on to the thing it's listening to
		//((MainActivity)getContext()).sendEventToBaseLayer(me);
		theGrid.update(me.getX(), me.getY());
    	invalidate();    	
    	
		return false;
	}
	
	private class Grid{
		int CellSizeX, CellSizeY, CellOffsetX, CellOffsetY;
		final int ALPHA = 125;
		final int NUM_COLORS = 12;
		int[] spectrum;
		Grid(int windowHeight, int windowWidth){
				
			CellSizeX = windowWidth/NUMCOLS;
			CellSizeY = windowHeight/NUMROWS;
			CellOffsetX = CellSizeX/2;
			CellOffsetY = CellSizeY/2;
			
			initSpectrum();
			
			GridPaint = new Paint();
			GridPaint.setStyle(Paint.Style.FILL);
			GridPaint.setColor(spectrum[0]);
			
		}
		
		Paint GridPaint;
		private void initSpectrum(){	
			spectrum = new int[NUM_COLORS];
			int maxRGB = 255;
			int red = 0;
			int green = 0;
			int blue = maxRGB;			
			
			for(int i = 0; i < NUM_COLORS; i++){
				spectrum[i]=Color.argb(ALPHA, red+(i*(maxRGB/(NUM_COLORS-1))), 
											green, 
											blue-(i*(maxRGB/(NUM_COLORS-1))));
			}
			
		}
		public final int NUMROWS = 10;
		public final int NUMCOLS = 10;
		//each cell holds the number of times it's been touched
		//the r and c value for each cell can be mathematically transformed into screen coordinates
		int[][] cells = new int [NUMROWS][NUMCOLS];		
		
		int convertToScreenCoordX(int index){			
			return (index*CellSizeX)+CellOffsetX;
		}
		int convertToScreenCoordY(int index){			
			return (index*CellSizeY)+CellOffsetY;
		}
		public Rect getCellToColor (int row, int col){
			
			//Now, depending on the number of touches, a different color
			int left 	= convertToScreenCoordX(col)-CellOffsetX;
			int top 	=  convertToScreenCoordY(row)-CellOffsetY;
			int right = convertToScreenCoordX(col)+CellOffsetX;
			int bottom = convertToScreenCoordY(row)+CellOffsetY;
			//drawRect(left, top, right, bottom, GridPaint);
			return new Rect(left, top, right, bottom);
		}
		public void update(float x, float y){
			//What cell should be incremented and used to draw with?
			int col = (int)x/CellSizeX;
			int row = (int)y/CellSizeY;
			//Increment the counter for that cell
			if(cells[row][col] <= spectrum.length-1) //no more than 10 touches will be recorded
				cells[row][col]++;			
		}
		public Paint getHeat(int row, int col){
			//Set the grid paint color to the correct color
			GridPaint.setColor(spectrum[cells[row][col]-1]);
			return GridPaint;
		}
		
		
	}

}


