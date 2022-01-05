package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// screen dimensions
	final int normalTileSize = 16;	// 16x16 tiles, basically since it's 16 bit game
	final int scale = 3;	// Enlarge the 16 pixels
	final int tileSize = normalTileSize * scale;	// Scaled tile
	
	// Ratio 4:3 screen
	final int screenCol = 16;
	final int screenrow = 12;
	final int screenWidth = tileSize * screenCol;	// 768 pixel
	final int screenHeight = tileSize * screenrow;	// 576 pixel
	
	// Controls the time of the game; starting and stopping
	Thread gameThread;
	
	// Gets keys pressed by user
	KeyHandler key = new KeyHandler();
	
	// Player's default position
	int posX = 100;
	int posY = 100;
	int speed = 4;
	
	// Frames per second
	int fps = 60;
	
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
	}
	
	@Override
	public void run() {
		
		// Update: update information
		// Draw: draw screen with information
		
		// Main part of game to keep it running
		while(gameThread != null) {
			
			// Interval is 1 second in 1 billion nanoseconds
			double interval = 1000000000 / fps;	// 0.01666 seconds
			double nextTime = System.nanoTime() + interval;	
			
			update();
			
			repaint();
			
			try {
				
				double remainingTime = nextTime - System.nanoTime();
				remainingTime = remainingTime / 1000000;
				
				if(remainingTime < 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextTime += interval;
				
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
	}
	
	public void update() {
		
		// When user presses key, player position moves by 4 (speed) pixels
		if(key.up) {
			posY -= speed;
		} else if(key.down) {
			posY += speed;
		} else if(key.left) {
			posX -= speed;
		} else if(key.right) {
			posX += speed;
		}
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Graphics2D better than Graphics
		Graphics g2 = (Graphics2D)g;
		
		// Temporary "Character" for game. Which is just a white square :p
		g2.setColor(Color.white);		
		g2.fillRect(posX, posY, tileSize, tileSize);
		
		g2.dispose();
	}
}
