package com.example.aplikasimahasiswa;

public class Mahasiswa {

    private String nim;
    private String nama;
    private String alamat;
    private String kelamin;
    private String imageUrl;
    public Mahasiswa(){};

    public Mahasiswa(String nim, String nama, String alamat, String kelamin, String imageUrl) {
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.kelamin = kelamin;
        this.imageUrl = imageUrl;
    }

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKelamin() {
        return kelamin;
    }

    public void setKelamin(String kelamin) {
        this.kelamin = kelamin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
