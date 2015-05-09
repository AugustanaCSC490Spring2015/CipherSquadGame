package game;

import android.graphics.Bitmap;


/**
 * Created by Matt on 4/15/2015.
 */
public class BreadPowerUp extends PowerUp {


    // sandwich.png was found on http://www.pdclipart.org/displayimage.php?album=search&cat=0&pos=18
    // under Public Domain

    BreadPowerUp(Bitmap image, int x, int y) {
        // swiss_cheese taken from http://simple.wikipedia.org/wiki/Swiss_cheese under Public Domain
        // http://simple.wikipedia.org/wiki/Swiss_cheese#/media/File:NCI_swiss_cheese.jpg
        this.image = image;
        mazeX = x;
        mazeY = y;
    }

}
