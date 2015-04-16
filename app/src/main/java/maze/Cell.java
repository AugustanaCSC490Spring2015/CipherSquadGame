package maze;

class Cell {
	private int x;
	private int y;
	private int visits;
	
	/**
	 * Creates a new cell object having the given coordinates.
	 * 
	 * @param x
	 *            the cell's X-coordinate
	 * @param y
	 *            the cell's Y-coordinate
	 */
	public Cell(int x, int y) {
		/*if (x <= 0 || y <= 0) {
			throw new IllegalArgumentException("Cell position must be positive");
		}*/
		this.x = x;
		this.y = y;
		visits = 0;
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int setX(int x){
		int temp = this.x;
		this.x = x;
		return temp;
	}
	
	public int setY(int y){
		int temp = this.y;
		this.y = y;
		return temp;
	}
	
	
	
	public int getVisits(){
		return visits;
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}
	
	public int incrementVisits(){
		int temp = visits;
		visits++;
		return temp;
	}
	
	public boolean equals(Cell cell){
		if (this.x == cell.x && this.y ==cell.y && this.visits == cell.visits){
			return true;
		} else {
		return false;
		}
	}
}
