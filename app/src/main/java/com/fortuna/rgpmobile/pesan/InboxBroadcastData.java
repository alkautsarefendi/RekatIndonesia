package com.fortuna.rgpmobile.pesan;

public class InboxBroadcastData {

    private String ID_RELAWAN;
    private String TGL_BROADCAST;
    private String ID_BROADCAST;
    private String JNS_BROADCAST;
    private String DESKRIPSI;
    private String TGL_EXPIRED;
    private String DATE_CREATE;
    private String USERID_CREATE;

    public InboxBroadcastData(String ID_RELAWAN, String TGL_BROADCAST, String ID_BROADCAST, String JNS_BROADCAST,
                            String DESKRIPSI, String TGL_EXPIRED, String DATE_CREATE, String USERID_CREATE) {
        this.ID_RELAWAN = ID_RELAWAN;
        this.TGL_BROADCAST = TGL_BROADCAST;
        this.ID_BROADCAST = ID_BROADCAST;
        this.JNS_BROADCAST = JNS_BROADCAST;
        this.DESKRIPSI = DESKRIPSI;
        this.TGL_EXPIRED = TGL_EXPIRED;
        this.DATE_CREATE = DATE_CREATE;
        this.USERID_CREATE = USERID_CREATE;
    }


    public String getID_RELAWAN() {
        return ID_RELAWAN;
    }

    public void setID_RELAWAN(String ID_RELAWAN) {
        this.ID_RELAWAN = ID_RELAWAN;
    }

    public String getTGL_BROADCAST() {
        return TGL_BROADCAST;
    }

    public void setTGL_BROADCAST(String TGL_BROADCAST) {
        this.TGL_BROADCAST = TGL_BROADCAST;
    }

    public String getID_BROADCAST() {
        return ID_BROADCAST;
    }

    public void setID_BROADCAST(String ID_BROADCAST) {
        this.ID_BROADCAST = ID_BROADCAST;
    }

    public String getJNS_BROADCAST() {
        return JNS_BROADCAST;
    }

    public void setJNS_BROADCAST(String JNS_BROADCAST) {
        this.JNS_BROADCAST = JNS_BROADCAST;
    }

    public String getDESKRIPSI() {
        return DESKRIPSI;
    }

    public void setDESKRIPSI(String DESKRIPSI) {
        this.DESKRIPSI = DESKRIPSI;
    }

    public String getTGL_EXPIRED() {
        return TGL_EXPIRED;
    }

    public void setTGL_EXPIRED(String TGL_EXPIRED) {
        this.TGL_EXPIRED = TGL_EXPIRED;
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
