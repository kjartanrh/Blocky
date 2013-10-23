package is.ru.Blocky;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Notandi
 * Date: 22.10.2013
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity {

    private List<Challenge> challenges = new ArrayList<Challenge>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        TextView myXmlContent = (TextView)findViewById(R.id.gameboard);
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
    }
}
