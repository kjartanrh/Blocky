package is.ru.Blocky;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.*;
import org.xmlpull.v1.XmlPullParserException;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 24.10.2013
 * Time: 18:40
 * To change this template use File | Settings | File Templates.
 */
public class PuzzleMenuActivity extends ListActivity {

    private PuzzlesDbAdapter puzzlesDbAdapter = new PuzzlesDbAdapter( this );
    private SimpleCursorAdapter cursorAdapter;
    private List<Challenge> challenges = new ArrayList<Challenge>();


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String stringXmlContent;
        try {
            challenges = ChallengeParser.getChallenges(this);
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        List<String> values = new ArrayList<String>();
        for (int i = 1; i <= challenges.size(); i++) {
            values.add("Puzzle " + i);
        }


        Cursor cursor = puzzlesDbAdapter.queryPuzzlesSolved();
        String[] cols = DBHelper.TablePuzzlesSolvedCol;
        String[] from = { cols[1], cols[2] };
        int[] to = { R.id.s_puzzlename, R.id.s_completed };

        startManagingCursor( cursor );
        cursorAdapter = new SimpleCursorAdapter( this, R.layout.selectpuzzle, cursor, from, to );

        cursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int i) {
                if ( i == 1 ) {
                    ((TextView) view).setText("Puzzle " + cursor.getInt(i));
                    return true;
                }
                if ( i == 2 ) {
                    ((ImageView) view).setImageResource(
                            (cursor.getInt(i) == 0) ? R.drawable.uncompleted : R.drawable.completed );
                    return true;
                }
                return false;
            }
        });

        setListAdapter(cursorAdapter);
    }

    protected void onListItemClick( ListView l, View v, int position, long id  ) {
        Intent intent = new Intent(PuzzleMenuActivity.this, GameActivity.class);
        Bundle b = new Bundle();
        b.putInt("level", (int)(id));
        intent.putExtras(b);
        startActivity(intent);
    }

    protected void onRestart() {
        super.onRestart();
        cursorAdapter.getCursor().requery();
    }

    protected void onDestroy() {
        super.onRestart();
        puzzlesDbAdapter.close();
    }
}