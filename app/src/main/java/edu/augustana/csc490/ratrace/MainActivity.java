// GameStarter.java
package edu.augustana.csc490.ratrace;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

import edu.augustana.csc490.gamestarter.R;

/**
 * @author CypherSquad
 * MainActivity displays the MainGameFragment as well as pulls the GAME_SCORES from
 * SharedPreferences so that evvery time you open the app the scores are saved from last time.
 */

public class MainActivity extends Activity {
    static SharedPreferences gameScores;
    public static final String GAME_SCORES = "ScoreFile";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameScores = getSharedPreferences(GAME_SCORES, 0);
    }
}

