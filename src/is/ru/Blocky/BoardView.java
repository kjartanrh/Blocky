package is.ru.Blocky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 22.10.2013
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public class BoardView extends View {
    public enum Orientation{
        VERTICAL,
        HORIZONTAL
    }

    private class MyShape {
        MyShape( Rect r, int c, Orientation or ) {
            rect = r;
            color = c;
            orient = or;
        }
        Rect rect;
        int  color;
        Orientation orient;
    }

    Paint mPaint = new Paint();
    ArrayList<MyShape> mShapes = new ArrayList<MyShape>();
    MyShape mMovingShape = null;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mShapes.add(new MyShape(new Rect(0, 25, 50, 100), Color.RED, Orientation.HORIZONTAL));
        mShapes.add(new MyShape(new Rect(200, 300, 300, 350), Color.BLUE, Orientation.VERTICAL));
    }

    protected void onDraw( Canvas canvas ) {
        for ( MyShape shape : mShapes ) {
            mPaint.setColor( shape.color );
            canvas.drawRect( shape.rect, mPaint );
        }
    }

    public boolean onTouchEvent( MotionEvent event ) {

        int x = (int) event.getX();
        int y = (int) event.getY();

        switch ( event.getAction() ) {
            case MotionEvent.ACTION_DOWN:
                mMovingShape = findShape( x, y );
                break;
            case MotionEvent.ACTION_UP:
                if ( mMovingShape != null ) {
                    mMovingShape = null;
                    // emit an custom event ....
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if ( mMovingShape != null ) {
                    x = Math.min( x, getWidth() - mMovingShape.rect.width() );
                    mMovingShape.rect.offsetTo( x, y );
                    invalidate();
                }
                break;
        }
        return true;
    }

    private MyShape findShape( int x, int y ) {
        for ( MyShape shape : mShapes ) {
            if ( shape.rect.contains( x, y ) ) {
                return shape;
            }
        }
        return null;
    }
}
