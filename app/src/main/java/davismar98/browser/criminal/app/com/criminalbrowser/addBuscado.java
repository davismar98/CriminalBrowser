package davismar98.browser.criminal.app.com.criminalbrowser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class addBuscado extends AppCompatActivity {

    ImageView fotoB;
    Button cargar, selectFecha, addDelito, agregarBuscado;
    EditText url,nombresB, apellidosB, aliasB, peculiaridadB;
    TextView fechaN, estaturaB, recompensaB, delitosB;
    CheckBox cbAlias, cbPeculiaridad, cbFechaN, cbRecompensa, cbFotoDefault;
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
    ArrayList<String> ojos = new ArrayList<>();
    ArrayList<String> cabellos = new ArrayList<>();
    ArrayList<String> cabelloColores = new ArrayList<>();
    ArrayList<String> razas = new ArrayList<>();

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR)-18;
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int estatura=100;
    int recompensa=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_buscado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        administrador= (Administrador) getIntent().getSerializableExtra("admin");

        fotoB = (ImageView) findViewById(R.id.fotoB);
        url= (EditText) findViewById(R.id.etUrl);
        nombresB = (EditText) findViewById(R.id.etNombreB);
        apellidosB =(EditText) findViewById(R.id.etApellidosB);
        fechaN = (TextView) findViewById(R.id.fechaNacimientoB);
        //fechaN.setText(year2+"/"+month2+"/"+day2);

        cbFotoDefault = (CheckBox) findViewById(R.id.cbFotoDefault);
        cbFotoDefault.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    url.setText("http://www.criminalbrowser.com/delincuentes/h.jpg");
                }
            }
        });


        cargar = (Button) findViewById(R.id.btnCargar);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String  foto = url.getText().toString();
                    String[] link= foto.split("://");
                    if(link.length==2 && foto.contains(".com")){
                        new ImageDownloaderTask(fotoB).execute(url.getText().toString());
                    }else{
                        Toast.makeText(addBuscado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(addBuscado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spGenero = (Spinner) findViewById(R.id.spGenero);

        PostResponseAsyncTask task1= new PostResponseAsyncTask(addBuscado.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myGeneros= new JsonConverter<Genero>().toArrayList(s, Genero.class);
                myAdapter = new ArrayAdapter<Genero>(addBuscado.this, android.R.layout.simple_spinner_item, myGeneros);
                spGenero.setAdapter(myAdapter);

            }
        });
        task1.execute("http://www.criminalbrowser.com/CriminalBrowser/genero.php");

        spPais = (Spinner) findViewById(R.id.spPais);
        HashMap PostData = new HashMap();
        PostData.put("country",administrador.getPais());
        PostResponseAsyncTask task2= new PostResponseAsyncTask(addBuscado.this,PostData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myPaises= new JsonConverter<Pais>().toArrayList(s, Pais.class);
                myAdapterP = new ArrayAdapter<Pais>(addBuscado.this, android.R.layout.simple_spinner_item, myPaises);
                spPais.setAdapter(myAdapterP);
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

        setColores();
        setRazas();
        setCabellos();
        setCabelloColores();

        estaturaB = (TextView) findViewById(R.id.estaturaB);
        sbEstatura = (SeekBar) findViewById(R.id.seekBarEstatura);
        sbEstatura.setMax(220);
        sbEstatura.setProgress(estatura);
        sbEstatura.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                estatura=progress;
                if(estatura<100){
                    sbEstatura.setProgress(100);
                }
                estaturaB.setText(estatura + " cm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        aliasB = (EditText) findViewById(R.id.etAlias);
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
                    fechaN.setText("Sin seleccionar");
                }
            }
        });

        peculiaridadB = (EditText) findViewById(R.id.peculiaridadB);
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
        sbRecompensa.setProgress(recompensa);
        sbRecompensa.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                recompensa=progress;
                recompensaB.setText("$" + recompensa*1000000 + " (COP)");
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
        PostResponseAsyncTask task4= new PostResponseAsyncTask(addBuscado.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myDelitos= new JsonConverter<Delito>().toArrayList(s, Delito.class);
                myAdapterD = new ArrayAdapter<Delito>(addBuscado.this, android.R.layout.simple_spinner_item, myDelitos);
                spDelito.setAdapter(myAdapterD);

            }
        });
        task4.execute("http://www.criminalbrowser.com/CriminalBrowser/delito.php");

        delitosB = (TextView) findViewById(R.id.delitosE);
        addDelito = (Button) findViewById(R.id.addDelito);
        addDelito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spDelito.getSelectedItem()!=null){
                    Delito delitoSelected = (Delito) spDelito.getSelectedItem();
                    if(idDelitos.contains(delitoSelected.getId())){
                        Toast.makeText(addBuscado.this, "Ya se agregó ese delito", Toast.LENGTH_SHORT).show();
                    }else {
                        idDelitos.add(delitoSelected.getId());
                        delitosB.setText(idDelitos.size() + " delitos agregados");
                    }

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
                    if(link.length==2 && foto.contains(".com") && (link[0].equals("http") || link[0].equals("https"))){
                        foto = url.getText().toString();
                    }else{
                        myErrors.add("Debe ingresar una URL válida");
                    }
                }catch (Exception e){
                    myErrors.add("Debe ingresar una URL válida");
                }


                String nombre = nombresB.getText().toString();
                String apellido = apellidosB.getText().toString();
                String n1,n2,a1,a2 = "";
                if (nombre.equals("")) {
                    myErrors.add("Debe ingresar los nombres ");
                }else {
                    String[] nomb = nombre.split(" ");
                    if(nomb.length>1){
                        n1= nomb[0];
                        n2 = nomb[1];
                        if (!Pattern.matches("[a-zA-Z]+", n1) || !Pattern.matches("[a-zA-Z]+", n2)) {
                            myErrors.add("El nombre no puede contener números o carácteres extraños");
                        }
                    }else {
                        n1 = nomb[0];
                        if (!Pattern.matches("[a-zA-Z]+", n1)) {
                            myErrors.add("El nombre no puede contener números o carácteres extraños");
                        }
                    }
                }

                if(apellido.equals("")){
                    myErrors.add("Debe ingresar los apellidos ");
                }else{
                    String[] ape = apellido.split(" ");
                    if (ape.length > 1) {
                        a1 = ape[0];
                        a2 = ape[1];
                        if (!Pattern.matches("[a-zA-Z]+", a1) || !Pattern.matches("[a-zA-Z]+", a2)) {
                            myErrors.add("El apellido no puede contener números o carácteres extraños");
                        }
                    } else {
                        a1 = ape[0];
                        if (!Pattern.matches("[a-zA-Z]+", a1)) {
                            myErrors.add("El apellido no puede contener números o carácteres extraños");
                        }
                    }
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
                if(cabelloS.equals("N/A")){
                    cabelloS="";
                }if(cabelloC.equals("N/A")){
                    cabelloC="";
                }
                cabello=cabelloS+ " "+ cabelloC;
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

                recompensa=recompensa*1000000;

                if(idDelitos.size()==0){
                    myErrors.add("Debe agregar al menos un delito");
                }

                Random rand = new Random();
                final int  idDelincuente = rand.nextInt(99999) + 1000;
                Random rand2 = new Random();
                int idBuscado = rand2.nextInt(99999) + 1000;
                Random rand3 = new Random();
                int idPerfil = rand3.nextInt(99999) +1000;

                String losErrores="";
                for(int i=0; i<myErrors.size(); i++){
                    losErrores = losErrores + "*" + myErrors.get(i) + "\n";
                }

                if(losErrores!=""){
                    AlertDialog.Builder alert = new AlertDialog.Builder(addBuscado.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(losErrores);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                }else{

                    Toast.makeText(addBuscado.this, "Muy bien!", Toast.LENGTH_SHORT).show();
                    HashMap PostData4 = new HashMap();
                    PostData4.put("txtID_D", ""+idDelincuente);
                    PostData4.put("txtName", nombre);
                    PostData4.put("txtLastN", apellido);
                    PostData4.put("txtFechaN", fechaNacimientoB);
                    PostData4.put("txtGenre", ""+idGenero);
                    PostData4.put("txtPic", foto);
                    PostData4.put("txtCountry", ""+idPais);
                    PostData4.put("txtID_B", ""+idBuscado);
                    PostData4.put("txtRecompensa", ""+recompensa);
                    PostData4.put("txtID_P", ""+idPerfil);
                    PostData4.put("txtEstatura", ""+estatura);
                    PostData4.put("txtRaza", raza);
                    PostData4.put("txtCabello", cabello);
                    PostData4.put("txtOjos", ojos);
                    PostData4.put("txtAlias", alias);
                    PostData4.put("txtPeculiaridad", peculiaridad);

                    PostResponseAsyncTask task5= new PostResponseAsyncTask(addBuscado.this, PostData4, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            //Toast.makeText(addEncarcelado.this, s,Toast.LENGTH_SHORT).show();
                            //finish();
                            if(s.contains("success")) {
                                for (int i = 0; i < idDelitos.size(); i++) {
                                    HashMap PostData = new HashMap();
                                    PostData.put("delito", idDelitos.get(i) + "");
                                    PostData.put("idDelincuente", idDelincuente + "");
                                    PostResponseAsyncTask task = new PostResponseAsyncTask(addBuscado.this, PostData, new AsyncResponse() {
                                        @Override
                                        public void processFinish(String s) {
                                        }
                                    });
                                    task.execute("http://www.criminalbrowser.com/CriminalBrowser/insertDelitos.php");
                                }
                                Toast.makeText(addBuscado.this, "Se ha insertado el buscado satisfactoriamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(addBuscado.this, "Oopps, ocurrió un error. Intenta más tarde", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    task5.execute("http://www.criminalbrowser.com/CriminalBrowser/insertBuscado.php");

                }

            }
        });


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(addBuscado.this, myDateListener, year, month, day);
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

        ArrayAdapter adapter = new ArrayAdapter(addBuscado.this,android.R.layout.simple_spinner_item, ojos);
        spOjos = (Spinner) findViewById(R.id.spOjos);
        spOjos.setAdapter(adapter);
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

        ArrayAdapter adapter = new ArrayAdapter(addBuscado.this,android.R.layout.simple_spinner_item, razas);
        spRaza = (Spinner) findViewById(R.id.spRaza);
        spRaza.setAdapter(adapter);
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

        ArrayAdapter adapter = new ArrayAdapter(addBuscado.this, android.R.layout.simple_spinner_item, cabellos);
        spCabSytle = (Spinner) findViewById(R.id.spCabStyle);
        spCabSytle.setAdapter(adapter);
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

        ArrayAdapter adapter = new ArrayAdapter(addBuscado.this, android.R.layout.simple_spinner_item, cabelloColores);
        spCabColor = (Spinner) findViewById(R.id.spCabColor);
        spCabColor.setAdapter(adapter);


    }
}
