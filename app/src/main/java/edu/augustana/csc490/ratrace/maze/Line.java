
package edu.augustana.csc490.ratrace.maze;

import android.graphics.Point;

public class Line {
    public Point start = new Point(); // start Point--(0,0) by default
    public Point end = new Point(); // end Point--(0,0) by default
    public float startX;
    public float startY;
    public float endX;
    public float endY;

    public Line(int startX, int startY, int endX, int endY) {
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
        start = new Point(startX, startY);
        end = new Point(endX, endY);
    }

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
        startX = start.x;
        startY = start.y;
        endX = end.x;
        endY = end.y;
    }


} // end class Line
