package application;

public class TileMove {

	private Location end;
	private Tile tile;
	
	public TileMove(Tile tile, Location end) {
		this.tile = tile;
		this.end = end;
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public void completeMove() {
		tile.setLocation(end);
	}
	
	public int getDifference() {
		if (tile.getLocation().getX() > end.getX()) return -(int) tile.getSize().getX() - 10;
		if (tile.getLocation().getX() < end.getX()) return  (int) tile.getSize().getX() + 10;
		if (tile.getLocation().getY() > end.getY()) return -(int) tile.getSize().getY() - 10;
		if (tile.getLocation().getY() < end.getY()) return  (int) tile.getSize().getY() + 10;
		return 0;
	}
	
	public Direction getDirection() {
		if (tile.getLocation().getX() > end.getX()) return Direction.LEFT;
		if (tile.getLocation().getX() < end.getX()) return Direction.RIGHT;
		if (tile.getLocation().getY() > end.getY()) return Direction.DOWN;
		if (tile.getLocation().getY() < end.getY()) return Direction.UP;
		return null;
	}
	
	public static enum Direction {
		UP,
		DOWN,
		LEFT,
		RIGHT;
	}
}
