package org.d3ifcool.rememberactivities.Model;

import java.io.Serializable;

/**
 * Created by Yoga Wahyu Yuwono on 08/10/2018.
 */

public class Kegiatan implements Serializable {

    private String namaKegiatan,tglKegiatan,jamKegiatan,berakhirKegiatan,tempatKegiatan,CatatanKegiatan,key,email;
    double lang,lat;

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Kegiatan() {
    }

    public String getNamaKegiatan() {
        return namaKegiatan;
    }

    public void setNamaKegiatan(String namaKegiatan) {
        this.namaKegiatan = namaKegiatan;
    }

    public String getTglKegiatan() {
        return tglKegiatan;
    }

    public void setTglKegiatan(String tglKegiatan) {
        this.tglKegiatan = tglKegiatan;
    }

    public String getJamKegiatan() {
        return jamKegiatan;
    }

    public void setJamKegiatan(String jamKegiatan) {
        this.jamKegiatan = jamKegiatan;
    }

    public String getBerakhirKegiatan() {
        return berakhirKegiatan;
    }

    public void setBerakhirKegiatan(String berakhirKegiatan) {
        this.berakhirKegiatan = berakhirKegiatan;
    }

    public String getTempatKegiatan() {
        return tempatKegiatan;
    }

    public void setTempatKegiatan(String tempatKegiatan) {
        this.tempatKegiatan = tempatKegiatan;
    }

    public String getCatatanKegiatan() {
        return CatatanKegiatan;
    }

    public void setCatatanKegiatan(String catatanKegiatan) {
        CatatanKegiatan = catatanKegiatan;
    }

    @Override
    public String toString() {
        return "" +
                namaKegiatan + '\'' +
                tglKegiatan + '\'' +
                jamKegiatan + '\'' +
                berakhirKegiatan + '\'' +
                tempatKegiatan + '\'' +
                CatatanKegiatan + '\'' +
                email + '\''
                ;
    }

    public Kegiatan(String namaKegiatan, String tglKegiatan, String jamKegiatan, String berakhirKegiatan, String tempatKegiatan,double langtitude,double latitude ,String catatanKegiatan, String email) {
        this.namaKegiatan = namaKegiatan;
        this.tglKegiatan = tglKegiatan;
        this.jamKegiatan = jamKegiatan;
        this.berakhirKegiatan = berakhirKegiatan;
        this.tempatKegiatan = tempatKegiatan;
        this.lang=langtitude;
        this.lat=latitude;
        CatatanKegiatan = catatanKegiatan;
        this.email = email;

    }
}
