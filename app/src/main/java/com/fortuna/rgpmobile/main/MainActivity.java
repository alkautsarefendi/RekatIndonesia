package com.fortuna.rgpmobile.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

import com.fortuna.rgpmobile.DSLActivity;
import com.fortuna.rgpmobile.R;
import com.fortuna.rgpmobile.animation.BaseActivity;
import com.fortuna.rgpmobile.animation.BaseActivityHome;
import com.fortuna.rgpmobile.berita.BeritaActivity;
import com.fortuna.rgpmobile.broadcast.BroadcastActivity;
import com.fortuna.rgpmobile.dashboard.DashboardActivity;
import com.fortuna.rgpmobile.hitung.HitungSuaraActivity;
import com.fortuna.rgpmobile.lapor.LaporActivity;
import com.fortuna.rgpmobile.pesan.PesanMasukActivity;
import com.fortuna.rgpmobile.relawan.DaftarKoordinatorActivity;
import com.fortuna.rgpmobile.tps.InputTPSActivity;

public class MainActivity extends BaseActivityHome {

    Toolbar toolbar_main;
    ImageButton daftarRelawan, laporUpline, broadcastDownline, inputTPS, dashboard, berita, hitungSuara, pesanMasuk, formulir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        setContentView(R.layout.activity_main);

        setupToolbar();
        initComponent();
    }


    @SuppressWarnings("ConstantConditions")
    private void setupToolbar() {

        toolbar_main = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar_main);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void initComponent() {

        daftarRelawan = findViewById(R.id.btnRelawan);
        daftarRelawan.setOnClickListener(new ButtonDaftarRelawanClick());
        laporUpline = findViewById(R.id.btnKejadian);
        laporUpline.setOnClickListener(new ButtonLaporUplineClick());
        broadcastDownline = findViewById(R.id.btnBroadcast);
        broadcastDownline.setOnClickListener(new ButtonBCDownlineClick());
        inputTPS = findViewById(R.id.btnHasilTPS);
        inputTPS.setOnClickListener(new ButtonInputTPSClick());
        dashboard = findViewById(R.id.btnDashboard);
        dashboard.setOnClickListener(new ButtonDashboardClick());
        berita = findViewById(R.id.btnBerita);
        berita.setOnClickListener(new ButtonBeritaClick());
        hitungSuara = findViewById(R.id.btnHitung);
        hitungSuara.setOnClickListener(new ButtonHitungSuaraClick());
        pesanMasuk = findViewById(R.id.btnPesanMasuk);
        pesanMasuk.setOnClickListener(new ButtonPesanMasukClick());
        /*formulir = findViewById(R.id.btnFormulir);
        formulir.setOnClickListener(new ButtonFormulir());*/

    }

    /*private class ButtonFormulir implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, DSLActivity.class));

        }
    }*/

    private class ButtonDaftarRelawanClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, DaftarKoordinatorActivity.class));

        }
    }

    private class ButtonLaporUplineClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, LaporActivity.class));

        }
    }

    private class ButtonBCDownlineClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, BroadcastActivity.class));

        }
    }

    private class ButtonInputTPSClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, InputTPSActivity.class));

        }
    }

    private class ButtonDashboardClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, DashboardActivity.class));

        }
    }

    private class ButtonBeritaClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, BeritaActivity.class));

        }
    }

    private class ButtonHitungSuaraClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, HitungSuaraActivity.class));

        }
    }

    private class ButtonPesanMasukClick implements View.OnClickListener {
        @Override
        public void onClick(View v) {

            startActivity(new Intent(MainActivity.this, PesanMasukActivity.class));

        }
    }


    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(this)
                .setMessage("Kembali ke Halaman Login ?")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("Tidak", null)
                .show();

    }

}
