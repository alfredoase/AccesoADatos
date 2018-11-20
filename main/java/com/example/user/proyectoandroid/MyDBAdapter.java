package com.example.user.proyectoandroid;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MyDBAdapter {
    public static int numeroEs;
    public static int numeroPr;
    private static final String DATABASENAME = "dbColegio.db";
    private static final String DATABASETABLEProfesores = "profesores";
    private static final String DATABASETABLEEstudiantes = "estudiantes";
    private static final int DATABASEVERSION = 1;


    //DATOS ESTUDIANTE
    private String es_nombre, es_ciclo, es_curso;
    private int es_edad, es_media;

    //DATOS PROFESOR
    private String pr_nombre, pr_ciclo, pr_curso;
    private int pr_edad, pr_despacho;

    private MyDbHelper dbHelper;

    private SQLiteDatabase db;


    //SENTENCIAS SQL ESTUDIANTES
    private static final String DATABASE_CREATE_ES = "CREATE TABLE "+DATABASETABLEEstudiantes+"(_id integer primary key autoincrement, es_nombre text, es_ciclo text, es_curso text, es_edad int, es_media int);";
    private static final String DATABASE_DROP_ES = "DROP TABLE IF EXISTS "+DATABASETABLEEstudiantes+";";

    //SENTENCIAS SQL ESTUDIANTES
    private static final String DATABASE_CREATE_PR = "CREATE TABLE "+DATABASETABLEProfesores+"(_id integer primary key autoincrement, pr_nombre text, pr_ciclo text, pr_curso text, pr_edad int, pr_despacho text);";
    private static final String DATABASE_DROP_PR = "DROP TABLE IF EXISTS "+DATABASETABLEProfesores+";";

    private final Context context;

    private static class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE_ES);
            db.execSQL(DATABASE_CREATE_PR);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL(DATABASE_DROP_ES);
            db.execSQL(DATABASE_DROP_PR);
            onCreate(db);
        }
    }

    public MyDBAdapter(Context c){
        context = c;
        dbHelper = new MyDbHelper(context, DATABASENAME, null, DATABASEVERSION);
        DatabaseOpen();
    }

    public void DatabaseOpen(){
        try{
            db = dbHelper.getWritableDatabase();
        }catch (SQLException e){
            db = dbHelper.getReadableDatabase();
        }
    }

    public String InsertarEstudiante(String es_nombre, String es_ciclo, String es_curso, int es_edad, int es_media){
        String mensaje = "";
        ContentValues newValuesEs = new ContentValues();

        newValuesEs.put("es_nombre", es_nombre);
        newValuesEs.put("es_ciclo", es_ciclo);
        newValuesEs.put("es_curso", es_curso);
        newValuesEs.put("es_edad", es_edad);
        newValuesEs.put("es_media", es_media);

        try {
            db.insert(DATABASETABLEEstudiantes,null, newValuesEs);
            mensaje = "Estudiante ingresado con exito.";
        }catch (SQLException e){
            mensaje = "Error en la insercion: "+ e;

        }

        db.close();
        return mensaje;
    }

    public String InsertarProfesor(String pr_nombre, String pr_ciclo, String pr_curso, int pr_edad, String pr_despacho){
        String mensaje = "";
        ContentValues newValuesPr = new ContentValues();

        newValuesPr.put("pr_nombre", pr_nombre);
        newValuesPr.put("pr_ciclo", pr_ciclo);
        newValuesPr.put("pr_curso", pr_curso);
        newValuesPr.put("pr_edad", pr_edad);
        newValuesPr.put("pr_despacho", pr_despacho);

        try {
            db.insert(DATABASETABLEProfesores,null, newValuesPr);
            mensaje = "Profesor ingresado con exito.";
        }catch (SQLException e){
            mensaje = "Error en la insercion: "+ e;

        }

        db.close();
        return mensaje;
    }

    public ArrayList<String> recuperarEstudiantes(){
        ArrayList<String> listadoEst = new ArrayList<String>();
        Cursor cursor = db.query(DATABASETABLEEstudiantes,null,null, null,null,null,null);

        if(cursor.moveToFirst()){
            do{
                listadoEst.add("Nombre: "+cursor.getString(1)+ " Ciclo: "+ cursor.getString(2) + " Curso: "+ cursor.getString(3));
            }while(cursor.moveToNext());
        }

        return listadoEst;
    }

    public ArrayList<String> recuperarEstudiantesCiclo(String ciclo){
        ArrayList<String> listadoEst = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM estudiantes WHERE es_ciclo = ?", new String[] {ciclo});

        if(cursor.moveToFirst()){
            do{
                listadoEst.add("Nombre: "+cursor.getString(1)+ " Ciclo: "+ cursor.getString(2) + " Curso: "+ cursor.getString(3));
            }while(cursor.moveToNext());
        }

        return listadoEst;
    }

    public ArrayList<String> recuperarEstudiantesCurso(String curso){
        ArrayList<String> listadoEst = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM estudiantes WHERE es_curso = ?", new String[] {curso});

        if(cursor.moveToFirst()){
            do{
                listadoEst.add("Nombre: "+cursor.getString(1)+ " Ciclo: "+ cursor.getString(2) + " Curso: "+ cursor.getString(3));
            }while(cursor.moveToNext());
        }

        return listadoEst;
    }

 public ArrayList<String> recuperarProfesores(){
        ArrayList<String> listadoEst = new ArrayList<String>();
        Cursor cursor = db.query(DATABASETABLEProfesores,null,null, null,null,null,es_ciclo);

        if(cursor.moveToFirst()){
            do{
                listadoEst.add("Nombre: "+cursor.getString(1));
            }while(cursor.moveToNext());
        }

        return listadoEst;
    }

    public ArrayList<String> recuperarTodos(){
        ArrayList<String> listadoTodos = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM estudiantes UNION All SELECT * FROM profesores", null);

        if(cursor.moveToFirst()){
            do{
                listadoTodos.add("Nombre: "+cursor.getString(1));
            }while(cursor.moveToNext());
        }

        return listadoTodos;
    }

    public void EliminarDatos(){
        db.execSQL("DELETE FROM profesores");
    }

    public ArrayList<String> recuperarEstudiantesExamen(String nombre, int edad){
        ArrayList<String> listadoEst = new ArrayList<String>();
        Cursor cursor = db.rawQuery("SELECT * FROM estudiantes WHERE es_nombre = ? AND es_edad = ?", new String[] {nombre, Integer.toString(edad)});

        if(cursor.moveToFirst()){
            do{
                listadoEst.add("Nombre: "+cursor.getString(1)+ " Ciclo: "+ cursor.getString(2) + " Curso: "+ cursor.getString(3)+ " Edad: "+ cursor.getString(4));
            }while(cursor.moveToNext());
        }

        db = dbHelper.getWritableDatabase();
        return listadoEst;
    }
}
