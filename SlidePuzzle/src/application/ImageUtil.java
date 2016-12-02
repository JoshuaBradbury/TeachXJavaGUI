package application;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;

public final class ImageUtil {

	private ImageUtil() {}
	
	public static Image loadImageByName(String name) {
		return new Image(ImageUtil.class.getResource(name).toExternalForm());
	}
	
	public static Image getSubImage(Image image, int x, int y, int width, int height) {
		PixelReader reader = image.getPixelReader();
		WritableImage newImage = new WritableImage(reader, x, y, width, height);
		return newImage;
	}
}
