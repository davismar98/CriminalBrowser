package davismar98.browser.criminal.app.com.criminalbrowser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
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

public class updateEncarcelado extends AppCompatActivity {
    ImageView fotoE;
    Button cargar, selectFecha, selectFechaIng, addDelito, agregarEncarcelado, verDelitos;
    CheckBox cvEliminarDelitos, cbFotoDefault;
    EditText url, nRegE, nombresE, apellidosE ;
    TextView fechaN, condenaE, visitasE, fechaIng, delitosE;
    Spinner spGenero, spPais, spCarcel, spDelito;
    SeekBar sbCondena, sbVisitas;

    Administrador administrador;
    Pais pais;
    ArrayAdapter<Genero> myAdapter;
    ArrayAdapter<Pais> myAdapterP;
    ArrayAdapter<Carcel> myAdapterC;
    ArrayAdapter<Delito> myAdapterD;
    ArrayList<Genero> myGeneros;
    ArrayList<Pais> myPaises;
    ArrayList<Carcel> myCarceles;
    ArrayList<Delito> myDelitos;
    ArrayList<Integer> idDelitos = new ArrayList<Integer>();
    ArrayList<Delito> delitosEnc = new ArrayList<>();
    boolean removeDelitos=false;
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR)-18;
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int year2 = c.get(Calendar.YEAR);
    int month2 = c.get(Calendar.MONTH);
    int day2 = c.get(Calendar.DAY_OF_MONTH);
    int condena =0;
    int visitas=0;
    //Más variables
    Encarcelado encarcelado;
    public int idDelincuente_;
    public int idEncarcelado_;
    public String nombre_;
    public String apellido_;
    public String edad_;
    public String fechaN_;
    public String genero_;
    public int idGenero_;
    public String pais_;
    public int idPais_;
    public String foto_;
    public String nRegistro_;
    public int condena_;
    public int idCarcel_;
    public String carcel_;
    public String fechaIngreso_;
    public int nVisitas_;
    int nDelito=1;
    String de="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_encarcelado);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        administrador= (Administrador) getIntent().getSerializableExtra("administrador");
        encarcelado= (Encarcelado) getIntent().getSerializableExtra("encarcelado");

        //Traemos los datos del encarcelado
        if(encarcelado!=null){
            idDelincuente_ = encarcelado.idDelincuente;
            idEncarcelado_ = encarcelado.idEncarcelado;
            nombre_ = encarcelado.nombre;
            apellido_ = encarcelado.apellido;
            nRegistro_ = encarcelado.nRegistro;
            pais_ = encarcelado.pais;
            idPais_ = encarcelado.getIdPais();
            idCarcel_ = encarcelado.idCarcel;
            carcel_ = encarcelado.carcel;
            edad_ = encarcelado.edad;
            fechaN_ = encarcelado.fechaN;
            genero_ = encarcelado.genero;
            idGenero_=encarcelado.getIdGenero();
            fechaIngreso_ = encarcelado.fechaIngreso;
            condena_ = encarcelado.condena;
            nVisitas_ = encarcelado.nVisitas;
            foto_ = encarcelado.foto;
        }


        fotoE = (ImageView) findViewById(R.id.fotoE);
        if (fotoE != null) {
            new ImageDownloaderTask(fotoE).execute(foto_);
        }
        url= (EditText) findViewById(R.id.etUrl);
        url.setText(foto_);
        nombresE = (EditText) findViewById(R.id.etNombreE);
        nombresE.setText(nombre_);
        apellidosE =(EditText) findViewById(R.id.etApellidosE);
        apellidosE.setText(apellido_);
        fechaN = (TextView) findViewById(R.id.fechaNacimientoE);

        String[] fn= fechaN_.split("-");
        try {
            day = Integer.parseInt(fn[2]);
            month = Integer.parseInt(fn[1]);
            year = Integer.parseInt(fn[0]);
            fechaN.setText(year+"/"+month+"/"+day);
        }catch (Exception e){
            Toast.makeText(updateEncarcelado.this, "Error fecha", Toast.LENGTH_SHORT).show();
        }

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

        //fechaN.setText(year2+"/"+month2+"/"+day2);
        cargar = (Button) findViewById(R.id.btnCargar);
        cargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  foto = url.getText().toString();
                try{
                    String[] link= foto.split("://");
                    if(link.length==2 && foto.contains(".com")){
                        new ImageDownloaderTask(fotoE).execute(url.getText().toString());
                    }else{
                        Toast.makeText(updateEncarcelado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(updateEncarcelado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                }

            }
        });
        spGenero = (Spinner) findViewById(R.id.spGenero);

        PostResponseAsyncTask task1= new PostResponseAsyncTask(updateEncarcelado.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myGeneros= new JsonConverter<Genero>().toArrayList(s, Genero.class);
                Genero g = myGeneros.get(0);
                for(int i = 0; i<myGeneros.size(); i++){
                    if(myGeneros.get(i).getId()==idGenero_){
                        g = myGeneros.get(i);
                    }
                }
                myAdapter = new ArrayAdapter<Genero>(updateEncarcelado.this, android.R.layout.simple_spinner_item, myGeneros);
                spGenero.setAdapter(myAdapter);
                if (!genero_.equals(null)) {
                    int spinnerPosition = myAdapter.getPosition(g);
                    spGenero.setSelection(spinnerPosition);
                }

            }
        });
        task1.execute("http://www.criminalbrowser.com/CriminalBrowser/genero.php");


        spPais = (Spinner) findViewById(R.id.spPais);
        spCarcel = (Spinner) findViewById(R.id.spCarcel);
        HashMap PostData = new HashMap();
        PostData.put("country",administrador.getPais());
        PostResponseAsyncTask task2= new PostResponseAsyncTask(updateEncarcelado.this,PostData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myPaises= new JsonConverter<Pais>().toArrayList(s, Pais.class);
                Pais p=myPaises.get(0);
                for(int i = 0; i<myPaises.size(); i++){
                    if(myPaises.get(i).getId()==idPais_){
                        p = myPaises.get(i);
                    }
                }
                myAdapterP = new ArrayAdapter<Pais>(updateEncarcelado.this, android.R.layout.simple_spinner_item, myPaises);
                spPais.setAdapter(myAdapterP);
                if (!pais_.equals(null)) {
                    int spinnerPosition = myAdapterP.getPosition(p);
                    spPais.setSelection(spinnerPosition);
                }
                spPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Pais paisSelected = (Pais) spPais.getSelectedItem();

                        HashMap PostData2 = new HashMap();
                        PostData2.put("country",paisSelected.getPais());
                        PostResponseAsyncTask task3= new PostResponseAsyncTask(updateEncarcelado.this,PostData2, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                myCarceles= new JsonConverter<Carcel>().toArrayList(s, Carcel.class);
                                Carcel c = myCarceles.get(0);
                                for(int i = 0; i<myCarceles.size(); i++){
                                    if(myCarceles.get(i).getId()==idCarcel_){
                                        c = myCarceles.get(i);
                                    }
                                }
                                myAdapterC = new ArrayAdapter<Carcel>(updateEncarcelado.this, android.R.layout.simple_spinner_item, myCarceles);
                                spCarcel.setAdapter(myAdapterC);
                                if (!carcel_.equals(null)) {
                                    int spinnerPosition = myAdapterC.getPosition(c);
                                    spCarcel.setSelection(spinnerPosition);
                                }
                            }
                        });
                        task3.execute("http://www.criminalbrowser.com/CriminalBrowser/carcel.php");
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


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

        condenaE = (TextView) findViewById(R.id.condenaE);
        visitasE = (TextView) findViewById(R.id.visitasE);
        nRegE = (EditText) findViewById(R.id.nRegE);
        nRegE.setText(nRegistro_);


        /*HashMap PostData2 = new HashMap();
        PostData2.put("country",administrador.getPais());
        PostResponseAsyncTask task3= new PostResponseAsyncTask(addEncarcelado.this,PostData2, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myCarceles= new JsonConverter<Carcel>().toArrayList(s, Carcel.class);
                myAdapterC = new ArrayAdapter<Carcel>(addEncarcelado.this, android.R.layout.simple_spinner_item, myCarceles);
                spCarcel.setAdapter(myAdapterC);

            }
        });
        task3.execute("http://www.criminalbrowser.com/CriminalBrowser/carcel.php");*/

        fechaIng= (TextView) findViewById(R.id.fechaIngresoE);
        selectFechaIng = (Button) findViewById(R.id.btnSelectFechaIng);
        selectFechaIng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(998);
            }
        });
        String[] fi= fechaIngreso_.split("-");
        try {
            day2 = Integer.parseInt(fi[2]);
            month2 = Integer.parseInt(fi[1]);
            year2 = Integer.parseInt(fi[0]);
            fechaIng.setText(year2+"/"+month2+"/"+day2);
        }catch (Exception e){
            Toast.makeText(updateEncarcelado.this, "Error fecha", Toast.LENGTH_SHORT).show();
        }


        sbCondena = (SeekBar) findViewById(R.id.seekBarCondena);
        sbCondena.setMax(100);
        if(condena_>100){
            condena_=100;
        }
        condenaE.setText(condena_+ " años");
        sbCondena.setProgress(condena_);
        sbCondena.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                condena_=progress;
                condenaE.setText(condena_+ " años");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        sbVisitas = (SeekBar) findViewById(R.id.seekBarVisitas);
        sbVisitas.setMax(10);
        if(nVisitas_>10){
            nVisitas_=10;
        }
        visitasE.setText(nVisitas_ + " al año");
        sbVisitas.setProgress(nVisitas_);
        sbVisitas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                nVisitas_=progress;
                visitasE.setText(nVisitas_ + " al año");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        spDelito = (Spinner) findViewById(R.id.spDelitos);
        //spDelito.setClickable(false);
        // PostData2.put("country",administrador.getPais());
        PostResponseAsyncTask task4= new PostResponseAsyncTask(updateEncarcelado.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myDelitos= new JsonConverter<Delito>().toArrayList(s, Delito.class);
                myAdapterD = new ArrayAdapter<Delito>(updateEncarcelado.this, android.R.layout.simple_spinner_item, myDelitos);
                spDelito.setAdapter(myAdapterD);

            }
        });
        task4.execute("http://www.criminalbrowser.com/CriminalBrowser/delito.php");

        delitosE = (TextView) findViewById(R.id.delitosE);
        addDelito = (Button) findViewById(R.id.addDelito);
        addDelito.setClickable(false);
        addDelito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spDelito.getSelectedItem()!=null){
                    Delito delitoSelected = (Delito) spDelito.getSelectedItem();
                    if(idDelitos.contains(delitoSelected.getId())){
                        Toast.makeText(updateEncarcelado.this, "Ya se agregó ese delito", Toast.LENGTH_SHORT).show();
                    }else {
                        idDelitos.add(delitoSelected.getId());
                        delitosEnc.add(delitoSelected);
                        de = de + nDelito + ". " +delitoSelected.delito + "\n";
                        delitosE.setText(idDelitos.size() + " delitos agregados");
                        nDelito+=1;
                    }

                }
            }
        });

        HashMap PostData7= new HashMap();
        PostData7.put("idDelincuente",encarcelado.idDelincuente+"");
        PostResponseAsyncTask task6= new PostResponseAsyncTask(updateEncarcelado.this, PostData7, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                if(s.contains("null")){
                    de="No se ha encontrado ningún delito";
                }else {
                    delitosEnc = new JsonConverter<Delito>().toArrayList(s, Delito.class);
                    for (int i = 0; i < delitosEnc.size(); i++) {
                        Delito d = delitosEnc.get(i);
                        de = de + (i+1) + ". " +delitosEnc.get(i) + "\n";
                        idDelitos.add(d.getId());
                    }
                    delitosE.setText(idDelitos.size() + " delitos agregados");
                }

            }
        });
        task6.execute("http://www.criminalbrowser.com/CriminalBrowser/getDelitos.php");

        verDelitos = (Button) findViewById(R.id.btnVerDelitos);
        verDelitos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(updateEncarcelado.this);
                alert.setTitle("Delitos");
                alert.setMessage(de);
                alert.setPositiveButton("Aceptar", null);
                alert.show();
            }
        });


        cvEliminarDelitos = (CheckBox) findViewById(R.id.cvEliminarDelitos);
        cvEliminarDelitos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    cvEliminarDelitos.setEnabled(false);
                    idDelitos.clear();
                    de="";
                    delitosE.setText(" 0 delitos agregados");
                    removeDelitos=true;
                    /*HashMap PostData8= new HashMap();
                    PostData8.put("idDelincuente",encarcelado.idDelincuente+"");
                    PostResponseAsyncTask task7= new PostResponseAsyncTask(updateEncarcelado.this, PostData8, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            Toast.makeText(updateEncarcelado.this, s + "delitos eliminados" ,Toast.LENGTH_SHORT).show();
                        }
                    });
                    task7.execute("http://www.criminalbrowser.com/CriminalBrowser/removeDelitos.php");*/

                }
            }
        });

        agregarEncarcelado = (Button) findViewById(R.id.btnAddEncarcelado);
        agregarEncarcelado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                ArrayList<String> myErrors = new ArrayList<String>();
                String  foto = url.getText().toString();
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

                /*if(foto.equals("")){
                    myErrors.add("Debe ingresar una URL");
                }*/

                String nombre = nombresE.getText().toString();
                String apellido = apellidosE.getText().toString();
                if(nombre.equals("") || apellido.equals("")){
                    myErrors.add("Debe ingresar nombres y apellidos válidos");
                }else{
                }

                Genero generoSel= (Genero) spGenero.getSelectedItem();
                int idGenero = generoSel.getId();
                String fechaNacimientoE =year+"/"+month+"/"+day;;
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

                Carcel carcelSel = (Carcel) spCarcel.getSelectedItem();
                int idCarcel= carcelSel.getId();

                String fechaIngresoE=year2+"/"+month2+"/"+day2;

                if (year2 >= c.get(Calendar.YEAR) && month2 > c.get(Calendar.MONTH) && day2 > c.get(Calendar.DAY_OF_MONTH)) {
                    myErrors.add("La fecha de ingreso no puede ser después de hoy");
                } else {
                    fechaIngresoE = year2 + "/" + month2 + "/" + day2;
                }


                int NReg=0;
                try{
                    NReg = Integer.parseInt(nRegE.getText().toString());
                }
                catch (Exception e){
                    myErrors.add("Ingrese un número de registro válido");
                }

                int condenaE = condena_;
                if(condenaE==0){
                    myErrors.add("La condena debe ser mayor a 0 años");
                }

                int visitasE = nVisitas_;
                if(visitasE==0){
                    myErrors.add("El número de visitas debe ser mayor a 0");
                }

                if(idDelitos.size()==0){
                    myErrors.add("Debe agregar al menos un delito");
                }


                String losErrores="";
                for(int i=0; i<myErrors.size(); i++){
                    losErrores = losErrores + "*" + myErrors.get(i) + "\n";
                }

                if(losErrores!=""){
                    AlertDialog.Builder alert = new AlertDialog.Builder(updateEncarcelado.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(losErrores);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                }else {

                   /* String  enc = "D: "+encarcelado.idDelincuente + " - " + nombre + " -- " + apellido + " -- " + fechaNacimientoE + " -- " + idGenero + " -- " +
                            foto + " -- " + idPais + " -- E:  " + encarcelado.idEncarcelado + " -- " + condenaE + " -- " + NReg +
                            " -- " + visitasE + " -- " + idCarcel + " -- " + fechaIngresoE;

                    AlertDialog.Builder alert = new AlertDialog.Builder(updateEncarcelado.this);
                    alert.setTitle("Encarcelado: ");
                    alert.setMessage(enc);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();*/
                    if(removeDelitos){
                        HashMap PostData8= new HashMap();
                        PostData8.put("idDelincuente",encarcelado.idDelincuente+"");
                        PostResponseAsyncTask task7= new PostResponseAsyncTask(updateEncarcelado.this, PostData8, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                Toast.makeText(updateEncarcelado.this, s + "delitos eliminados" ,Toast.LENGTH_SHORT).show();
                            }
                        });
                        task7.execute("http://www.criminalbrowser.com/CriminalBrowser/removeDelitos.php");
                    }

                    Toast.makeText(updateEncarcelado.this, "Muy bien!", Toast.LENGTH_SHORT).show();
                    HashMap PostData4 = new HashMap();
                    PostData4.put("txtID_D", ""+encarcelado.idDelincuente);
                    PostData4.put("txtName", nombre);
                    PostData4.put("txtLastN", apellido);
                    PostData4.put("txtFechaN", fechaNacimientoE);
                    PostData4.put("txtGenre", ""+idGenero);
                    PostData4.put("txtPic", foto);
                    PostData4.put("txtCountry", ""+idPais);
                    PostData4.put("txtID_E", ""+encarcelado.idEncarcelado);
                    PostData4.put("txtCondena", ""+condenaE);
                    PostData4.put("txtNReg", ""+NReg);
                    PostData4.put("txtNvisitas", ""+visitasE);
                    PostData4.put("txtCarcel", ""+idCarcel);
                    PostData4.put("txtFIngreso", fechaIngresoE);

                    PostResponseAsyncTask task5= new PostResponseAsyncTask(updateEncarcelado.this, PostData4, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            //Toast.makeText(addEncarcelado.this, s,Toast.LENGTH_SHORT).show();
                            //finish();
                            if(s.contains("success")) {

                                for (int i = 0; i < idDelitos.size(); i++) {
                                    HashMap PostData = new HashMap();
                                    PostData.put("delito", idDelitos.get(i) + "");
                                    PostData.put("idDelincuente", encarcelado.idDelincuente+ "");
                                    PostResponseAsyncTask task = new PostResponseAsyncTask(updateEncarcelado.this, PostData, new AsyncResponse() {
                                        @Override
                                        public void processFinish(String s) {
                                        }
                                    });
                                    task.execute("http://www.criminalbrowser.com/CriminalBrowser/insertDelitos.php");
                                }
                                Toast.makeText(updateEncarcelado.this, "Se ha actualizado el encarcelado satisfactoriamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(updateEncarcelado.this, "Oopps, ocurrió un error. Intenta más tarde", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    task5.execute("http://www.criminalbrowser.com/CriminalBrowser/updateEncarcelado.php");
                }


            }
        });



    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(updateEncarcelado.this, myDateListener, year, month, day);
        }
        else if(id==998){
            return new DatePickerDialog(updateEncarcelado.this, myDateListener2, year2, month2, day2);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int arg0, int monthOfYear, int dayOfMonth) {
            year2=arg0;
            month2=monthOfYear+1;
            day2=dayOfMonth;
            fechaIng.setText(year2+"/"+month2+"/"+day2);
        }
    };

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
}