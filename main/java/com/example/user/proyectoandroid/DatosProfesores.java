package com.example.user.proyectoandroid;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import static com.example.user.proyectoandroid.MyDBAdapter.numeroEs;
import static com.example.user.proyectoandroid.MyDBAdapter.numeroPr;

public class DatosProfesores extends AppCompatActivity implements View.OnClickListener{

    private MyDBAdapter db;

    private Button bt_enviardatos_pr;
    private EditText etName_pr, etCiclo_pr, etCurso_pr,  etEdad_pr, etDespacho_pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_dbadapter_profesores);

        bt_enviardatos_pr = findViewById(R.id.bt_enviardatos_pr);

        etName_pr = findViewById(R.id.et_name_pr);
        etCiclo_pr = findViewById(R.id.et_ciclo_pr);
        etCurso_pr = findViewById(R.id.et_curso_pr);
        etEdad_pr = findViewById(R.id.et_edad_pr);
        etDespacho_pr = findViewById(R.id.et_despacho_pr);

        bt_enviardatos_pr.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.bt_enviardatos_pr){
            db = new MyDBAdapter(getApplication());

            String nombre = etName_pr.getText().toString();
            String ciclo = etCiclo_pr.getText().toString();
            int edad = Integer.parseInt(etEdad_pr.getText().toString());
            String despacho = etDespacho_pr.getText().toString();
            String curso = etCurso_pr.getText().toString();

            String mensaje = db.InsertarProfesor(nombre, ciclo, curso, edad, despacho);
            Toast.makeText(getApplicationContext(), mensaje,Toast.LENGTH_SHORT).show();
        }
    }
}

