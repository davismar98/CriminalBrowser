package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 8/11/2016.
 */

public class Denuncia implements Serializable {
    @SerializedName("id")
    public int id;
    @SerializedName("fecha")
    public String fecha;
    @SerializedName("pais")
    public String pais;
    @SerializedName("BusNombre")
    public String BusNombre;
    @SerializedName("idUsuario")
    public int idUsuario;
    @SerializedName("usuarioN")
    public String usuarioN;
    @SerializedName("usuarioEmail")
    public String usuarioEmail;
    @SerializedName("usuarioTel")
    public String usuarioTel;
    @SerializedName("usuarioID")
    public String usuarioID;
    @SerializedName("comentario")
    public  String comentario;

    public String getComentario() {
        return comentario;
    }

    public void setComentaio(String comentaio) {
        this.comentario = comentaio;
    }

    @Override
    public String toString() {
        return "Denuncia{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", pais='" + pais + '\'' +
                ", BusNombre='" + BusNombre + '\'' +
                ", idUsuario=" + idUsuario +
                ", usuarioN='" + usuarioN + '\'' +
                ", usuarioEmail='" + usuarioEmail + '\'' +
                ", usuarioTel='" + usuarioTel + '\'' +
                ", usuarioID='" + usuarioID + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getBusNombre() {
        return BusNombre;
    }

    public void setBusNombre(String busNombre) {
        BusNombre = busNombre;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuarioN() {
        return usuarioN;
    }

    public void setUsuarioN(String usuarioN) {
        this.usuarioN = usuarioN;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getUsuarioTel() {
        return usuarioTel;
    }

    public void setUsuarioTel(String usuarioTel) {
        this.usuarioTel = usuarioTel;
    }

    public String getUsuarioID() {
        return usuarioID;
    }

    public void setUsuarioID(String usuarioID) {
        this.usuarioID = usuarioID;
    }
}
