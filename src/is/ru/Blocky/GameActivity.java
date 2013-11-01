package is.ru.Blocky;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
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
    private Challenge currentChallenge;
    Button buttonPrevious;
    Button buttonNext;
    BoardView gameBoard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        gameBoard = (BoardView)findViewById(R.id.gameboard);
        buttonPrevious = (Button) findViewById(R.id.buttonPrev);
        buttonNext = (Button) findViewById(R.id.buttonNext);

        gameBoard.setMoveEventHandler(new OnMoveEventHandler() {
            @Override
            public void onMove(int blockId, int offset) {
                //String point = "Move block id: " + blockId + " by " + offset + " points.";
                //Toast.makeText(getApplicationContext(), point, Toast.LENGTH_SHORT).show();
                //TODO: update moveBlock function in Challenge.java so that it checks if it is allowed to move
                currentChallenge.moveBlock(blockId, offset);
                gameBoard.invalidate();
            }
        });

        buttonPrevious.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( currentChallenge.getId() > 1 ) {
                    changeChallenge(currentChallenge.getId() - 1);
                }
            }
        });

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( currentChallenge.getId() < challenges.size()) {
                    changeChallenge(currentChallenge.getId() + 1);
                }
            }
        });

        String stringXmlContent;
        try {
            challenges = ChallengeParser.getChallenges(this);
            if(challenges.size() > 0) {
                Bundle b = getIntent().getExtras();
                if(b != null) {
                int value = b.getInt("level");
                    changeChallenge(value);
                } else {
                    changeChallenge(1);
                }

            }
        } catch (XmlPullParserException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void changeChallenge(int id) {
        if(challenges.size() > id - 1) {
            currentChallenge = challenges.get(id - 1);
        }

        buttonPrevious.setEnabled(!currentChallengeIsFirst());
        buttonNext.setEnabled(!currentChallengeIsLast());

        gameBoard.setChallenge( currentChallenge );
    }

    private boolean currentChallengeIsFirst() {
        return currentChallenge.getId() == 1;
    }

    private boolean currentChallengeIsLast() {
        return currentChallenge.getId() == challenges.size();
    }

}
