package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 9/11/2016.
 */

public class DelitoPais implements Serializable{

    @SerializedName("pais")
    public String pais;
    @SerializedName("delito")
    public String delito;
    @SerializedName("coincidencias")
    public int coincidencias;

    @Override
    public String toString() {
        return "DelitoPais{" +
                "pais='" + pais + '\'' +
                ", delito='" + delito + '\'' +
                ", coincidencias=" + coincidencias +
                '}';
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDelito() {
        return delito;
    }

    public void setDelito(String delito) {
        this.delito = delito;
    }

    public int getCoincidencias() {
        return coincidencias;
    }

    public void setCoincidencias(int coincidencias) {
        this.coincidencias = coincidencias;
    }
}
