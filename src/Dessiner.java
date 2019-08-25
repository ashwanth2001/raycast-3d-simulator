import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Dessiner {

	private int width;
	private int height;
	private int maxDist;
	private double dif;
	ArrayList<int[][][]> textures;
	int[][] grid;
	
	public Dessiner(int width, int height, int maxDist, double dif, int[][] grid) {
		this.width = width;
		this.height = height;
		this.maxDist = maxDist;
		this.dif = dif;
		textures = new ArrayList<int[][][]>();
		this.grid = grid;
	}
	
	public void addTexture(String s) {
		String src = new File("").getAbsolutePath() + "/src/";
		File file= new File(src + s);
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// Getting pixel color by position x and y 
		int[][][] clrs = new int[image.getHeight()][image.getWidth()][3];
		for(int i = 0; i<image.getWidth(); i++) {
			for(int j = 0; j<image.getHeight(); j++) {
				int clr = image.getRGB(i,j); 
				int[] rgb = {(clr & 0x00ff0000) >> 16, (clr & 0x0000ff00) >> 8, clr & 0x000000ff};
				clrs[j][i] = rgb;
			}
		}
		textures.add(clrs);
		
	}
	
	public void draw3D(Graphics g, ArrayList<double[]> rays, double angley) {
		g.setColor(Color.CYAN);
		g.fillRect(0, 0, width, height/2);
		g.setColor(Color.GRAY);
		g.fillRect(0, height/2, width, height/2);
		
		if(rays.size()==0) {
			return;
		}
		
		double dist;
		double step = width / rays.size();
		for(int i = 0; i<rays.size(); i++) {
			
			double[] ray = rays.get(i);
			//int text = text.get(i);
			drawLine(g, ray, i, angley);
		}
	}
	
	public void drawLine(Graphics g, double[] ray, int xd, double angley) {
		double dist = dist(ray[0], ray[1], ray[2], ray[3]);
		double h = 16000/dist;
		double vert = Math.sin(angley*Math.PI/180)*h;
		
		if(textures.size()==0)
			return;
		
		int[][][] txtr = textures.get((int)ray[5]);
		int x = (int)(((5*ray[2]%(int)dif)*((ray[4]+1)%2)+(5*ray[3]%(int)dif)*(ray[4]%2))/dif*txtr.length);

		int ht = txtr.length;
		double height = h/ht;
		for(int i = 0; i<txtr.length; i++) {
			int[] clr = txtr[i][x];
			g.setColor(new Color(clr[0], clr[1], clr[2]));
			g.drawRect(xd, (int)(width/2-h/2+height*i+vert), 1, (int)height);
		}
	}
	
	public double dist(double x1, double y1, double x2, double y2) {
	    return (double) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}
}

//Color c = new Color((int) (Math.random()*255),(int) (Math.random()*255), (int) (Math.random()*255));
//Color c = new Color((int) ((ray[3]*1000)%255),(int) ((ray[3]*1000)%255), (int) ((ray[3]*1000)%255));