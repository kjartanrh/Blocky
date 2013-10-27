package is.ru.Blocky;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.graphics.drawable.shapes.RectShape;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

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
    private int m_cellWidth = 0;
    private int m_cellHeight = 0;
    private char[][] m_board = new char[6][6];
    private Paint m_paint = new Paint();

    ShapeDrawable m_shape = new ShapeDrawable( new RectShape() );
    Rect board = new Rect();
    Challenge currentChallenge;

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        m_paint.setColor( Color.WHITE );
        m_paint.setStyle( Paint.Style.STROKE );
    }

    /*  WAS USED IN TIC TAC TOE, FOR LOOP DIFFICULT
    public void setBoard( String string )
    {
        for ( int idx=0, r=5; r>=0; --r ) {
            for ( int c=0; c<6; ++c, ++idx ) {
                m_board[c][r] = string.charAt( idx );
            }
        }
        invalidate();
    }
    */

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
                        if( block.occupies(c,r)){
                            m_shape.draw( canvas );
                        }
                    }
                }


                //board.inset( (int)(board.width() * 0.1), (int)(board.height() * 0.1) );
                /*m_shape.setBounds( m_rect );
                switch ( m_board[c][r] ) {
                    case 'x':
                        m_shape.getPaint().setColor( Color.RED );
                        m_shape.draw( canvas );
                        break;
                    case 'o':
                        m_shape.getPaint().setColor( Color.BLUE );
                        m_shape.draw( canvas );
                        break;
                    default:
                        break;
                }*/

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
}
