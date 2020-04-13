
public class Aknakereso implements Runnable{

	MyFrame frame = new MyFrame();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		new Thread(new Aknakereso()).start();

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {
			frame.repaint();
			
			if (frame.getPanel().getNyert() == true)
				frame.setLabelNyert();
			else
				frame.setLabel1();
		}
		
	}

}
