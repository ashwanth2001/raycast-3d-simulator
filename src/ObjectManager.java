import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class ObjectManager {
	Player player;
	int playerSpeed;
	Map tmap;
	Caster caster;
	Dessiner d;
	
	int width;
	int height;
	int mwidth;
	int mheight;
	
	public ObjectManager(int width, int height) {
		this.width = width;
		this.height = height;
		mwidth = 1000;
		mheight = 1000;
		
		player = new Player(75, 75, 15);
		playerSpeed = 2;
		
		int[][] pgrid = {
			{1, 3, 3, 3, 3, 3, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
			{1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 1}, 
			{1, 1, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1}, 
			{1, 1, 2, 3, 0, 0, 0, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1}, 
			{1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1}, 
			{1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1}, 
			{1, 1, 0, 1, 0, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1}, 
			{1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 1}, 
			{1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 0, 0, 0, 1}, 
			{1, 1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1}, 
			{1, 0, 0, 1, 0, 1, 0, 1, 1, 1, 0, 0, 0, 1, 1, 1, 1}, 
			{1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 1, 1, 1}, 
			{1, 1, 0, 0, 0, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 1}, 
			{1, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1}, 
			{1, 0, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 1}, 
			{1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1},
			{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		};
		
		tmap = new Map(mwidth, mheight, pgrid);
		caster = new Caster(mwidth, mheight, width, height);
		d = new Dessiner(width, height, caster.getMaxDist(), tmap.getDif(), pgrid);
		
		d.addTexture("bluestone.png");
		d.addTexture("greystone.png");
		d.addTexture("redbrick.png");
		d.addTexture("wood.png");
	}
	
	public void movePlayer(double[] input) {
		input[0]*=playerSpeed;
		input[1]*=playerSpeed;
		input = tmap.checkCollision(player, input);
		player.move(input);
	}
	
	public void updateCaster() {
		caster.cast(tmap, player);
	}

	public void draw(Graphics g) {
		draw3D(g);
	}
	
	public void draw3D(Graphics g) {
		d.draw3D(g, caster.getRays(), player.getAngley());
		tmap.draw(g, width*3/4, 0, width/4, width/4);
		player.draw(g, width*3/4, 0, (double)width/mwidth*.25);
	}
	
	public void draw2D(Graphics g) {
		caster.draw2D(g);
		tmap.draw(g, 0, 0, width, height);
		player.draw(g, 0, 0, (double)width/mwidth);
	}
}
