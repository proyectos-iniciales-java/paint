package Paint;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFileChooser;

public class ManejoArchivos {
		//serializacion
		public static void guardarArchivo(ArrayList<InfoLinea> infoLineas) {
		
		try {
			//me permite traer la ruta de un archivo
			JFileChooser file =new JFileChooser();
	    	file.showSaveDialog(null);
	    	String url = file.getSelectedFile().getAbsolutePath(); 
	    	System.out.println(file.getSelectedFile().getAbsolutePath());
	    	//System.out.println(file.getSelectedFile().getCanonicalPath());
	    	 
			
			//para guardar el archivo
			//ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream("C:\\Users\\Lina\\eclipse-workspace\\Paint\\src\\Paint\\paint.dat"));
	    	ObjectOutputStream escribiendo_fichero = new ObjectOutputStream(new FileOutputStream(url+".dat"));
	    	escribiendo_fichero.writeObject(infoLineas);
			escribiendo_fichero.close();
			
			
		}catch(Exception e) {
			
			System.out.println("hubo un problema guardando el archivo!!");
		}
	}	
		public static ArrayList <InfoLinea> abrirArchivo() {
			
			try {
				
				//me permite leer la ruta de un archivo
				JFileChooser file=new JFileChooser();
		    	file.showOpenDialog(null);
		    	String url = file.getSelectedFile().getAbsolutePath(); 
		    	
				//para leer el archivos
				//ObjectInputStream leyendo_fichero = new ObjectInputStream(new FileInputStream("C:\\Users\\Lina\\eclipse-workspace\\Paint\\src\\Paint\\paint.dat"));
		    	ObjectInputStream leyendo_fichero = new ObjectInputStream(new FileInputStream(url));
		    	ArrayList <InfoLinea> arrayList2 = ( ArrayList <InfoLinea> )leyendo_fichero.readObject();
				leyendo_fichero.close();
				
				return arrayList2;
				
			}catch(Exception e){
				
				System.out.println("hubo un problema cargando el archivo!!");
			}
			return null;
			
			
		}
	}
	

	