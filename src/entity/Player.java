package entity;

import main.KeyHandler;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;

public class Player extends Entity{

	GamePanel gp;
	KeyHandler keyH;
	
	public final int screenX;
	public final int screenY;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		screenX = gp.screenWidth / 2;
		screenY = gp.screenHeight / 2;
		
		// Sets 4 variables for each corner of rectangle
		solidArea = new Rectangle();
		
		solidArea.x = 28;
		solidArea.y = 20;
		solidArea.width = 8;
		solidArea.height = 32;
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		// Default position of character centre of screen
		worldX = gp.tileSize * 23 - (gp.tileSize / 2);
		worldY = gp.tileSize * 21 - (gp.tileSize / 2);	
		
		// All characters uses the same speed
		speed = 4;
		
		// Defailt direction is down
		direction = "down";
	}
	
	public void update() {
		
		// Player sprite changes only when key is pressed
		if(keyH.up || keyH.down || keyH.left || keyH.right) {
			
			// When user presses key, player position moves by 4 (speed) pixels
			if(keyH.up) {
				direction = "up";
			} else if(keyH.down) {
				direction = "down";
			} else if(keyH.left) {
				direction = "left";
			} else if(keyH.right) {
				direction = "right";
			}
			
			// Checks tile collisions
			collisionOn = false;
			gp.collisionCheck.checkTile(this);
			
			// If false, player unable to move
			if(collisionOn == false) {
				switch(direction) {
					case "up":
						worldY -= speed;
						break;
					case "down":
						worldY += speed;
						break;
					case "left":
						worldX -= speed;
						break;
					case "right":
						worldX += speed;
						break;
				}
			}
			
			spriteCounter++;
			
			// Player sprite transitions every 10 frames
			if(spriteCounter > 12) {
				if(spriteNum == 1) {
					spriteNum = 2;
				} else if(spriteNum == 2) {
					spriteNum = 1;
				}
				
				spriteCounter = 0;
			}
		} else if(!keyH.up && !keyH.down && !keyH.left && !keyH.right) {
			if(direction == "down") {
				direction = "downStill";
			} else if(direction == "up") {
				direction = "upStill";
			} else if(direction == "left") {
				direction = "leftStill";
			} else if(direction == "right") {
				direction = "rightStill";
			}
		}
	}
	
	public void getPlayerImage() {
		
		try {
			
			// Gets images of sprites into IO and displays
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Up1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Up2.png"));
			up3 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Up.png"));
			
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Down2.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Down3.png"));
			down3 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Down.png"));
			
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Left.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Left2.png"));
			
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Right.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/Blueman_Right2.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		// Get walking animation between 2 sprites
		switch(direction) {
		case "downStill":
			image = down3;
			break;
		case "upStill":
			image = up3;
			break;
		case "rightStill":
			image = right1;
			break;
		case "leftStill":
			image = left1;
			break;
		case "up":
			if(spriteNum == 1) {
				image = up1;
			}
			
			if(spriteNum == 2) {
				image = up2;
			}
			
			break;
		case "down":
			if(spriteNum == 1) {
				image = down1;
			}
			
			if(spriteNum == 2) {
				image = down2;
			}
			
			break;
		case "left":
			if(spriteNum == 1) {
				image = left1;
			}
			
			if(spriteNum == 2) {
				image = left2;
			}
			
			break;
		case "right":
			if(spriteNum == 1) {
				image = right1;
			}
			
			if(spriteNum == 2) {
				image = right2;
			}
			
			break;
		}
		
		g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
	}
}
