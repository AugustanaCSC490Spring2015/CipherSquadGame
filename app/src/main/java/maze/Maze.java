package maze;

import java.io.PrintStream;
import java.util.Arrays;

public class Maze {


	// Represents UP.
	public static final int UP = 0;

	// Represents RIGHT.
	public static final int RIGHT = 1;

	// Represents DOWN.
	public static final int DOWN = 2;

	// Represents LEFT.
	public static final int LEFT = 3;
	
	private int width;
	private int height;
	private Cell start;
	private Cell end;

	
	// Stores whether the walls exist or not
	// Stores the maze

	private boolean[] horizWalls;
	private boolean[] vertWalls;

	//constructs a maze given a start and an end
	public Maze(int width, int height, Cell start, Cell end){
		initialize(width, height, start, end);
	}
	
	//constructs a maze using a random start and end
	public Maze(int width, int height){
		Cell start = new Cell(0, 0);
		Cell end = new Cell(width - 1, height - 1);
		initialize(width, height, start, end);
	}
	
	//initializes an empty maze
	private void initialize(int width, int height, Cell start, Cell end){
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("Size must be positive");
		}
		this.width = width;
		this.height = height;
		checkLocation(start.getX(), start.getY());
		checkLocation(end.getX(), end.getY());
		this.start = start;
		this.end = end;
		this.setMaze(new boolean[width * (height + 1)], new boolean[(width + 1) * height]);
	}
	//used by the MazeGenerator class to set the walls in the maze
	protected void setMaze(boolean[] horizWalls, boolean[] vertWalls){
		this.horizWalls = horizWalls;
		this.vertWalls = vertWalls;
	}
	
	//checks if the location of an (x, y) coordinate is within the maze
	public void checkLocation(int x, int y) {
		if (x < 0 || width <= x) {
			System.out.println(x);
			System.out.println(width);
			throw new IndexOutOfBoundsException("X out of range: " + x);
		}
		if (y < 0 || height <= y) {
			throw new IndexOutOfBoundsException("Y out of range: " + y);
		}
	}
	
	//checks if the location of a cell is within the maze
	public void checkLocation(Cell cell){
		int x = cell.getX();
		int y = cell.getY();
		if (x < 0 || width <= x) {
			throw new IndexOutOfBoundsException("X out of range: " + x);
		}
		if (y < 0 || height <= y) {
			throw new IndexOutOfBoundsException("Y out of range: " + y);
		}
	}
	
	//checks if the direction integer matches up with the final directions declared above
	public void checkDirection(int direction){
			switch (direction) {
			case UP:
			case RIGHT:
			case DOWN:
			case LEFT:
				break;
			default:
				throw new IllegalArgumentException("Bad direction: " + direction);
			}
		}
	
	public boolean isWallPresent(int x, int y, int direction) {

		checkDirection(direction);
		checkLocation(x, y);

		int index = -1;
		boolean[] array = null;

		switch (direction) {
		case Maze.UP:
			index = y * width + x;
			array = horizWalls;
			break;
		case Maze.DOWN:
			index = (y + 1) * width + x;
			array = horizWalls;
			break;
		case Maze.LEFT:
			index = y * (width + 1) + x;
			array = vertWalls;
			break;
		case Maze.RIGHT:
			index = y * (width + 1) + (x + 1);
			array = vertWalls;
			break;
		}
		return array[index];
	}
	//resets the maze
	protected final void reset() {
		// Fill the walls
		Arrays.fill(horizWalls, true);
		Arrays.fill(vertWalls, true);
		//start = new Cell(rand.nextInt(width), rand.nextInt(height));
		//end = new Cell(rand.nextInt(width), rand.nextInt(height));
		
	}
	
	public boolean[] getHorizWalls(){
		return horizWalls;
	}
	
	public boolean[] getVertWalls(){
		return vertWalls;
	}
	
	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Cell getStart(){
		return start;
	}

	public Cell getEnd(){
		return end;
	}
	
	
	
	/**
	 * Prints the maze. The following characters are used for each part.
	 * <ul>
	 * <li><code>'-'</code> for horizontal walls</li>
	 * <li><code>'|'</code> for vertical walls</li>
	 * <li><code>'*'</code> for the corner fillers</li>
	 * </ul>
	 * 
	 * @param out
	 *            the target {@link PrintStream}
	 */
	public void print(PrintStream out) {
		int startX = start.getX();
		int startY = start.getY();
		int endX = end.getX();
		int endY = end.getY();
		
		for (int y = 0; y < height; y++) {
			// Print a row of horizontal walls
			
			int rowBase = y * width;
			for (int x = 0; x < width; x++) {
				out.print('*');
				if (x == startX && y == startY){
					out.print("S");
				} else {
					out.print(horizWalls[rowBase + x] ? '-' : ' ');
				}
				
			}
			out.println('*');

			// Print a row of vertical walls

			rowBase = y * (width + 1);
			for (int x = 0; x < width; x++) {	
				if (x == startX && y == startY){
					out.print("S");
				} else {
					out.print(vertWalls[rowBase + x] ? '|' : ' ');
				}
				out.print(' ');
				
			}
			if (width == startX && y == startY){
				out.print("S");
			} else if (width == endX && y == endY){
				out.print("E");
			} else {
				out.println(vertWalls[rowBase + width] ? '|' : ' ');
			}
			
		}

		// Print the last row of horizontal walls

		int rowBase = height * width;
		for (int x = 0; x < width; x++) {
			out.print('*');
			if (endX == x){
				out.print("E");
			} else {
			out.print(horizWalls[rowBase + x] ? '-' : ' ');
			}
		}
		out.println('*');
	}
}


