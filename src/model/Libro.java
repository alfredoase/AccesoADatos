package model;

import java.io.Serializable;

public class Libro implements Serializable {
		
		private String titulo = null, autor = null, editor = null;
		private int id = 0, año = 0, n_pags = 0;
	
		public Libro() {
		}
	
		public Libro(int id, String titulo, String autor, int año, String editor, int n_pags) {
			id = id;
			titulo = titulo;
			autor = autor;
			año = año;
			editor = editor;
			n_pags = n_pags;
		}
	
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
	
		public String getTitulo() {
			return titulo;
		}
		public void setTitulo(String titulo) {
			this.titulo = titulo;
		}
		
		public String getAutor() {
			return autor;
		}
		public void setAutor(String autor) {
			this.autor = autor;
		}
		
		public int getAño() {
			return año;
		}
		public void setAño(int año) {
			this.año = año;
		}
		
		public String getEditor() {
			return editor;
		}
		public void setEditor(String editor) {
			this.editor = editor;
		}
		
		public int getPaginas() {
			return n_pags;
		}
		public void setPaginas(int n_pags) {
			this.n_pags = n_pags;
		}
		
		public void print(){
			System.out.println("Identificador: "+id);
			System.out.println("Titulo: "+titulo);
			System.out.println("Autor: "+autor);
			System.out.println("Año: "+año);
			System.out.println("Editor: "+editor);
			System.out.println("Paginas: "+n_pags);
		}
}
