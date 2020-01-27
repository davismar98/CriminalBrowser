package davismar98.browser.criminal.app.com.criminalbrowser;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.Serializable;

public class adminActivity extends AppCompatActivity {


    TextView adminN, adminUser, adminPais, orgNombre, orgDir, orgTel, orgCorreo;
    ImageView bandera;
    Button btnVerB, btnVerE, btnBuscarB, btnBuscarE, btnAgregarB, btnAgregarE, btnDenuncia, btnVisita;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                final CharSequence[] items = {"Delincuente más buscado de cada país", "Géneros por cárcel", "Delitos más cometidos en cada país"};

                AlertDialog.Builder builder = new AlertDialog.Builder(adminActivity.this);
                builder.setTitle("Estadísticas");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                        if(item==1){
                            Intent intent = new Intent(adminActivity.this, verGenerosCarcel.class);
                            startActivity(intent);
                        }else if(item==0){
                            Intent intent = new Intent(adminActivity.this, verBuscadosPais.class);
                            startActivity(intent);
                        }else if(item==2){
                            Intent intent = new Intent(adminActivity.this, verDelitoPais.class);
                            startActivity(intent);
                        }

                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Administrador administrador= (Administrador) getIntent().getSerializableExtra("administrador");

        adminN = (TextView)findViewById(R.id.adminN);
        adminUser = (TextView) findViewById(R.id.adminUser);
        adminPais = (TextView) findViewById(R.id.adminPais);
        orgNombre = (TextView) findViewById(R.id.organismoN);
        orgDir = (TextView) findViewById(R.id.organismoDir);
        orgTel = (TextView) findViewById(R.id.organismoTel);
        orgCorreo = (TextView) findViewById(R.id.organismoCorreo);
        bandera = (ImageView) findViewById(R.id.bandera);



        if(administrador!=null){
            adminN.setText(administrador.nombre + " " +administrador.apellido);
            adminUser.setText(administrador.username);
            adminPais.setText(administrador.pais);
            orgNombre.setText(administrador.organismo);
            orgDir.setText(administrador.direccion);
            orgTel.setText(administrador.telefono);
            orgCorreo.setText(administrador.correo);
            int id = this.getResources().getIdentifier(administrador.bandera, "drawable",this.getPackageName());
            if (id!=0) {
                bandera.setImageResource(id);
            }

        }
        /**/

        btnVerB = (Button) findViewById(R.id.btnVerB);
        btnVerB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(adminActivity.this, verBuscados.class);
                in.putExtra("country", administrador.getPais());
                in.putExtra("admin", true);
                in.putExtra("administrador", (Serializable) administrador);
                startActivity(in);
            }
        });

        btnVerE = (Button) findViewById(R.id.btnVerE);
        btnVerE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(adminActivity.this, verEncarcelados.class);
                in.putExtra("country", administrador.getPais());
                in.putExtra("admin", true);
                in.putExtra("administrador", (Serializable) administrador);
                startActivity(in);
            }
        });

        btnBuscarB = (Button) findViewById(R.id.btnBuscarB);
        btnBuscarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dfdf
            }
        });

        btnBuscarE = (Button) findViewById(R.id.btnBuscarE);
        btnBuscarE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //sd
            }
        });

        btnAgregarB = (Button) findViewById(R.id.btnAgregarB);
        btnAgregarB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(adminActivity.this, addBuscado.class);
                in.putExtra("admin", (Serializable) administrador);
                startActivity(in);
            }
        });

        btnAgregarE = (Button) findViewById(R.id.btnAgregarE);
        btnAgregarE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(adminActivity.this, addEncarcelado.class);
                in.putExtra("admin", (Serializable) administrador);
                startActivity(in);
            }
        });


        btnDenuncia = (Button) findViewById(R.id.btnDenuncia);
        btnDenuncia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(adminActivity.this, verDenuncias.class);
                in.putExtra("country", administrador.getPais());
                in.putExtra("admin", true);
                in.putExtra("administrador", (Serializable) administrador);
                startActivity(in);
            }
        });

        btnVisita = (Button) findViewById(R.id.btnVisita);
        btnVisita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(adminActivity.this, verVisitas.class);
                in.putExtra("country", administrador.getPais());
                in.putExtra("admin", true);
                in.putExtra("administrador", (Serializable) administrador);
                startActivity(in);
            }
        });

    }

}
