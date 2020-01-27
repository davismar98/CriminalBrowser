package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 22/10/2016.
 */

public class Encarcelado implements Serializable{

    @SerializedName("idDelincuente")
    public int idDelincuente;
    @SerializedName("idEncarcelado")
    public int idEncarcelado;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("apellido")
    public String apellido;
    @SerializedName("edad")
    public String edad;
    @SerializedName("fechaN")
    public String fechaN;
    @SerializedName("genero")
    public String genero;
    @SerializedName("idGenero")
    public int idGenero;
    @SerializedName("pais")
    public String pais;
    @SerializedName("idPais")
    public int idPais;
    @SerializedName("foto")
    public String foto;
    @SerializedName("nRegistro")
    public String nRegistro;
    @SerializedName("condena")
    public int condena;
    @SerializedName("idCarcel")
    public int idCarcel;
    @SerializedName("carcel")
    public String carcel;
    @SerializedName("fechaIngreso")
    public String fechaIngreso;
    @SerializedName("nVisitas")
    public int nVisitas;

    public int getCondena() {
        return condena;
    }

    public void setCondena(int condena) {
        this.condena = condena;
    }

    public int getnVisitas() {
        return nVisitas;
    }

    public void setnVisitas(int nVisitas) {
        this.nVisitas = nVisitas;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getFechaN() {
        return fechaN;
    }

    public void setFechaN(String fechaN) {
        this.fechaN = fechaN;
    }

    public int getIdEncarcelado() {
        return idEncarcelado;
    }

    public void setIdEncarcelado(int idEncarcelado) {
        this.idEncarcelado = idEncarcelado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getIdDelincuente() {
        return idDelincuente;
    }

    public void setIdDelincuente(int idDelincuente) {
        this.idDelincuente = idDelincuente;
    }

    public String getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(String fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getCarcel() {
        return carcel;
    }

    public void setCarcel(String carcel) {
        this.carcel = carcel;
    }

    public int getIdCarcel() {
        return idCarcel;
    }

    public void setIdCarcel(int idCarcel) {
        this.idCarcel = idCarcel;
    }

    public String getnRegistro() {
        return nRegistro;
    }

    public void setnRegistro(String nRegistro) {
        this.nRegistro = nRegistro;
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

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    @Override
    public String toString() {
        return "Encarcelado{" +
                "idDelincuente=" + idDelincuente +
                ", idEncarcelado=" + idEncarcelado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad='" + edad + '\'' +
                ", genero='" + genero + '\'' +
                ", pais='" + pais + '\'' +
                ", foto='" + foto + '\'' +
                ", nRegistro='" + nRegistro + '\'' +
                ", condena='" + condena + '\'' +
                ", idCarcel=" + idCarcel +
                ", carcel='" + carcel + '\'' +
                ", fechaIngreso='" + fechaIngreso + '\'' +
                '}';
    }

}
