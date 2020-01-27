package davismar98.browser.criminal.app.com.criminalbrowser;

import android.app.DatePickerDialog;
import android.app.Dialog;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class addEncarcelado extends AppCompatActivity {

    ImageView fotoE;
    Button cargar, selectFecha, selectFechaIng, addDelito, agregarEncarcelado;
    EditText url, nRegE, nombresE, apellidosE ;
    TextView fechaN, condenaE, visitasE, fechaIng, delitosE;
    Spinner spGenero, spPais, spCarcel, spDelito;
    SeekBar sbCondena, sbVisitas;
    CheckBox cbFotoDefault;

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

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR)-18;
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int year2 = c.get(Calendar.YEAR);
    int month2 = c.get(Calendar.MONTH);
    int day2 = c.get(Calendar.DAY_OF_MONTH);
    int condena =0;
    int visitas=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_encarcelado);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        administrador= (Administrador) getIntent().getSerializableExtra("admin");


        fotoE = (ImageView) findViewById(R.id.fotoE);
        url= (EditText) findViewById(R.id.etUrl);
        nombresE = (EditText) findViewById(R.id.etNombreE);
        apellidosE =(EditText) findViewById(R.id.etApellidosE);
        fechaN = (TextView) findViewById(R.id.fechaNacimientoE);
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
                        new ImageDownloaderTask(fotoE).execute(url.getText().toString());
                    }else{
                        Toast.makeText(addEncarcelado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(addEncarcelado.this, "Debe ingresar una URL válida", Toast.LENGTH_SHORT).show();
                }
            }
        });
        spGenero = (Spinner) findViewById(R.id.spGenero);

        PostResponseAsyncTask task1= new PostResponseAsyncTask(addEncarcelado.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myGeneros= new JsonConverter<Genero>().toArrayList(s, Genero.class);
                myAdapter = new ArrayAdapter<Genero>(addEncarcelado.this, android.R.layout.simple_spinner_item, myGeneros);
                spGenero.setAdapter(myAdapter);

            }
        });
        task1.execute("http://www.criminalbrowser.com/CriminalBrowser/genero.php");

        spPais = (Spinner) findViewById(R.id.spPais);
        spCarcel = (Spinner) findViewById(R.id.spCarcel);
        HashMap PostData = new HashMap();
        PostData.put("country",administrador.getPais());
        PostResponseAsyncTask task2= new PostResponseAsyncTask(addEncarcelado.this,PostData, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myPaises= new JsonConverter<Pais>().toArrayList(s, Pais.class);
                myAdapterP = new ArrayAdapter<Pais>(addEncarcelado.this, android.R.layout.simple_spinner_item, myPaises);
                spPais.setAdapter(myAdapterP);
                spPais.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        Pais paisSelected = (Pais) spPais.getSelectedItem();

                        HashMap PostData2 = new HashMap();
                        PostData2.put("country",paisSelected.getPais());
                        PostResponseAsyncTask task3= new PostResponseAsyncTask(addEncarcelado.this,PostData2, new AsyncResponse() {
                            @Override
                            public void processFinish(String s) {
                                myCarceles= new JsonConverter<Carcel>().toArrayList(s, Carcel.class);
                                myAdapterC = new ArrayAdapter<Carcel>(addEncarcelado.this, android.R.layout.simple_spinner_item, myCarceles);
                                spCarcel.setAdapter(myAdapterC);
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

        sbCondena = (SeekBar) findViewById(R.id.seekBarCondena);
        sbCondena.setMax(100);
        sbCondena.setProgress(condena);
        sbCondena.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                condena=progress;
                condenaE.setText(condena+ " años");
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
        sbVisitas.setProgress(visitas);
        sbVisitas.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                visitas=progress;
                visitasE.setText(visitas + " al año");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        spDelito = (Spinner) findViewById(R.id.spDelitos);
       // PostData2.put("country",administrador.getPais());
        PostResponseAsyncTask task4= new PostResponseAsyncTask(addEncarcelado.this, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                myDelitos= new JsonConverter<Delito>().toArrayList(s, Delito.class);
                myAdapterD = new ArrayAdapter<Delito>(addEncarcelado.this, android.R.layout.simple_spinner_item, myDelitos);
                spDelito.setAdapter(myAdapterD);

            }
        });
        task4.execute("http://www.criminalbrowser.com/CriminalBrowser/delito.php");

        delitosE = (TextView) findViewById(R.id.delitosE);
        addDelito = (Button) findViewById(R.id.addDelito);
        addDelito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spDelito.getSelectedItem()!=null){
                    Delito delitoSelected = (Delito) spDelito.getSelectedItem();
                    if(idDelitos.contains(delitoSelected.getId())){
                        Toast.makeText(addEncarcelado.this, "Ya se agregó ese delito", Toast.LENGTH_SHORT).show();
                    }else {
                        idDelitos.add(delitoSelected.getId());
                        delitosE.setText(idDelitos.size() + " delitos agregados");
                    }

                }
            }
        });


        agregarEncarcelado = (Button) findViewById(R.id.btnAddEncarcelado);
        agregarEncarcelado.setOnClickListener(new View.OnClickListener() {
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


                String nombre = nombresE.getText().toString();
                String apellido = apellidosE.getText().toString();
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


                Genero generoSel = (Genero) spGenero.getSelectedItem();
                int idGenero = generoSel.getId();
                String fechaNacimientoE = year + "/" + month + "/" + day;

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

                if(fechaIng.getText().toString().contains("seleccionar")){
                    myErrors.add("Debe agregar una fecha de ingreso");
                }else {
                    if (year2 >= c.get(Calendar.YEAR) - 18 && month2 > c.get(Calendar.MONTH) && day2 > c.get(Calendar.DAY_OF_MONTH)) {
                        myErrors.add("La fecha de ingreso no puede ser después de hoy");
                    } else {
                        fechaIngresoE = year2 + "/" + month2 + "/" + day2;
                    }

                }

                int NReg=0;
                try{
                    NReg = Integer.parseInt(nRegE.getText().toString());
                }
                catch (Exception e){
                    myErrors.add("Ingrese un número de registro válido");
                }

                int condenaE = condena;
                if(condenaE==0){
                    myErrors.add("La condena debe ser mayor a 0 años");
                }

                int visitasE = visitas;
                if(visitasE==0){
                    myErrors.add("El número de visitas debe ser mayor a 0");
                }

                if(idDelitos.size()==0){
                    myErrors.add("Debe agregar al menos un delito");
                }

                Random rand = new Random();
                final int  idDelincuente = rand.nextInt(99999) + 1000;
                Random rand2 = new Random();
                int idEncarcelado = rand2.nextInt(99999) + 1000;

                String losErrores="";
                for(int i=0; i<myErrors.size(); i++){
                    losErrores = losErrores + "*" + myErrors.get(i) + "\n";
                }

                if(losErrores!=""){
                    AlertDialog.Builder alert = new AlertDialog.Builder(addEncarcelado.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(losErrores);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                }else {

                    /*String  enc = idDelincuente + " - " + nombre + " -- " + apellido + " -- " + fechaNacimientoE + " -- " + idGenero + " -- " +
                            foto + " -- " + idPais + " -- " + idEncarcelado + " -- " + condenaE + " -- " + NReg +
                            " -- " + visitasE + " -- " + idCarcel + " -- " + fechaIngresoE;

                    AlertDialog.Builder alert = new AlertDialog.Builder(addEncarcelado.this);
                    alert.setTitle("Encarcelado: ");
                    alert.setMessage(enc);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();*/


                    Toast.makeText(addEncarcelado.this, "Muy bien!", Toast.LENGTH_SHORT).show();
                    HashMap PostData4 = new HashMap();
                    PostData4.put("txtID_D", ""+idDelincuente);
                    PostData4.put("txtName", nombre);
                    PostData4.put("txtLastN", apellido);
                    PostData4.put("txtFechaN", fechaNacimientoE);
                    PostData4.put("txtGenre", ""+idGenero);
                    PostData4.put("txtPic", foto);
                    PostData4.put("txtCountry", ""+idPais);
                    PostData4.put("txtID_E", ""+idEncarcelado);
                    PostData4.put("txtCondena", ""+condenaE);
                    PostData4.put("txtNReg", ""+NReg);
                    PostData4.put("txtNvisitas", ""+visitasE);
                    PostData4.put("txtCarcel", ""+idCarcel);
                    PostData4.put("txtFIngreso", fechaIngresoE);

                    PostResponseAsyncTask task5= new PostResponseAsyncTask(addEncarcelado.this, PostData4, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            //Toast.makeText(addEncarcelado.this, s,Toast.LENGTH_SHORT).show();
                            //finish();
                            if(s.contains("success")) {

                                for (int i = 0; i < idDelitos.size(); i++) {
                                    HashMap PostData = new HashMap();
                                    PostData.put("delito", idDelitos.get(i) + "");
                                    PostData.put("idDelincuente", idDelincuente + "");
                                    PostResponseAsyncTask task = new PostResponseAsyncTask(addEncarcelado.this, PostData, new AsyncResponse() {
                                        @Override
                                        public void processFinish(String s) {
                                        }
                                    });
                                    task.execute("http://www.criminalbrowser.com/CriminalBrowser/insertDelitos.php");
                                }
                                Toast.makeText(addEncarcelado.this, "Se ha insertado el encarcelado satisfactoriamente", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                Toast.makeText(addEncarcelado.this, "Oopps, ocurrió un error. Intenta más tarde", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                    task5.execute("http://www.criminalbrowser.com/CriminalBrowser/insertEncarcelado.php");
                }


            }
        });



    }
    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(addEncarcelado.this, myDateListener, year, month, day);
        }
        else if(id==998){
            return new DatePickerDialog(addEncarcelado.this, myDateListener2, year2, month2, day2);
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
