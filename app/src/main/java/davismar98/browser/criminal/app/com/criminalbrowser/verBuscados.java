package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class verBuscados extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener {
    ArrayList<Buscado> listBuscado;
    ListView lvBuscado;
    FloatingActionButton fab;
    String pais;
    boolean admin;
    CustomListAdapterB adapterB;
    Administrador administrador;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_todos);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(verBuscados.this, addBuscado.class);
                in.putExtra("admin", (Serializable) administrador);
                startActivity(in);
                finish();
            }
        });

        administrador= (Administrador) getIntent().getSerializableExtra("administrador");

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {//ver si contiene datos
            pais= (String) extras.get("country");
            admin=(boolean) extras.get("admin");
        }
        if(!admin){
            fab.setVisibility(View.GONE);
        }

        HashMap PostData = new HashMap();
        PostData.put("country",pais);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(verBuscados.this, PostData, this);
        taskRead.execute("http://www.criminalbrowser.com/CriminalBrowser/buscadoPais.php");
        lvBuscado = (ListView) findViewById(R.id.custom_list);
        if(admin) {
            registerForContextMenu(lvBuscado);
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_context_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        final Buscado buscadoSelected = (Buscado) adapterB.getItem(info.position);

        if(item.getItemId() == R.id.menuUpdate){
            Toast.makeText(verBuscados.this, buscadoSelected.getNombre(), Toast.LENGTH_SHORT).show();
            Intent in = new Intent(verBuscados.this, updateBuscado.class);
            in.putExtra("buscado", (Serializable) buscadoSelected);
            in.putExtra("administrador", (Serializable) administrador);
            startActivity(in);
            finish();

        }else if(item.getItemId() == R.id.menuRemove){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmación");
            alert.setMessage("¿Quiere eliminar el buscado " + buscadoSelected.nombre + " " + buscadoSelected.apellido +"?");
            alert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HashMap PostData = new HashMap();
                    PostData.put("txtID_D", ""+ buscadoSelected.idDelincuente);
                    PostData.put("txtID_B", ""+ buscadoSelected.idBuscado);
                    PostData.put("txtID_P", "" + buscadoSelected.idPerfil);
                    PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(verBuscados.this, PostData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.contains("success")){
                                Toast.makeText(verBuscados.this, "Se ha eliminado el buscado", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(verBuscados.this, "Ocurrió un error inesperado :v. Intente más tarde", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    taskRemove.execute("http://www.criminalbrowser.com/CriminalBrowser/removeBuscado.php");

                    listBuscado.remove(buscadoSelected);
                    adapterB.notifyDataSetChanged();
                }
            });
            alert.setNegativeButton("Cancel", null);
            alert.show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void processFinish(String s) {
        listBuscado= new JsonConverter<Buscado>().toArrayList(s, Buscado.class);
        //Toast.makeText(verBuscados.this,listBuscado.size(), Toast.LENGTH_SHORT).show();
        adapterB = new CustomListAdapterB(this, listBuscado);
        lvBuscado.setAdapter(adapterB);
        lvBuscado.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Buscado buscadoSelected = (Buscado) lvBuscado.getItemAtPosition(position);
        //Toast.makeText(verBuscados.this, buscadoSelected.getFoto(), Toast.LENGTH_LONG).show();
        Intent in= new Intent(verBuscados.this, DetailBuscado.class);
        in.putExtra("buscado", (Serializable) buscadoSelected);
        startActivity(in);
    }
}
