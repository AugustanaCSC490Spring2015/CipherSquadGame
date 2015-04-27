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
    int algorithm; //algorithm for maze generation chosen by user

    EditText sizeEditText; //for user to enter the desired size for the initial maze

    RadioButton algo2; //Hunt and Kill algorithm
    RadioButton algo3; //Prim algorithm

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        Button launchButton = (Button) findViewById(R.id.launchButton);
        sizeEditText = (EditText) findViewById(R.id.sizeEditText);

        // algo1 RadioButton for Recursive Backtracker algorithm is unnecessary as it is the default option
        algo2 = (RadioButton) findViewById(R.id.algo2);
        algo3 = (RadioButton) findViewById(R.id.algo3);

        launchButton.setOnClickListener(clickHandler);
    }

    // click handler used to launch the game when launchButton is clicked
    View.OnClickListener clickHandler = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            size = Integer.parseInt(sizeEditText.getText().toString());

            algorithm = 1;
            if (algo2.isChecked()) {
                algorithm = 2;
            } else if (algo3.isChecked()) {
                algorithm = 3;
            }

            intent.putExtra("size", size);
            intent.putExtra("algorithm", algorithm);
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
