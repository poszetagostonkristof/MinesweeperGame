import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class MyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3426940946811133635L;

	private int uresHely=3;
	private int koord1;
	private int koord2;
	private int xx;
	private int yy;
	private boolean kikapott = false;
	private int dbHelyes = 0;
	private boolean nyert = false;
	private int osszakna = 0;
	private int nehezseg = 10;
	private int[][] akna;
	private int[][] szomszedok;
	private int[][] feltart;
	private Random rand;

	public static final Color VILAGOS_PIROS = new Color(255,102,102);
	public static final Color SOTET_PIROS = new Color(153,0,0);
	public static final Color VILAGOS_ZOLD = new Color(0,255,51);
	public static final Color SOTET_ZOLD = new Color(0,102,0);
	public static final Color SOTET_KEK = new Color(0,0,153);
	public static final Color VILAGOS_SARGA = new Color(255,255,204);
	private Mozgatva mozgatva;
	private Klikkelve klikkelve;
	
	
	public MyPanel() {
		mozgatva = new Mozgatva();
		this.addMouseMotionListener(mozgatva);
	
		klikkelve = new Klikkelve();
		this.addMouseListener(klikkelve);
		
		akna = new int[12][10];
		feltoltAknaval(akna);
		
		feltart = new int[12][10];
		feltoltNullaval(feltart);
		
		szomszedok = new int[12][10];
		feltoltSzomszeddal(szomszedok);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 900, 820);
		
		for (int i = 0; i<12; i++) {
			for (int j = 0; j<10; j++) {
				g.setColor(Color.LIGHT_GRAY);
				//(x,y) = (15,30)-tol toltom ki negyzetracsokkal
				koord1 = 15+uresHely+i*70;
				koord2 = uresHely+30+j*70;
				
				//aknakat megjelenit - ellenorzeskepp
				/*if (akna[i][j] == 1)
					g.setColor(Color.cyan);*/
				
				if (feltart[i][j]!=0) {
					
					if (akna[i][j] == 1)
						g.setColor(Color.BLACK); //ha aknat talaltam, fekete lesz a negyzetracs hattere
					else
						g.setColor(VILAGOS_SARGA);
					if (szomszedok[i][j] == 0 && akna[i][j] == 0) { //ha nincs 1 szomszedja sem, feltarjuk a szomszedjait
						osszSzomszedFeltar(feltart,i,j);
					}
				}

				if (negyzetbenVan(koord1,koord2)) {
					g.setColor(Color.MAGENTA);
				}
				
				g.fillRect(koord1, koord2, 70 - uresHely, 70 - uresHely);
				
				if (feltart[i][j] != 0) {
					if (akna[i][j] == 1) { //ha aknat talaltunk, kikapott a jatekos
						g.setColor(Color.RED);
						g.fillOval(koord1+20, koord2+20, 30, 30);
						
						kikapott = true;
						
					} else {
						//aszerint allitjuk be a szamok szineit, hogy hany szomszedjuk van a negyzetracsoknak
						szinBeallitas(szomszedok,i,j,g);		
					}
				
					if (szomszedok[i][j] != 0 && akna[i][j] != 1) {
						g.setFont(new Font("Tahoma", Font.BOLD, 40));
						g.drawString(Integer.toString(szomszedok[i][j]), koord1+20, koord2+45);
				
					}
				}
				
				hanyatTartunkFel();
				if (dbHelyes == (120 - osszakna)) 
					nyert = true; 
				
				//ha mar kikapott vagy nyert, nem tud klikkelni
				if (klikkelve.getB() == true && kikapott == false && nyert == false) {
					xx = koordX(klikkelve.getX2());
					yy = koordY(klikkelve.getY2());
					if (megfeleloKoord(xx,yy)) {
						feltart[xx][yy] = 1;
					}
					hanyatTartunkFel();
					klikkelve.setB(false);
				}
				if (dbHelyes == (120 - osszakna)) 
					nyert = true; 
				
			}	
		}
		
	}
	
	public boolean getNyert() {
		return nyert;
	}
	
	public boolean negyzetbenVan(int koord1, int koord2){
		return (mozgatva.getX() >= koord1 && mozgatva.getX() <= koord1+67 && mozgatva.getY() >= koord2 && mozgatva.getY()<=koord2+67);
	}
	
	public void hanyatTartunkFel() {
		dbHelyes = 0;
		for (int i = 0; i<12; i++) {
			for (int j = 0; j<10; j++) {
				if (feltart[i][j] == 1)
					dbHelyes++;
			}
		}
	}
	
	public void feltoltAknaval(int [][] akna) {
		rand = new Random();
		for (int i = 0; i<12; i++) {
			for (int j = 0; j<10; j++) {
				if (rand.nextInt(nehezseg) == 0) {
					osszakna++;
					akna[i][j] = 1;
				}
				 else 
					akna[i][j] = 0;
				
			}
		}
	}
	
	public void feltoltNullaval(int [][] feltart) {
		for (int i = 0; i<12; i++) {
			for (int j = 0; j<10; j++) {
				feltart[i][j] = 0;
			}
		}
	}
	
	public void szinBeallitas(int [][] szomszedok, int i, int j, Graphics g) {
		switch (szomszedok[i][j]) {
		case 1:
			g.setColor(Color.blue);
			break;
		case 2:
			g.setColor(VILAGOS_ZOLD);
			break;
		case 3:
			g.setColor(VILAGOS_PIROS);
			break;
		case 4:
			g.setColor(SOTET_KEK);
			break;
		case 5:
			g.setColor(SOTET_PIROS);
			break;
		case 6:
			g.setColor(SOTET_ZOLD);
			break;
		case 7:
			g.setColor(Color.PINK);
			break;
		case 8:
			g.setColor(Color.cyan);
			break;
		}
	}
	
	public int koordX(int x) {
		
		for (int i = 0; i<12; i++) {
			for (int j = 0; j<10; j++) {
				koord1 = 15+uresHely+i*70;
				koord2 = uresHely+30+j*70;
				
				if (negyzetbenVan(koord1,koord2))
					return i;
			}
		}
		return -100;
	}
	
	public int koordY(int Y) {
		
		for (int i = 0; i<12; i++) {
			for (int j = 0; j<10; j++) {
				koord1 = 15+uresHely+i*70;
				koord2 = uresHely+30+j*70;
				
				if (negyzetbenVan(koord1,koord2))
					return j;
			}
		}
		return -100;
	}
	
	public boolean megfeleloKoord(int x, int y) {
		return (x != -100 && y != -100);
	}
	
	
	public void allomanybaIr(int dbHelyes) {
	
		    String str = Integer.toString(dbHelyes);
		    BufferedWriter writer;
			try {
				writer = new BufferedWriter(new FileWriter("vmi.txt", true));
				writer.append('\n');
			    writer.append(str);
			    writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	}
	
	public void resetGame1(int nehezs) {
		nehezseg = nehezs;
		kikapott = false;
		nyert = false;
		allomanybaIr(dbHelyes);
		dbHelyes = 0;
		osszakna = 0;
		feltoltAknaval(akna);
		feltoltNullaval(feltart);
		feltoltSzomszeddal(szomszedok);
	}
	
	public void feltoltSzomszeddal(int[][] szomszedok) {
		int dbSz = 0;
		for (int i=0; i<12; i++) {
			for (int j = 0; j<10; j++) {
				dbSz=0;
				if (i-1 >= 0)
					if (akna[i-1][j] == 1)
						dbSz++;
				if ( (i-1 >= 0) && (j-1 >= 0))
					if (akna[i-1][j-1] == 1)
						dbSz++;
				if ((i-1 >= 0) && (j+1 < 10))
					if (akna[i-1][j+1] == 1)
						dbSz++;
				if (j-1 >= 0)
					if (akna[i][j-1] == 1)
						dbSz++;
				if (j+1 < 10)
					if (akna[i][j+1] == 1)
						dbSz++;
				if (i+1 < 12)
					if (akna[i+1][j] == 1)
						dbSz++;
				if ((i+1 < 12) && (j-1 >= 0))
					if (akna[i+1][j-1] == 1)
						dbSz++;
				if ((i+1 < 12) && (j+1 < 10))
					if (akna[i+1][j+1] == 1)
						dbSz++;
				szomszedok[i][j] = dbSz;
			}
		}
	}
	
	public void osszSzomszedFeltar(int[][] feltart, int i, int j) {
		if (i-1 >= 0)
			feltart[i-1][j] = 1;
		
		if ((i-1 >= 0) && (j-1 >= 0))
			 feltart[i-1][j-1] = 1;
				
		if ((i-1 >= 0) && (j+1 < 10))
			 feltart[i-1][j+1] = 1;
				
		if (j-1 >= 0)
			 feltart[i][j-1] =1;
				
		if (j+1 < 10)
			 feltart[i][j+1] = 1;
				
		if (i+1 < 12)
			feltart[i+1][j] = 1;
		
		if ((i+1 < 12) && (j-1 >= 0))
			feltart[i+1][j-1] = 1;
				
		if ((i+1 < 12) && (j+1 < 10))
			feltart[i+1][j+1] = 1;
		
	}
}
