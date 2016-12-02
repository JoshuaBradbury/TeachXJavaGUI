package application;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

public class Board {

	private Tile[][] board;
	private Image image;
	private GridPane parent;
	private int divisions, shuffles;
	private boolean shuffling, isTileMoving, finished;
	private Random random;
	private LinkedList<TileMove> tileMoves;
	private Location emptyLocation;

	public Board(GridPane parent, Image image, int divisions) {
		this.parent = parent;
		this.image = image;
		random = new Random();
		tileMoves = new LinkedList<TileMove>();
		genBoard(divisions);
	}

	public void genBoard(int divisions) {
		this.divisions = divisions;
		board = new Tile[divisions][divisions];
		parent.getChildren().clear();

		emptyLocation = new Location(0, 0);
		
		shuffles = 0;

		int subWidth = (int) image.getWidth() / divisions, subHeight = (int) image.getHeight() / divisions;

		for (int x = 0; x < divisions; x++) {
			for (int y = 0; y < divisions; y++) {
				if (x == 0 && y == 0)
					continue;
				board[x][y] = new Tile(this, new Location(x, y), new Location(subWidth, subHeight), new ImageView(ImageUtil.getSubImage(image, x * subWidth, y * subHeight, subWidth, subHeight)));
				parent.add(board[x][y].getImage(), x, y);
			}
		}
		shuffle();
	}

	public void moveTile(Tile tile) {
		if (finished) return;
		Location location = findEmptySlot(tile.getLocation());
		if (location != null) {
			emptyLocation = tile.getLocation().clone();
			if (!isTileMoving) {
				startAnimation(new TileMove(tile, location));
			} else {
				tileMoves.add(new TileMove(tile, location));
			}
		}
	}

	public void startAnimation(TileMove move) {
		if (finished) return;
		isTileMoving = true;
		Timeline timeline = new Timeline();

		final DoubleProperty property;
		if (move.getDirection() == TileMove.Direction.LEFT || move.getDirection() == TileMove.Direction.RIGHT)
			property = move.getTile().getImage().translateXProperty();
		else
			property = move.getTile().getImage().translateYProperty();

		Number startValue = property.getValue();

		KeyFrame startFrame = new KeyFrame(Duration.ZERO, new KeyValue(property, property.getValue()));

		int duration = 500;

		if (shuffling)
			duration = 50;

		KeyFrame endFrame = new KeyFrame(new Duration(duration), new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				Location loc = move.getTile().getLocation().clone();
				move.completeMove();

				board[move.getTile().getLocation().getX()][move.getTile().getLocation().getY()] = move.getTile();
				board[loc.getX()][loc.getY()] = null;
				refreshBoard();
				property.setValue(startValue);

				if (!tileMoves.isEmpty()) {
					startAnimation(tileMoves.poll());
				} else {
					isTileMoving = false;
				}
				
				if (shuffling && shuffles < 100) {
					shuffle();
				} else {
					shuffling = false;
				}
			}
		}, new KeyValue(property, property.getValue().intValue() + move.getDifference()));

		timeline.getKeyFrames().addAll(startFrame, endFrame);
		timeline.play();
	}

	public void refreshBoard() {
		parent.getChildren().clear();

		boolean complete = true;
		
		for (int x = 0; x < divisions; x++) {
			for (int y = 0; y < divisions; y++) {
				if (board[x][y] != null) {
					parent.add(board[x][y].getImage(), x, y);
					if (!board[x][y].atFinalLocation()) complete = false;
				}
			}
		}
		
		if (complete && !shuffling) {
			parent.getChildren().clear();
			parent.add(new ImageView(image), 0, 0);
			int padding = (divisions - 1) * 5;
			parent.setPadding(new Insets(padding, padding, padding, padding));
			finished = true;
		}
	}

	public Location findEmptySlot(Location location) {
		if (Math.abs(location.getX() - emptyLocation.getX()) == 1 && location.getY() - emptyLocation.getY() == 0)
			return emptyLocation;
		if (Math.abs(location.getY() - emptyLocation.getY()) == 1 && location.getX() - emptyLocation.getX() == 0)
			return emptyLocation;
		return null;
	}

	private void shuffle() {
		shuffling = true;
		shuffles++;
		
		Location empty = null;
		for (int x = 0; x < divisions; x++) {
			for (int y = 0; y < divisions; y++) {
				empty = findEmptySlot(new Location(x, y));
				if (empty != null)
					break;
			}
			if (empty != null)
				break;
		}

		if (empty != null) {
			ArrayList<Tile> tiles = new ArrayList<Tile>();
			if (empty.getX() != 0 && board[empty.getX() - 1][empty.getY()] != null)
				tiles.add(board[empty.getX() - 1][empty.getY()]);
			if (empty.getX() != divisions - 1 && board[empty.getX() + 1][empty.getY()] != null)
				tiles.add(board[empty.getX() + 1][empty.getY()]);
			if (empty.getY() != 0 && board[empty.getX()][empty.getY() - 1] != null)
				tiles.add(board[empty.getX()][empty.getY() - 1]);
			if (empty.getY() != divisions - 1 && board[empty.getX()][empty.getY() + 1] != null)
				tiles.add(board[empty.getX()][empty.getY() + 1]);
			moveTile(tiles.get(random.nextInt(tiles.size())));
		}
	}
}
