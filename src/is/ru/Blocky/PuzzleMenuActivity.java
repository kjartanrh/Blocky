package is.ru.Blocky;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import org.xmlpull.v1.XmlPullParserException;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
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

    SimpleCursorAdapter mAdapter;
    ListView puzzleList;
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

        setListAdapter(new ArrayAdapter<String>(this,R.layout.selectpuzzle,values));

        ListView listView = getListView();
        listView.setTextFilterEnabled(true);

        listView.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Intent intent = new Intent(PuzzleMenuActivity.this, GameActivity.class);
                Bundle b = new Bundle();
                b.putInt("level", (int)(id +1));
                intent.putExtras(b);
                startActivity(intent);
            }
        });
    }
}