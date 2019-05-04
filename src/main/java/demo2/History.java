package demo2;

import java.awt.Point;

public class History {
	private Line line;
	private int x;
	private int y;
	private Point point;
	
	public Point getPoint() {
		return point;
	}
	public void setPoint(Point point) {
		this.point = point;
	}
	public History(Line line, int x, int y) {
		this.line = line;
		this.x = x;
		this.y = y;
	}
	public Line getLine() {
		return line;
	}
	public void setLine(Line line) {
		this.line = line;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	
	

}
