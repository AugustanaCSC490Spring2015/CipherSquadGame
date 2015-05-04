package edu.augustana.csc490.gamestarter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;


public class IntroActivity extends Activity {

    int size; //initial maze size entered by user

    EditText sizeEditText; //for user to enter the desired size for the initial maze
    Button launchButton;
    Button scoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        launchButton = (Button) findViewById(R.id.launchButton);
        scoresButton = (Button) findViewById(R.id.scoresButton);
        sizeEditText = (EditText) findViewById(R.id.sizeEditText);

        launchButton.setOnClickListener(launchClickHandler);
        scoresButton.setOnClickListener(scoresClickHandler);

    }

    // click handler used to launch the game when launchButton is clicked
    View.OnClickListener launchClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            size = Integer.parseInt(sizeEditText.getText().toString());

            intent.putExtra("size", size);
            startActivity(intent);
        }
    };

    // click handler used to view the high scores when scoreButton is clicked
    View.OnClickListener scoresClickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(IntroActivity.this, ScoresActivity.class);

            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
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
