package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 8/11/2016.
 */

public class Visita implements Serializable {

    @SerializedName("id")
    public int id;
    @SerializedName("fecha")
    public String fecha;
    @SerializedName("hora")
    public String hora;
    @SerializedName("pais")
    public String pais;
    @SerializedName("encID")
    public String encID;
    @SerializedName("encNombre")
    public String encNombre;
    @SerializedName("encRegistro")
    public String encRegistro;
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

    @Override
    public String toString() {
        return "Visita{" +
                "id=" + id +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", encID='" + encID + '\'' +
                ", pais='" + pais + '\'' +
                ", encNombre='" + encNombre + '\'' +
                ", encRegistro='" + encRegistro + '\'' +
                ", idUsuario=" + idUsuario +
                ", usuarioN='" + usuarioN + '\'' +
                ", usuarioEmail='" + usuarioEmail + '\'' +
                ", usuarioTel='" + usuarioTel + '\'' +
                ", usuarioID='" + usuarioID + '\'' +
                '}';
    }

    public String getEncID() {
        return encID;
    }

    public void setEncID(String encID) {
        this.encID = encID;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
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

    public String getEncNombre() {
        return encNombre;
    }

    public void setEncNombre(String encNombre) {
        this.encNombre = encNombre;
    }

    public String getEncRegistro() {
        return encRegistro;
    }

    public void setEncRegistro(String encRegistro) {
        this.encRegistro = encRegistro;
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

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEncarcelado() {
        return encID;
    }

    public void setEncarcelado(String encarcelado) {
        this.encID = encarcelado;
    }

}
