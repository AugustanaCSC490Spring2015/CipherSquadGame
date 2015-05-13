package game;

import android.graphics.Point;

import edu.augustana.csc490.ratrace.maze.Maze;
import edu.augustana.csc490.ratrace.maze.RecursiveMazeSolver;

import java.util.LinkedList;

/**
 * Created by Ethan Halsall on 3/30/2015.
 *
 * @author CypherSquad
 */
public class AIMouse extends Mouse {
    public LinkedList<Point> solution;
    public LinkedList<Point> aiPath;
    private RecursiveMazeSolver mazeSolver;

    /**
     * AIMouse object sets the initial mouse, sets the mazeSolver, sets the solution,
     * and gets the path the AI is to take.
     * @param maze takes in the maze in order to find a solution to it
     */
    public AIMouse(Maze maze) {
        super.initMouse();
        mazeSolver = new RecursiveMazeSolver(maze);
        solution = mazeSolver.getSolution();
        aiPath = mazeSolver.getAiPath();
    }

    /**
     * levelUp takes in the maze and sets the mazeSolver, stores the maze solution,
     * and gets the AI path of this new maze.
     * @param maze takes in the maze object
     */
    public void levelUp(Maze maze) {
        mazeSolver = new RecursiveMazeSolver(maze);
        solution = mazeSolver.getSolution();
        aiPath = mazeSolver.getAiPath();
    }
}
