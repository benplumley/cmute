package DesktopClient;

public class MapPoint implements java.io.Serializable {

	private int x, y;

	public MapPoint(int localX, int localY) {
		x = localX;
		y = localY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
