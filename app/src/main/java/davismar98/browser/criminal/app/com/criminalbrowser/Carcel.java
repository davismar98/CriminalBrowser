package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 5/11/2016.
 */

public class Carcel implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("carcel")
    public String carcel;
    @SerializedName("correo")
    public String correo;
    @SerializedName("direccion")
    public String direccion;
    @SerializedName("telefono")
    public String telefono;
    @SerializedName("pais")
    public String pais;
    @SerializedName("horaI")
    public String horaI;
    @SerializedName("horaF")
    public String horaF;
    @SerializedName("dia")
    public String dia;

    @Override
    public String toString() {
        return carcel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarcel() {
        return carcel;
    }

    public void setCarcel(String carcel) {
        this.carcel = carcel;
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

    public String getHoraI() {
        return horaI;
    }

    public void setHoraI(String horaI) {
        this.horaI = horaI;
    }

    public String getHoraF() {
        return horaF;
    }

    public void setHoraF(String horaF) {
        this.horaF = horaF;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }
}
