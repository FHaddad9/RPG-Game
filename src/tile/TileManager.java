package tile;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {

	GamePanel gp;
	Tile[] tile;
	
	int mapTileNum[][];
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		
		tile = new Tile[10];
		mapTileNum = new int[gp.screenCol][gp.screenrow];
		
		getTileImage();
		loadMap("/maps/land.txt");
	}
	
	// Loads tile images
	public void getTileImage() {
		
		try {
			
			tile[0] = new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grass.png"));
			tile[1] = new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/water.png"));
			tile[2] = new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/poppies.png"));
			tile[3] = new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/sunflowers.png"));
			tile[4] = new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/cobble.png"));
			tile[5] = new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/grassyCobble.png"));
			
		} catch(IOException e) {
			e.printStackTrace();
		} 
		
	}
	
	public void loadMap(String map) {
		
		try {
			
			InputStream is = getClass().getResourceAsStream(map);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			
			int col = 0;
			int row = 0;
			
			while(col < gp.screenCol && row < gp.screenrow) {
				
				String line = br.readLine();
				
				while(col < gp.screenCol) {
					
					String nums[] = line.split(" ");
					
					int num = Integer.parseInt(nums[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				if(col == gp.screenCol) {
					col = 0;
					row++;
				}
			}
			
			br.close();
			
		} catch (Exception e) {
			
		}
	}
	
	public void draw(Graphics2D g2) {
		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize, null); 
		
		int col = 0;
		int row = 0;
		int x = 0;
		int y = 0;
		
		while(col < gp.screenCol && row < gp.screenrow) {
			
			int tileNum = mapTileNum[col][row];
			
			g2.drawImage(tile[tileNum].image, x, y, gp.tileSize, gp.tileSize, null);
			col++;
			x += gp.tileSize;
			
			if(col == gp.screenCol) {
				col = 0;
				x = 0;
				row++;
				y += gp.tileSize;
			}
		}
	}
}