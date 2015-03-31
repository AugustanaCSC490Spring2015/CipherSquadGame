
package maze;

import java.util.Random;

public abstract class MazeGenerator{
	
	
	//Stores the maze
	public Maze maze;
	protected Random rand = new Random();


	//generates the maze
	public final void generate() {
		maze.reset();
		generateMaze();
	}


	protected abstract void generateMaze();


	
	public Maze getMaze(){
		return maze;
	}
	
	protected boolean carve(int x, int y, int direction) {
		// Check the arguments

		maze.checkDirection(direction);
		maze.checkLocation(x, y);

		int index = -1;
		boolean[] array = null;

		switch (direction) {
		case Maze.UP:
			index = y * maze.getWidth() + x;
			array = maze.getHorizWalls();
			break;
		case Maze.DOWN:
			index = (y + 1) * maze.getWidth() + x;
			array = maze.getHorizWalls();
			break;
		case Maze.LEFT:
			index = y * (maze.getWidth() + 1) + x;
			array = maze.getVertWalls();
			break;
		case Maze.RIGHT:
			index = y * (maze.getWidth() + 1) + (x + 1);
			array = maze.getVertWalls();
			break;
		}

		// Set the wall to 'false' and return what it was before

		boolean b = array[index];
		array[index] = false;
		return b;
	}


}