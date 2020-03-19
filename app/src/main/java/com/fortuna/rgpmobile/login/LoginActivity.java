package com.fortuna.rgpmobile.login;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.main.MainActivity;
import com.fortuna.rgpmobile.main.RPGValidator;
import com.fortuna.rgpmobile.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LoginActivity extends BaseActivity {

    Button login, register;
    EditText username;
    EditText password;

    public static final String LoginPref = "loginPrefs";
    public static final String ID_RELAWAN = "idRelawanKey";
    public static final String ID_RELAWAN_UPLINE = "idRelawanUplineKey";
    public static final String NM_REFF = "namaReffKey";
    public static final String KD_AREA_TUGAS = "kdAreaTugasKey";
    public static final String NAMA = "namaKey";
    public static final String NO_HP = "noHPKey";
    SharedPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_login);

        initComponent();
    }


    private void initComponent() {

        login = findViewById(R.id.btnLogin);
        username = findViewById(R.id.txtNoHP);
        password = findViewById(R.id.txtPassword);

        loginPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Date c = Calendar.getInstance().getTime();
                @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                String ts = df.format(c);

                if (ts.equals("2018-10-20")) {

                    new AlertDialog.Builder(LoginActivity.this)
                            .setIcon(R.mipmap.ic_launcher)
                            .setTitle(getString(R.string.versi_baru_tersedia))
                            .setMessage(getString(R.string.old_version))
                            .setPositiveButton("Update", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    String namaPack = getString(R.string.tentang_kita_playstore);

                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setData(Uri.parse("market://details?id=" + namaPack));
                                    startActivity(intent);

                                }
                            })
                            .setNegativeButton("Nanti", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();

                } else if (username.getText().toString().equals("")) {

                    Util.showmsg(LoginActivity.this, null, "Nomor HP Kosong");

                } else if (password.getText().toString().equals("")) {

                    Util.showmsg(LoginActivity.this, null, "Password Kosong");

                } else {

                    CheckingDataLogin();

                }

                /*startActivity(new Intent(LoginActivity.this, MainActivity.class));*/

            }
        });
    }

    private void soal3() {

        int a = 0;


    }

    private void CheckingDataLogin() {

        String no_hp = username.getText().toString();
        String pass_login = password.getText().toString();

        String url = "http://202.152.27.8:49183/rgp-0/ws/login/get";
        RPGValidator rpgv = new RPGValidator();
        JSONObject object = rpgv.checkLogin(url, no_hp, pass_login);

        if (object != null) {

            try {

                if ((object.has("CODE") && object.getString("CODE").equals("00"))) {

                    String notes = object.getString("DATA");
                    JSONArray ibBroadcast = new JSONArray(notes);

                    for (int i = 0; i < ibBroadcast.length(); i++) {

                        JSONObject catObj = (JSONObject) ibBroadcast.get(i);

                        SharedPreferences.Editor editor = loginPreferences.edit();
                        editor.putString("idRelawanKey", catObj.getString("id_relawan"));
                        editor.putString("idRelawanUplineKey", catObj.getString("id_relawan_upline"));
                        editor.putString("namaReffKey", catObj.getString("nm_reff"));
                        editor.putString("kdAreaTugasKey", catObj.getString("kd_area_tugas"));
                        editor.putString("namaKey", catObj.getString("nama"));
                        editor.putString("noHPKey", catObj.getString("no_hp"));
                        editor.apply();

                        /*String idrelawan = catObj.getString("ID_RELAWAN");
                        String nama = catObj.getString("NAMA");
                        String alamat = catObj.getString("ALAMAT");
                        String kdpos = catObj.getString("KD_POS");
                        String kdprop = catObj.getString("KD_PROP");
                        String kdkota = catObj.getString("KD_KOTAKAB");
                        String kdkec = catObj.getString("KD_KEC");
                        String kdkel = catObj.getString("KD_KEL");
                        String stsRelawanelawan = catObj.getString("STS_RELAWAN");
                        String kdAreaTugas = catObj.getString("KD_AREA_TUGAS");
                        String kdTps = catObj.getString("KD_TPS");
                        String noHp = catObj.getString("NO_HP");
                        String email = catObj.getString("EMAIL");
                        String nmReff = catObj.getString("NM_REFF");
                        String password = catObj.getString("PASSWORD");
                        String catatan = catObj.getString("CATATAN");
                        String nik = catObj.getString("NIK");
                        String imgKtp = catObj.getString("IMG_KTP");
                        String idRelawanUpline = catObj.getString("ID_RELAWAN_UPLINE");
                        String dateCreate = catObj.getString("DATE_CREATE");
                        String useridCreate = catObj.getString("USERID_CREATE");*/

                    }

                    startActivity(new Intent(LoginActivity.this, MainActivity.class));

                } else {

                    Util.showmsg(LoginActivity.this, null, "Nomor HP / Password Anda Salah");

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            runOnUiThread(new Runnable() {
                public void run() {

                    Util.showmsg(LoginActivity.this, null, "Gagal Terkoneksi ke Server");
                    finish();

                }
            });

        }

    }
}
