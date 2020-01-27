package davismar98.browser.criminal.app.com.criminalbrowser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class visitaActivity extends AppCompatActivity {

    TextView carcelN, carcelCorreo, carcelTel, carcelDir, horaI, horaF, diaVista, encNombre, encReg, encVisitas, dia, hora;
    EditText userNombre, userApellido, userID, userEmail, userPhone;
    Button agregarDia, agregarHora;
    FloatingActionButton fab;
    Encarcelado encarcelado;
    int visitas;
    Carcel carcel;
    Date date;
    String dayOfWeek="";
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int min = c.get(Calendar.MINUTE);
    int cHoraI, cHoraF, cMinI, cMinF;
    String error="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visita2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        encarcelado= (Encarcelado) getIntent().getSerializableExtra("encarcelado");

        carcelN = (TextView) findViewById(R.id.carcelNombre);
        carcelCorreo = (TextView) findViewById(R.id.carcelCorreo);
        carcelDir = (TextView) findViewById(R.id.carcelDir);
        carcelTel = (TextView) findViewById(R.id.carcelTel);
        horaI = (TextView) findViewById(R.id.horaI);
        horaF = (TextView) findViewById(R.id.horaF);
        diaVista = (TextView) findViewById(R.id.diaVisita);
        encNombre = (TextView) findViewById(R.id.encNombre);
        encReg = (TextView) findViewById(R.id.encReg);
        encVisitas = (TextView) findViewById(R.id.encVisitas);
        dia = (TextView) findViewById(R.id.dia);
        hora =(TextView) findViewById(R.id.hora);
        userNombre = (EditText) findViewById(R.id.userName);
        userApellido = (EditText) findViewById(R.id.userApellidos);
        userID = (EditText) findViewById(R.id.userID);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPhone = (EditText) findViewById(R.id.userPhone);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              /*  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                ArrayList<String> errores = new ArrayList<>();

                if(visitas<=0){
                    errores.add("El encarcelado no tiene visitas disponibles");
                }

                final String nombre, apellido, id, tel, email, hora2, fecha2;
                nombre = userNombre.getText().toString();
                apellido=userApellido.getText().toString();
               /* if(nombre.equals("")){
                    errores.add("Ingrese un nombre válido");
                }
                if(apellido.equals("")){
                    errores.add("Ingrese un apellido válido");
                }*/

                String n1,n2,a1,a2 = "";
                if (nombre.equals("")) {
                    errores.add("Debe ingresar su nombre");
                }else {
                    String[] nomb = nombre.split(" ");
                    if(nomb.length>1){
                        n1= nomb[0];
                        n2 = nomb[1];
                        if (!Pattern.matches("[a-zA-Z]+", n1) || !Pattern.matches("[a-zA-Z]+", n2)) {
                            errores.add("El nombre no puede contener números o carácteres extraños");
                        }
                    }else {
                        n1 = nomb[0];
                        if (!Pattern.matches("[a-zA-Z]+", n1)) {
                            errores.add("El nombre no puede contener números o carácteres extraños");
                        }
                    }
                }

                if(apellido.equals("")){
                    errores.add("Debe ingresar sus apellidos");
                }else{
                    String[] ape = apellido.split(" ");
                    if (ape.length > 1) {
                        a1 = ape[0];
                        a2 = ape[1];
                        if (!Pattern.matches("[a-zA-Z]+", a1) || !Pattern.matches("[a-zA-Z]+", a2)) {
                            errores.add("El apellido no puede contener números o carácteres extraños");
                        }
                    } else {
                        a1 = ape[0];
                        if (!Pattern.matches("[a-zA-Z]+", a1)) {
                            errores.add("El apellido no puede contener números o carácteres extraños");
                        }
                    }
                }


                id=userID.getText().toString();
                if(id.equals("")){
                    errores.add("Ingrese un número de indentificación");
                }
                email =userEmail.getText().toString();
                if(email.equals("")){
                    errores.add("Ingrese una dirección de correo");
                }else if(!email.contains("@") && !email.contains(".com")){
                    errores.add("La dirección de correo no es válida");
                }
                tel = userPhone.getText().toString();
                if(tel.equals("")){
                    errores.add("Ingrese un número de teléfono");
                }

               fecha2 = dia.getText().toString();

                if(fecha2.contains("seleccionar")){
                    errores.add("Debe serleccionar una fecha");
                }else{
                    int cY = c.get(Calendar.YEAR);
                    int cM = c.get(Calendar.MONTH) + 1;
                    int cD = c.get(Calendar.DAY_OF_MONTH);
                    String f = cY+"/"+cM+"/"+cD;
                    //errores.add("La fecha debe ser después de hoy ("+f+")");
                    if(year<=cY && month<=cM &&day<=cD){
                        errores.add("La fecha debe ser después de hoy ("+f+")");
                    }else{
                        if(!dayOfWeek.equals(carcel.dia.toLowerCase())){
                            errores.add("El día debe ser un " + carcel.dia);
                        }
                    }

                }

                hora2 = hora.getText().toString()+":00";
                if(hora2.contains("seleccionar")){
                    errores.add("Debe seleccionar una hora");
                }else{
                    if((hour<cHoraI || hour>cHoraF) || (hour==cHoraI && min<cMinI) || (hour==cHoraF && min>cMinF)){
                        errores.add("La hora debe ser entre las " + carcel.horaI +" y las " + carcel.horaF);
                    }
                }


                for (int i=0; i<errores.size(); i++){
                    error = error + "*"+errores.get(i) + "\n";
                }

                if (error.equals("")){
                    Toast.makeText(visitaActivity.this, "Bien", Toast.LENGTH_SHORT).show();
                    Random rand = new Random();
                    final int  idUsuario = rand.nextInt(99999) + 1000;
                    Random rand2 = new Random();
                    int idVisita = rand2.nextInt(99999) + 1000;

                    HashMap postData = new HashMap();
                    postData.put("idUsuario", idUsuario+"");
                    postData.put("nombre", nombre+"");
                    postData.put("apellido", apellido+"");
                    postData.put("correo", email+"");
                    postData.put("telefono", tel+"");
                    postData.put("id", id+"");
                    postData.put("idPais", encarcelado.idPais+"");
                    postData.put("idVisita", idVisita+"");
                    postData.put("fecha", fecha2+"");
                    postData.put("hora", hora2+"");
                    postData.put("idEncarcelado", encarcelado.idEncarcelado+"");

                   PostResponseAsyncTask task13 = new PostResponseAsyncTask(visitaActivity.this, postData, "Confirmando...", new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.contains("success")){
                                AlertDialog.Builder alert = new AlertDialog.Builder(visitaActivity.this);
                                alert.setTitle("Visita Concretada");
                                alert.setIcon(R.drawable.prisioner3);
                                alert.setMessage("Su visita quedó agendada correctamente.\n\nDía: " + fecha2 + " ("+dayOfWeek+")\nHora: "+ hora2);
                                //alert.setMessage(s);
                                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                alert.show();
                            }else{
                                Toast.makeText(visitaActivity.this, s, Toast.LENGTH_SHORT).show();
                                finish();
                            }


                        }
                    });
                    task13.execute("http://www.criminalbrowser.com/CriminalBrowser/insertVisita.php");

                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(visitaActivity.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(error);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                    error="";
                }

            }
        });

        final Encarcelado encarcelado= (Encarcelado) getIntent().getSerializableExtra("encarcelado");


        HashMap PostData = new HashMap();
        PostData.put("idCarcel", encarcelado.idCarcel+"");
        PostResponseAsyncTask task1 = new PostResponseAsyncTask(visitaActivity.this, PostData, "Cargando cárceles", new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                try {
                    ArrayList<Carcel> carceles = new JsonConverter<Carcel>().toArrayList(s, Carcel.class);
                    carcel = carceles.get(0);
                    carcelN.setText(carcel.carcel);
                    carcelCorreo.setText(carcel.correo);
                    carcelTel.setText(carcel.telefono);
                    carcelDir.setText(carcel.direccion);
                    diaVista.setText(carcel.dia);
                    horaI.setText(carcel.horaI);
                    horaF.setText(carcel.horaF);
                    String[] horaInicio = carcel.horaI.split(":");
                    cHoraI = Integer.parseInt(horaInicio[0]);
                    cMinI = Integer.parseInt(horaInicio[1]);
                    String[] horaFin = carcel.horaF.split(":");
                    cHoraF = Integer.parseInt(horaFin[0]);
                    cMinF = Integer.parseInt(horaFin[1]);

                }catch (Exception e){
                    Toast.makeText(visitaActivity.this, "Cárcel error: " + s, Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        task1.execute("http://www.criminalbrowser.com/CriminalBrowser/getCarcel.php");

        encNombre.setText(encarcelado.nombre + " " + encarcelado.apellido);
        encReg.setText(encarcelado.nRegistro);

        HashMap PostData2 = new HashMap();
        PostData2.put("idEncarcelado", encarcelado.idEncarcelado+"");
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(visitaActivity.this, PostData2, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                try {
                    ArrayList<visitaEncarcelado> visitasEnc= new JsonConverter<visitaEncarcelado>().toArrayList(s, visitaEncarcelado.class);
                    visitas = visitasEnc.get(0).getVisitas();
                    encVisitas.setText(visitas+"");
                }catch (Exception e){
                    //Toast.makeText(visitaActivity.this, "Visitas error : " + s, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(visitaActivity.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(s);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                }
            }
        });
        task2.execute("http://www.criminalbrowser.com/CriminalBrowser/getVisitas2.php");


        agregarDia = (Button) findViewById(R.id.btnAddDia);
        agregarDia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });

        agregarHora = (Button) findViewById(R.id.btnAddHora);
        agregarHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(998);
            }
        });


    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(visitaActivity.this, myDateListener, year, month, day);
        }
        else if(id==998){
            return new TimePickerDialog(visitaActivity.this, myTimeListener, hour, min, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay;
            min=minute;
            if (hour<10){
                if(min>9) {
                    hora.setText("0" + hour + ":" + minute);
                }else{
                    hora.setText("0" + hour + ":0" + minute);
                }
            }else{
                hora.setText(hour + ":" + minute);
            }

        }
    };

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            year = arg1;
            month = arg2+1;
            day = arg3 ;
            dia.setText(year+"/"+month+"/"+day);
            SimpleDateFormat simpledateformat = new SimpleDateFormat("EEEE");
            date = new Date(year, month, day-3);
            dayOfWeek = simpledateformat.format(date);
            Toast.makeText(visitaActivity.this, dayOfWeek, Toast.LENGTH_SHORT).show();
        }
    };

}
