package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 8/11/2016.
 */

public class GenerosCarcel implements Serializable {

    @SerializedName("carcel")
    public String carcel;
    @SerializedName("hombres")
    public int hombres;
    @SerializedName("mujeres")
    public int mujeres;
    @SerializedName("indefinido")
    public int indefinido;
    @SerializedName("pais")
    public String pais;

    @Override
    public String toString() {
        return "GenerosCarcel{" +
                "carcel='" + carcel + '\'' +
                ", hombres=" + hombres +
                ", mujeres=" + mujeres +
                ", indefinido=" + indefinido +
                ", pais='" + pais + '\'' +
                '}';
    }

    public String getCarcel() {
        return carcel;
    }

    public void setCarcel(String carcel) {
        this.carcel = carcel;
    }

    public int getHombres() {
        return hombres;
    }

    public void setHombres(int hombres) {
        this.hombres = hombres;
    }

    public int getMujeres() {
        return mujeres;
    }

    public void setMujeres(int mujeres) {
        this.mujeres = mujeres;
    }

    public int getIndefinido() {
        return indefinido;
    }

    public void setIndefinido(int indefinido) {
        this.indefinido = indefinido;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
