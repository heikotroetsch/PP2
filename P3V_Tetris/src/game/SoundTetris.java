package game;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * Diese Klasse sorgt fuer die Hintergrundmusik, die im SingMusikThread in
 * Schleife abgespielt werden soll.
 */
public class SoundTetris {
	private static Mixer mixer;
	private static Clip clip1;

	public static Clip getClip1() {
		return clip1;
	}

	public static void singTetris() {

		Mixer.Info[] mixInfos = AudioSystem.getMixerInfo();
		mixer = AudioSystem.getMixer(mixInfos[0]);

		DataLine.Info dataInfo = new DataLine.Info(Clip.class, null);

		try {
			clip1 = (Clip) mixer.getLine(dataInfo);
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		}

		try {
			URL soundURL = SoundTetris.class.getResource("/sound.wav");
			AudioInputStream audioStream = AudioSystem.getAudioInputStream(soundURL);
			clip1.open(audioStream);
		} catch (LineUnavailableException lue) {
			lue.printStackTrace();
		} catch (UnsupportedAudioFileException uafe) {
			uafe.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		clip1.start();

	}

}
