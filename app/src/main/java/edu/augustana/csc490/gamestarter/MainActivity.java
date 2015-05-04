// GameStarter.java
// MainActivity displays the MainGameFragment
package edu.augustana.csc490.gamestarter;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;

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

