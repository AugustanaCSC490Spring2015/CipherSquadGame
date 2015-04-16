
package maze;

public class HuntAndKillMaze extends MazeGenerator{
	// The starting coordinates

	private int startX;
	private int startY;
	
	//creates a Hunt And Kill Maze given a width and height
	public HuntAndKillMaze(int width, int height) {
		maze = new Maze(width, height);
		startX = maze.getStart().x;
		startY = maze.getStart().y;
		generate();
	}
	
	//creates a Hunt And Kill Maze given a width, height, start cell, end cell
	public HuntAndKillMaze(int width, int height, Cell start,
			Cell end) {
		maze = new Maze(width, height, start, end);
		startX = start.getX();
		startY = start.getY();
		generate();
	}
	
	//creates a Hunt And Kill Maze given an existing maze
		public HuntAndKillMaze(Maze maze){
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

		// Starting position

		int x = startX;
		int y = startY;

		int[] neighbours = new int[4];

		mainLoop: do {
			// Mark the current cell as visited

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
					if (x < width - 1 && !cells[y * width + (x + 1)]) {
						;
						neighbours[freeNeighbourCount++] = i;
					}
					break;
				case Maze.DOWN:
					if (y < height - 1 && !cells[(y + 1) * width + x]) {
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
				switch (neighbours[rand.nextInt(freeNeighbourCount)]) {
				case Maze.UP:
					carve(x, y, Maze.UP);
					y--;
					break;
				case Maze.RIGHT:
					carve(x, y, Maze.RIGHT);
					x++;
					break;
				case Maze.DOWN:
					carve(x, y, Maze.DOWN);
					y++;
					break;
				case Maze.LEFT:
					carve(x, y, Maze.LEFT);
					x--;
					break;
				}
			} else {
				// Enter "hunt" mode by searching for an unvisited cell
				// next to a carved-into one

				for (int i = 0; i < cells.length; i++) {
					if (!cells[i]) {
						// See if there is an adjacent carved-into cell
						// If so, carve into it and continue

						x = i % width;
						y = i / width;

						for (int j = 0; j < 4; j++) {
							switch (j) {
							case Maze.UP:
								if (y > 0) {
									for (int k = 0; k < 4; k++) {
										if (!maze.isWallPresent(x, y - 1, k)) {
											carve(x, y, Maze.UP);
											continue mainLoop;
										}
									}
								}
								break;
							case Maze.RIGHT:
								if (x < width - 1) {
									for (int k = 0; k < 4; k++) {
										if (!maze.isWallPresent(x + 1, y, k)) {
											carve(x, y, Maze.RIGHT);
											continue mainLoop;
										}
									}
								}
								break;
							case Maze.DOWN:
								if (y < height - 1) {
									for (int k = 0; k < 4; k++) {
										if (!maze.isWallPresent(x, y + 1, k)) {
											carve(x, y, Maze.DOWN);
											continue mainLoop;
										}
									}
								}
								break;
							case Maze.LEFT:
								if (x > 0) {
									for (int k = 0; k < 4; k++) {
										if (!maze.isWallPresent(x - 1, y, k)) {
											carve(x, y, Maze.LEFT);
											continue mainLoop;
										}
									}
								}
								break;
							}
						}
					}// Test the current unvisited cell
				}// Hunt loop

				break;
			}// No free neighbours
		} while (true);
	}

	/**
	 * Returns a string representation of this object.
	 * 
	 * @return a string representation of this object.
	 */
	public String toString() {
		return "Hunt and Kill maze generator";
	}

}
