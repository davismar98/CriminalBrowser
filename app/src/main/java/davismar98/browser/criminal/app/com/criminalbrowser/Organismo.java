package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 8/11/2016.
 */

public class Organismo implements Serializable {

    @SerializedName("idOrganismo")
    public int idOrganismo;
    @SerializedName("organismo")
    public String organismo;
    @SerializedName("correo")
    public String correo;
    @SerializedName("direccion")
    public String direccion;
    @SerializedName("telefono")
    public String telefono;
    @SerializedName("pais")
    public String pais;

    @Override
    public String toString() {
        return "Organismo{" +
                "idOrganismo=" + idOrganismo +
                ", organismo='" + organismo + '\'' +
                ", correo='" + correo + '\'' +
                ", direccion='" + direccion + '\'' +
                ", telefono='" + telefono + '\'' +
                ", pais='" + pais + '\'' +
                '}';
    }

    public int getIdOrganismo() {
        return idOrganismo;
    }

    public void setIdOrganismo(int idOrganismo) {
        this.idOrganismo = idOrganismo;
    }

    public String getOrganismo() {
        return organismo;
    }

    public void setOrganismo(String organismo) {
        this.organismo = organismo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }
}
