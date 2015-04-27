package game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import edu.augustana.csc490.gamestarter.MainGameView;
import edu.augustana.csc490.gamestarter.R;

/**
 * Created by Matt on 4/15/2015.
 */
public class CheesePowerUp extends PowerUp {
    Bitmap cheeseImage;
    CheesePowerUp(int screenWidth, int screenHeight, int width, int height) {
        cheeseImage = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.cheese_icon);
        cheeseImage = Bitmap.createScaledBitmap(cheeseImage,screenWidth / width,screenHeight/height,true);

    }


    public Bitmap getBitmapImage() {
        return cheeseImage;
    }
}
