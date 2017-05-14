package io;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;

import javax.imageio.ImageIO;

import game.GameSettings;

/**
 * Klasse zum Laden und Skalieren von Bildern
 */

public class ImageLoader {
	
	
	public final static String FILESEPERATOR = FileSystems.getDefault().getSeparator();
	public final static String DEFAULTDIRECTORY = "resources" + FILESEPERATOR;


	private static final int SCALE_SMOOTH = 0;
	private static Image blue = getBlue(GameSettings.stoneSize, GameSettings.stoneSize);
	private static Image green = getGreen(GameSettings.stoneSize, GameSettings.stoneSize);
	private static Image lightBlue = getLightBlue(GameSettings.stoneSize, GameSettings.stoneSize);
	private static Image orange = getOrange(GameSettings.stoneSize, GameSettings.stoneSize);
	private static Image purple = getPurple(GameSettings.stoneSize, GameSettings.stoneSize);
	private static Image red = getRed(GameSettings.stoneSize, GameSettings.stoneSize);
	private static Image yellow = getYellow(GameSettings.stoneSize, GameSettings.stoneSize);

	public static Image get(int i) {

		switch (i) {
		case 1:
			if (orange != null)
				return orange;

		case 2:
			if (purple != null)
				return purple;

		case 3:
			if (lightBlue != null)
				return lightBlue;

		case 4:
			if (yellow != null)
				return yellow;

		case 5:
			if (red != null)
				return red;

		case 6:
			if (green != null)
				return green;

		case 7:
			if (blue != null)
				return blue;

		}
		return null;

	}

	private static Image getBlue(int width, int height) {
		if (blue == null) {
			try {
				blue = ImageIO.read(new File(DEFAULTDIRECTORY + "blue.png"));

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return blue.getScaledInstance(width, height, SCALE_SMOOTH);
	}

	private static Image getGreen(int width, int height) {
		if (green == null) {
			try {
				green = ImageIO.read(new File(DEFAULTDIRECTORY + "green.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return green.getScaledInstance(width, height, SCALE_SMOOTH);
	}

	private static Image getLightBlue(int width, int height) {
		if (lightBlue == null) {
			try {
				lightBlue = ImageIO.read(new File(DEFAULTDIRECTORY + "lightblue.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return lightBlue.getScaledInstance(width, height, SCALE_SMOOTH);
	}

	private static Image getOrange(int width, int height) {
		if (orange == null) {
			try {
				orange = ImageIO.read(new File(DEFAULTDIRECTORY + "orange.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return orange.getScaledInstance(width, height, SCALE_SMOOTH);
	}

	private static Image getPurple(int width, int height) {
		if (purple == null) {
			try {
				purple = ImageIO.read(new File(DEFAULTDIRECTORY + "purple.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return purple.getScaledInstance(width, height, SCALE_SMOOTH);
	}

	private static Image getRed(int width, int height) {
		if (red == null) {
			try {
				red = ImageIO.read(new File(DEFAULTDIRECTORY + "red.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return red.getScaledInstance(width, height, SCALE_SMOOTH);
	}

	private static Image getYellow(int width, int height) {
		if (yellow == null) {
			try {
				yellow = ImageIO.read(new File(DEFAULTDIRECTORY + "yellow.png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return yellow.getScaledInstance(width, height, SCALE_SMOOTH);
	}
}
