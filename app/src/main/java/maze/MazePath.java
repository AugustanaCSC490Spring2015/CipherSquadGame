package maze;

import java.util.LinkedList;

public class MazePath {

    private static LinkedList<Cell> path;

    public MazePath() {
        path = new LinkedList<Cell>();
    }

    public static Cell addFirst(Cell cell) {
        path.addFirst(cell);
        return cell;
    }

    public static Cell addLast(Cell cell) {
        path.addLast(cell);
        return cell;
    }

    public static Cell removeLast() {
        return path.removeLast();
    }

    public static Cell removeFirst() {
        return path.removeFirst();
    }

}
