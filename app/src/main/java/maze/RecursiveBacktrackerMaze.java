
package maze;

import java.util.LinkedList;

/**
 * This produces a maze with a small number of longer dead ends, and usually a
 * long and twisty solution.
*/

public class RecursiveBacktrackerMaze extends MazeGenerator {
	// The starting coordinates
	private int startX;
	private int startY;

	//creates a Recursive Backtracker Maze given a width and height
	public RecursiveBacktrackerMaze(int width, int height) {
		maze = new Maze(width, height);
		startX = maze.getStart().x;
		startY = maze.getStart().y;
		generate();
	}
	
	//creates a Recursive Backtracker Maze given a width, height, start Cell, and end Cell
	public RecursiveBacktrackerMaze(int width, int height, Cell start,
			Cell end) {
		maze = new Maze(width, height, start, end);
		startX = start.getX();
		startY = start.getY();
		generate();
	}
	
	//creates a Recursive Backtracker Maze given an existing maze
	public RecursiveBacktrackerMaze(Maze maze){
		this.maze = maze;
		startX = maze.getStart().x;
		startY = maze.getStart().y;
		generate();
	}

	//generates the maze
	protected void generateMaze() {
		int width = maze.getWidth();
		int height = maze.getHeight();

		boolean[] cells = new boolean[width * height]; // Visited flags
		LinkedList<Cell> stack = new LinkedList<Cell>();

		Cell cell = new Cell(startX, startY);
		stack.addFirst(cell);
		int[] neighbours = new int[4];
		int x;
		int y;

		do {
			// Mark the current cell as visited
			x = cell.getX();
			y = cell.getY();
			cells[y * width + x] = true;
			
			// Examine the current cell's neighbours

			int freeNeighbourCount = 0;
			for (int i = 0; i < 4; i++) {
				switch (i) {
				case Maze.UP:
					if (y > 0 && !cells[(y - 1) * width + x]) {
						neighbours[freeNeighbourCount++] = i;
					}
					break;
				case Maze.RIGHT:
					if (x < width - 1
							&& !cells[y * width + (x + 1)]) {
						;
						neighbours[freeNeighbourCount++] = i;
					}
					break;
				case Maze.DOWN:
					if (y < height - 1
							&& !cells[(y + 1) * width + x]) {
						neighbours[freeNeighbourCount++] = i;
					}
					break;
				case Maze.LEFT:
					if (x > 0 && !cells[y * width + (x - 1)]) {
						neighbours[freeNeighbourCount++] = i;
					}
					break;
				}
			}

			// Pick a random free neighbour

			if (freeNeighbourCount > 0) {
				stack.addFirst(cell);
				cell = new Cell(x, y);

				switch (neighbours[rand.nextInt(freeNeighbourCount)]) {
				case Maze.UP:
					carve(x, y, Maze.UP);
					y--;
					cell.setY(y);
					break;
				case Maze.RIGHT:
					carve(x, y, Maze.RIGHT);
					x++;
					cell.setX(x);
					break;
				case Maze.DOWN:
					carve(x, y, Maze.DOWN);
					y++;
					cell.setY(y);
					break;
				case Maze.LEFT:
					carve(x, y, Maze.LEFT);
					x--;
					cell.setX(x);
					break;
				}

			} else {
				cell = stack.removeFirst();
			}
		} while (!stack.isEmpty());
	}

	/**
	 * Returns a string representation of this object.
	 * 
	 * @return a string representation of this object.
	 */
	public String toString() {
		return "Recursive Backtracker maze generator";
	}
}
