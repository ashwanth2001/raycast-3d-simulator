import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;

public class Map {
	private int width;
	private int height;
	private int[][] grid; 
	
	private int xint;
	private int yint;
	private ArrayList<int[]> lines;
	
	public Map(int width, int height, int[][] grid) {
		this.width = width;
		this.height = height;
		this.grid = grid; 
		instantiate();
	}
	
	private void instantiate() {
		intervals();
		calcLines2();
	}
	
	private void intervals() {
		xint = width/grid[0].length;
		yint = width/grid.length;
	}
	
	private void calcLines() {
		
		lines = new ArrayList<int[]>();
		int start = -1;
		
		for(int i = 1; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i-1][j]!=0 ^ grid[i][j]!=0) {
					if(start == -1) {
						start = j;
					}
					if(j == grid[0].length-1) {
						int[] line = {start, i, j+1, i, 0};
						lines.add(line);
						start = -1;
					}
				}
				else if(start!=-1) {
					int[] line = {start, i, j, i, 0};
					lines.add(line);
					start = -1;
				}
			}
		}
				
		for(int j = 1; j<grid[0].length; j++) {
			for(int i = 0; i<grid.length; i++) {
				if(grid[i][j-1]==1 ^ grid[i][j]==1) {
					if(start == -1) {
						start = i;
					}
					if(i == grid.length-1) {
						int[] line = {j, start, j, i+1, 1};
						lines.add(line);
						start = -1;
					}
				}
				else if(start!=-1) {
					int[] line = {j, start, j, i, 1};
					lines.add(line);
					start = -1;
				}
			}
		}
		
		for(int i = 0; i<lines.size(); i++) {
			lines.get(i)[0]*=xint;
			lines.get(i)[1]*=yint;
			lines.get(i)[2]*=xint;
			lines.get(i)[3]*=yint;
		}
	}
	
	private void calcLines2() {
		lines = new ArrayList<int[]>();
		
		int ind;
		for(int i = 1; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i-1][j]!=0 ^ grid[i][j]!=0) {
					ind = grid[i-1][j]+grid[i][j];
					int[] line = {j, i, j+1, i, 0, ind};
					lines.add(line);
				}
			}
		}
				
		for(int j = 1; j<grid[0].length; j++) {
			for(int i = 0; i<grid.length; i++) {
				if(grid[i][j-1]!=0 ^ grid[i][j]!=0) {
					ind = grid[i][j-1]+grid[i][j];
					int[] line = {j, i, j, i+1, 1, ind};
					lines.add(line);
				}
			}
		}
		
		for(int i = 0; i<lines.size(); i++) {
			lines.get(i)[0]*=xint;
			lines.get(i)[1]*=yint;
			lines.get(i)[2]*=xint;
			lines.get(i)[3]*=yint;
		}
	}
	
	public void printLines() {
		for(int i = 0; i<lines.size(); i++) {
			System.out.println(Arrays.toString(lines.get(i)));
		}
	}
	
	public double[] checkCollision(Player p, double[] input) {
		double[] ret = new double[6];
		ret[0] = input[0];
		ret[1] = input[1];
		ret[2] = 1;
		ret[3] = 1;
		ret[4] = input[2];
		ret[5] = input[3];
		
		int anglex = p.getAnglex();
		int rad = p.getRad();
		
		double plx = p.getX() - (Math.cos(anglex*Math.PI/180)*input[1]+Math.cos((anglex-90)*Math.PI/180)*input[0]);
		double ply = p.getY() - (Math.sin(anglex*Math.PI/180)*input[1]+Math.sin((anglex-90)*Math.PI/180)*input[0]);
		
		//System.out.println(plx + ", " + ply);
		
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i][j] == 1) {
					int wx = j*xint;
					int wy = i*yint;
					double nx = Math.max(wx, Math.min(plx, wx+xint));
					double ny = Math.max(wy, Math.min(ply, wy+yint));
					
					if(circlePoint(plx, ply, rad, nx, ny)) {						
						if((p.getX()-rad>=wx+xint||p.getX()+rad<=wx) && 
								(p.getY()-rad>=wy+yint||p.getY()+rad<=wy)) {
						}
						else if(p.getX()-rad>=wx+xint||p.getX()+rad<=wx) {
							ret[2] = 0;
						}
						else if(p.getY()-rad>=wy+yint||p.getY()+rad<=wy) {
							ret[3] = 0;
						}
					}
				}
			}
		}
		
		return ret;
	}
	
	public boolean circlePoint(double cx, double cy, int rad, double px, double py) {
		double dx = cx-px;
		double dy = cy-py;
		boolean b = dx*dx+dy*dy<rad*rad;
		return b;
	}
	
	public ArrayList<int[]> getLines() {
		return lines;
	}
	
	public int[] getSize() {
		int[] ret = {width, height};
		return ret;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void draw(Graphics g, int x, int y, int width, int height) {
		double xw = (double)width/grid[0].length;
		double yw = (double)height/grid.length;
		
		g.setColor(Color.BLACK);
		for(int i = 0; i<grid.length; i++) {
			for(int j = 0; j<grid[0].length; j++) {
				if(grid[i][j] == 1) {
					g.fillRect((int)(x+xw*(j-1/2)), (int)(y+yw*(i-1/2)), (int)xw+1, (int)yw+1);
				}
			}
		}
		
		/*g.setColor(Color.RED);
		for(int i = 0; i<lines.size(); i++) {
			g.drawLine(lines.get(i)[0], lines.get(i)[1], lines.get(i)[2], lines.get(i)[3]);			
		}*/
	}

	public double getDif() {
		// TODO Auto-generated method stub
		return (double)width/grid.length;
	}
}
