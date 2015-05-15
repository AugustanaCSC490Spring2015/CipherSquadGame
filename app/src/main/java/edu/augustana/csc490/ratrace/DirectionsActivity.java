package edu.augustana.csc490.ratrace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import edu.augustana.csc490.ratrace.R;

/**
 * @author CipherSquad
 * DirectionsActivity gets called from IntroActivity and displays the directions.
 * It also has a button to go to the IntroActivity again.
 */

public class DirectionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directions);

        //Button playButton = (Button) findViewById(R.id.playButton);
        TextView objectiveTextView = (TextView) findViewById(R.id.objectiveTextView);
        TextView howToPlayTextView = (TextView) findViewById(R.id.howToPlayTextView);
        TextView powerUpsTextView = (TextView) findViewById(R.id.powerupsTextView);

        //playButton.setOnClickListener(playButtonListener);
        objectiveTextView.setText("Objective: Guide the mouse through the maze!");
        howToPlayTextView.setText("How to Play: Use your finger to move the mouse from the upper " +
                "left corner to the lower right corner and collect power ups along the way");
        powerUpsTextView.setText("Power ups: Move the mouse over a power up to increase your score");
    }

    // playButtonListener adds an anonymous listener to the play button that sends you to the
    // IntroActivity
    /*OnClickListener playButtonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent playIntent = new Intent(DirectionsActivity.this, IntroActivity.class);
            startActivity(playIntent);
        }
    };*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_directions, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
