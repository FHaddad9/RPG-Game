package entity;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

// stores variables for characters
public class Entity {
	
	public int worldX, worldY;
	public int speed;
	
	// Image with accessible buffer image data
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	public String direction;
	
	// Ensure walking animation
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	// Setting the player's solid areas
	public Rectangle solidArea;
	public boolean collisionOn = false;
}
