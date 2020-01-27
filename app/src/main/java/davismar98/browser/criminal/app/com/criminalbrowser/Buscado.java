package davismar98.browser.criminal.app.com.criminalbrowser;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by DavidM on 22/10/2016.
 */

public class Buscado implements Serializable{

    @SerializedName("idDelincuente")
    public int idDelincuente;
    @SerializedName("idBuscado")
    public int idBuscado;
    @SerializedName("nombre")
    public String nombre;
    @SerializedName("apellido")
    public String apellido;
    @SerializedName("edad")
    public String edad;
    @SerializedName("fechaN")
    public String fechaN;
    public String genero;
    @SerializedName("idGenero")
    public int idGenero;
    @SerializedName("pais")
    public String pais;
    @SerializedName("idPais")
    public int idPais;
    @SerializedName("foto")
    public String foto;
    @SerializedName("recompensa")
    public int recompensa;
    @SerializedName("idPerfil")
    public int idPerfil;
    @SerializedName("estatura")
    public int estatura;
    @SerializedName("raza")
    public String raza;
    @SerializedName("cabello")
    public String cabello;
    @SerializedName("ojos")
    public String ojos;
    @SerializedName("alias")
    public String alias;
    @SerializedName("peculiaridad")
    public String peculiaridad;

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public int getIdGenero() {
        return idGenero;
    }

    public void setIdGenero(int idGenero) {
        this.idGenero = idGenero;
    }

    public String getFechaN() {
        return fechaN;
    }

    public void setFechaN(String fechaN) {
        this.fechaN = fechaN;
    }

    public int getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(int idPerfil) {
        this.idPerfil = idPerfil;
    }

    public int getIdDelincuente() {
        return idDelincuente;
    }

    public void setIdDelincuente(int idDelincuente) {
        this.idDelincuente = idDelincuente;
    }

    public int getIdBuscado() {
        return idBuscado;
    }

    public void setIdBuscado(int idBuscado) {
        this.idBuscado = idBuscado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public int getRecompensa() {
        return recompensa;
    }

    public void setRecompensa(int recompensa) {
        this.recompensa = recompensa;
    }

    public int getEstatura() {
        return estatura;
    }

    public void setEstatura(int estatura) {
        this.estatura = estatura;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getCabello() {
        return cabello;
    }

    public void setCabello(String cabello) {
        this.cabello = cabello;
    }

    public String getOjos() {
        return ojos;
    }

    public void setOjos(String ojos) {
        this.ojos = ojos;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getPeculiaridad() {
        return peculiaridad;
    }

    public void setPeculiaridad(String peculiaridad) {
        this.peculiaridad = peculiaridad;
    }

    @Override
    public String toString() {
        return "Buscado{" +
                "idDelincuente=" + idDelincuente +
                ", idBuscado=" + idBuscado +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", edad='" + edad + '\'' +
                ", genero='" + genero + '\'' +
                ", pais='" + pais + '\'' +
                ", foto='" + foto + '\'' +
                ", recompensa=" + recompensa +
                ", idPerfil=" + idPerfil +
                ", estatura=" + estatura +
                ", raza='" + raza + '\'' +
                ", cabello='" + cabello + '\'' +
                ", ojos='" + ojos + '\'' +
                ", alias='" + alias + '\'' +
                ", peculiaridad='" + peculiaridad + '\'' +
                '}';
    }
}
