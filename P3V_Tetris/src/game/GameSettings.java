package game;

import java.awt.Color;

/**
 * Klasse, die alle Einstellungsmoeglichkeiten und weitere Konstanten
 * beinhaltet.
 */

public class GameSettings {

	/**
	 * diese Variable legt die verschiedenen Groessen fest. Um das Feld zu
	 * vergroessern/verkleinern muss also die Variable stoneSize veraendert werden.
	 */
	public static final int stoneSize = 40;

	/**
	 * gibt die benoetigte Linienanzahl bis zum naechsten Level an
	 */
	public static final int linesToNextLevel = 6;
	
	/**
	 * Masze des GameOverFrames
	 */
	public static final int gameOverFrameWidth = 310;
	public static final int gameOverFrameHeight = 170;
	public static final int gameOverLabelSize = 45;
	public static final int scoreLabelSize = 15;
	
	/**
	 * Settings der Sourroundings  
	 */
	public static final int spaceBetweenGamePanelAndPreviewPanel = 25;
	public static final int spaceLeft = 25;
	public static final int spaceRight = 25;
	public static final int spaceUp = 25;
	public static final int spaceDown = 75;
	
	/**
	 * Masze des GamePanels
	 */
	public static final int gameWidth = 10;
	public static final int gameHeight = 18;
	public static final int gamePanelWidth = stoneSize * gameWidth;
	public static final int gamePanelHeight = stoneSize * gameHeight; 
	public static final Color gamePanelBackgroundColor = Color.GRAY;

	/**
	 * Masze des PreviewPanles
	 */
	public static final int previewWidth = 6;
	public static final int previewHeight = 6;
	public static final int previewPanelWidth = stoneSize * previewWidth;
	public static final int previewPanelHeight = stoneSize * previewHeight;
	public static final int previewPositionWidth = spaceLeft + spaceBetweenGamePanelAndPreviewPanel + gamePanelWidth;

	/**
	 * Masze des GameFrames
	 */
	public static final int gameFrameWidth = gamePanelWidth + previewPanelWidth + spaceBetweenGamePanelAndPreviewPanel
			+ spaceLeft + spaceRight;
	public static final int gameFrameHeight = gamePanelHeight + spaceDown + spaceUp;

	/**
	 * Masze der Textgroessee und Position
	 */
	public static final float fontSize = (float) ((2.0 / 3.0) * stoneSize);
	public static final int textPositionWidth = (int) (gamePanelWidth + spaceLeft
			+ spaceBetweenGamePanelAndPreviewPanel);
	public static final int textPositionHeight = (int) ((double) gamePanelHeight / 2.0
			- (double) gamePanelHeight / 10.0);

	

}
