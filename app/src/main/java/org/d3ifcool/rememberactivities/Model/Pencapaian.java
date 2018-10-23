package org.d3ifcool.rememberactivities.Model;

import java.io.Serializable;

public class Pencapaian implements Serializable {
    String namaPencapaian,tgl_pencapain,catatanPencapaian,tempatPencapaian,keyPencapaian;
    double lat,lang;
    public Pencapaian(){}
    public Pencapaian(String namaPencapaian, String tgl_pencapain, String catatanPencapaian , double lat, double lang) {
        this.namaPencapaian = namaPencapaian;
        this.tgl_pencapain = tgl_pencapain;
        this.catatanPencapaian = catatanPencapaian;
        this.lat = lat;
        this.lang = lang;
    }

    public String getNamaPencapaian() {
        return namaPencapaian;
    }

    public void setNamaPencapaian(String namaPencapaian) {
        this.namaPencapaian = namaPencapaian;
    }

    public String getTgl_pencapain() {
        return tgl_pencapain;
    }

    public void setTgl_pencapain(String tgl_pencapain) {
        this.tgl_pencapain = tgl_pencapain;
    }

    public String getCatatanPencapaian() {
        return catatanPencapaian;
    }

    public void setCatatanPencapaian(String catatanPencapaian) {
        this.catatanPencapaian = catatanPencapaian;
    }

    public String getTempatPencapaian() {
        return tempatPencapaian;
    }

    public void setTempatPencapaian(String tempatPencapaian) {
        this.tempatPencapaian = tempatPencapaian;
    }

    public String getKeyPencapaian() {
        return keyPencapaian;
    }

    public void setKeyPencapaian(String keyPencapaian) {
        this.keyPencapaian = keyPencapaian;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLang() {
        return lang;
    }

    public void setLang(double lang) {
        this.lang = lang;
    }

    @Override
    public String toString() {
        return "Pencapaian{" +
                "namaPencapaian='" + namaPencapaian + '\'' +
                ", tgl_pencapain='" + tgl_pencapain + '\'' +
                ", catatanPencapaian='" + catatanPencapaian + '\'' +
                ", tempatPencapaian='" + tempatPencapaian + '\'' +
                ", keyPencapaian='" + keyPencapaian + '\'' +
                ", lat=" + lat +
                ", lang=" + lang +
                '}';
    }
}
