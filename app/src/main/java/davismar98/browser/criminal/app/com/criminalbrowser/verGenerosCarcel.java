package davismar98.browser.criminal.app.com.criminalbrowser;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.kosalgeek.android.json.JsonConverter;
import com.kosalgeek.genasync12.AsyncResponse;
import com.kosalgeek.genasync12.PostResponseAsyncTask;

import java.util.ArrayList;
import java.util.HashMap;

public class verGenerosCarcel extends AppCompatActivity implements AsyncResponse {
    ArrayList<GenerosCarcel> listGC;
    ListView lvGC;
    AdapterGenerosC adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_generos_carcel);


        lvGC = (ListView) findViewById(R.id.lvGC);
        PostResponseAsyncTask taskRead = new PostResponseAsyncTask(verGenerosCarcel.this, this);
        taskRead.execute("http://www.criminalbrowser.com/CriminalBrowser/generosCarcel.php");
    }

    @Override
    public void processFinish(String s) {
        try {
            listGC = new JsonConverter<GenerosCarcel>().toArrayList(s, GenerosCarcel.class);
            adapter = new AdapterGenerosC(this, listGC);
            lvGC.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(verGenerosCarcel.this, listGC.size(), Toast.LENGTH_SHORT).show();
        }
    }
}
