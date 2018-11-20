package com.example.user.proyectoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import java.util.ArrayList;

public class ActivityExamen extends AppCompatActivity implements View.OnClickListener {
    private EditText etNombreEstudiante;
    private NumberPicker npEdadEstudiante;
    private Button btBusquedaExamen;
    private MyDBAdapter db;
    private ListView listItem;

    private ArrayList<String> estudiantes;
    private ArrayAdapter adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examen);

        etNombreEstudiante = findViewById(R.id.et_nombre_est);
        npEdadEstudiante = findViewById(R.id.np_edad_est);
        btBusquedaExamen = findViewById(R.id.bt_busquedaexamen);
        listItem = findViewById(R.id.list_view);

        npEdadEstudiante.setMinValue(1);
        npEdadEstudiante.setMaxValue(100);

        btBusquedaExamen.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bt_busquedaexamen:
                String nombreEstudiante = etNombreEstudiante.getText().toString();
                int edadEstudiante = npEdadEstudiante.getValue();

                BusquedaExamen(nombreEstudiante, edadEstudiante);
                break;
            default:

                break;
        }
    }

    private void BusquedaExamen(String nombre, int edad){
        db = new MyDBAdapter(getApplicationContext());
        estudiantes = db.recuperarEstudiantesExamen(nombre, edad);

        adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, estudiantes);

        listItem.setAdapter(adaptador);
    }

}