package davismar98.browser.criminal.app.com.criminalbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class Start extends AppCompatActivity {

    Button btnAdmin, btnEmpezar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        btnEmpezar= (Button) findViewById(R.id.btnEmpezar);
        btnEmpezar.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(Start.this, selectType.class);
                startActivity(myIntent);
            }
        });

        btnAdmin=(Button) findViewById(R.id.btnAdmin);
        btnAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(Start.this, LoginActivity.class);
                startActivity(in);
            }
        });
    }

}
