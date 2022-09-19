package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// screen dimensions
	final int normalTileSize = 16;	// 16x16 tiles, basically since it's 16 bit game
	final int scale = 3;	// Enlarge the 16 pixels
	public final int tileSize = normalTileSize * scale;	// Scaled tile
	
	// Ratio 4:3 screen
	public final int screenCol = 16;
	public final int screenrow = 12;
	public final int screenWidth = tileSize * screenCol;	// 768 pixel
	public final int screenHeight = tileSize * screenrow;	// 576 pixel
	
	// World settings
	public final int maxWorldCol = 50;
	public final int maxWorldRow = 50;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	// Gets keys pressed by user
	KeyHandler key = new KeyHandler();
	
	// Get player class for entitys
	public Player player = new Player(this, key);
		
	// Frames per second
	int fps = 60;
	
	// Gets the collisions for entitys
	public CollisionChecker collisionCheck = new CollisionChecker(this);
	
	Sound sound = new Sound();
	
	TileManager tileM = new TileManager(this);
	
	// Controls the time of the game; starting and stopping
	Thread gameThread;	
	
	public GamePanel() {
		
		// Constructor for the window
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(key);
		this.setFocusable(true);
	}

	public void startGameThread() {
		
		// Starts the game by calling the run method
		gameThread = new Thread(this);
		gameThread.start();
		playMusic(0);
	}
	
	public void run() {
		
		// Update: update information
		// Draw: draw screen with information
		
		// Interval is 1 second in 1 billion nanoseconds
		double interval = 1000000000 / fps;	// 0.01666 seconds
		double delta = 0;
		
		long lastTime = System.nanoTime();
		long currentTime;
		long timer = 0;
		int drawCount = 0;
		
		// Delta gets the interval between the time when loop started and current time and repaints
		while(gameThread != null) {
			
			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / interval;
			timer += (currentTime - lastTime);
			lastTime = currentTime;
			
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}		
			
			if(timer >= 1000000000) {
				System.out.println("FPS: " + drawCount);
				drawCount = 0;
				timer = 0;
			}
		}
	}
	
	public void update() {
		player.update();		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Graphics2D better than Graphics
		Graphics2D g2 = (Graphics2D)g;

		// Drawing is based on layers. In this case, tiles before player
		tileM.draw(g2);
		player.draw(g2);
		
		g2.dispose();
	}
	
	public void playMusic(int i) {
		
		sound.setFile(i);
		sound.play();
		sound.loop();
	}
	
	public void stopMusic() {
		
		sound.stop();
	}
	
	public void playSfx(int i) {
		
		sound.setFile(i);
		sound.play();
	}
}
