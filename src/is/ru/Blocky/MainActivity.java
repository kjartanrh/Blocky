package is.ru.Blocky;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    Button m_buttonPlayGame;
    Button m_buttonSelectPuzzle;
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        m_buttonSelectPuzzle = (Button) findViewById( R.id.buttonSelectPuzzle);
        m_buttonPlayGame = (Button) findViewById( R.id.buttonPlayGame );

        m_buttonPlayGame.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        m_buttonSelectPuzzle.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, PuzzleMenuActivity.class);
                startActivity(intent);
            }
        });
    }
}
