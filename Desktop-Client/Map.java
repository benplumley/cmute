public class Map {

	private JLabel mapImage;

	public Map() {
		String filePath = "maps/bath.png"
		try {
			mapImage = new JLabel(new ImageIcon(ImageIO.read(new File(filePath))));
		} catch (IOException e) {
			System.err.println("File missing: " + filePath);
		}
	}

}
