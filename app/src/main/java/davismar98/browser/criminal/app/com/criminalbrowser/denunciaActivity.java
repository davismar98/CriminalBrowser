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

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.regex.Pattern;

public class denunciaActivity extends AppCompatActivity {

    TextView orgN, orgCorreo, orgTel, orgDir,busNombre, busAlias, busRec;
    EditText userNombre, userApellido, userID, userEmail, userPhone, denuncia;
    FloatingActionButton fab;
    Buscado buscado;
    Organismo org;
    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    int day = c.get(Calendar.DAY_OF_MONTH);
    int hour = c.get(Calendar.HOUR_OF_DAY);
    int min = c.get(Calendar.MINUTE);
    String error="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_denuncia);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        orgN = (TextView) findViewById(R.id.organismoNombre);
        orgCorreo = (TextView) findViewById(R.id.organismoCorreo);
        orgDir = (TextView) findViewById(R.id.organismoDir);
        orgTel = (TextView) findViewById(R.id.organismoTel);

        busNombre = (TextView) findViewById(R.id.busNombre);
        busAlias= (TextView) findViewById(R.id.busAlias);
        busRec = (TextView) findViewById(R.id.busRecomp);

        userNombre = (EditText) findViewById(R.id.userName);
        userApellido = (EditText) findViewById(R.id.userApellidos);
        userID = (EditText) findViewById(R.id.userID);
        userEmail = (EditText) findViewById(R.id.userEmail);
        userPhone = (EditText) findViewById(R.id.userPhone);

        denuncia = (EditText) findViewById(R.id.comentarios);

        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ArrayList<String> errores = new ArrayList<>();

                final String nombre, apellido, id, tel, email, hora2, fecha2;
                nombre = userNombre.getText().toString();
                apellido=userApellido.getText().toString();

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


                fecha2=year+"/"+month+"/"+day;

                for (int i=0; i<errores.size(); i++){
                    error = error + "*"+errores.get(i) + "\n";
                }

                String comentario = denuncia.getText().toString();

                if (error.equals("")){
                    Toast.makeText(denunciaActivity.this, "Bien", Toast.LENGTH_SHORT).show();
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
                    postData.put("idPais", buscado.idPais+"");
                    postData.put("idDenuncia", idVisita+"");
                    postData.put("fecha", fecha2+"");
                    postData.put("comentario", comentario+"");
                    postData.put("idBuscado", buscado.idBuscado+"");

                    PostResponseAsyncTask task13 = new PostResponseAsyncTask(denunciaActivity.this, postData, "Confirmando...", new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.contains("success")){
                                AlertDialog.Builder alert = new AlertDialog.Builder(denunciaActivity.this);
                                alert.setTitle("Denuncia realizada");
                                alert.setIcon(R.drawable.report);
                                alert.setMessage("¡Gracias!\n Su denuncia se ha enviado satisfactoriamente. Pronto un agente de "+org.organismo + " se comunicará con usted.");
                                //alert.setMessage(s);
                                alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        finish();
                                    }
                                });
                                alert.show();
                            }else{
                                Toast.makeText(denunciaActivity.this, s, Toast.LENGTH_SHORT).show();
                                finish();
                            }

                        }
                    });
                    task13.execute("http://www.criminalbrowser.com/CriminalBrowser/insertDenuncia.php");

                }else{
                    AlertDialog.Builder alert = new AlertDialog.Builder(denunciaActivity.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(error);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                    error="";
                }

            }
        });

        buscado = (Buscado) getIntent().getSerializableExtra("buscado");


        busNombre.setText(buscado.nombre + " " + buscado.apellido);
        busAlias.setText(buscado.alias);
        busRec.setText("$"+buscado.recompensa+" (COP)");

        HashMap PostData2 = new HashMap();
        PostData2.put("idPais", buscado.idPais+"");
        PostResponseAsyncTask task2 = new PostResponseAsyncTask(denunciaActivity.this, PostData2, new AsyncResponse() {
            @Override
            public void processFinish(String s) {
                try {
                    ArrayList<Organismo> organismos= new JsonConverter<Organismo>().toArrayList(s, Organismo.class);
                    org= organismos.get(0);
                    orgN.setText(org.organismo);
                    orgTel.setText(org.telefono+"");
                    orgDir.setText(org.direccion);
                    orgCorreo.setText(org.correo);
                    /*AlertDialog.Builder alert = new AlertDialog.Builder(denunciaActivity.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(org.organismo + "  " + org.correo + "  "+ org.telefono + "  " + org.direccion);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();*/
                }catch (Exception e){
                    //Toast.makeText(visitaActivity.this, "Visitas error : " + s, Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder alert = new AlertDialog.Builder(denunciaActivity.this);
                    alert.setTitle("Revise los siguientes errores");
                    alert.setMessage(s);
                    alert.setPositiveButton("Entiendo", null);
                    alert.show();
                }
            }
        });
        task2.execute("http://www.criminalbrowser.com/CriminalBrowser/getOrganismo.php");


    }



}
