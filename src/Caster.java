import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

public class Caster {

	private int n;
	private int fov;
	private int maxDist;
	
	private int mwidth;
	private int mheight;
	private int width;
	private int height;
	
	private ArrayList<double[]> rays_pr;
	
	public Caster(int mwidth, int mheight, int width, int height) {
		this.mwidth = mwidth;
		this.mheight = mheight;
		this.width = width;
		this.height = height;
		
		n = width;
		fov = 30;
		maxDist = Math.max(mwidth, mheight);
		rays_pr = new ArrayList<double[]>();
	}
	
	public ArrayList<double[]> cast(Map m, Player p) {
		rays_pr = calcRays(m, p);
		rays_pr = overlap(m.getLines(), rays_pr);
		
		return rays_pr;
	}
	
	public ArrayList<double[]> calcRays(Map m, Player p) {
		int[] size = m.getSize();
		ArrayList<int[]> mapLines = m.getLines();
		
		double angle = p.getAnglex();
		double x = p.getX();
		double y = p.getY();
		
		ArrayList<double[]> rays = new ArrayList<double[]>();
		
		for(int i = 0; i<n; i++) {
			double angtemp = angle+fov*((double)i/n-1/2.0);
			double x1 = x+Math.cos(angtemp*Math.PI/180.0)*maxDist;
			double y1 = y+Math.sin(angtemp*Math.PI/180.0)*maxDist;			
			double[] addray = {x, y, x1, y1};
			rays.add(addray);
		}
		
		return rays;
	}
	
	public ArrayList<double[]> overlap(ArrayList<int[]> lines, ArrayList<double[]> rays_pr2) {
		
		int max;
		
		for(int i = 0; i<n; i++) {
			max = maxDist;
			for(int j = 0; j<lines.size(); j++) {
				double[] raytemp = rays_pr2.get(i);
				int[] linetemp = lines.get(j);
				
				double[] r = getRayCast(raytemp[0], raytemp[1], raytemp[2], raytemp[3], linetemp[0], linetemp[1], linetemp[2], linetemp[3], linetemp[4], linetemp[5]);
				if(dist(r[0], r[1], r[2], r[3])<max) {
					max = (int)dist(r[0], r[1], r[2], r[3]);
					rays_pr2.set(i, r);
				}
			}
		}
		return rays_pr2;
	}

	/////
	public double dist(double x1, double y1, double x2, double y2) {
	    return (double) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
	}

	public double[] getRayCast(double p0_x, double p0_y, double p1_x, double p1_y, double p2_x, double p2_y, double p3_x, double p3_y, double type, double txtr) {
	    double s1_x, s1_y, s2_x, s2_y;
	    s1_x = p1_x - p0_x;
	    s1_y = p1_y - p0_y;
	    s2_x = p3_x - p2_x;
	    s2_y = p3_y - p2_y;

	    double s, t;
	    s = (-s1_y * (p0_x - p2_x) + s1_x * (p0_y - p2_y)) / (-s2_x * s1_y + s1_x * s2_y);
	    t = (s2_x * (p0_y - p2_y) - s2_y * (p0_x - p2_x)) / (-s2_x * s1_y + s1_x * s2_y);

	    if (s >= 0 && s <= 1 && t >= 0 && t <= 1) {
	        double x = p0_x + (t * s1_x);
	        double y = p0_y + (t * s1_y);

	        double[] ret = {p0_x, p0_y, x, y, type, txtr};
	        return ret;
	    }
	    
	    double[] ret = {p0_x, p0_y, p1_x, p1_y, type, txtr};
	    return ret;
	}
	/////
	
	public void draw2D(Graphics g) {
		g.setColor(Color.GREEN);
		for(int i = 0; i<rays_pr.size(); i++) {
			double[] line = rays_pr.get(i);
			g.drawLine((int)line[0], (int)line[1], (int)line[2], (int)line[3]);
		}
	}
	
	public ArrayList<double[]> getRays() {
		return rays_pr;
	}

	public int getMaxDist() {
		return maxDist;
	}
}
