import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class Client implements Runnable {

	String hostname = "localhost";
	String portNumber = "55511";

	public static void main(String[] args) {
		Client client = new Client();
	}

	public void Client() {
		Thread thread = new Thread(this);
		thread.start();
	}

	public void run() {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			// matches the operating system's look and feel
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {}
		setupPanels();
		setupFrame();
	}

	private void setupFrame() {
		JFrame window = new JFrame("Car Sharing");
		window.setIconImage(Toolkit.getDefaultToolkit().getImage("graphics/icon.png"));
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		populateFrame(window.getContentPane());
		window.pack();
		window.setLocationByPlatform(true);
		window.setVisible(true);
		Dimension windowSize = new Dimension(400, 200);
		window.setSize(windowSize);
		window.setResizable(true);
	}

	private void setupPanels() {

	}

	private void populateFrame(Container frame) {

	}

}
