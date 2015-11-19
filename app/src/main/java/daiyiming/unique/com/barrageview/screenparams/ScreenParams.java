package daiyiming.unique.com.barrageview.screenparams;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class ScreenParams {

	private int height = 0;
	private int width = 0;
	
	public ScreenParams(Context context){
		WindowManager windowManager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		
		height = metrics.heightPixels;
		width = metrics.widthPixels;
	}
	
	public int getScreenHeight(){
		return height;
	}
	
	public int getScreenWidth(){
		return width;
	}
	
}
