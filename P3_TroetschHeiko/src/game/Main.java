package game;

public class Main {

	public static void main(String[] args) {
		GameController.getInstance().initiate();
		GameController.getInstance().getGameFrame().setVisible(true);

	}

}
