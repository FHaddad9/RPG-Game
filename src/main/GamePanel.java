package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable{
	
	// screen dimensions
	final int normalTileSize = 16;	// 16x16 tiles, basically since it's 16 bit game
	final int scale = 3;	// Enlargen the 16 pixels
	final int tileSize = normalTileSize * scale;	// Scaled tile
	
	// Ratio 4:3 screen
	final int screenCol = 16;
	final int screenrow = 12;
	final int screenWidth = tileSize * screenCol;	// 768 pixel
	final int screenHeight = tileSize * screenrow;	// 576 pixel
	
	// Controls the time of the game; starting and stopping
	Thread gameThread;
	
	public GamePanel() {
		
		// Constructor for the window
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
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
			System.out.println("Game is running");
			
			update();
			
			repaint();
		}
	}
	
	public void update() {
		
	}
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		// Graphics2D better than Graphics
		Graphics g2 = (Graphics2D)g;
		
		// Temporary "Character" for game. Which is just a white square :p
		g2.setColor(Color.white);		
		g2.fillRect(100, 100, tileSize, tileSize);
		
		g2.dispose();
	}
}
