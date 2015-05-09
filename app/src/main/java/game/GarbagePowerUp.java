package game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import edu.augustana.csc490.gamestarter.R;
import edu.augustana.csc490.ratrace.MainGameView;

/**
 * Created by Matt on 4/15/2015.
 */
public class GarbagePowerUp extends PowerUp {
    int xLocation;
    int yLocation;
    Bitmap garbageImage;

    GarbagePowerUp(int screenWidth, int screenHeight, int width, int height) {
        // applecore taken from https://openclipart.org/ under Unlimited Commercial Use
        // https://openclipart.org/detail/40357/apple-core
        garbageImage = BitmapFactory.decodeResource(MainGameView.currentGameView.getResources(), R.raw.applecore);
        garbageImage = Bitmap.createScaledBitmap(garbageImage,screenWidth / width,screenHeight/width,true);
    }

    public Bitmap getBitmapImage() {
        return garbageImage;
    }

    public void setMazeX(int x) {
        xLocation = x;
    }

    public void setMazeY(int y) {
        yLocation = y;
    }


    @Override
    public int getMazeX() {

        return xLocation;
    }

    @Override
    public int getMazeY() {

        return yLocation;
    }
}
