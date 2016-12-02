package application;

public class Location {

	private int x, y;
	
	public Location(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public Location clone() {
		return new Location(x, y);
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Location) {
			Location loc = (Location) o;
			return (loc.x == x && loc.y == y);
		}
		return false;
	}
}
