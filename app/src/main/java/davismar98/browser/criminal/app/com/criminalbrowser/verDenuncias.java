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

public class verDenuncias extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener{
    ArrayList<Denuncia> listDenuncias;
    ListView lvDenuncias;
    AdapterDenuncias adapter;
    Administrador administrador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_denuncias);
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
        lvDenuncias= (ListView) findViewById(R.id.lvDenuncias);
        HashMap PostData = new HashMap();
        PostData.put("country",administrador.pais+"");
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(verDenuncias.this, PostData, this);
        taskRead.execute("http://www.criminalbrowser.com/CriminalBrowser/denuncias.php");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Denuncia d = listDenuncias.get(position);
        String s = "FECHA: "+ d.fecha +
                "\n\nBUSCADO: " + d.BusNombre+
                "\n\nUSUARIO: "+d.usuarioN +
                "\nPAÍS: " +d.pais+
                "\nID: " +d.usuarioID+
                "\nTELÉFONO: "+ d.usuarioTel+
                "\nCORREO: "+d.usuarioEmail+
                "\n\nCOMENTARIO: "+ d.comentario;
        AlertDialog.Builder alert = new AlertDialog.Builder(verDenuncias.this);
        alert.setTitle("Detalle de la denuncia");
        alert.setIcon(R.drawable.information);
        alert.setMessage(s);
        alert.setPositiveButton("Entiendo", null);
        alert.show();
    }

    @Override
    public void processFinish(String s) {

        try {
            listDenuncias = new JsonConverter<Denuncia>().toArrayList(s, Denuncia.class);
            adapter = new AdapterDenuncias(this, listDenuncias);
            lvDenuncias.setAdapter(adapter);
            lvDenuncias.setOnItemClickListener(this);
        }catch (Exception e){
            Toast.makeText(verDenuncias.this, listDenuncias.size(), Toast.LENGTH_SHORT).show();
        }

    }

}
