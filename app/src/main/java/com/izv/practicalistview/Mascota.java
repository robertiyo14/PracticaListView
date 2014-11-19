package com.izv.practicalistview;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by rober on 12/10/2014.
 */
public class Mascota implements Comparable<Mascota>, Parcelable,Serializable {

    private String nombre;
    private String especie;
    private String raza;
    private String biografia;


    public Mascota(String nombre, String especie, String raza) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
    }

    public Mascota() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                '}';
    }

    @Override
    public int compareTo(Mascota mascota) {
        if(this.getNombre().compareTo(mascota.getNombre())!=0)
            return this.getNombre().compareTo(mascota.getNombre());
        if(this.getEspecie().compareTo(mascota.getEspecie())!=0)
            return this.getEspecie().compareTo(mascota.getEspecie());
        if(this.getRaza().compareTo(mascota.getRaza())!=0)
            return this.getRaza().compareTo(mascota.getRaza());
        return 0;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.nombre);
        parcel.writeString(this.especie);
        parcel.writeString(this.raza);
        parcel.writeString(this.biografia);
    }

    public static final Parcelable.Creator<Mascota> CREATOR = new Parcelable.Creator<Mascota>() {
        @Override
        public Mascota createFromParcel(Parcel p) {
            String nombre = p.readString();
            String especie = p.readString();
            String raza = p.readString();
            String biografia = p.readString();
            Mascota m = new Mascota(nombre, raza,especie);
            m.setBiografia(biografia);
            return m;
            //return new Contacto(p);
        }

        @Override
        public Mascota[] newArray(int i) {
            return new Mascota[i];
        }
    };

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        Mascota m = (Mascota) o;

        if (!nombre.equals(m.nombre)) return false;
        if (!especie.equals(m.especie)) return false;
        if (!raza.equals(m.raza)) return false;
        return true;
    }
}
