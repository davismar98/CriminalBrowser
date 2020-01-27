package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 8/11/2016.
 */

public class BuscadoPais implements Serializable{

    @SerializedName("pais")
    public String pais;
    @SerializedName("buscado")
    public String buscado;
    @SerializedName("recompensa")
    public int recompensa;
    @SerializedName("foto")
    public String foto;

    @Override
    public String toString() {
        return "BuscadoPais{" +
                "pais='" + pais + '\'' +
                ", buscado='" + buscado + '\'' +
                ", recompensa=" + recompensa +
                '}';
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getBuscado() {
        return buscado;
    }

    public void setBuscado(String buscado) {
        this.buscado = buscado;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }
}
