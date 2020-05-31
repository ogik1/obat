package com.aa183.mahendra;

import java.util.Date;

public class Obat {
    private int idObat;
    private String namaObat;
    private Date tanggal;
    private String gambar;
    private String deskripsi;
    private String indikasi;
    private String pabrik;
    private String kemasan;
    private String link;

    public Obat(int idObat, String namaObat, Date tanggal, String gambar, String deskripsi, String indikasi, String pabrik, String kemasan, String link) {
        this.idObat = idObat;
        this.namaObat = namaObat;
        this.tanggal = tanggal;
        this.gambar = gambar;
        this.deskripsi = deskripsi;
        this.indikasi = indikasi;
        this.pabrik = pabrik;
        this.kemasan = kemasan;
        this.link = link;
    }

    public int getIdObat() {
        return idObat;
    }

    public void setIdObat(int idObat) {
        this.idObat = idObat;
    }

    public String getNamaObat() {
        return namaObat;
    }

    public void setNamaObat(String namaObat) {
        this.namaObat = namaObat;
    }

    public Date getTanggal() {
        return tanggal;
    }

    public void setTanggal(Date tanggal) {
        this.tanggal = tanggal;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getIndikasi() {
        return indikasi;
    }

    public void setIndikasi(String indikasi) {
        this.indikasi = indikasi;
    }

    public String getPabrik() {
        return pabrik;
    }

    public void setPabrik(String pabrik) {
        this.pabrik = pabrik;
    }

    public String getKemasan() {
        return kemasan;
    }

    public void setKemasan(String kemasan) {
        this.kemasan = kemasan;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
