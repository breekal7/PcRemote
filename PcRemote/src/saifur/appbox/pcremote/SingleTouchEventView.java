package saifur.appbox.pcremote;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class SingleTouchEventView  extends View{
  public SingleTouchEventView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

private Paint paint = new Paint();
  private Path path = new Path();
  PathMeasure pm = new PathMeasure(path,false);
  public float l = 0;
  

  public float le(){
	  return l;
  }
  
  public boolean onTouchEvent(MotionEvent event) {
    float eventX = event.getX();
    float eventY = event.getY();

    switch (event.getAction()) {
    case MotionEvent.ACTION_DOWN:
      path.moveTo(eventX, eventY);
      return true;
    case MotionEvent.ACTION_MOVE:
      path.lineTo(eventX, eventY);
      pm.setPath(path, true);
      l = pm.getLength();
      break;
    case MotionEvent.ACTION_UP:
      // nothing to do
      break;
    default:
      return false;
    }

    // Schedules a repaint.
    invalidate();
    return true;
  }
} 