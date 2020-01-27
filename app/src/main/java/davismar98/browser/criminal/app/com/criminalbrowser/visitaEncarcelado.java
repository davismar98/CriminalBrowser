package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 7/11/2016.
 */

public class visitaEncarcelado implements Serializable {
    @SerializedName("idEncarcelado")
    public int idEncarcelado;
    @SerializedName("visitas")
    public int visitas;

    @Override
    public String toString() {
        return "visitaEncarcelado{" +
                "visitas=" + visitas +
                ", idEncarcelado=" + idEncarcelado +
                '}';
    }

    public int getIdEncarcelado() {
        return idEncarcelado;
    }

    public void setIdEncarcelado(int idEncarcelado) {
        this.idEncarcelado = idEncarcelado;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }
}
