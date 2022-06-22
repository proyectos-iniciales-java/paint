package Paint;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.LayoutManager;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

public class Lienzo extends JPanel implements MouseMotionListener, MouseListener, ActionListener, Serializable {

	//declaracion de variables
	private ArrayList<InfoLinea> infoLineas = new ArrayList<InfoLinea>();
	
	private Point inicioArrastre;
	private Point finArrastre;

	private JMenuBar barraMenu;
	private JMenu archivo;
	private JMenuItem guardar,abrir,menuColor, borrar;
	
	private Graphics2D g2;
	private Graphics2D grafico;
	
	private JButton botonAzul, botonRojo, botonAmarillo, botonNaranja, botonNegro, botonMagenta, botonBorrar;
	
	private Color colorLinea = Color.black;
	
	//constructor objeto lienzo		
	public Lienzo() {
		
		this.setLayout(null);
		this.setBounds(100, 100, 500, 500);
		this.setBackground(Color.WHITE);
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	
	}
	
	//metodo para la creacion del menu
	public JMenuBar creacionMenu() {
		barraMenu = new JMenuBar();
		archivo = new JMenu ("Archivo");
		menuColor = new JMenu ("Color");
		
		barraMenu.add(archivo);
		barraMenu.add(menuColor);
		
		
		abrir = new JMenuItem("Abrir");
		abrir.addActionListener(this);
		guardar = new JMenuItem("guardar");
		guardar.addActionListener(this);
		borrar = new JMenuItem("Borrar");
		borrar.addActionListener(this);
		
		archivo.add(abrir);
		archivo.add(guardar);
		archivo.add(borrar);
		
		creacionBotones();
		menuColor.add(botonAzul);
		menuColor.add(botonNegro);
		menuColor.add(botonRojo);
		menuColor.add(botonAmarillo);
		menuColor.add(botonMagenta);
		menuColor.add(botonNaranja);
		
		return barraMenu;
		
	}
	//metodo para la creacion de botones
	public void creacionBotones() {
		botonAzul = new JButton("     azul        ");
		this.add(botonAzul);
		botonAzul.addActionListener(this);
		botonAzul.setBounds(50, 50, 10, 10);
		botonAzul.setForeground(Color.white);
		botonAzul.setBackground(Color.blue);
		
		botonNegro = new JButton("   negro       ");
		this.add(botonNegro);
		botonNegro.addActionListener(this);
		botonNegro.setBounds(50, 50, 10, 10);
		botonNegro.setForeground(Color.white);
		botonNegro.setBackground(Color.black);
		
		botonRojo = new JButton("     rojo        ");
		this.add(botonRojo);
		botonRojo.addActionListener(this);
		botonRojo.setBounds(50, 50, 10, 10);
		botonRojo.setForeground(Color.white);
		botonRojo.setBackground(Color.red);
		
		botonAmarillo = new JButton("  amarillo   ");
		this.add(botonAmarillo);
		botonAmarillo.addActionListener(this);
		botonAmarillo.setBounds(50, 50, 10, 10);
		botonAmarillo.setBackground(Color.yellow);
		
		botonMagenta = new JButton("  magenta  ");
		this.add(botonMagenta);
		botonMagenta.addActionListener(this);
		botonMagenta.setBounds(50, 50, 10, 10);
		botonMagenta.setForeground(Color.white);
		botonMagenta.setBackground(Color.MAGENTA);
		
		botonNaranja = new JButton("  Naranja    ");
		this.add(botonNaranja);
		botonNaranja.addActionListener(this);
		botonNaranja.setBounds(50, 50, 10, 10);
		botonNaranja.setBackground(Color.orange);
		
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		 	
			if (e.getSource() == botonAzul) {
			  colorLinea = Color.blue;
	      } else if (e.getSource() == botonRojo) {
	    	  colorLinea = Color.red;
	      } else if (e.getSource() == botonAmarillo) {
	    	  colorLinea = Color.yellow;
	      } else if (e.getSource() == botonNaranja) {
	    	  colorLinea = Color.orange;
	      } else if (e.getSource() == botonNegro) {
	    	  colorLinea = Color.black;
	      }else if (e.getSource() == botonMagenta) {
			  colorLinea = Color.MAGENTA;
	      }else if (e.getSource() == borrar) {
	    	  borrar();
	    	  repaint();
	      }else if(e.getSource() == guardar) {
	    	  ManejoArchivos.guardarArchivo(infoLineas); 
	      }else if (e.getSource() == abrir) {
	    	  infoLineas = ManejoArchivos.abrirArchivo();
	    	  repaint();
	      }
		
	}
	
	//evento del mouse
	@Override
	public void mouseDragged(MouseEvent e) {
		finArrastre = new Point(e.getX(), e.getY());
		Shape linea = crearLinea(inicioArrastre.x, inicioArrastre.y, finArrastre.x, finArrastre.y);
		InfoLinea infoLinea = new InfoLinea();
		infoLinea.linea = linea;
		infoLinea.color = colorLinea;
		infoLineas.add(infoLinea);
		inicioArrastre = new Point(finArrastre.x, finArrastre.y);
		repaint();
		revalidate();
	}
	//metodo para dibujar las lineas
	@Override
	public void paint(Graphics g) {
		
		g2 = (Graphics2D) g;
		g2.setColor(colorLinea);
		printComponent(g);
		for (int i = 0; i<infoLineas.size() ;i++) {
			InfoLinea infoLinea = infoLineas.get(i);
			g2.setColor(infoLinea.color);
			g2.draw(infoLinea.linea);
		}
		
	}
	public void borrar() {
		infoLineas.clear();
	}
	

	@Override
	public void mousePressed(MouseEvent e) {
		inicioArrastre = new Point(e.getX(), e.getY());
		repaint();
		revalidate();
		

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		finArrastre = new Point(e.getX(), e.getY());
		Shape linea = crearLinea(inicioArrastre.x, inicioArrastre.y, finArrastre.x, finArrastre.y);
		InfoLinea infoLinea = new InfoLinea();
		infoLinea.linea = linea;
		infoLinea.color = colorLinea;
		infoLineas.add(infoLinea);
		repaint();
		revalidate();
	}
	
	//evento que permite dibujar las lineas en 2d
	private Shape crearLinea(int x1, int y1, int x2, int y2) {
		return new Line2D.Float(x1, y1, x2, y2);
	}
	
	
	public static void main(String[] args) {
		Lienzo lienzo = new Lienzo();
		JFrame tablero = new JFrame();
		tablero.setJMenuBar(lienzo.creacionMenu());
		tablero.add(lienzo);
		tablero.setVisible(true);
		tablero.setBounds(600, 300, 500, 500);
		tablero.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}
	
	@Override
	public void mouseMoved(MouseEvent e) {

	}
	
}
