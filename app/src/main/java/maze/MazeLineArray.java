package maze;

import android.graphics.Point;

import java.util.ArrayList;

/**
 * Created by Ethan Halsall on 4/1/2015.
 * <p/>
 * Creates an array of lines that represents the maze walls to that a the maze can be easily drawn on the canvas
 */
public class MazeLineArray {
    //stores information for the maze line array
    private Maze maze;
    private ArrayList<Line> mazeLineArray;
    private int screenWidth;
    private int screenHeight;
    private int width;
    private int height;
    private boolean[] horizWalls;
    private boolean[] vertWalls;
    private int widthSpacing;
    private int heightSpacing;


    //uses the screen dimensions to scale the size of the lines
    public MazeLineArray(Maze maze, int screenWidth, int screenHeight) {
        this.maze = maze;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        width = maze.getWidth();
        height = maze.getHeight();
        horizWalls = maze.getHorizWalls();
        vertWalls = maze.getVertWalls();
        widthSpacing = screenWidth / width;
        heightSpacing = screenHeight / height;
        createMazeLineArray();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getWidthSpacing() {
        return widthSpacing;
    }

    public int getHeightSpacing() {
        return heightSpacing;
    }

    public Line getLineAtIndex(int index) {
        return mazeLineArray.get(index);
    }

    public int getSize() {
        return mazeLineArray.size();
    }

    public Maze getMaze() {
        return maze;
    }

    public Point screenToMazePos(int screenX, int screenY) {
        int mazeX = screenX / (screenWidth + widthSpacing / 2);
        int mazeY = screenY / (screenHeight + heightSpacing / 2);
        return new Point(mazeX, mazeY);
    }

    /*public ArrayList<Line> getMazeLineArray() {
        return mazeLineArray;
    }*/

    private void createMazeLineArray() {
        mazeLineArray = new ArrayList<Line>();

        int rowBase;

        int tempX = 0;
        int tempY = 0;

        for (int y = 0; y < height; y++) {
            // Print a row of horizontal walls
            rowBase = y * width;
            tempX = 0;
            tempY = y * heightSpacing;

            for (int x = 0; x < width; x++) {
                //out.print('*');
                if (horizWalls[rowBase + x]) {
                    mazeLineArray.add(new Line(tempX, tempY, tempX + widthSpacing, tempY));
                }
                tempX += widthSpacing;
            }
            //out.println('*');

            // Print a row of vertical walls

            rowBase = y * (width + 1);
            tempX = 0;
            for (int x = 0; x <= width; x++) {
                if (vertWalls[rowBase + x]) {
                    mazeLineArray.add(new Line(tempX, tempY, tempX, tempY + heightSpacing));
                }
                ;
                tempX += widthSpacing;
                //out.print(' ');

            }
            //out.println(vertWalls[rowBase + width] ? '|' : ' ');
        }

        // Print the last row of horizontal walls

        rowBase = height * width;
        tempX = 0;
        tempY = width * heightSpacing;
        for (int x = 0; x < width; x++) {
            if (horizWalls[rowBase + x]) {
                mazeLineArray.add(new Line(tempX, tempY, tempX + widthSpacing, tempY));
            }
            tempX += widthSpacing;
        }
        //out.println('*');
    }
}
