package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class RecordPanel extends JPanel implements ActionListener, KeyListener{
	
	private Timer timer;

	public static final int WIDTH = GamePanel.WIDTH, HEIGHT = GamePanel.HEIGHT;
	
	private BufferedImage deletar, voltar, background, logo;
	
	private int choice = 0;
	
	private File recordFile;
	
	private Scanner scanner;
	
	private MainWindow window;
	
	private int record;
	
	public RecordPanel(MainWindow window) {
		
		this.window = window;
		
		setFocusable(true);
		addKeyListener(this);
		
		try {
			recordFile = new File("res/record.dat");
			deletar = ImageIO.read(new File("res/deletar.png"));
			voltar = ImageIO.read(new File("res/voltar.png"));
			background = ImageIO.read(new File("res/bg.png"));
			logo = ImageIO.read(new File("res/ico.png"));
			
			if(!recordFile.exists()) {
				recordFile.createNewFile();
			}
			
			scanner = new Scanner(recordFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		if(scanner.hasNext()) {
			record = scanner.nextInt();
		} else {
			record = 0;
		}
		
		timer = new Timer(20, this);
		timer.start();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D) g);
	}
	
	private void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.drawImage(background, 0, 0, null);
		
		g.drawImage(deletar, 400, 270, null);
		g.drawImage(voltar, 410, 310, null);
		
		if(choice == 0) {
			g.drawImage(logo, 360, 267, null);
		} else if(choice == 1) {
			g.drawImage(logo, 372, 308, null);
		}

		
		g.setFont(new Font("Trebuchet MS", Font.PLAIN, 40));
		if(record == 0) {
			g.drawString("(EMPTY)", WIDTH / 2 - 90,	HEIGHT / 2 - 20);
		} else {
			g.drawString("Melhor pontuação: "+record, 100,	HEIGHT / 2 - 20);
		}
			
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP) {
			choice--;
			if(choice < 0) choice = 1;
		} else if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			choice++;
			if(choice > 1) choice = 0;
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(choice == 0) {
				Object[] options = { "Não", "Sim" };
				this.repaint();
				int dialog = JOptionPane.showOptionDialog(null, "Deseja realmente apagar os recordes?", "Atenção!",
						JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
				if (dialog == 1) {
					record = 0;
					try {
						PrintWriter pw  = new PrintWriter(recordFile);
						pw.close();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
				}
			} else if(choice == 1) {
				toMenu();
			}
		}
	}

	private void toMenu() {
		window.removeKeyListener(window.record);
		window.remove(window.record);
		
		MenuPanel menu = new MenuPanel(window);
		window.menu = menu;
		
		window.pack();
		window.add(menu);
		window.addKeyListener(menu);
		window.setSize(WIDTH, HEIGHT);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.requestFocus();
		window.setVisible(true);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}

}
