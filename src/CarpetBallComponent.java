import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class CarpetBallComponent extends JComponent {
	private GameState state;
	private Table table;
	BufferedImage[] balls = new BufferedImage[13];


	public CarpetBallComponent(CarpetBall carpetBall) {
		state = carpetBall.getState();
		table = carpetBall.getTable();

		setFocusable(true);
		setPreferredSize(new Dimension((int) table.getWidth(), (int) table.getHeight()));

		for (int x = 0; x < balls.length; x++) {
			balls[x] = getBufferedImage(new File(x + "Ball.png"));
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		super.paintComponent(g2);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT);
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		int barTwo = (int) table.getHeight() - (int) table.getBarDistance() - 10;
		//       g.setColor(new Color(200, 165, 80));
		//       g.fillRect(0, 0, (int) table.getWidth(), (int) table.getHeight());
		// sets gutter color
		g.setColor(Color.BLACK);
		// sets top gutter color to black
		g.fillRect(0, 0, (int) table.getWidth(), (int) table.getGutterDepth());
		// sets bottom gutter color to black
		g.fillRect(0, (int) table.getHeight() - 50, (int) table.getWidth(), (int) table.getGutterDepth());
		//sets table segment (gray part)
		g.setColor(new Color(117, 117, 117));
		// sets table area to gray
		g.fillRect(0, (int) table.getGutterDepth(), (int) table.getWidth(), (int) table.getHeight() - (int) table.getGutterDepth() * 2);
		// sets bar color
		g.setColor(new Color(200, 165, 80));
		// sets area of top bar
		g.fillRect(0, (int) table.getBarDistance(), (int) table.getWidth(), 5);
		// sets area of bottom bar
		g.fillRect(0, barTwo, (int) table.getWidth(), 5);


		for (Ball ball : state.getMyBalls()) {
			drawBall(g2, ball);
		}
		for (Ball ball : state.getTheirBalls()) {
			drawBall(g2, ball);
		}
		drawBall(g2, state.getCueBall());
		if (!state.isInGame()) {
			g.setColor(Color.RED);
			g.drawString("WAITING FOR PLAYER 2", 85, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance() - 20);
		} else {
//			if (state.isNaming() && !state.isSettingUp() && !state.isMyTurn() && )
//				g.setColor(Color.RED);
//				//TODO MAKE SURE IS NAMING HAPPENS BEFORE IsInGaming == true
//				g.drawString("ENTER YOUR", 46, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
//				g.drawString("NAME, PLEASE", 173, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
//

			if (state.isMyTurn() && !state.isSettingUp()) {
				g.setColor(Color.RED);
				g.drawString("YOUR", 95, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
				g.drawString("TURN", 175, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
			}
			if (state.isSettingUp()) {
				g.setColor(Color.RED);
				g.drawString("SET", 95, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
				g.drawString("UP", 175, (barTwo - (int) table.getBarDistance()) / 2 + (int) table.getBarDistance());
			}
		}

	}

	private BufferedImage getBufferedImage(File input) {
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			System.err.println(input.getAbsolutePath());
			e.printStackTrace();
		}
		return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
	}

	private void drawBall(Graphics g, Ball b) {
		Point2D loc = b.getLocation();
		int size = (int) Ball.BALL_RADIUS;
		if (b.isHovered()) {
			g.setColor(Color.WHITE);
			g.fillOval((int) loc.getX() - size - 5, (int) loc.getY() - size - 5, (size + 5) * 2, (size + 5) * 2);
		}

		g.drawImage(balls[b.getNumber()], (int) loc.getX() - size, (int) loc.getY() - size, size * 2, size * 2, null);
	}


	public void play(String filename) {
		try {
			Clip clip = AudioSystem.getClip();
			clip.open(AudioSystem.getAudioInputStream(new File(filename)));
		} catch (Exception exc) {
			exc.printStackTrace(System.out);
		}
	}
}