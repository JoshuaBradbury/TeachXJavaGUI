package application;

import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class Tile {

	private Location location, finalLocation, size;
	private ImageView imageView;
	
	public Tile(Board board, Location location, Location size, ImageView imageView) {
		this.location = location;
		finalLocation = location.clone();
		this.size = size;
		this.imageView = imageView;
		final Tile tile = this;
		imageView.setOnMouseReleased(new EventHandler<MouseEvent> () {
			public void handle(MouseEvent event) {
				board.moveTile(tile);
			}
		});
	}
	
	public Location getSize() {
		return size;
	}
	
	public ImageView getImage() {
		return imageView;
	}
	
	public Location getLocation() {
		return location;
	}
	
	public boolean atFinalLocation() {
		return location.equals(finalLocation);
	}
	
	public void setLocation(Location location) {
		this.location = location;
	}
}
