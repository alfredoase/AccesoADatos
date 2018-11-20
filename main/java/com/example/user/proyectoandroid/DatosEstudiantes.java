package com.example.user.proyectoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.user.proyectoandroid.MyDBAdapter.numeroEs;

public class DatosEstudiantes extends AppCompatActivity implements View.OnClickListener{

    MyDBAdapter db;

    private Button bt_enviardatos;
    private EditText etName, etCiclo, etCurso,  etEdad, etMedia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dbadapter_estudiantes);

        bt_enviardatos = findViewById(R.id.bt_enviardatos);
        etName = findViewById(R.id.et_name);
        etCiclo = findViewById(R.id.et_ciclo);
        etCurso = findViewById(R.id.et_curso);
        etEdad = findViewById(R.id.et_edad);
        etMedia = findViewById(R.id.et_media);

        bt_enviardatos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId() == bt_enviardatos.getId()){
            db = new MyDBAdapter(getApplication());

            String es_nombre = etName.getText().toString();
            String es_ciclo = etCiclo.getText().toString();
            int es_edad = Integer.parseInt(etEdad.getText().toString());
            int es_media = Integer.parseInt(etMedia.getText().toString());
            String es_curso = etCurso.getText().toString();

            String mensaje = db.InsertarEstudiante(es_nombre, es_ciclo, es_curso, es_edad, es_media);
            Toast.makeText(getApplicationContext(), mensaje,Toast.LENGTH_SHORT).show();
        }

    }

}
