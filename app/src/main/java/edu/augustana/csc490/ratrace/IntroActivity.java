package edu.augustana.csc490.ratrace;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.text.InputType;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import edu.augustana.csc490.gamestarter.R;


public class IntroActivity extends Activity {

    int size = 5; //initial maze size entered by user
    String[] levelUpPaces = {"novice", "easy", "medium", "hard", "Expert"};
    String setPace = levelUpPaces[1];
    int setPaceElementNumber = 1; //this number is to help set and manage the radiogroupand should be equal at all times to setPace's element number
   // EditText sizeEditText; for user to enter the desired size for the initial maze
    Button launchButton;
    Button scoresButton;
    ImageButton difficultyButton;
    Button directionsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        launchButton = (Button) findViewById(R.id.launchButton);
        scoresButton = (Button) findViewById(R.id.scoresButton);
        //sizeEditText = (EditText) findViewById(R.id.sizeEditText);
        difficultyButton = (ImageButton) findViewById(R.id.difficultyImageButton);
        directionsButton = (Button) findViewById(R.id.directionsButton);


        launchButton.setOnClickListener(launchClickHandler);
        scoresButton.setOnClickListener(scoresClickHandler);
        difficultyButton.setOnClickListener(difficultyClickHandler);
        directionsButton.setOnClickListener(directionsListener);

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

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    String value = input.getText().toString();
                    Intent intent = new Intent(IntroActivity.this, MainActivity.class);

                    intent.putExtra("size", size);
                    intent.putExtra("initials", value);
                    intent.putExtra("difficulty", setPace);
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

    View.OnClickListener difficultyClickHandler = new View.OnClickListener(){
        @Override
        public void onClick(View view){
            final AlertDialog.Builder alert = new AlertDialog.Builder(IntroActivity.this);
            LayoutInflater inflater = IntroActivity.this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.dialog_settings, null);
            alert.setView(dialogView);
            final EditText startingDifficulty = (EditText) dialogView.findViewById(R.id.startingDifficultyEditText);
            final RadioGroup levelPace = (RadioGroup) dialogView.findViewById(R.id.levelPaceRadioGroup);
            startingDifficulty.setText(size + "");
            final int[] radioIds = {R.id.noviceRadioButton, R.id.easyRadioButton, R.id.mediumRadioButton,
                            R.id.hardRadioButton, R.id.expertRadioButton};
            RadioButton[] radioButtons = {(RadioButton)dialogView.findViewById(radioIds[0]),
                    (RadioButton)dialogView.findViewById(radioIds[1]),
                    (RadioButton)dialogView.findViewById(radioIds[2]),
                    (RadioButton)dialogView.findViewById(radioIds[3]),
                    (RadioButton)dialogView.findViewById(radioIds[4])};
            radioButtons[setPaceElementNumber].setChecked(true);

            alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    size = Integer.parseInt(startingDifficulty.getText().toString());
                    int checked = levelPace.getCheckedRadioButtonId();
                    for(int i = 0; i<radioIds.length;i++){
                        if(radioIds[i]==checked){
                            setPaceElementNumber = i;
                            setPace = levelUpPaces[i];
                        }
                    }


                }
            });


            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();

        }
    };

    View.OnClickListener directionsListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent directionsIntent = new Intent(IntroActivity.this, DirectionsActivity.class);
            startActivity(directionsIntent);
        }
    };
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
