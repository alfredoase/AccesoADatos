package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JTextField;
import javax.swing.text.View;

import model.*;
import view.*;

public class GestionEventos {

	private GestionDatos model;
	private LaunchView view;
	private ActionListener actionListener_comparar, actionListener_buscar;
	private String error = "Error no se encuentra el archivo, o es posible que haya algun error en la insercion de los datos.";

	public GestionEventos(GestionDatos model, LaunchView view) {
		this.model = model;
		this.view = view;
	}

	public void contol() {
		actionListener_comparar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				//call_compararContenido();
				call_GuardarLibro();
			}
		};
		view.getComparar().addActionListener(actionListener_comparar);

		actionListener_buscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					call_buscarPalabra();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		view.getBuscar().addActionListener(actionListener_buscar);
	}

	private int call_compararContenido() throws FileNotFoundException{
		try {
			String fichero1 = view.getFichero1().getText();
			String fichero2 = view.getFichero2().getText();
			
			model.compararContenido(fichero1, fichero2);
			
			if(model.compararContenido(fichero1, fichero2) == true) {
				view.getTextArea().setText("SON IGUALES. \n");
			}else {
				view.getTextArea().setText("SON DIFERENTES. \n");
			}
		}catch(IOException e){
			view.showError(error);
		}
		
		return 1;
	}

	private int call_buscarPalabra() throws FileNotFoundException{
		try {
			String fichero1 = view.getFichero1().getText();
			String palabraBuscar = view.getPalabra().getText();
			Boolean primera_aparicion = view.getPrimera().isSelected();
			int numeroLinea = model.numeroLineaPalabra;
			
			if(model.buscarPalabra(fichero1, palabraBuscar,primera_aparicion) == 1) {
				view.showAdvert("SI NO APARECE NADA, VOLVER A PULSAR EL BOTON");
				view.getTextArea().setText("Primera aparicion de la palabra ("+ palabraBuscar +") en la linea: "+numeroLinea+".");
			}
			if(model.buscarPalabra(fichero1, palabraBuscar,primera_aparicion) == 0) {
				view.showAdvert("SI NO APARECE NADA, VOLVER A PULSAR EL BOTON");
				view.getTextArea().setText("Segunda aparicion de la palabra ("+ palabraBuscar +") en la linea: "+numeroLinea+".");
			}
		}
		catch(IOException e){
			view.showError(error);
		}
		return 1;
	}

	//METODOS DE LA SEGUNDA NO HECHOS
	private void call_GuardarLibro() {
		Libro libro = new Libro();
		
		model.guardar_libro(libro);
	}
}
