package davismar98.browser.criminal.app.com.criminalbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

public class verBuscadosPais extends AppCompatActivity implements AsyncResponse {
    ArrayList<BuscadoPais> listBP;
    ListView lvBP;
    AdapterBP adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscados_pais);

        lvBP = (ListView) findViewById(R.id.lvBP);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(verBuscadosPais.this, this);
        taskRead.execute("http://www.criminalbrowser.com/CriminalBrowser/masBuscado.php");
    }

    @Override
    public void processFinish(String s) {
        try {
            listBP = new JsonConverter<BuscadoPais>().toArrayList(s, BuscadoPais.class);
            adapter = new AdapterBP(this, listBP);
            lvBP.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(verBuscadosPais.this, listBP.size(), Toast.LENGTH_SHORT).show();
        }
    }

}
