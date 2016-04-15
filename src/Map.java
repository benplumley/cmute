import java.io.File;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Map extends JLabel {

	public Map() {
		String filePath = "/Users/Artlac/GitHub/IntegratedProject/src/maps/bath.png";
//				"maps/bath.png";
		
		try {
			this.setIcon(new ImageIcon(ImageIO.read(new File(filePath))));
		} catch (IOException e) {
			System.err.println("File missing: " + filePath);
		}
	}

}
