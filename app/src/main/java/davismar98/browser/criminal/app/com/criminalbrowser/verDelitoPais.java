package davismar98.browser.criminal.app.com.criminalbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;

public class verDelitoPais extends AppCompatActivity implements AsyncResponse {
    ArrayList<DelitoPais> listDP;
    ListView lvDP;
    AdapterDP adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_delito_pais);

        lvDP = (ListView) findViewById(R.id.lvDP);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(verDelitoPais.this, this);
        taskRead.execute("http://www.criminalbrowser.com/CriminalBrowser/delitosPais.php");
    }

    @Override
    public void processFinish(String s) {
        try {
            listDP = new JsonConverter<DelitoPais>().toArrayList(s, DelitoPais.class);
            adapter = new AdapterDP(this, listDP);
            lvDP.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(verDelitoPais.this, listDP.size(), Toast.LENGTH_SHORT).show();
        }
    }
}
