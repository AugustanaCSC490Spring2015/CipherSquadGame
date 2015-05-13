package edu.augustana.csc490.ratrace.maze;

import android.graphics.Point;

public class Maze {
    /*
     * Code written by Ethan Halsall
     */
    protected boolean[] vWalls;
    protected boolean[] hWalls;
    private Point startPoint;
    private Point endPoint;
    private int width;
    private int height;

    public static final int N = 1;
    public static final int E = 2;
    public static final int S = 3;
    public static final int W = 4;

    public Maze(int width, int height) {
        // creates and generates a maze of given size
        // for individual players
        this.width = width;
        this.height = height;
        initializeMaze();
    }

    private Maze(boolean[] vWalls, boolean[] hWalls, Point start, Point end,
                 int width, int height) {
        this.vWalls = vWalls;
        this.hWalls = hWalls;
        this.startPoint = start;
        this.endPoint = end;
        this.width = width;
        this.height = height;
    }

    private void initializeMaze() {
        hWalls = new boolean[width * (height + 1)];
        vWalls = new boolean[height * (width + 1)];
        fillGrid();
        startPoint = new Point(0, 0);
        endPoint = new Point(width - 1, height - 1);
    }

    public int getDirection(int prevX, int prevY, int newX, int newY) {
        if (prevX == newX && prevY < newY) {
            return S;
        } else if (prevX == newX && prevY > newY) {
            return N;
        } else if (prevX > newX && prevY == newY) {
            return W;
        } else if (prevX < newX && prevY == newY) {
            return E;
        }
        return 0;
    }

    public Maze getMaze() {
        // returns a duplicate of the maze
        // for use when networking
        return new Maze(vWalls, hWalls, startPoint, endPoint, width, height);
    }

    public Point getStart() {
        return new Point(startPoint.x, startPoint.y);
    }

    public Point getEnd() {
        return new Point(endPoint.x, endPoint.y);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[] getHWalls() {
        boolean[] temp = new boolean[hWalls.length];
        for (int i = 0; i < hWalls.length; i++) {
            temp[i] = hWalls[i];
        }
        return temp;
    }

    public boolean[] getVWalls() {
        boolean[] temp = new boolean[vWalls.length];
        for (int i = 0; i < vWalls.length; i++) {
            temp[i] = vWalls[i];
        }
        return temp;
    }

    public boolean checkLocation(Point location) {
        // checks if the location references a cell inside the maze
        if (location.x < 0 || location.x >= width || location.y < 0
                || location.y >= height) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isWallPresent(Point location, int direction) {
        // assumes direction is valid
        if (!checkLocation(location)) {
            throw new IllegalArgumentException("Location not valid");
        }
        switch (direction) {
            case N:
                return hWalls[location.x + location.y * width];
            case E:
                return vWalls[location.y + location.x * height + height];
            case S:
                return hWalls[location.x + location.y * width + width];
            case W:
                return vWalls[location.y + location.x * height];
            default:
                return true;
        }
    }

    protected void fillGrid() {
        for (int i = 0; i < hWalls.length; i++) {
            for (int j = 0; j < vWalls.length; j++) {
                vWalls[j] = true;
            }
            hWalls[i] = true;
        }
    }

    public boolean equals(Maze maze) {
        if (this.getHeight() != maze.getHeight() || this.getWidth() != maze.getWidth()) {
            return false;
        } else if (!this.hWalls.equals(maze.getHWalls()) || !this.vWalls.equals(maze.getVWalls())) {
            return false;
        } else {
            return true;
        }
    }

}
