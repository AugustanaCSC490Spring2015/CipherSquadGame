package maze;

import java.util.ArrayList;

import edu.augustana.csc490.gamestarter.Line;

/**
 * Created by Ethan Halsall on 4/1/2015.
 *
 * Creates an array of lines that represents the maze walls to that a the maze can be easily drawn on the canvas
 *
 */
public class MazeLineArray {
    //stores information for the maze line array
    private Maze maze;
    private ArrayList<Line> mazeLineArray;
    private int screenWidth;
    private int screenHeight;

    //uses the screen dimensions to scale the size of the lines
    public MazeLineArray(Maze maze, int screenWidth, int screenHeight) {
        this.maze = maze;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        createMazeLineArray();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public Line getLineAtIndex(int index) {
        return mazeLineArray.get(index);
    }

    public int getSize() {
        return mazeLineArray.size();
    }

    public Maze getMaze(){
        return maze;
    }

    /*public ArrayList<Line> getMazeLineArray() {
        return mazeLineArray;
    }*/

    private void createMazeLineArray() {
        mazeLineArray = new ArrayList<Line>();

        int rowBase;
        int width = maze.getWidth();
        int height = maze.getHeight();
        boolean[] horizWalls = maze.getHorizWalls();
        boolean[] vertWalls = maze.getVertWalls();
        Line temp;
        int widthSpacing = screenWidth / width;
        int heightSpacing = screenHeight / height;
        int tempX = 0;
        int tempY = 0;

        for (int y = 0; y < height; y++) {
            // Print a row of horizontal walls
            rowBase = y * width;
            tempX = 0;
            tempY = y * heightSpacing;

            for (int x = 0; x < width; x++) {
                //out.print('*');
                if(horizWalls[rowBase + x]){
                    mazeLineArray.add(new Line(tempX, tempY, tempX + widthSpacing, tempY));
                }
                tempX+=widthSpacing;
            }
            //out.println('*');

            // Print a row of vertical walls

            rowBase = y * (width + 1);
            tempX = 0;
            for (int x = 0; x <= width; x++) {
                if(vertWalls[rowBase + x]){
                    mazeLineArray.add(new Line(tempX, tempY, tempX, tempY + heightSpacing));
                };
                tempX+=widthSpacing;
                //out.print(' ');

            }
                //out.println(vertWalls[rowBase + width] ? '|' : ' ');
            }

        // Print the last row of horizontal walls

        rowBase = height * width;
        tempX = 0;
        tempY = width * heightSpacing;
        for (int x = 0; x < width; x++) {
            if(horizWalls[rowBase + x]){
                mazeLineArray.add(new Line(tempX, tempY, tempX + widthSpacing, tempY));
            }
            tempX+=widthSpacing;
        }
        //out.println('*');
    }
}
