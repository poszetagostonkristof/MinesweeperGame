import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;


public class MyFrame extends JFrame {

/**
	 * 
	 */
	private static final long serialVersionUID = 757311480543112438L;

	private MyPanel panel;
	private JLabel label;
	private JMenuBar menubar;
	
	private JMenu menu;
	private JMenu menu2;
	
	private JMenuItem m1;
	private JMenuItem m2;
	private JMenuItem m21;
	private JMenuItem m22;
	private JMenuItem m23;
	
	private Vector<Integer> vect;
	private boolean h = false;
	public MyFrame() {
		menubar = new JMenuBar();
		menu = new JMenu("                                    MENU                                             ");
		m1 = new JMenuItem("                                   Uj jatek                                         ");
		m2 = new JMenuItem("                                 Highscore-ok                                         ");
		
		menu2 = new JMenu("           Nehezsegi szintek             ");
		m21 = new JMenuItem("Kezdo");
		m22 = new JMenuItem("Kozephalado");
		m23 = new JMenuItem("Halado");
		this.panel = new MyPanel();
		this.add(panel,BorderLayout.CENTER);
		
		label = new JLabel();
		this.add(label,BorderLayout.NORTH);
	
		m1.addActionListener(e->{
			//label.setText("Uj jatek");	
			h = false;
			panel.resetGame1(10); //alapesetben kozephalado szinten indul a jatek
			
		});
		
		m2.addActionListener(e->{
			h = true;
			File file = new File("vmi.txt");
			BufferedReader br;
			try {
				 vect = new Vector<Integer>(); 
					int ertek = 0;
					br = new BufferedReader(new FileReader(file));
					String st;
					try {
						while ((st = br.readLine()) != null) 
						    if (st != "" && st != " ") {
							ertek = Integer.parseInt(st);
							vect.add(ertek);
							}
						
						vect.sort(Collections.reverseOrder());
						label.setText("Highscore-ok: "+vect);
					
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
			  
			   
		});
		
		m21.addActionListener(e->{
			h = false;
			panel.resetGame1(20); //5 % esellyel dob aknat minden negyzetracsba
		});
		

		m22.addActionListener(e->{
			h = false;
			panel.resetGame1(10); //10 % esely
		});
		
		m23.addActionListener(e->{
			h = false;
			panel.resetGame1(5); // 20% esely
		});
		menubar.add(menu);
		menu.add(m1);
		menu.add(m2);
		menubar.add(menu2);
		menu2.add(m21);
		menu2.add(m22);
		menu2.add(m23);
		setJMenuBar(menubar);
		this.setSize(900,820);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		
		
	}
	
	public MyPanel getPanel() {
		return panel;
	}
	public void setLabelNyert() {
		label.setText("Gratulalok! Megnyerte a jatekot.");
	}
	public void setLabel1() {
		if (!h)
			label.setText("Uj jatek");
		else
			label.setText("Highscore-ok :" + vect);
	}
}
