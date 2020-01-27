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

public class DetailBuscado extends Activity {

    ImageView image, bandera;
    TextView nombreCompleto;
    TextView nombre;
    TextView apellido;
    TextView edad;
    TextView fechaN;
    TextView genero;
    TextView pais;
    TextView raza;
    TextView estatura;
    TextView recompensa;
    TextView cabello;
    TextView ojos;
    TextView alias;
    TextView peculiaridad;
    TextView recomp;
    Button denuncia;
    Button delitos;
    ArrayList<Delito> delitosBus = new ArrayList<>();
    String db="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_buscado);

        final Buscado buscado = (Buscado) getIntent().getSerializableExtra("buscado");

        nombreCompleto = (TextView) findViewById(R.id.headline);
        nombre = (TextView) findViewById(R.id.delito);
        apellido =(TextView) findViewById(R.id.apellido);
        edad= (TextView)findViewById(R.id.edad);
        fechaN = (TextView) findViewById(R.id.fechaN);
        genero=(TextView) findViewById(R.id.genero);
        pais=(TextView) findViewById(R.id.pais);
        estatura=(TextView)findViewById(R.id.estatura);
        alias=(TextView) findViewById(R.id.aliasB);
        recompensa=(TextView)findViewById(R.id.recompensa);
        recomp =(TextView) findViewById(R.id.recomp);
        cabello=(TextView)findViewById(R.id.cabello);
        ojos=(TextView)findViewById(R.id.ojos);
        peculiaridad=(TextView)findViewById(R.id.peculiaridad);
        raza=(TextView)findViewById(R.id.raza);
        image=(ImageView)findViewById(R.id.image);
        bandera =(ImageView) findViewById(R.id.bandera);
        if (buscado!=null){
            nombreCompleto.setText(buscado.nombre+" "+buscado.apellido);
            nombre.setText(buscado.nombre);
            apellido.setText(buscado.apellido);
            recompensa.setText("$"+buscado.recompensa +"(COP)");
            recomp.setText("$"+buscado.recompensa +"(COP)");


            if (buscado.fechaN.equals("0000-00-00")) {
                fechaN.setText("Desconocida");
                edad.setText("");
            }else{
                fechaN.setText(buscado.fechaN);
                edad.setText("("+buscado.edad+" años)");
            }

            genero.setText(buscado.genero);
            pais.setText(buscado.pais);
            if(buscado.alias.equals("")){
                alias.setText("No tiene");
            }else{
                alias.setText(buscado.alias);
            }

            if(buscado.raza.equals("")){
                raza.setText("Desconocida");
            }else {
                raza.setText(buscado.raza);
            }
            if(buscado.estatura==100){
                estatura.setText("Desconocida");
            }else{
                estatura.setText(buscado.estatura+" cm");
            }
            if(buscado.cabello.equals("") || buscado.cabello.equals(" ")){
                cabello.setText("Desconocido");
            }else{
                cabello.setText(buscado.cabello);
            }
            if(buscado.ojos.equals("")){
                ojos.setText("Desconocido");
            }else {
                ojos.setText(buscado.ojos);
            }
            if(buscado.peculiaridad.equals("")){
                peculiaridad.setText("Ninguna");
            }else {
                peculiaridad.setText(buscado.peculiaridad);
            }
            if (image != null) {
                new ImageDownloaderTask(image).execute(buscado.foto);
            }
            String band = buscado.pais;
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
        denuncia = (Button) findViewById(R.id.btnDenunciar);
        denuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(DetailBuscado.this, denunciaActivity.class);
                in.putExtra("buscado", (Serializable) buscado);
                startActivity(in);
            }
        });


        HashMap PostData7= new HashMap();
        PostData7.put("idDelincuente",buscado.idDelincuente+"");
        PostResponseAsyncTask task6= new PostResponseAsyncTask(DetailBuscado.this, PostData7, "Cargando perfil", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if(s.contains("null")){
                    db="No se ha encontrado ningún delito";
                }else {
                    delitosBus = new JsonConverter<Delito>().toArrayList(s, Delito.class);
                    for (int i = 0; i < delitosBus.size(); i++) {
                        Delito d = delitosBus.get(i);
                        db = db + (i+1) + ". " +delitosBus.get(i) + "\n";
                    }
                }
            }
        });
        task6.execute("http://www.criminalbrowser.com/CriminalBrowser/getDelitos.php");

        delitos = (Button) findViewById(R.id.btnVerDelitos);
        delitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(DetailBuscado.this);
                alert.setTitle("Delitos");
                alert.setMessage(db);
                alert.setPositiveButton("Aceptar", null);
                alert.show();
            }
        });
    }

}
