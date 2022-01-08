package entity;

import java.awt.image.BufferedImage;

// stores variables for characters
public class Entity {
	
	public int x, y;
	public int speed;
	
	// Image with accessible buffer image data
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	
	public String direction;
	
	// Ensure walking animation
	public int spriteCounter = 0;
	public int spriteNum = 1;
}
