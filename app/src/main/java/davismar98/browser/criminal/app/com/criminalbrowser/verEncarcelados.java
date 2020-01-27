package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

/**
 * Created by DavidM on 9/10/2016.
 */

public class verEncarcelados extends AppCompatActivity implements AsyncResponse, AdapterView.OnItemClickListener{

    ArrayList<Encarcelado> listEncarcelado;
    ListView lvEncarcelado;
    CustomListAdapter adapter;
    FloatingActionButton fab;
    String pais;
    boolean admin;
    Administrador administrador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_encarcelados);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(verEncarcelados.this, addEncarcelado.class);
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
            admin= (boolean) extras.get("admin");
        }
       // Toast.makeText(verEncarcelados.this, ""+admin, Toast.LENGTH_SHORT).show();
        if(!admin){
            fab.setVisibility(View.GONE);
        }
        HashMap PostData = new HashMap();
        PostData.put("country",pais);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(verEncarcelados.this, PostData, this);
        taskRead.execute("http://www.criminalbrowser.com/CriminalBrowser/encarceladoPais.php");
        lvEncarcelado = (ListView) findViewById(R.id.custom_list);
        if(admin==true){
            registerForContextMenu(lvEncarcelado);
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
        final Encarcelado encarceladoSelected = (Encarcelado) adapter.getItem(info.position);

        if(item.getItemId() == R.id.menuUpdate){
            //Toast.makeText(verEncarcelados.this, encarceladoSelected.getNombre(), Toast.LENGTH_SHORT).show();
            Intent in = new Intent(verEncarcelados.this, updateEncarcelado.class);
            in.putExtra("encarcelado", (Serializable) encarceladoSelected);
            in.putExtra("administrador", (Serializable) administrador);
            startActivity(in);
            finish();


        }else if(item.getItemId() == R.id.menuRemove){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Confirmación");
            alert.setMessage("¿Quiere eliminar el encarcelado " + encarceladoSelected.nombre + " " + encarceladoSelected.apellido +"?");
            alert.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    HashMap PostData = new HashMap();
                    PostData.put("txtID_D", ""+ encarceladoSelected.idDelincuente);
                    PostData.put("txtID_E", ""+ encarceladoSelected.idEncarcelado);
                    PostResponseAsyncTask taskRemove = new PostResponseAsyncTask(verEncarcelados.this, PostData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.contains("success")){
                                Toast.makeText(verEncarcelados.this, "Se ha eliminado el encarcelado", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(verEncarcelados.this, "Ocurrió un error inesperado :v. Intente más tarde", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    taskRemove.execute("http://www.criminalbrowser.com/CriminalBrowser/removeEncarcelado.php");

                    listEncarcelado.remove(encarceladoSelected);
                    adapter.notifyDataSetChanged();
                }
            });
            alert.setNegativeButton("Cancel", null);
            alert.show();
        }

        return super.onContextItemSelected(item);
    }

    @Override
    public void processFinish(String s) {

        listEncarcelado= new JsonConverter<Encarcelado>().toArrayList(s, Encarcelado.class);
        adapter = new CustomListAdapter(this, listEncarcelado);
        lvEncarcelado.setAdapter(adapter);
        lvEncarcelado.setOnItemClickListener(this);


}

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Encarcelado encarceladoSelected = (Encarcelado) lvEncarcelado.getItemAtPosition(position);
        //Toast.makeText(verEncarcelados.this, encarceladoSelected.getIdEncarcelado() + " : " +encarceladoSelected.getIdCarcel(), Toast.LENGTH_LONG).show();
        Intent in= new Intent(verEncarcelados.this, DetailEncarcelado.class);
        in.putExtra("encarcelado", (Serializable) encarceladoSelected);
        startActivity(in);
    }

}