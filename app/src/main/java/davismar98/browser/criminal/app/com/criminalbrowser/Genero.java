package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 5/11/2016.
 */

public class Genero implements Serializable{

    @SerializedName("id")
    int id;
    @SerializedName("genero")
    String genero;

    @Override
    public String toString() {
        return  genero;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
}
