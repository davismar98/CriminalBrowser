package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    ImageView btnVer;
    EditText etUsername, etContraseña;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = (EditText) findViewById(R.id.etUsername);
        etContraseña= (EditText) findViewById(R.id.etContraseña);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnVer = (ImageView) findViewById(R.id.verUsuarios);
        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verUsuarios();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap PostData = new HashMap();
                PostData.put("txtUsername",etUsername.getText().toString());
                PostData.put("txtPassword", etContraseña.getText().toString());
                PostResponseAsyncTask task1= new PostResponseAsyncTask(LoginActivity.this, PostData, "Iniciando sesión", new AsyncResponse() {
                    @Override
                    public void processFinish(String s) {
                    if (s.contains("failed")) {
                        Toast.makeText(LoginActivity.this, "Usuario o Contraseña incorrectos", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Inicio Exitoso", Toast.LENGTH_SHORT).show();
                        ArrayList<Administrador> administrador = new JsonConverter<Administrador>().toArrayList(s,Administrador.class);
                        Administrador admin = administrador.get(0);
                        //Toast.makeText(LoginActivity.this, admin.getNombre(), Toast.LENGTH_SHORT).show();
                        Intent in= new Intent(LoginActivity.this, adminActivity.class);
                        in.putExtra("administrador", (Serializable) admin);
                        startActivity(in);

                    }
                }
            });
            task1.execute("http://www.criminalbrowser.com/CriminalBrowser/login.php");
            }
        });
    }


    public void verUsuarios(){
        String s = "Jonn,davidmapu,andrescopi,promero3,gdiaz4,dnichols5,marnold6,ccarpenter7,rbrooks8,areid9,hmedinaa,sarmstrongb,andresrepe,davidrohe,gabrielcasa";
        AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
        alert.setTitle("ADMINISTADORES");
        alert.setIcon(R.drawable.information);
        alert.setMessage(s);
        alert.setPositiveButton("Entiendo", null);
        alert.show();
    }

}
