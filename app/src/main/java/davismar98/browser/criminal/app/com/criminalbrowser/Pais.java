package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 5/11/2016.
 */

public class Pais implements Serializable{

    @SerializedName("id")
    int id;
    @SerializedName("pais")
    String pais;

    @Override
    public String toString() {
        return pais;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
