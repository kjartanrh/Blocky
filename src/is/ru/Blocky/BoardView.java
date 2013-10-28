package is.ru.Blocky;

import android.content.Context;
import android.graphics.*;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 22.10.2013
 * Time: 19:10
 * To change this template use File | Settings | File Templates.
 */
public class BoardView extends View {
    private int m_cellWidth = 0;
    private int m_cellHeight = 0;
    private char[][] m_board = new char[6][6];
    private Paint m_paint = new Paint();
    private OnMoveEventHandler moveHandler;
    private Challenge.BlockPosition blockToMove;
    private Point selectPoint;
    private boolean isDown = false;

    ShapeDrawable m_shape = new ShapeDrawable( new RectShape() );
    Rect board = new Rect();
    Challenge currentChallenge;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_paint.setColor( Color.WHITE );
        m_paint.setStyle( Paint.Style.STROKE );
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        m_cellWidth = xNew / 6;
        m_cellHeight = yNew / 6;
    }

    public void onDraw( Canvas canvas )
    {
        for ( int r=0; r<6; ++r ) {
            for ( int c=0; c<6; ++c ) {
                board.set(c * m_cellWidth, r * m_cellHeight,
                        c * m_cellWidth + m_cellWidth, r * m_cellHeight + m_cellHeight);
                canvas.drawRect( board, m_paint );

                m_shape.setBounds( board );
                if( currentChallenge != null )
                {
                    for(Challenge.BlockPosition block : currentChallenge.getBlocks() ){
                        m_shape.getPaint().setColor( Color.RED );

                        if( block.isEscapee() ) {
                            m_shape.getPaint().setColor( Color.BLUE );
                        }
                        if( block.contains(c, r)){
                            m_shape.draw( canvas );
                        }
                    }
                }
            }
        }
    }

    private int xToCol( int x ) {
        return x / m_cellWidth;
    }

    private int yToRow( int y ) {
        return y / m_cellHeight;
    }

    public void setChallenge( Challenge challenge ) {
        this.currentChallenge = challenge;
        invalidate();
    }

    public boolean onTouchEvent(MotionEvent event ) {
        Point point = new Point();
        point.x = (int) event.getX();
        point.y = (int) event.getY();

        if ( event.getAction() == MotionEvent.ACTION_DOWN && moveHandler != null) {
            try {
                blockToMove = currentChallenge.getBlockAtXY(xToCol(point.x), yToRow(point.y));
                selectPoint = new Point();
                selectPoint.set(xToCol(point.x), yToRow(point.y));
                this.isDown = true;
            } catch (BlockNotFoundException ex) {
                blockToMove = null;
            }
        } else if (event.getAction() == MotionEvent.ACTION_MOVE
                                     && isDown
                                     && moveHandler != null
                                     && blockToMove != null) {
            int offset = 0;

            if(blockToMove.getAlignment() == Challenge.Alignment.Horizontal) {
                offset = xToCol(point.x) - selectPoint.x;
            } else {
                offset = yToRow(point.y) - selectPoint.y;
            }
            if( offset != 0) {
                moveHandler.onMove(blockToMove.getIndex(), offset);
                selectPoint = new Point();
                selectPoint.set(xToCol(point.x), yToRow(point.y));
            }

        } else if (event.getAction() == MotionEvent.ACTION_UP && moveHandler != null) {
            isDown = false;
        }


        return true;
    }

    public void setMoveEventHandler( OnMoveEventHandler handler ) {
        moveHandler = handler;
    }
}
