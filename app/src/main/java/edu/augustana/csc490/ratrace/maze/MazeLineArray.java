package edu.augustana.csc490.ratrace.maze;

import android.graphics.Point;
import android.util.Log;

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
    private float screenWidth;
    private float screenHeight;
    private float width;
    private float height;
    private boolean[] hWalls;
    private boolean[] vWalls;


    //uses the screen dimensions to scale the size of the lines
    public MazeLineArray(Maze maze, int screenWidth, int screenHeight) {
        this.maze = maze;
        this.screenHeight = screenHeight - 1;
        this.screenWidth = screenWidth - 1;
        width = maze.getWidth();
        height = maze.getHeight();
        hWalls = maze.getHWalls();
        vWalls = maze.getVWalls();
        createMazeLineArray();
    }

    public int getScreenWidth() {
        return (int) screenWidth;
    }

    public int getScreenHeight() {
        return (int) screenHeight;
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

    public int getWSpacing() {
        /*double widthSpacing = 0;
        double i;
        for (i = 1; i <= width; i++){
            widthSpacing += (screenWidth * (i / width));
        }
        widthSpacing = widthSpacing / i;
        return (int) widthSpacing;*/
        return (int) (screenWidth / width);
    }

    public int getHSpacing() {
        return (int) (screenHeight / height);
    }

    private void createMazeLineArray() {
        mazeLineArray = new ArrayList<Line>();

        //iterates through the maze boolean arrays to create an array of lines
        int wCursorPosLeft;
        int wCursorPosRight;
        int hCursorPosTop;
        int hCursorPosBottom;
        float wall;

        for (int i = 0; i < height; i++) {
            //currentH = i * hSpacing;
            hCursorPosTop = Math.round(screenHeight * (i / height));
            hCursorPosBottom = Math.round(screenHeight * ((i + 1) / height));

            //draws one set of horizontal walls
            wall = 0;
            for (int h = i * (int) width; h < (i + 1) * width; h++) {

                wCursorPosLeft = Math.round(screenWidth * (wall / width));
                wCursorPosRight = Math.round(screenWidth * ((wall + 1) / width));
                if (hWalls[h]) {
                    mazeLineArray.add(new Line(wCursorPosLeft, hCursorPosTop, wCursorPosRight, hCursorPosTop));
                }
                wall++;

            }

            //draws one set of vertical walls
            wall = 0;
            for (int v = i; v <= height * width + i; v += height) {

                wCursorPosLeft = Math.round(screenWidth * (wall / width));
                wall++;

                if (vWalls[v]) {
                    mazeLineArray.add(new Line(wCursorPosLeft, hCursorPosTop, wCursorPosLeft, hCursorPosBottom));
                }
            }
        }

        //draws the last set of horizontal walls
        hCursorPosTop = (int) screenHeight;
        wall = 0;
        for (int h = (int) width * (int) width; h < (width + 1) * width; h++) {
            wCursorPosLeft = Math.round(screenWidth * (wall / width));
            wCursorPosRight = Math.round(screenWidth * ((wall + 1) / width));

            if (hWalls[h]) {
                mazeLineArray.add(new Line(wCursorPosLeft, hCursorPosTop, wCursorPosRight, hCursorPosTop));
            }
            wall++;
        }

    }

}
