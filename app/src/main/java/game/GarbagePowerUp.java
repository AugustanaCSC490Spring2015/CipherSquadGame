package game;


import android.graphics.Bitmap;

/**
 * Created by Matt on 4/15/2015.
 */
public class GarbagePowerUp extends PowerUp {


    GarbagePowerUp(Bitmap image, int x, int y) {
        // applecore taken from https://openclipart.org/ under Unlimited Commercial Use
        // https://openclipart.org/detail/40357/apple-core
        this.image = image;
        mazeX = x;
        mazeY = y;
    }


}
