package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class tile {
	
	Image icon;
	String type;
	
	public tile (Image img, String type) {
		this.icon = img;
		this.type = type;
	}
	
	public ImageView createTile() {
		ImageView tileObject = new ImageView();
		tileObject.setImage(icon);
		return tileObject;
	}
	
	public String getType() {
		return type;
	}

}
