package davismar98.browser.criminal.app.com.criminalbrowser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by DavidM on 9/10/2016.
 */

public class DetailEncarcelado extends Activity{
    ImageView image, bandera;
    TextView nombreCompleto;
    TextView nombre;
    TextView apellido;
    TextView nReg;
    TextView pais;
    TextView genero;
    TextView edad;
    TextView fechaN;
    TextView carcel;
    TextView reg;
    TextView fechaIng;
    TextView condena;
    TextView visitas;
    ArrayList<Delito> delitosEnc = new ArrayList<>();
    String de="";
    Encarcelado encarcelado;


    Button delitos, visitar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_encarcelado);

        encarcelado= (Encarcelado) getIntent().getSerializableExtra("encarcelado");

        nombreCompleto = (TextView) findViewById(R.id.headline);
        nombre = (TextView) findViewById(R.id.delito) ;
        apellido = (TextView) findViewById(R.id.apellido);
        nReg = (TextView) findViewById(R.id.registro);
        pais = (TextView) findViewById(R.id.pais);
        carcel = (TextView) findViewById(R.id.carcel);
        reg = (TextView) findViewById(R.id.nReg);
        edad = (TextView) findViewById(R.id.edad);
        fechaN=(TextView) findViewById(R.id.fechaN);
        genero = (TextView) findViewById(R.id.genero);
        fechaIng = (TextView) findViewById(R.id.fechaIngreso);
        condena = (TextView) findViewById(R.id.condena);
        visitas = (TextView) findViewById(R.id.visitas);
        image = (ImageView) findViewById(R.id.image);
        bandera=(ImageView) findViewById(R.id.bandera);

        if(encarcelado!=null){
            nombreCompleto.setText(encarcelado.nombre + " " + encarcelado.apellido);
            nombre.setText(encarcelado.nombre);
            apellido.setText(encarcelado.apellido);
            nReg.setText("N° "+encarcelado.nRegistro);
            pais.setText(encarcelado.pais);
            carcel.setText(encarcelado.carcel);
            reg.setText(encarcelado.nRegistro);
            fechaN.setText(encarcelado.fechaN);
            edad.setText("("+encarcelado.edad + " años)");
            genero.setText(encarcelado.genero);
            fechaIng.setText(encarcelado.fechaIngreso);
            condena.setText(encarcelado.condena + " años");
            visitas.setText(encarcelado.nVisitas+" al año");
            if (image != null) {
                new ImageDownloaderTask(image).execute(encarcelado.foto);
            }
            String band = encarcelado.pais;
            String b = band.toLowerCase();
            if(b.equals("méxico")){
                b="mexico";
            }else if(b.equals("perú")){
                b="peru";
            }

            int id = this.getResources().getIdentifier(b, "drawable",this.getPackageName());
            if (id!=0) {
                bandera.setImageResource(id);
            }
        }

        visitar=(Button)findViewById(R.id.btnVisitar);
        visitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DetailEncarcelado.this, visitaActivity.class);
                in.putExtra("encarcelado", (Serializable) encarcelado);
                startActivity(in);
            }
        });


        HashMap PostData7= new HashMap();
        PostData7.put("idDelincuente",encarcelado.idDelincuente+"");
        PostResponseAsyncTask task6= new PostResponseAsyncTask(DetailEncarcelado.this, PostData7, "Cargando perfil", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if(s.contains("null")){
                    de="No se ha encontrado ningún delito";
                }else {
                    delitosEnc = new JsonConverter<Delito>().toArrayList(s, Delito.class);
                    for (int i = 0; i < delitosEnc.size(); i++) {
                        Delito d = delitosEnc.get(i);
                        de = de + (i+1) + ". " +delitosEnc.get(i) + "\n";
                    }
                }
            }
        });
        task6.execute("http://www.criminalbrowser.com/CriminalBrowser/getDelitos.php");

        delitos = (Button) findViewById(R.id.btnVerDelitos);
        delitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailEncarcelado.this);
                alert.setTitle("Delitos");
                alert.setMessage(de);
                alert.setPositiveButton("Aceptar", null);
                alert.show();
            }
        });
    }



}
