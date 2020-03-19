package com.fortuna.rgpmobile;

public class Penugasan {

    String kodePenugasan;
    String namaPenugasan;

    public Penugasan() {
    }

    public Penugasan(String kodePenugasan, String namaPenugasan){
        this.kodePenugasan = kodePenugasan;
        this.namaPenugasan = namaPenugasan;
    }

    public String getKodePenugasan() {
        return kodePenugasan;
    }

    public void setKodePenugasan(String kodePenugasan) {
        this.kodePenugasan = kodePenugasan;
    }

    public String getNamaPenugasan() {
        return namaPenugasan;
    }

    public void setNamaPenugasan(String namaPenugasan) {
        this.namaPenugasan = namaPenugasan;
    }
}
