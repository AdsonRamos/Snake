package game;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

@SuppressWarnings("serial")
public class MainWindow  extends JFrame {
	
	protected MenuPanel menu;
	protected GamePanel game;
	protected RecordPanel record;
	
	public MainWindow() {
		super("Snake");

		menu = new MenuPanel(this);
		record = new RecordPanel(this);
		
		setPreferredSize(new Dimension(GamePanel.WIDTH, GamePanel.HEIGHT));
		add(menu);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e1) {
			e1.printStackTrace();
		}
		
		try {
			setIconImage(ImageIO.read(new File("res/ico.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new MainWindow();
	}
}
