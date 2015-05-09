package maze;

import android.graphics.Point;
import android.util.Log;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Ethan Halsall on 4/28/2015.
 */
public class RecursiveMazeSolver {

    private Maze maze;
    private LinkedList<Point> solution;
    private LinkedList<Point> aiPath;
    private Point start;
    private Point end;
    private boolean isSolved;
    public static final int N = 1;
    public static final int E = 2;
    public static final int S = 3;
    public static final int W = 4;

    private boolean[][] cellsVisited;

    Random r;

    public RecursiveMazeSolver(Maze maze) {
        this.maze = maze;
        end = maze.getEnd();
        start = maze.getStart();
        solution = new LinkedList<Point>();
        aiPath = new LinkedList<Point>();
        cellsVisited = new boolean[maze.getWidth()][maze.getHeight()];
        cellsVisited[maze.getStart().x][maze.getStart().y] = true;
        solve();
    }

    public LinkedList<Point> getSolution() {
        return solution;
    }

    public LinkedList<Point> getAiPath() {
        return aiPath;
    }

    private void solve() {
        r = new Random(System.currentTimeMillis());
        isSolved = false;
        recursion(start);
    }


    private boolean recursion(Point currentCell) {
        //recursively solves the maze
        //saves the path and solution in their corresponding linked lists
        if (currentCell.equals(maze.getEnd())) {
            solution.push(currentCell);
            Log.i("solution: ", "X = " + currentCell.x + " Y = " + currentCell.y);
            return true;
        }
        int directions[] = shuffledDirectionArray();
        for (int direction : directions) {
            Point newCell = advanceCell(currentCell, direction);

            if (newCell != currentCell) {

                if (!cellsVisited[newCell.x][newCell.y]) {

                    aiPath.add(newCell);
                    if (recursion(newCell)) {
                        solution.push(currentCell);
                        Log.i("solution: ", "X = " + currentCell.x + " Y = " + currentCell.y);
                        return true;
                    }
                    cellsVisited[newCell.x][newCell.y] = true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }


    private Point advanceCell(Point currentCell, int direction) {
        Point temp = new Point();
        switch (direction) {
            case N:
                temp = new Point(currentCell.x, currentCell.y - 1);
                break;
            case E:
                temp = new Point(currentCell.x + 1, currentCell.y);
                break;
            case S:
                temp = new Point(currentCell.x, currentCell.y + 1);
                break;
            case W:
                temp = new Point(currentCell.x - 1, currentCell.y);
                break;
        }
        if (maze.checkLocation(temp)) {
            return temp;
        } else {
            return currentCell;
        }
    }

    private int[] shuffledDirectionArray() {
        int[] array = new int[4];
        for (int i = 0; i <= 3; i++) {
            array[i] = i + 1;
        }
        int index, temp;
        for (int i = array.length - 1; i > 0; i--) {
            index = r.nextInt(i + 1);
            temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
        return array;
    }
}
