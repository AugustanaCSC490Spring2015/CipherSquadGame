package edu.augustana.csc490.gamestarter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


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

            final AlertDialog.Builder alert = new AlertDialog.Builder(IntroActivity.this);

            alert.setTitle("Enter Initials");

            // Set an EditText view to get user input
            //Also set the edit text to only accept 3 characters
            final EditText input = new EditText(IntroActivity.this);
            int maxLength = 3;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(maxLength);
            input.setFilters(FilterArray);
            alert.setView(input);
            //alert.setView(input);

            alert.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);
                    size = Integer.parseInt(sizeEditText.getText().toString());
                    intent.putExtra("size", size);
                    intent.putExtra("initials", value);
                    startActivity(intent);

                }
            });


            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    //cancel the action
                }
            });

            alert.show();
            // see http://www.androidsnippets.com/prompt-user-input-with-an-alertdialog

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
