package gestionficherosapp;

import java.io.File;
import java.io.IOException;
import java.util.Date;

import gestionficheros.FormatoVistas;
import gestionficheros.GestionFicheros;
import gestionficheros.GestionFicherosException;
import gestionficheros.TipoOrden;

public class GestionFicherosImpl implements GestionFicheros {
	private File carpetaDeTrabajo = null;
	private Object[][] contenido;

	private int filas = 0;
	private int columnas = 3;

	private FormatoVistas formatoVistas = FormatoVistas.NOMBRES;
	private TipoOrden tipoOrden = TipoOrden.DESORDENADO;

	public GestionFicherosImpl() {
		carpetaDeTrabajo = File.listRoots()[0];

		actualiza();
	}

	private void actualiza() {
		String[] ficheros = carpetaDeTrabajo.list();

		filas = ficheros.length / columnas;
		if (filas * columnas < ficheros.length) {
			filas++;
		}

		contenido = new String[filas][columnas];

		for (int i = 0; i < columnas; i++) {
			for (int j = 0; j < filas; j++) {
				int ind = j * columnas + i;
				if (ind < ficheros.length) {
					contenido[j][i] = ficheros[ind];
				} else {
					contenido[j][i] = "";
				}
			}
		}
	}

	// METODOS DE TRABAJO
	@Override
	public void arriba() {
		if (carpetaDeTrabajo.getParentFile() != null) {
			carpetaDeTrabajo = carpetaDeTrabajo.getParentFile();
			actualiza();
		}
	}

	@Override
	public void creaCarpeta(String arg0) throws GestionFicherosException {
		File archivo = new File(carpetaDeTrabajo, arg0);
		
		if (archivo.exists()) {
			throw new GestionFicherosException("La carpeta " + archivo.getAbsolutePath() + " ya existe.");
		}
		if (!carpetaDeTrabajo.canWrite()) {
			throw new GestionFicherosException("No tienes permisos para crear la carpeta: " + archivo.getAbsolutePath() + ".");
		}
		
		archivo.mkdirs();

		actualiza();
	}

	@Override
	public void creaFichero(String arg0) throws GestionFicherosException {
		File archivo = new File(carpetaDeTrabajo, arg0);

		if (archivo.exists()) {
			throw new GestionFicherosException("El archivo " + archivo.getAbsolutePath() + " ya existe.");
		}
		if (!carpetaDeTrabajo.canRead()) {
			throw new GestionFicherosException("No tienes permisos para: " + archivo.getAbsolutePath() + ".");
		}
		
		try {
			archivo.createNewFile();

			actualiza();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void elimina(String arg0) throws GestionFicherosException {
		File archivo = new File(carpetaDeTrabajo, arg0);
		
		if (!archivo.exists()) {
			throw new GestionFicherosException("No existe.");
		}

		if (!archivo.canRead()) {
			throw new GestionFicherosException("No tienes permisos para: " + archivo.getAbsolutePath() + ".");
		}
		
		archivo.delete();
		
		//SI NO BORRA LA CARPETA SALTA ERROR
		if( ( !archivo.delete() ) && ( archivo.isDirectory() ) ) {
			throw new GestionFicherosException("No se puede eliminar: " + archivo.getAbsolutePath() + " (Comprueba que este vacia).");
		}

		actualiza();
	}

	@Override
	public void entraA(String arg0) throws GestionFicherosException {
		File archivo = new File(carpetaDeTrabajo, arg0);
		try {

			if (!archivo.isDirectory()) {
				throw new GestionFicherosException("Se esperaba un directorio " + archivo.getAbsolutePath() + " no lo es.");
			}

			if (!archivo.canRead()) {
				throw new GestionFicherosException("No tienes permisos para: " + archivo.getAbsolutePath() + ".");
			}
	
			carpetaDeTrabajo = archivo;
			
			actualiza();

		} catch (NullPointerException e) {
			throw new GestionFicherosException("No se puede acceder a: " + archivo.getAbsolutePath() + ".");
		}
	}

	@Override
	public int getColumnas() {
		return columnas;
	}

	@Override
	public Object[][] getContenido() {
		return contenido;
	}

	@Override
	public String getDireccionCarpeta() {
		return carpetaDeTrabajo.getAbsolutePath();
	}

	@Override
	public String getEspacioDisponibleCarpetaTrabajo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getEspacioTotalCarpetaTrabajo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getFilas() {
		return filas;
	}

	@Override
	public FormatoVistas getFormatoContenido() {
		return formatoVistas;
	}

	@Override
	public String getInformacion(String arg0) throws GestionFicherosException {
		StringBuilder cadena = new StringBuilder();
		File archivo = new File(carpetaDeTrabajo, arg0);
		File[] elementosLista;

		int numElementos = 0;

		// CONTROLES
		if (!archivo.exists()) {
			throw new GestionFicherosException("No existe.");
		}
		if (!archivo.canRead()) {
			throw new GestionFicherosException("No tienes permisos.");
		}

		cadena.append("- INFORMACION DEL SISTEMA -");
		cadena.append("\n\n");

		// NOMBRE
		cadena.append("Nombre: " + arg0);
		cadena.append("\n");

		// TIPO
		if (archivo.isFile()) {
			cadena.append("Tipo: Archivo");
			cadena.append("\n");
		} else if (archivo.isDirectory()) {
			cadena.append("Tipo: Directorio");
			cadena.append("\n");
		}

		// RUTA
		cadena.append("Ruta: " + archivo.getAbsolutePath());
		cadena.append("\n");

		// FECHA MODIFICACION
		Date d = new Date(archivo.lastModified());

		cadena.append("Fecha ultima modificacion:" + d);
		cadena.append("\n");

		// FICHERO OCULTO
		if (archivo.isHidden()) {
			cadena.append("Oculto: Si.");
			cadena.append("\n");
		} else if (!archivo.isHidden()) {
			cadena.append("Oculto: No.");
			cadena.append("\n");
		}

		// TAMAÑO FICHERO
		if (archivo.isFile()) {
			cadena.append("Tamaño: " + archivo.length() + " bytes.");
		}

		// TAMAÑO DIRECTORIO
		if (archivo.isDirectory()) {
			cadena.append("Espacio libre: " + archivo.getFreeSpace() + " bytes.");
			cadena.append("\n");
			cadena.append("Espacio disponible: " + archivo.getUsableSpace() + " bytes.");
			cadena.append("\n");
			cadena.append("Espacio total: " + archivo.getTotalSpace() + " bytes.");
			cadena.append("\n");

			elementosLista = archivo.listFiles();
			numElementos = 0;

			for (int i = 1; i < elementosLista.length; i++) {
				numElementos = i;
			}
			cadena.append("Numero de elementos: " + numElementos);
		}

		//
		return cadena.toString();
	}

	@Override
	public boolean getMostrarOcultos() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getNombreCarpeta() {
		return carpetaDeTrabajo.getName();
	}

	@Override
	public TipoOrden getOrdenado() {
		return tipoOrden;
	}

	@Override
	public String[] getTituloColumnas() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getUltimaModificacion(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String nomRaiz(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int numRaices() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void renombra(String arg0, String arg1) throws GestionFicherosException {
		File archivo = new File(carpetaDeTrabajo, arg0);
		File archivo2 = new File(carpetaDeTrabajo, arg1);
		
		if (!archivo.exists()) {
			throw new GestionFicherosException("No existe.");
		}

		if (!archivo.canRead()) {
			throw new GestionFicherosException("No tienes permisos para: " + archivo.getAbsolutePath() + ".");
		}
		
		archivo.renameTo(archivo2);

		actualiza();
	}

	@Override
	public boolean sePuedeEjecutar(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sePuedeEscribir(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean sePuedeLeer(String arg0) throws GestionFicherosException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColumnas(int arg0) {
		columnas = arg0;

	}

	@Override
	public void setDirCarpeta(String arg0) throws GestionFicherosException {
		File archivo = new File(arg0);

		if (!archivo.isDirectory()) {
			throw new GestionFicherosException("Se esperaba un directorio " + archivo.getAbsolutePath() + " no lo es.");
		}

		if (!archivo.canRead()) {
			throw new GestionFicherosException("No tienes permisos para: " + archivo.getAbsolutePath() + ".");
		}

		carpetaDeTrabajo = archivo;
		actualiza();
	}

	@Override
	public void setFormatoContenido(FormatoVistas arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setMostrarOcultos(boolean arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOrdenado(TipoOrden arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeEjecutar(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeEscribir(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSePuedeLeer(String arg0, boolean arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}

	@Override
	public void setUltimaModificacion(String arg0, long arg1) throws GestionFicherosException {
		// TODO Auto-generated method stub

	}
}
