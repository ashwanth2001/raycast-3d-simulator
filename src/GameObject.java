import java.awt.Graphics;

public class GameObject {

	protected double x;
	protected double y;
	protected int width;
	protected int height;
	
	public GameObject(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void draw (Graphics g) {
		g.fillRect((int)(x-width/2), (int)(y-height/2), width, height);
	}
}
