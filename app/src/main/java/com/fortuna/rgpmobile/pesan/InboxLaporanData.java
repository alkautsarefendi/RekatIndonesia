package com.fortuna.rgpmobile.pesan;

public class InboxLaporanData {

    private String ID_RELAWAN;
    private String TGL_KEJADIAN;
    private String STS_LAPORAN;
    private String JUDUL;
    private String DESKRIPSI;
    private String ID_RELAWAN_TO;
    private String STS_REPLY;
    private String DATE_CREATE;
    private String USERID_CREATE;

    public InboxLaporanData(String ID_RELAWAN, String TGL_KEJADIAN, String STS_LAPORAN, String JUDUL,
                            String DESKRIPSI, String ID_RELAWAN_TO, String STS_REPLY, String DATE_CREATE,
                            String USERID_CREATE) {
        this.ID_RELAWAN = ID_RELAWAN;
        this.TGL_KEJADIAN = TGL_KEJADIAN;
        this.STS_LAPORAN = STS_LAPORAN;
        this.JUDUL = JUDUL;
        this.DESKRIPSI = DESKRIPSI;
        this.ID_RELAWAN_TO = ID_RELAWAN_TO;
        this.STS_REPLY = STS_REPLY;
        this.DATE_CREATE = DATE_CREATE;
        this.USERID_CREATE = USERID_CREATE;
    }


    public String getID_RELAWAN() {
        return ID_RELAWAN;
    }

    public void setID_RELAWAN(String ID_RELAWAN) {
        this.ID_RELAWAN = ID_RELAWAN;
    }

    public String getTGL_KEJADIAN() {
        return TGL_KEJADIAN;
    }

    public void setTGL_KEJADIAN(String TGL_KEJADIAN) {
        this.TGL_KEJADIAN = TGL_KEJADIAN;
    }

    public String getSTS_LAPORAN() {
        return STS_LAPORAN;
    }

    public void setSTS_LAPORAN(String STS_LAPORAN) {
        this.STS_LAPORAN = STS_LAPORAN;
    }

    public String getJUDUL() {
        return JUDUL;
    }

    public void setJUDUL(String JUDUL) {
        this.JUDUL = JUDUL;
    }

    public String getDESKRIPSI() {
        return DESKRIPSI;
    }

    public void setDESKRIPSI(String DESKRIPSI) {
        this.DESKRIPSI = DESKRIPSI;
    }

    public String getID_RELAWAN_TO() {
        return ID_RELAWAN_TO;
    }

    public void setID_RELAWAN_TO(String ID_RELAWAN_TO) {
        this.ID_RELAWAN_TO = ID_RELAWAN_TO;
    }

    public String getSTS_REPLY() {
        return STS_REPLY;
    }

    public void setSTS_REPLY(String STS_REPLY) {
        this.STS_REPLY = STS_REPLY;
    }

    public String getDATE_CREATE() {
        return DATE_CREATE;
    }

    public void setDATE_CREATE(String DATE_CREATE) {
        this.DATE_CREATE = DATE_CREATE;
    }

    public String getUSERID_CREATE() {
        return USERID_CREATE;
    }

    public void setUSERID_CREATE(String USERID_CREATE) {
        this.USERID_CREATE = USERID_CREATE;
    }
}
