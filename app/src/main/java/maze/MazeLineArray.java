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
    private boolean[] hWalls;
    private boolean[] vWalls;
    private int wSpacing;
    private int hSpacing;


    //uses the screen dimensions to scale the size of the lines
    public MazeLineArray(Maze maze, int screenWidth, int screenHeight) {
        this.maze = maze;
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        width = maze.getWidth();
        height = maze.getHeight();
        hWalls = maze.getHWalls();
        vWalls = maze.getVWalls();
        wSpacing = screenWidth / width;
        hSpacing = screenHeight / height;
        createMazeLineArray();
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getWSpacing() {
        return wSpacing;
    }

    public int getHSpacing() {
        return hSpacing;
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
        int mazeX = screenX / (screenWidth + wSpacing / 2);
        int mazeY = screenY / (screenHeight + hSpacing / 2);
        return new Point(mazeX, mazeY);
    }

    private void createMazeLineArray() {
        mazeLineArray = new ArrayList<Line>();

        //iterates through the maze boolean arrays to create an array of lines
        int currentH;
        int wCursorPos = 0;
        for (int i = 0; i < height; i++) {
            currentH = i * hSpacing;

            //draws one set of horizontal walls
            for (int h = i * width; h < (i + 1) * width; h++) {
                if (hWalls[h]) {
                    mazeLineArray.add(new Line(wCursorPos, currentH, wCursorPos + wSpacing, currentH));
                }
                wCursorPos += wSpacing;
            }

            //draws one set of vertical walls
            wCursorPos = 0;
            for (int v = i; v <= height * width + i; v += height) {
                if (vWalls[v]) {
                    mazeLineArray.add(new Line(wCursorPos, currentH, wCursorPos, currentH + hSpacing));
                }
                wCursorPos += wSpacing;
            }
            wCursorPos = 0;
        }

        //draws the last set of horizontal walls
        currentH = screenHeight;
        for (int h = width * width; h < (width + 1) * width; h++) {
            if (hWalls[h]) {
                mazeLineArray.add(new Line(wCursorPos, currentH, wCursorPos + wSpacing, currentH));
            }
            wCursorPos += wSpacing;
        }

    }

}
