package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

public class selectType extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageButton encarcelado, buscado;
    Spinner sPaises;
    Button next;
    String paisSelected;
    String[] countryNames={"Colombia", "Venezuela", "Perú", "Ecuador", "Brasil", "Bolivia", "Argentina", "Chile", "Uruguay", "Paraguay", "México", "Global"};
    int[] flags={R.drawable.colombia, R.drawable.venezuela, R.drawable.peru, R.drawable.ecuador, R.drawable.brazil, R.drawable.bolivia, R.drawable.argentina, R.drawable.chile, R.drawable.uruguay, R.drawable.paraguay, R.drawable.mexico, R.drawable.global};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        sPaises = (Spinner) findViewById(R.id.spinner);
        sPaises.setOnItemSelectedListener(this);

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), flags, countryNames);
        sPaises.setAdapter(customAdapter);

        encarcelado = (ImageButton) findViewById(R.id.encarcelado);
        buscado = (ImageButton) findViewById(R.id.buscado);

        encarcelado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent= new Intent(selectType.this, verEncarcelados.class);
                myIntent.putExtra("country", paisSelected);
                myIntent.putExtra("admin", false);
                startActivity(myIntent);
            }
        });

        buscado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent= new Intent(selectType.this, verBuscados.class);
                myIntent.putExtra("country", paisSelected);
                myIntent.putExtra("admin", false);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        paisSelected=countryNames[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
