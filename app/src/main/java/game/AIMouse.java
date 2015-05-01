package game;

import android.graphics.Point;

import maze.Maze;
import maze.RecursiveMazeSolver;

import java.util.LinkedList;

/**
 * Created by Ethan Halsall on 3/30/2015.
 */
public class AIMouse extends Mouse {
    private LinkedList<Point> solution;
    private LinkedList<Point> aiPath;
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

    public Point nextMove(Boolean newCell) {
        if (newCell) {
            return aiPath.removeFirst();
        } else {
            return aiPath.getFirst();
        }
    }

    public LinkedList<Point> getSolution() {
        return solution;
    }


}
