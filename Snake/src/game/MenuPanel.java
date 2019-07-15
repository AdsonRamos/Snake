package game;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements KeyListener, ActionListener{
	
	
	private Timer timer;
	public static final int WIDTH = GamePanel.WIDTH;
	public static final int HEIGHT = GamePanel.HEIGHT;
	
	private BufferedImage bg, title, start, records, exit, logo;
	
	private int menuChoice;
	
	private MainWindow window;
	
	public MenuPanel(MainWindow window) {
		
		this.window = window;
		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		
		try {
			loadImages();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		addKeyListener(this);
		requestFocus();
		setFocusable(true);
		
		menuChoice = 0;
		
		timer = new Timer(20, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
	}
	
	private void loadImages() throws IOException {
		bg = ImageIO.read(new File("res/bg.png"));
		title = ImageIO.read(new File("res/title.png"));
		start = ImageIO.read(new File("res/start.png"));
		records = ImageIO.read(new File("res/records.png"));
		exit = ImageIO.read(new File("res/exit.png"));
		logo = ImageIO.read(new File("res/ico.png"));
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render(g);
	}
	
	private void update() {
	}
	
	private void render(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.drawImage(bg, 0, 0, null);
		g2d.drawImage(title, 128, 30, null);
		g2d.drawImage(start, 245, 162, null);
		g2d.drawImage(records, 226, 201, null);
		g2d.drawImage(exit, 260, 239, null);
		
		if(menuChoice == 0) {
			g2d.drawImage(logo, 205, 159, null);
		} else if(menuChoice == 1) {
			g2d.drawImage(logo, 188, 198, null);
		} else if(menuChoice == 2) {
			g2d.drawImage(logo, 223, 237, null);
		}
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			menuChoice--;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			menuChoice++;
		} else if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(menuChoice == 0) {
				startGame();
			} else if(menuChoice == 1) {
				toRecords();
			} else if(menuChoice == 2) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		}
		
		if(menuChoice > 2) {
			menuChoice = 0;
		} else if(menuChoice < 0) {
			menuChoice = 2;
		}
	}

	private void toRecords() {
		window.removeKeyListener(window.menu);
		window.remove(window.menu);
		
		RecordPanel record = new RecordPanel(window);
		window.record = record;
		window.pack();
		window.addKeyListener(record);
		window.add(record);
		window.setSize(WIDTH+1, HEIGHT+1);
		window.requestFocus();
	}

	private void startGame() {
		window.remove(window.menu);
		window.removeKeyListener(window.menu);
		
		GamePanel game = new GamePanel();
		window.game = game;
		window.setSize(WIDTH + 7, HEIGHT+ 28);
		window.addKeyListener(game);
		window.requestFocus();
		window.setContentPane(game);
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
