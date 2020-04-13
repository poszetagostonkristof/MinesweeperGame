import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Mozgatva implements MouseMotionListener {

	private int x=0;
	private int y=0;
	
	
	public Mozgatva() {
	
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		x = e.getX();
		y = e.getY();
		//System.out.println("Eger megmozgatva: "+x+" "+y);
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x= x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

}
