package com.example.aplikasimahasiswa;

public class Mahasiswa {

    private String nim;
    private String nama;
    private String alamat;
    private String kelamin;
    public Mahasiswa(){};

    public Mahasiswa(String nim, String nama, String alamat, String kelamin) {
        this.nim = nim;
        this.nama = nama;
        this.alamat = alamat;
        this.kelamin = kelamin;
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
}
