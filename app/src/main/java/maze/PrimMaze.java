package maze;

import java.util.Arrays;

public class PrimMaze extends MazeGenerator {
    // The starting coordinates

    private int startX;
    private int startY;

    //creates a Prim Maze given a width and height
    public PrimMaze(int width, int height) {
        maze = new Maze(width, height);
        startX = maze.getStart().x;
        startY = maze.getStart().y;
        generate();
    }

    //creates a Prim Maze given a width, height, start Cell, and end Cell
    public PrimMaze(int width, int height, Cell start,
                    Cell end) {
        maze = new Maze(width, height, start, end);
        startX = start.getX();
        startY = start.getY();
        generate();
    }

    //creates a Prim Maze given an existing maze
    public PrimMaze(Maze maze) {
        this.maze = maze;
        startX = maze.getStart().x;
        startY = maze.getStart().y;
        generate();
    }

    private static final int IN = 0;
    private static final int FRONTIER = 1;
    private static final int OUT = 2;

    /**
     * Generate the maze.
     */
    protected void generateMaze() {
        int width = maze.getWidth();
        int height = maze.getHeight();

        int[] cells = new int[width * height]; // States
        Arrays.fill(cells, OUT);
        int[] frontierCells = new int[width * height];

        // Start cell
        // Make all its neighbours FRONTIER

        cells[startY * width + startX] = IN;
        int frontierCount = 0;
        for (int i = 0; i < 4; i++) {
            switch (i) {
                case Maze.UP:
                    if (startY > 0) {
                        int index = (startY - 1) * width + startX;
                        cells[index] = FRONTIER;
                        frontierCells[frontierCount++] = index;
                    }
                    break;
                case Maze.RIGHT:
                    if (startX < width - 1) {
                        int index = startY * width + (startX + 1);
                        cells[index] = FRONTIER;
                        frontierCells[frontierCount++] = index;
                    }
                    break;
                case Maze.DOWN:
                    if (startY < height - 1) {
                        int index = (startY + 1) * width + startX;
                        cells[index] = FRONTIER;
                        frontierCells[frontierCount++] = index;
                    }
                    break;
                case Maze.LEFT:
                    if (startX > 0) {
                        int index = startY * width + (startX - 1);
                        cells[index] = FRONTIER;
                        frontierCells[frontierCount++] = index;
                    }
                    break;
            }
        }

        // Loop until there are no more frontier cells

        int[] inNeighbours = new int[4];

        while (frontierCount > 0) {
            int frontierCellIndex = rand.nextInt(frontierCount);
            int index = frontierCells[frontierCellIndex];
            int x = index % width;
            int y = index / width;

            // Carve into this frontier cell from one of its IN neighbours

            int inNeighbourCount = 0;
            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case Maze.UP:
                        if (y > 0 && cells[(y - 1) * width + x] == IN) {
                            inNeighbours[inNeighbourCount++] = i;
                        }
                        break;
                    case Maze.RIGHT:
                        if (x < width - 1 && cells[y * width + (x + 1)] == IN) {
                            inNeighbours[inNeighbourCount++] = i;
                        }
                        break;
                    case Maze.DOWN:
                        if (y < height - 1 && cells[(y + 1) * width + x] == IN) {
                            inNeighbours[inNeighbourCount++] = i;
                        }
                        break;
                    case Maze.LEFT:
                        if (x > 0 && cells[y * width + (x - 1)] == IN) {
                            inNeighbours[inNeighbourCount++] = i;
                        }
                        break;
                }
            }

            carve(x, y, inNeighbours[rand.nextInt(inNeighbourCount)]);

            // Mark this cell as IN

            cells[index] = IN;
            if (frontierCellIndex < frontierCount - 1) {
                System.arraycopy(frontierCells, frontierCellIndex + 1,
                        frontierCells, frontierCellIndex, frontierCount
                                - frontierCellIndex - 1);
            }
            frontierCount--;

            // Mark any neighbouring OUT cells as FRONTIER

            for (int i = 0; i < 4; i++) {
                switch (i) {
                    case Maze.UP:
                        if (y > 0 && cells[index - width] == OUT) {
                            cells[index - width] = FRONTIER;
                            frontierCells[frontierCount++] = index - width;
                        }
                        break;
                    case Maze.RIGHT:
                        if (x < width - 1 && cells[index + 1] == OUT) {
                            cells[index + 1] = FRONTIER;
                            frontierCells[frontierCount++] = index + 1;
                        }
                        break;
                    case Maze.DOWN:
                        if (y < height - 1 && cells[index + width] == OUT) {
                            cells[index + width] = FRONTIER;
                            frontierCells[frontierCount++] = index + width;
                        }
                        break;
                    case Maze.LEFT:
                        if (x > 0 && cells[index - 1] == OUT) {
                            cells[index - 1] = FRONTIER;
                            frontierCells[frontierCount++] = index - 1;
                        }
                        break;
                }
            }
        }
    }

    /**
     * Returns a string representation of this object.
     *
     * @return a string representation of this object.
     */
    public String toString() {
        return "Prim's Algorithm maze generator";
    }

    public boolean solve() {
        return false;
    }
}
