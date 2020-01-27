package davismar98.browser.criminal.app.com.criminalbrowser;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class verVisitas extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener{
    ArrayList<Visita> listVisitas;
    ListView lvVistas;
    AdapterVisitas adapter;
    Administrador administrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_visitas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        administrador= (Administrador) getIntent().getSerializableExtra("administrador");
        lvVistas = (ListView) findViewById(R.id.lvVistas);
        HashMap PostData = new HashMap();
        PostData.put("country",administrador.pais+"");
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(verVisitas.this, PostData, this);
        taskRead.execute("http://www.criminalbrowser.com/CriminalBrowser/visitas.php");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Visita v = listVisitas.get(position);
        String s = "FECHA: "+ v.fecha +
                "\nHORA: "+ v.hora+
                "\n\nENCARCELADO: " + v.encNombre+
                "\nN° REGISTRO: " + v.encRegistro +
                "\n\nUSUARIO: "+v.usuarioN +
                "\nPAÍS: " +v.pais+
                "\nID: " +v.usuarioID+
                "\nTELÉFONO: "+ v.usuarioTel+
                "\nCORREO: "+v.usuarioEmail;
        AlertDialog.Builder alert = new AlertDialog.Builder(verVisitas.this);
        alert.setTitle("Detalle de la visita");
        alert.setIcon(R.drawable.information);
        alert.setMessage(s);
        alert.setPositiveButton("Entiendo", null);
        alert.show();
    }

    @Override
    public void processFinish(String s) {

        try {
            listVisitas = new JsonConverter<Visita>().toArrayList(s, Visita.class);
            adapter = new AdapterVisitas(this, listVisitas);
            lvVistas.setAdapter(adapter);
            lvVistas.setOnItemClickListener(this);
        }catch (Exception e){
            Toast.makeText(verVisitas.this, listVisitas.size(), Toast.LENGTH_SHORT).show();
        }

    }
}
