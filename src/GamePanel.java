import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, ActionListener, MouseListener{
	private Timer timer;
	private ObjectManager manager; 
	private int width;
	private int height;
	private boolean mouseDown;
	private boolean pause;
	
	private boolean[] controls;
	
	public GamePanel(int width, int height) {
		this.width = width;
		this.height = height;
		
		timer = new Timer(20, this);
		manager = new ObjectManager(width, height);
		mouseDown = false;
		pause = false;
		controls = new boolean[6];
	}
	
	public void start() {
		timer.start();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		updateMove();
		manager.updateCaster();	
		repaint();
	}
	
	public void updateMove() {
		if(pause) {
			return;
		}
		
		double[] input = new double[4];
		if(controls[0]) {
			input[1]--;
		}
		if(controls[1]) {
			input[0]--;
		}
		if(controls[2]) {
			input[1]++;
		}
		if(controls[3]) {
			input[0]++;
		}
		updateMouse(input);
		manager.movePlayer(input);
	}
	
	public void updateMouse(double[] input) {		
		double xdif = MouseInfo.getPointerInfo().getLocation().getX()-width/2;
		xdif/=3;
		input[2]+=xdif;
		
		double ydif = MouseInfo.getPointerInfo().getLocation().getY()-height/2;
		ydif/=3;
		//input[3]+=ydif;
		
		moveMouse(new Point(width/2, height/2));
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, width, height);
		manager.draw(g);
	}
	
	public void moveMouse(Point p) {
	    GraphicsEnvironment ge = 
	        GraphicsEnvironment.getLocalGraphicsEnvironment();
	    GraphicsDevice[] gs = ge.getScreenDevices();

	    // Search the devices for the one that draws the specified point.
	    for (GraphicsDevice device: gs) { 
	        GraphicsConfiguration[] configurations =
	            device.getConfigurations();
	        for (GraphicsConfiguration config: configurations) {
	            Rectangle bounds = config.getBounds();
	            if(bounds.contains(p)) {
	                // Set point to screen coordinates.
	                Point b = bounds.getLocation(); 
	                Point s = new Point(p.x - b.x, p.y - b.y);

	                try {
	                    Robot r = new Robot(device);
	                    r.mouseMove(s.x, s.y);
	                } catch (AWTException e) {
	                    e.printStackTrace();
	                }

	                return;
	            }
	        }
	    }
	    // Couldn't move to the point, it may be off screen.
	    return;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseDown = true;
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseDown = false;
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		setPause(e);
		setControls(e, true);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		setControls(e, false);
	}
	
	public void setPause(KeyEvent e) {
		if(e.getKeyCode() == 32) {
			pause = !pause;
		}
	}
	
	public void setControls(KeyEvent e, boolean b) {
		if(e.getKeyCode() == 87) {
			controls[0] = b;
			//System.out.println("w");
		}
		if(e.getKeyCode() == 65) {
			controls[1] = b;
			//System.out.println("a");
		}
		if(e.getKeyCode() == 83) {
			controls[2] = b;
			//System.out.println("s");
		}
		if(e.getKeyCode() == 68) {
			controls[3] = b;
			//System.out.println("d");
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
			controls[4] = b;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			controls[5] = b;
		}
	}
}
