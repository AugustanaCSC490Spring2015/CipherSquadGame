package game;

import android.graphics.Point;

import maze.Maze;
import maze.RecursiveMazeSolver;

import java.util.LinkedList;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public class AIMouse extends Mouse {
    public LinkedList<Point> solution;
    public LinkedList<Point> aiPath;
    private RecursiveMazeSolver mazeSolver;


    public AIMouse(Maze maze) {
        super.initMouse();
        mazeSolver = new RecursiveMazeSolver(maze);
        solution = mazeSolver.getSolution();
        aiPath = mazeSolver.getAiPath();
    }

    public void levelUp(Maze maze) {
        mazeSolver = new RecursiveMazeSolver(maze);
        solution = mazeSolver.getSolution();
        aiPath = mazeSolver.getAiPath();
    }
}
