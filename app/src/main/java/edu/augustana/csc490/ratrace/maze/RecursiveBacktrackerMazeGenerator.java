package edu.augustana.csc490.ratrace.maze;

import android.graphics.Point;
import android.util.Log;

import java.util.LinkedList;
import java.util.Random;


public class RecursiveBacktrackerMazeGenerator {

    /*
     * This program was derived from the recursive
     * backtracker algorithm described by Jamis Buck
     * See link: http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
     *
     * All code written by Ethan Halsall.
     */
    private Maze maze;
    public static final int N = 1;
    public static final int E = 2;
    public static final int S = 3;
    public static final int W = 4;
    private boolean[][] cellsVisited;
    Random r;

    public RecursiveBacktrackerMazeGenerator(Maze maze) {
        generate(maze);
    }

    private void generate(Maze maze) {
        // generates a maze using the recursive backtracker algorithm
        this.maze = maze;
        algorithm();
    }

    private void algorithm() {
        maze.fillGrid(); // resets the maze to start generation
        cellsVisited = new boolean[maze.getWidth()][maze.getHeight()];
        cellsVisited[maze.getStart().x][maze.getStart().y] = true;
        r = new Random(System.currentTimeMillis());
        iteration(maze.getStart());
        carve(maze.getEnd(), E); // carve the ending hole
    }

    private class BTState {
        Point curCell;
        private int[] directions;
        private int dirIndex;

        public BTState(Point curCell) {
            this.curCell = curCell;
            directions = shuffledDirectionArray();
            dirIndex = 0;
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

        public void advance() {
            dirIndex++;
        }

        public boolean isDone() {
            return dirIndex >= 4;
        }

        public int getCurrentDirection() {
            return directions[dirIndex];
        }
    }


    private void iteration(Point currentCell) {
        //creates the maze using a stack
        LinkedList<BTState> stack = new LinkedList<BTState>();
        stack.push(new BTState(currentCell));
        BTState curState;
        //int steps = 0;
        while (!stack.isEmpty()) {
            curState = stack.peek();
            if (curState.isDone()) {
                stack.pop();
            } else {
                Point newCell = advanceCell(curState.curCell, curState.getCurrentDirection());
                if (newCell != curState.curCell) {
                    if (!cellsVisited[newCell.x][newCell.y]) {
                        carve(curState.curCell, curState.getCurrentDirection());
                        cellsVisited[newCell.x][newCell.y] = true;
                        BTState next = new BTState(newCell);
                        //steps++;
                        //Log.i("Maze Creation", "x = " + newCell.x + " y = " + newCell.y + "; " + next.getCurrentDirection() + "; Step: " + steps);
                        stack.push(next);
                    }
                }
                curState.advance();
            }
        }
    }


    private void recursion(Point currentCell) {
        //recursively creates the maze
        int directions[] = shuffledDirectionArray();
        for (int direction : directions) {
            Point newCell = advanceCell(currentCell, direction);
            if (newCell != currentCell) {
                if (!cellsVisited[newCell.x][newCell.y]) {
                    carve(currentCell, direction);
                    cellsVisited[newCell.x][newCell.y] = true;
                    //System.out.println("x = " + newCell.x + " y = " + newCell.y + "; " + directions[i] + "; ");
                    recursion(newCell);
                }
            }
        }
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

    private void carve(Point location, int direction) {
        switch (direction) {
            case N:
                maze.hWalls[location.x + location.y * maze.getWidth()] = false;
                break;
            case E:
                maze.vWalls[location.y + location.x * maze.getHeight() + maze.getHeight()] = false;
                break;
            case S:
                maze.hWalls[location.x + location.y * maze.getWidth() + maze.getWidth()] = false;
                break;
            case W:
                maze.vWalls[location.y + location.x * maze.getHeight()] = false;
                break;
        }
    }
}
