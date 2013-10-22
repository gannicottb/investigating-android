package edu.lynchburg.heatmaptest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.Toast;

public class DrawPanel extends View implements OnTouchListener{

	Grid theGrid;

	
	public DrawPanel(Context context) {		
		super(context);
		
		setFocusable(true);
		setFocusableInTouchMode(true);
		WindowManager wm =(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		setOnTouchListener(this);
		
		//Initialize Grid with window information	
		theGrid = new Grid(display.getHeight(), display.getWidth());
	
	}

	@Override
	public void onDraw(Canvas canvas){		
		
		//Put a square of the appropriate color in each square of the grid
		for(int row = 0; row < theGrid.NUMROWS; row++){
			for(int col = 0; col < theGrid.NUMCOLS; col++ ){
				if(theGrid.cells[row][col]>0){
					//Draw a rectangle with the right color in the right place
					Rect toDraw = theGrid.getCellToColor(row, col);
					canvas.drawRect(toDraw, theGrid.getHeat(row, col));
				}
			}
		}		
	}
	
	@Override
	public boolean onTouch(View v, MotionEvent event){
		
    	theGrid.update(event.getX(), event.getY());
    	invalidate();    	
    	return false;
    }
	private class Grid{
		int ScreenHeight, ScreenWidth, CellSizeX, CellSizeY, CellOffsetX, CellOffsetY;
		Grid(int windowHeight, int windowWidth){
			
			ScreenHeight = windowHeight;
			ScreenWidth = windowWidth;
			CellSizeX = windowWidth/NUMCOLS;
			CellSizeY = windowHeight/NUMROWS;
			CellOffsetX = CellSizeX/2;
			CellOffsetY = CellSizeY/2;
			
			GridPaint = new Paint();
			GridPaint.setStyle(Paint.Style.FILL);
			GridPaint.setColor(spectrum[0]);
			
		}
		
		Paint GridPaint;
		int[] spectrum = {Color.argb(255, 0, 0, 255),
						Color.argb(255, 25, 0, 200),
						Color.argb(255, 50, 0, 175),
						Color.argb(255, 75, 0, 150),
						Color.argb(255, 100, 0, 125),
						Color.argb(255, 125, 0, 100),
						Color.argb(255, 150, 0, 75),
						Color.argb(255, 175, 0, 50),
						Color.argb(255, 200, 0, 25),
						Color.argb(255, 255, 0, 0)		
						}; //Cold(BLUE) to Hot(RED)
		
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
