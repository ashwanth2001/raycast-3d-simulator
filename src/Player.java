import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

public class Player {
	
	private double x;
	private double y;
	private int rad;
	private int anglex;
	private int angley;
	
	public Player(int x, int y, int rad) {
		this.x = x;
		this.y = y;
		this.rad = rad;
		anglex = 0;
		angley = 0;
	}
	
	public void move(double[] input) {
		x-=Math.cos(anglex*Math.PI/180)*input[1]*input[2];
		y-=Math.sin(anglex*Math.PI/180)*input[1]*input[3];
		
		x-=Math.cos((anglex-90)*Math.PI/180)*input[0]*input[2];
		y-=Math.sin((anglex-90)*Math.PI/180)*input[0]*input[3];
		
		anglex+=input[4];
		angley+=input[5];
		
		angley = Math.min(80, Math.max(-80, angley));
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getAnglex() {
		return anglex;
	}
	
	public int getAngley() {
		return angley;
	}

	public int getRad() {
		return rad;
	}
	
	public void draw(Graphics g, int x1, int y1, double ratio) {
		g.setColor(Color.RED);
		
		/*xcord = new int[4];
		ycord = new int[4];
		double rad = width/2*Math.pow(width, 1/2);
		for(int i = 0; i<4; i++) {
			xcord[i] = (int)(Math.cos((anglex+i*90+45)*Math.PI/180)*rad+x);
			ycord[i] = (int)(Math.sin((anglex+i*90+45)*Math.PI/180)*rad+y);
		}
		g.fillPolygon(xcord, ycord, 4);*/
		
		g.fillOval((int)((x-rad)*ratio+x1), (int)((y-rad)*ratio+y1), (int)(rad*2*ratio), (int)(rad*2*ratio));
	}
	
}
