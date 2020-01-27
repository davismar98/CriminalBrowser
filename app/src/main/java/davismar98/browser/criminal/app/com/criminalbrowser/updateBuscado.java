package davismar98.browser.criminal.app.com.criminalbrowser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class updateBuscado extends AppCompatActivity {
    ImageView fotoB;
    Button cargar, selectFecha, addDelito, agregarBuscado, verDelitos;
    EditText url,nombresB, apellidosB, aliasB, peculiaridadB;
    TextView fechaN, estaturaB, recompensaB, delitosB;
    CheckBox cbAlias, cbPeculiaridad, cbFechaN, cbRecompensa, cvEliminarDelitos, cbFotoDefault;
    Spinner spGenero, spPais, spRaza, spOjos, spCabSytle, spCabColor, spDelito;
    SeekBar sbEstatura, sbRecompensa;

    Administrador administrador;
    Pais pais;

    ArrayAdapter<Genero> myAdapter;
    ArrayAdapter<Pais> myAdapterP;
    ArrayAdapter<Delito> myAdapterD;
    ArrayList<Genero> myGeneros;
    ArrayList<Pais> myPaises;
    ArrayList<Delito> myDelitos;
    ArrayList<Integer> idDelitos = new ArrayList<Integer>();
    ArrayList<Delito> delitosBus = new ArrayList<>();
    ArrayList<String> ojos = new ArrayList<>();
    ArrayList<String> cabellos = new ArrayList<>();
    ArrayList<String> cabelloColores = new ArrayList<>();
    ArrayList<String> razas = new ArrayList<>();
    boolean removeDelitos=false;
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR)-18;
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int estatura=100;
    int recompensa=0;
    int nDelito=1;
    String db="";

    //Variables del buscado
    Buscado buscado;
    public int idDelincuente_;
    public int idBuscado_;
    public int idPerfil_;
    public String nombre_;
    public String apellido_;
    public String edad_;
    public String fechaN_;
    public String genero_;
    public int idGenero_;
    public String pais_;
    public int idPais_;
    public String foto_;
    public int recompensa_;
    public int estatura_;
    public String raza_;
    public String cabello_;
    public String ojos_;
    public String alias_;
    public String peculiaridad_;
    public String cabelloS="";
    public String cabelloC="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_buscado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        administrador= (Administrador) getIntent().getSerializableExtra("administrador");
        buscado = (Buscado) getIntent().getSerializableExtra("buscado");

        if(buscado!=null) {
            idDelincuente_ = buscado.idDelincuente;
            idBuscado_ = buscado.idBuscado;
            idPerfil_ = buscado.idPerfil;
            nombre_ = buscado.nombre;
            apellido_ = buscado.apellido;
            pais_ = buscado.pais;
            idPais_ = buscado.idPais;
            fechaN_ = buscado.fechaN;
            edad_ = buscado.edad;
            genero_ = buscado.genero;
            idGenero_ = buscado.idGenero;
            foto_ = buscado.foto;
            recompensa_ = buscado.recompensa;
            raza_=buscado.raza;
            estatura_=buscado.estatura;
            cabello_=buscado.cabello;
            ojos_=buscado.ojos;
            alias_=buscado.alias;
            peculiaridad_=buscado.peculiaridad;
        }



        fotoB = (ImageView) findViewById(R.id.fotoB);
        if (fotoB != null) {
            new ImageDownloaderTask(fotoB).execute(foto_);
        }
        url= (EditText) findViewById(R.id.etUrl);
        url.setText(foto_);
        nombresB = (EditText) findViewById(R.id.etNombreB);
        nombresB.setText(nombre_);
        apellidosB =(EditText) findViewById(R.id.etApellidosB);
        apellidosB.setText(apellido_);
        fechaN = (TextView) findViewById(R.id.fechaNacimientoB);

        String[] fn= fechaN_.split("-");
        try {
            day = Integer.parseInt(fn[2]);
            month = Integer.parseInt(fn[1]);
            year = Integer.parseInt(fn[0]);
            fechaN.setText(year+"/"+month+"/"+day);
        }catch (Exception e){
            Toast.makeText(updateBuscado.this, "Error fecha", Toast.LENGTH_SHORT).show();
        }
        //fechaN.setText(year2+"/"+month2+"/"+day2);

        cbFotoDefault = (CheckBox) findViewById(R.id.cbFotoDefault);
        cbFotoDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    url.setText("http://www.criminalbrowser.com/delincuentes/h.jpg");
                }else {
                    url.setText(foto_);
                }
            }
        });

        cargar = (Button) findViewById(R.id.btnCargar);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  foto = url.getText().toString();
                try{
                    String[] link= foto.split("://");
                    if(link.length==2 && foto.contains(".com")){
                        new ImageDownloaderTask(fotoB).execute(url.getText().toString());
                    }else{
                        Toast.makeText(updateBuscado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(updateBuscado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spGenero = (Spinner) findViewById(R.id.spGenero);
        PostResponseAsyncTask task1= new PostResponseAsyncTask(updateBuscado.this, "Cargando géneros", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myGeneros= new JsonConverter<Genero>().toArrayList(s, Genero.class);
                Genero g = myGeneros.get(0);
                for(int i = 0; i<myGeneros.size(); i++){
                    if(myGeneros.get(i).getId()==idGenero_){
                        g = myGeneros.get(i);
                    }
                }
                myAdapter = new ArrayAdapter<Genero>(updateBuscado.this, android.R.layout.simple_spinner_item, myGeneros);
                spGenero.setAdapter(myAdapter);
                if (!genero_.equals(null)) {
                    int spinnerPosition = myAdapter.getPosition(g);
                    spGenero.setSelection(spinnerPosition);
                }

            }
        });
        task1.execute("http://www.criminalbrowser.com/CriminalBrowser/genero.php");

       spPais = (Spinner) findViewById(R.id.spPais);
        HashMap PostData = new HashMap();
        PostData.put("country",administrador.getPais());
        PostResponseAsyncTask task2= new PostResponseAsyncTask(updateBuscado.this,PostData, "Cargando países", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myPaises= new JsonConverter<Pais>().toArrayList(s, Pais.class);
                Pais p=myPaises.get(0);
                for(int i = 0; i<myPaises.size(); i++){
                    if(myPaises.get(i).getId()==idPais_){
                        p = myPaises.get(i);
                    }
                }
                myAdapterP = new ArrayAdapter<Pais>(updateBuscado.this, android.R.layout.simple_spinner_item, myPaises);
                spPais.setAdapter(myAdapterP);
                if (!pais_.equals(null)) {
                    int spinnerPosition = myAdapterP.getPosition(p);
                    spPais.setSelection(spinnerPosition);
                }
            }
        });
        task2.execute("http://www.criminalbrowser.com/CriminalBrowser/pais.php");


        selectFecha = (Button) findViewById(R.id.btnSelectFechaN);
        selectFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
        try {
            String[]cabello = cabello_.split(" ");
            if(cabello.length==2){
                cabelloS=cabello[0];
                cabelloC=cabello[1];
            }else {
                String[]cabello2 = cabello_.split("-");
                if(cabello2.length==2){
                    cabelloS=cabello2[0];
                    cabelloC=cabello2[1];
                }
            }
        }catch (Exception e){
            //Toast.makeText(updateBuscado.this, "Paila los cabellos", Toast.LENGTH_SHORT).show();
        }
        setColores();
        setRazas();
        setCabellos();
        setCabelloColores();

        estaturaB = (TextView) findViewById(R.id.estaturaB);
        sbEstatura = (SeekBar) findViewById(R.id.seekBarEstatura);
        sbEstatura.setMax(220);
        if(estatura_>220){
            estatura_=220;
        }
        sbEstatura.setProgress(estatura_);
        estaturaB.setText(estatura_ + " cm");
        sbEstatura.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                estatura_=progress;
                if(estatura_<100){
                    sbEstatura.setProgress(100);
                }
                estaturaB.setText(estatura_ + " cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        aliasB = (EditText) findViewById(R.id.etAlias);
        aliasB.setText(alias_);
        cbAlias = (CheckBox) findViewById(R.id.cbAlias);
        cbAlias.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    aliasB.setAlpha(0.25f);
                    aliasB.setFocusable(false);
                    aliasB.setText("");
                }else{
                    aliasB.setFocusableInTouchMode(true);
                    aliasB.setAlpha(1.0f);
                }
            }
        });

        cbFechaN = (CheckBox) findViewById(R.id.cbFechaN);
        cbFechaN.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    selectFecha.setClickable(false);
                    selectFecha.setAlpha(0.25f);
                    fechaN.setText("Desconocida");
                }else {
                    selectFecha.setClickable(true);
                    selectFecha.setAlpha(1.0f);
                    //fechaN.setText("Sin seleccionar");
                    fechaN.setText(year+"/"+month+"/"+day);
                }
            }
        });

        peculiaridadB = (EditText) findViewById(R.id.peculiaridadB);
        peculiaridadB.setText(peculiaridad_);
        cbPeculiaridad = (CheckBox) findViewById(R.id.cbPeculiaridad);
        cbPeculiaridad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    peculiaridadB.setAlpha(0.25f);
                    peculiaridadB.setFocusable(false);
                    peculiaridadB.setText("");
                }else{
                    peculiaridadB.setFocusableInTouchMode(true);
                    peculiaridadB.setAlpha(1.0f);
                }
            }
        });


        recompensaB = (TextView) findViewById(R.id.recompensaB);
        sbRecompensa= (SeekBar) findViewById(R.id.seekBarRecompensa);
        sbRecompensa.setMax(100);
        if(recompensa_>100000000){
            recompensa_=100;
        }
        else if(recompensa_<1000000){
            recompensa_=1;
        }
        else{
            recompensa_=recompensa_/1000000;
            }
        sbRecompensa.setProgress(recompensa_);
        recompensaB.setText("$" + recompensa_*1000000 + " (COP)");
        sbRecompensa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                recompensa_=progress;
                recompensaB.setText("$" + recompensa_*1000000 + " (COP)");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        cbRecompensa = (CheckBox) findViewById(R.id.cbRecompensa);
        cbRecompensa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    sbRecompensa.setEnabled(true);
                    sbRecompensa.setProgress(recompensa);
                }else {
                    recompensaB.setText("$0 (COP)");
                    sbRecompensa.setProgress(0);
                    sbRecompensa.setEnabled(false);
                }
            }
        });

        spDelito = (Spinner) findViewById(R.id.spDelitos);
        // PostData2.put("country",administrador.getPais());
        PostResponseAsyncTask task4= new PostResponseAsyncTask(updateBuscado.this, "Cargando delitos",new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myDelitos= new JsonConverter<Delito>().toArrayList(s, Delito.class);
                myAdapterD = new ArrayAdapter<Delito>(updateBuscado.this, android.R.layout.simple_spinner_item, myDelitos);
                spDelito.setAdapter(myAdapterD);

            }
        });
        task4.execute("http://www.criminalbrowser.com/CriminalBrowser/delito.php");

        delitosB = (TextView) findViewById(R.id.delitosB);
        addDelito = (Button) findViewById(R.id.addDelito);
        addDelito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spDelito.getSelectedItem()!=null){
                    Delito delitoSelected = (Delito) spDelito.getSelectedItem();
                    if(idDelitos.contains(delitoSelected.getId())){
                        Toast.makeText(updateBuscado.this, "Ya se agregó ese delito", Toast.LENGTH_SHORT).show();
                    }else {
                        idDelitos.add(delitoSelected.getId());
                        delitosB.setText(idDelitos.size() + " delitos agregados");
                        delitosBus.add(delitoSelected);
                        db = db + nDelito + ". " +delitoSelected.delito + "\n";
                        delitosB.setText(idDelitos.size() + " delitos agregados");
                        nDelito+=1;
                    }

                }
            }
        });

        HashMap PostData7= new HashMap();
        PostData7.put("idDelincuente",buscado.idDelincuente+"");
        PostResponseAsyncTask task6= new PostResponseAsyncTask(updateBuscado.this, PostData7, "Cargando delitos delincuente",new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if(s.contains("null")){
                    db="No se ha encontrado ningún delito";
                }else {
                    delitosBus = new JsonConverter<Delito>().toArrayList(s, Delito.class);
                    for (int i = 0; i < delitosBus.size(); i++) {
                        Delito d = delitosBus.get(i);
                        db = db + (i+1) + ". " +delitosBus.get(i) + "\n";
                        idDelitos.add(d.getId());
                    }
                    delitosB.setText(idDelitos.size() + " delitos agregados");
                    //db= delitosBus.get(0).getDelito();
                }

            }
        });
        task6.execute("http://www.criminalbrowser.com/CriminalBrowser/getDelitos.php");

        verDelitos = (Button) findViewById(R.id.btnVerDelitos);
        verDelitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(updateBuscado.this);
                alert.setTitle("Delitos");
                alert.setMessage(db);
                alert.setPositiveButton("Aceptar", null);
                alert.show();
            }
        });

        cvEliminarDelitos = (CheckBox) findViewById(R.id.cvEliminarDelitos);
        cvEliminarDelitos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    Toast.makeText(updateBuscado.this, "Muy bien!", Toast.LENGTH_SHORT).show();
                    cvEliminarDelitos.setEnabled(false);
                    idDelitos.clear();
                    db="";
                    delitosB.setText(" 0 delitos agregados");
                    removeDelitos=true;
                }
            }
        });


        agregarBuscado = (Button) findViewById(R.id.btnAddBuscado);
        agregarBuscado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> myErrors = new ArrayList<String>();


                String foto = url.getText().toString();
                try{
                    String[] link= foto.split("://");
                    if(link.length==2 && foto.contains(".") && (link[0].equals("http") || link[0].equals("https"))){
                        foto = url.getText().toString();
                    }else{
                        myErrors.add("Debe ingresar una URL válida");
                    }
                }catch (Exception e){
                    myErrors.add("Debe ingresar una URL válida");
                }




                String nombre = nombresB.getText().toString();
                String apellido = apellidosB.getText().toString();
                if(nombre.equals("") || apellido.equals("")){
                    myErrors.add("Debe ingresar nombres y apellidos válidos");
                }else{
                }

                Genero generoSel= (Genero) spGenero.getSelectedItem();
                int idGenero = generoSel.getId();

                String fechaNacimientoB=year+"/"+month+"/"+day;
                if (fechaN.getText().toString().contains("seleccionar")) {
                    myErrors.add("Debe agregar una fecha de nacimiento");
                } else {
                    int cY = c.get(Calendar.YEAR) - 18;
                    int cM = c.get(Calendar.MONTH) + 1;
                    int cD = c.get(Calendar.DAY_OF_MONTH);
                    /*myErrors.add(cY + "-" + cM + "-" + cD);
                    myErrors.add(year + "-" + month + "-" + day);*/
                    if ((year > cY) || (year == cY && month > cM) || (year == cY && month == cM && day > cD)) {
                        myErrors.add("El delincuente debe ser mayor de edad");
                    }
                }


                Pais paisSel = (Pais) spPais.getSelectedItem();
                int idPais = paisSel.getId();

                String raza = (String) spRaza.getSelectedItem();
                if (raza.equals("N/A")){
                    raza="";
                }
                String ojos = (String) spOjos.getSelectedItem();
                if(ojos.equals("N/A")){
                    ojos="";
                }
                String cabello="";
                String cabelloS= (String) spCabSytle.getSelectedItem();
                String cabelloC=  (String) spCabColor.getSelectedItem();
                if(cabelloS.equals("N/A") && cabelloC.equals("N/A")){
                    cabello="";
                }else {
                    if (cabelloS.equals("N/A")){
                        cabello=cabelloC;
                    }else {
                        cabello=cabelloS;
                    }
                }
                String alias = aliasB.getText().toString();
                if(cbAlias.isChecked()){
                    alias="";
                }else{
                    if(aliasB.getText().toString().equals("")){
                        myErrors.add("Debe agregar un alias válido");
                    }
                }

                String peculiaridad = peculiaridadB.getText().toString();
                if(cbPeculiaridad.isChecked()){
                    peculiaridad="";
                }else{
                    if(peculiaridadB.getText().toString().equals("")){
                        myErrors.add("Debe agregar una peculiaridad");
                    }
                }

                recompensa_=recompensa_*1000000;

                if(idDelitos.size()==0){
                    myErrors.add("Debe agregar al menos un delito");
                }

                String losErrores="";
                for(int i=0; i<myErrors.size(); i++){
                    losErrores = losErrores + "*" + myErrors.get(i) + "\n";
                }

                if(losErrores!=""){
                    AlertDialog.Builder alert = new AlertDialog.Builder(updateBuscado.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(losErrores);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                }else{

                    Toast.makeText(updateBuscado.this, "Muy bien!", Toast.LENGTH_SHORT).show();
                    HashMap PostData4 = new HashMap();
                    PostData4.put("txtID_D", ""+idDelincuente_);
                    PostData4.put("txtName", nombre);
                    PostData4.put("txtLastN", apellido);
                    PostData4.put("txtFechaN", fechaNacimientoB);
                    PostData4.put("txtGenre", ""+idGenero);
                    PostData4.put("txtPic", foto);
                    PostData4.put("txtCountry", ""+idPais);
                    PostData4.put("txtID_B", ""+idBuscado_);
                    PostData4.put("txtRecompensa", ""+recompensa_);
                    PostData4.put("txtID_P", ""+idPerfil_);
                    PostData4.put("txtEstatura", ""+estatura_);
                    PostData4.put("txtRaza", raza);
                    PostData4.put("txtCabello", cabello);
                    PostData4.put("txtOjos", ojos);
                    PostData4.put("txtAlias", alias);
                    PostData4.put("txtPeculiaridad", peculiaridad);

                    PostResponseAsyncTask task5= new PostResponseAsyncTask(updateBuscado.this, PostData4, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            //Toast.makeText(addEncarcelado.this, s,Toast.LENGTH_SHORT).show();
                            //finish();
                            if(s.contains("success")) {
                                for (int i = 0; i < idDelitos.size(); i++) {
                                    HashMap PostData = new HashMap();
                                    PostData.put("delito", idDelitos.get(i) + "");
                                    PostData.put("idDelincuente", idDelincuente_ + "");
                                    PostResponseAsyncTask task = new PostResponseAsyncTask(updateBuscado.this, PostData, new AsyncResponse() {
                                        @Override
                                        public void processFinish(String s) {
                                        }
                                    });
                                    task.execute("http://www.criminalbrowser.com/CriminalBrowser/insertDelitos.php");
                                }
                                Toast.makeText(updateBuscado.this, "Se ha actualizado el buscado satisfactoriamente", Toast.LENGTH_SHORT).show();
                                Intent in= new Intent(updateBuscado.this, adminActivity.class);
                                in.putExtra("administrador", (Serializable) administrador);
                                startActivity(in);
                                finish();
                            }else{
                                Toast.makeText(updateBuscado.this, "Oopps, ocurrió un error. Intenta más tarde", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    task5.execute("http://www.criminalbrowser.com/CriminalBrowser/updateBuscado.php");

                }

            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(updateBuscado.this, myDateListener, year, month, day);
        }
        return null;
    }


    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            year = arg1;
            month = arg2+1;
            day = arg3 ;
            //Toast.makeText(addEncarcelado.this, year+"/"+month+"/"+day, Toast.LENGTH_SHORT).show();
            fechaN.setText(year+"/"+month+"/"+day);
        }
    };

    public void setColores(){
        ojos.add("N/A");
        ojos.add("Negros");
        ojos.add("Cafés");
        ojos.add("Verdes");
        ojos.add("Azules");
        ojos.add("Grises");
        ojos.add("Marrón");
        ojos.add("Miel");
        ojos.add("Claros");

        ArrayAdapter adapter = new ArrayAdapter(updateBuscado.this,android.R.layout.simple_spinner_item, ojos);
        spOjos = (Spinner) findViewById(R.id.spOjos);
        spOjos.setAdapter(adapter);
        if (!ojos_.equals(null)) {
            int spinnerPosition = adapter.getPosition(ojos_);
            spOjos.setSelection(spinnerPosition);
        }
    }

    public void setRazas(){
        razas.add("Negro");
        razas.add("Caucásico");
        razas.add("Blanco");
        razas.add("Indio");
        razas.add("Amarillo");
        razas.add("Mestizo");
        razas.add("Amerindio");
        razas.add("Mongólico");
        razas.add("Asiático");
        razas.add("N/A");

        ArrayAdapter adapter = new ArrayAdapter(updateBuscado.this,android.R.layout.simple_spinner_item, razas);
        spRaza = (Spinner) findViewById(R.id.spRaza);
        spRaza.setAdapter(adapter);
        if (!raza_.equals(null)) {
            int spinnerPosition = adapter.getPosition(raza_);
            spRaza.setSelection(spinnerPosition);
        }
    }

    public void setCabellos(){
        cabellos.add("N/A");
        cabellos.add("Liso");
        cabellos.add("Ondulado");
        cabellos.add("Lacio");
        cabellos.add("Ondeado");
        cabellos.add("Rizado");
        cabellos.add("Crespo");
        cabellos.add("Calvo");
        cabellos.add("Largo");
        cabellos.add("Corto");

        ArrayAdapter adapter = new ArrayAdapter(updateBuscado.this, android.R.layout.simple_spinner_item, cabellos);
        spCabSytle = (Spinner) findViewById(R.id.spCabStyle);
        spCabSytle.setAdapter(adapter);
        if (!cabelloS.equals(null)) {
            int spinnerPosition = adapter.getPosition(cabelloS);
            spCabSytle.setSelection(spinnerPosition);
        }


    }

    public void setCabelloColores(){
        cabelloColores.add("N/A");
        cabelloColores.add("Negro");
        cabelloColores.add("Castaño");
        cabelloColores.add("Castaño Claro");
        cabelloColores.add("Claro");
        cabelloColores.add("Oscuro");
        cabelloColores.add("Rubio");
        cabelloColores.add("Marrón");
        cabelloColores.add("Rojo");
        cabelloColores.add("Naranja");
        cabelloColores.add("Gris Claro");
        cabelloColores.add("Gris Oscuro");
        cabelloColores.add("Blanco");
        cabelloColores.add("Violeta");
        cabelloColores.add("Azul Claro");
        cabelloColores.add("Azul");
        cabelloColores.add("Ceniza");
        cabelloColores.add("Beige");
        cabelloColores.add("Dorado");
        cabelloColores.add("Caoba");
        cabelloColores.add("Cobre");

        ArrayAdapter adapter = new ArrayAdapter(updateBuscado.this, android.R.layout.simple_spinner_item, cabelloColores);
        spCabColor = (Spinner) findViewById(R.id.spCabColor);
        spCabColor.setAdapter(adapter);
        if (!cabelloS.equals(null)) {
            int spinnerPosition = adapter.getPosition(cabelloC);
            spCabColor.setSelection(spinnerPosition);
        }


    }

}
