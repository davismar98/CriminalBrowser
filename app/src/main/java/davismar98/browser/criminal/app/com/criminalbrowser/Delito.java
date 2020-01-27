package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 5/11/2016.
 */

public class Delito implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("delito")
    public String delito;

    @Override
    public String toString() {
        return delito;
    }

    public String getDelito() {
        return delito;
    }

    public void setDelito(String delito) {
        this.delito = delito;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
