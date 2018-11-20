package com.example.user.proyectoandroid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    protected Button btSettings, btEstudiantes, btProfesores;
    protected TextView tvName, tvUserName, tvDate, tvGenre;
    private int REQUEST_ESTUDIANTES = 2;
    private int REQUEST_PROFESORES = 3;

    private ListView listItem;

    private ArrayList<String> estudiantes;

    private ArrayList<String> profesores;

    private ArrayAdapter adaptador;

    MyDBAdapter db;

    RadioGroup rg_searchBy;

    RadioButton rbEstudiantes, rbCicloEst, rbCursoEst, rbProf, rbTodos;

    TextView textoLista;

    EditText etCursoCiclo;

    String search = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BOTONES
        btEstudiantes = findViewById(R.id.btEstudiantes);
        btProfesores = findViewById(R.id.btProfesores);



        //TEXT VIEWS PARA VER INFORMACION
        tvName = findViewById(R.id.tvName);
        tvUserName = findViewById(R.id.tvUserName);
        tvDate = findViewById(R.id.tvDate);
        tvGenre = findViewById(R.id.tvGenre);
        rg_searchBy = findViewById(R.id.rg_searchby);
        rbEstudiantes = findViewById(R.id.rb_estudiantes);
        rbCicloEst = findViewById(R.id.rb_ciclosest);
        rbCursoEst = findViewById(R.id.rb_cursoest);
        rbProf = findViewById(R.id.rb_profesores);
        rbTodos = findViewById(R.id.rb_todos);
        textoLista = findViewById(R.id.textoLista);
        etCursoCiclo = findViewById(R.id.et_cursociclo);

        listItem = findViewById(R.id.list_view);

        //LISTENERS PARA LOS BOTONES
        btEstudiantes.setOnClickListener(this);
        btProfesores.setOnClickListener(this);
        rbEstudiantes.setOnClickListener(this);
        rbCicloEst.setOnClickListener(this);
        rbCursoEst.setOnClickListener(this);
        rbProf.setOnClickListener(this);
        rbTodos.setOnClickListener(this);



        SetInfoPreferences();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Depends which option calls open differents methods
        switch (item.getItemId()){
            case R.id.men_preferences:
                OpenSettings();
                return true;
            case R.id.men_activityexamen:
                Intent intentActivityExamen = new Intent(this, ActivityExamen.class);
                startActivity(intentActivityExamen);
            default:
                return false;
        }
    }

    public void OpenSettings(){
        Intent intentSettingsActivity = new Intent(this, PreferencesActivity.class);

        startActivity(intentSettingsActivity);
        onPause();
    }

    @Override
    protected void onStart() {
        SetInfoPreferences();

        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btEstudiantes:
                Intent activityEstudiantes = new Intent(this, DatosEstudiantes.class);
                startActivityForResult(activityEstudiantes, REQUEST_ESTUDIANTES);
                break;
            case R.id.btProfesores:
                Intent activityProfesores = new Intent(this, DatosProfesores.class);
                startActivityForResult(activityProfesores, REQUEST_PROFESORES);
                break;
            case R.id.rb_estudiantes:
                textoLista.setText("Estudiantes: ");
                ChangeSearch(search);
                break;
            case R.id.rb_ciclosest:
                textoLista.setText("Estudiantes por ciclo: ");

                search = etCursoCiclo.getText().toString();

                ChangeSearch(search);
                break;
            case R.id.rb_cursoest:
                textoLista.setText("Estudiantes por curso: ");

                search = etCursoCiclo.getText().toString();

                ChangeSearch(search);

                break;
            case R.id.rb_profesores:
                textoLista.setText("Profesores: ");
                ChangeSearch(search);
                break;
            case R.id.rb_todos:
                textoLista.setText("Estudiantes y profesores: ");
                ChangeSearch(search);
                break;
            default:
                break;
        }
    }

    protected void SetInfoPreferences() {
        SharedPreferences datosPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String nombrePreferences = datosPreferences.getString("name","Default Name: Change in settingss");
        String usuarioPreferences = datosPreferences.getString("user_name","Default User: Change in settings.");
        String fechaPreferences = datosPreferences.getString("date","Default Birthday: Change in settings.");
        String generoPreferences = datosPreferences.getString("genre","Default Genre: Change in settings.");

        tvName.setText(nombrePreferences);
        tvUserName.setText(usuarioPreferences);
        tvDate.setText(fechaPreferences);
        tvGenre.setText(generoPreferences);
    }

    public void ShowInfoEstudiantes(){

    }

    public void ShowInfoProfesores(){

    }

    private void ChangeSearch(String search){
        int cat_prod_int = rg_searchBy.getCheckedRadioButtonId();

        if (cat_prod_int == rbEstudiantes.getId()){
            db = new MyDBAdapter(getApplicationContext());
            estudiantes = db.recuperarEstudiantes();
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, estudiantes);

            listItem.setAdapter(adaptador);

        }
        if(cat_prod_int == rbCicloEst.getId()){
            db = new MyDBAdapter(getApplicationContext());
            String ciclo = search;
            estudiantes = db.recuperarEstudiantesCiclo(ciclo);
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, estudiantes);

            listItem.setAdapter(adaptador);

        }
        if(cat_prod_int == rbCursoEst.getId()){
            db = new MyDBAdapter(getApplicationContext());
            String curso = search;
            estudiantes = db.recuperarEstudiantesCurso(curso);
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, estudiantes);

            listItem.setAdapter(adaptador);

        }
        if(cat_prod_int == rbProf.getId()){
            db = new MyDBAdapter(getApplicationContext());
            profesores = db.recuperarProfesores();
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesores);

            listItem.setAdapter(adaptador);

        }
        if(cat_prod_int == rbTodos.getId()){
            db = new MyDBAdapter(getApplicationContext());
            profesores = db.recuperarTodos();
            adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, profesores);

            listItem.setAdapter(adaptador);
        }
    }
}
